package service;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.setContentType("text/html; charset=utf-8");
	    String id = request.getParameter("id");
	    String pw = request.getParameter("pw"); // crypto Cipher

	    HttpSession session = request.getSession();
	    
	    session.setAttribute("id", id);
	    
	    PrintWriter out = response.getWriter();
	    out.println("<html>");
	    out.println("<head></head>");
	    out.println("<body>");
	    out.println("<p>처리 완료</p>");
	    out.println("</body>");
	    out.println("</html>");
	    
	    response.sendRedirect("/");
	}

}
