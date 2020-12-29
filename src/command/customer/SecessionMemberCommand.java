package command.customer;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import action.ActionResult;
import command.Command;
import dao.CustomerDAO;

/**
 * 자진 탈퇴 기능입니다.
 *
 */
public class SecessionMemberCommand extends Command {
	@Override
	public ActionResult execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		result.start(request, response);
		
		CustomerDAO dao = CustomerDAO.getInstance();
		
		HttpSession session = request.getSession();
		String id = (String)session.getAttribute("id");
				
		String password = request.getParameter("password");
		
		if(dao.secessionCustomer(id, password)) {
			// 로그 아웃 처리
			session.invalidate();
	
			response.sendRedirect("/");
			
		} else {
			// 회원 가입 실패 처리
			request.setAttribute("errorMessage", "비밀 번호가 맞지 않거나 남아있는 글이 있어 탈퇴에 실패했습니다.");
			request.setAttribute("url", "/members/modifyMemberForm.do?id=" + id);
			result.forward("/pages/error.jsp");
			return result;
		}
		
		return null;
	}
}
