package command;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;

import action.ActionResult;
import dao.CustomerDAO;
import service.SendMailService;
import utils.JWTUtils;

public class VertifyEmailCheckCommand extends Command {
	@Override
	public ActionResult execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		result.start(request, response);
		
		String accessToken = request.getParameter("accessToken");
		
		JSONObject data = JWTUtils.verifyJWT(accessToken);
		
		if(data != null) {
			// 인증 성공
			
			String id = (String)data.get("id");
			String email = (String)data.get("email");
			
			// 임시 비밀번호 발급
			String fakePassword = SendMailService.getRandomPassword(id + email);

			// 비밀 번호 변경 처리
			CustomerDAO cust = CustomerDAO.getInstance();
			boolean isChangedOK = cust.changePassword(id, fakePassword);

			// 비밀번호 변경 완료
			if(isChangedOK) {
				HttpSession session = request.getSession();
				session.setAttribute("tempPassword", fakePassword);
				
				result.setPath("/pages/vertifyEmailSuccess.jsp");				
			} else {
				response.setContentType("text/html; charset=UTF-8");
				response.setCharacterEncoding("UTF-8");
				PrintWriter out = response.getWriter();
				
				out.println("<html><head>");
				out.println("<meta charset=\"UTF-8\">");
				out.println("<script charset='utf-8'>alert('DB에 연결되지 않아 비밀번호 변경에 실패하였습니다.');");
				out.println("location.href='" + request.getContextPath() + "/pages/find_password.jsp" +"'");
				out.println("</script></head></html>");
				
				result = null;
			}
						
		} else {
			// 만료
			response.setContentType("text/html; charset=UTF-8");
			response.setCharacterEncoding("UTF-8");
			
			PrintWriter out = response.getWriter();
			out.println("<html><head>");
			out.println("<meta charset=\"UTF-8\">");
			out.println("<script charset='utf-8'>alert('인증에 실패하였습니다. 이미 만료된 토큰입니다.');");
			out.println("location.href='" + request.getContextPath() + "/pages/find_password.jsp" +"'");
			out.println("</script></head></html>");
			
			result = null;
		}
		
		return result;
	}
}
