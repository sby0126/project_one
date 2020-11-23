package manager;


import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "loadConfig", urlPatterns = { "/loadConfig" }, loadOnStartup = 1)
public class AppConfigServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	private ServletContext context;

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		
		context = config.getServletContext();
		manager.DataManager.getInstance().setMainApplication(context);
		System.out.println(manager.DataManager.getInstance().getApplicationPath());
		DataManager.getInstance().makeConfigFile();
		
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setCharacterEncoding("EUC-KR");
        PrintWriter out = response.getWriter();

        out.println("<html><body><div class='container'>");
        out.println("<h1>서버 파일 목록 출력</h1>");
        
        try (Stream<Path> paths = Files.walk(Paths.get(context.getRealPath(".")))) {
            paths
                .filter(Files::isRegularFile)
                .forEach(i -> {
                	out.print(i);
                	out.println("<br>");
                });
        } 
        
        out.println("</div></body></html>");

	}

}
