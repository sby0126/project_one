package controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import service.SendMailService;
import vo.MailHandler;

/**
 * 메일을 전송하는 기능을 수행합니다.
 * 
 * 키를 발급하고 세션을 생성하여 세션에 값이 있으면 인증된 것을 보고 있습니다. 
 *
 * 하지만 실제 이메일 인증은 세션 없이 액세스 토큰을 발급하는 OAuth를 사용합니다.
 * OAuth는 메일로 인증 정보가 담긴 Access Token(JSON WEB TOKEN)을 이를 클릭하면 인증된 것으로 보는 기술입니다.
 * 
 * Access Token은 JSON으로 되어있으며 BASE64와 SHA-256으로 인코딩하여 주소에 JSON을 자체를 보냅니다. 
 * 
 */
@WebServlet("/password/sendMail.do")
public class MailController extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doHandle(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doHandle(request, response);
	}
	
	protected void doHandle(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		SendMailService mailService = new SendMailService(request.getServletContext().getRealPath("mail.properties"));
		
		String id = request.getParameter("id");
		String email = request.getParameter("email");
		
		String key = (String)request.getAttribute("key");
		
		String body = String.join(
				"<div>인증 번호는</div><br>",
				"<div><p><b>" + key.toString() + "</p></b></div><br>",
				"<div>입니다.</div><br>"
				);
		
		try {
			MailHandler handler = new MailHandler(email, id + "님의 인증 번호입니다", body);
			mailService.sendMail(handler);
			
			HttpSession session = request.getSession();
			
			// 메일 발송에 성공하였으므로 인증 키를 세션에 저장합니다.
			session.setAttribute("key", key);
			
			// 아이디를 세션에 저장합니다.
			session.setAttribute("tempId", id);
			
			response.sendRedirect("/pages/find_password_ok.jsp");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
}
