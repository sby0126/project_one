package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.security.MessageDigest;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;

@WebServlet("/password/findPassword.do")
public class FindPasswordController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doHandle(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doHandle(request, response);
	}
	
	protected void doHandle(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {	
			// MessageDigest md5Chipper = MessageDigest.getInstance("md5");
			
			String id = request.getParameter("id");
			
			// byte[] bytesOfMessage = id.getBytes("UTF-8");
			// byte[] thedigest = md5Chipper.digest(bytesOfMessage);
			
			String thedigest = String.valueOf( (int)(Math.floor(10000 + Math.random() * 30000)) ); 
			
			System.out.println("아이디는 " + id);
			System.out.println("이메일은 " + request.getParameter("email"));
			
			// DB 상의 ID와 이메일이 일치하는 지 확인
			
			
			// 키를 보냅니다.
			request.setAttribute("key", thedigest.toString());
			
			RequestDispatcher dispatcher = request.getRequestDispatcher("/password/sendMail.do");
			dispatcher.forward(request, response);
			
		} catch(Exception e) {
			
		}
		
	}

}
