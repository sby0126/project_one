package utils;

import java.io.File;
import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/myadmin/*")
public class AdminRouter extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public AdminRouter() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doHandle(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doHandle(request, response);
	}
	
	protected void doHandle(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String reqeustURI = request.getRequestURI();
		String contextPath = request.getContextPath();
		String command = reqeustURI.substring(contextPath.length());
		String path = request.getPathInfo();
		
		System.out.println(command);
		
		if (path.equals("/openFileBrowser.do")) {
			String filename = request.getParameter("filename");
	    	try {
				Runtime.getRuntime().exec("explorer.exe /select," + filename);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    	
	    	response.sendRedirect("/admin");
	    	
		} else if(path.equals("/fileDelete.do")) {
			String filename = request.getParameter("filename");
			
			try {
				File file = new File(filename);
				if(file.exists()) {
					file.delete();
				}					
			} catch(Exception e) {
				e.printStackTrace();
			}
			
			response.sendRedirect("/admin");
		}
	}

}
