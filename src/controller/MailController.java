package controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import service.SendMailService;
import vo.MailHandler;

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
			
			session.setAttribute("key", key);
			request.setAttribute("tempId", id);
			
			response.sendRedirect("/pages/find_password_ok.jsp");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
}
