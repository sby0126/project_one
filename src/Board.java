/**
 * @author Eo Jinseok
 * @descripption
 * This class allows user to see certain post after getting a data from database.
 */
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/board/*")
public class Board extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("application/x-www-form-urlencoded;charset=utf-8;");
		
		PrintWriter out = response.getWriter();
		
		String reqUrl = request.getRequestURI();
		String mappingUrl = request.getServletPath();
		String pathInfo = request.getPathInfo();
		
		String postNumber = pathInfo.substring(1);
		
		out.print("{\"pathInfo\": \"" + pathInfo + ",");
		out.print("\"content\": \"" + "Å×½ºÆ®" + "\",");
		out.print("\"postNumber\": \"" + postNumber + "\"}");
	}

}
