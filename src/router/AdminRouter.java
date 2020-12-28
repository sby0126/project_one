package router;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

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
	
	public void deleteFile(String realFileName) {
		// 파일이 존재하는가?
		File file = new File(realFileName);
		if(file.exists()) {
			// 존재한다면 삭제 처리합니다.
			file.delete();
		}				
	}
	
	protected void doHandle(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String reqeustURI = request.getRequestURI();
		String contextPath = request.getContextPath();
		String command = reqeustURI.substring(contextPath.length());
		String path = request.getPathInfo();

		if (path.equals("/openFileBrowser.do")) {
			String platform = System.getProperty("os.name");		
			String filename = request.getParameter("filename");
			
			// 실행 환경이 윈도우즈 인가?
			if(platform.indexOf("Windows") >= 0) {
		    	try {
					Runtime.getRuntime().exec("explorer.exe /select," + filename);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}				
			}
			
	    	response.sendRedirect("/admin");
	    	
		} else if(path.equals("/fileDelete.do")) {
			String filename = request.getParameter("filename");
			
			try {
				String realFileName = request.getServletContext().getRealPath(filename);
				
				// 파일이 존재하는가?
				deleteFile(realFileName);				
			} catch(Exception e) {
				e.printStackTrace();
			}
			
			response.sendRedirect("/admin");
		} else if(path.equals("/multipleFileDelete.do")) {
			
			List<String> files = Arrays.asList(request.getParameterValues("file"));
			
			for(String s : files) {
				String realFileName = request.getServletContext().getRealPath(s);
				deleteFile(realFileName);		
			}
			
			response.sendRedirect("/admin");
		}
	}

}
