package controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.CustomerDAO;

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
			String email = request.getParameter("email");
			String nextPage = null;
			
			// 임시 번호를 MD5로 발급합니다 (주석 처리)
			// byte[] bytesOfMessage = id.getBytes("UTF-8");
			// byte[] thedigest = md5Chipper.digest(bytesOfMessage);
			
			// 임시 번호를 발급합니다.
			String thedigest = String.valueOf( (int)(Math.floor(10000 + Math.random() * 30000)) ); 
			
			// DB 상의 ID와 이메일이 일치하는 지 확인
			boolean isValid = CustomerDAO.getInstance().checkWithIdAndEmail(id, email);
			
			if(isValid) {
				// 키를 보냅니다.
				request.setAttribute("key", thedigest.toString());
				nextPage = "/password/sendMail.do";
			} else {
				// 수동으로 오류 처리 (순서 중요 응답이 먼저, 그 다음 프린트라이터)
				response.setContentType("text/html; charset=UTF-8");
				response.setCharacterEncoding("UTF-8");
				
				PrintWriter out = response.getWriter();
				out.println("<html><head>");
				out.println("<meta charset=\"UTF-8\">");
				out.println("<script charset='utf-8'>alert('존재하지 않는 아이디 또는 이메일입니다');");
				out.println("location.href='" + request.getContextPath() + "/pages/find_password.jsp" +"'");
				out.println("</script></head></html>");
				return;
			}
			
			// 포워드 처리
			RequestDispatcher dispatcher = request.getRequestDispatcher(nextPage);
			dispatcher.forward(request, response);			
						
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}

}
