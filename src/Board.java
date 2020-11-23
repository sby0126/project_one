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

import org.json.simple.JSONObject;


@WebServlet("/board/*")
public class Board extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final boolean isDownloaded = false;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		
		if(isDownloaded) {
			response.setContentType("application/x-www-form-urlencoded;charset=utf-8;");	
		} else {
			response.setContentType("application/json;charset=utf-8;");	
		}
		
		PrintWriter out = response.getWriter();
		
		String reqUrl = request.getRequestURI();
		String mappingUrl = request.getServletPath();
		
		String pathInfo = request.getPathInfo();
		
		// Extract the post number from path info.
		String postNumber = pathInfo.substring(1);
		
		JSONObject json = new JSONObject();
		json.put("pathInfo", pathInfo);
		json.put("content", "뭐지");
		json.put("postNumber", postNumber);
		
		// Print out a json text to response header.
		out.println(json.toJSONString());
	}

}
