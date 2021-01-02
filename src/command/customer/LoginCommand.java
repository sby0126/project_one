package command.customer;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import action.ActionResult;
import command.Command;
import dao.CustomerDAO;
import utils.AdminUtil;

/**
 * 일반 로그인을 구현합니다.
 */
public class LoginCommand extends Command {
	@Override
	public ActionResult execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		result.start(request, response);
		
		CustomerDAO customerDAO = CustomerDAO.getInstance();
		
		// 로그인 처리		
		String id = request.getParameter("id");
		String pw = request.getParameter("pw");
		
		// XSS 공격 대응
		if(id != null && pw != null) {
			id = id.replaceAll("<", "&lt;");
			pw = pw.replaceAll(">", "&gt;");
		}		
		
		boolean isValidLogin = customerDAO.processLogin(id, pw);
		
		if(isValidLogin) {
			AdminUtil.getInstance().loggingIP(id, request.getRemoteAddr());
		}
		
		String referer = request.getHeader("referer");
		
		if(customerDAO.isSNSMember(id)) {
			request.setAttribute("errorMessage", "소셜 네트워크(네이버/카카오) 멤버 전용 로그인을 이용하세요.");
			request.setAttribute("url", referer);
			
			result.forward("/pages/error.jsp");
			isValidLogin = false;
		}
		
		// 로그인 성공 처리
		if(isValidLogin) {
			
			// 최근 로그인 시간 업데이트
			customerDAO.updateLastLogin(id);
			
			HttpSession session  = request.getSession();
			session.setAttribute("id", id);

			response.sendRedirect(referer);					
			
			return null;				
		} else {
			request.setAttribute("errorMessage", "아이디 또는 비밀번호가 틀렸습니다.");
			request.setAttribute("url", referer);
			result.forward("/pages/error.jsp");
		}
		
		return result;
		
	}
}
