package controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.nio.file.Paths;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/download.do")
public class FileDownload extends HttpServlet {
	private static final long serialVersionUID = 1L;


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doHandle(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doHandle(request, response);
	}
	
	public void doHandle(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		
		String fileRepo = request.getServletContext().getRealPath("uploads");
		String fileName = (String)request.getParameter("filename");
		
		OutputStream out = response.getOutputStream();
		String downFile = Paths.get(fileRepo, fileName).toString();
		
		File f = new File(downFile);
		
		if(!f.exists()) {
			PrintWriter errorOut = response.getWriter();
			request.setCharacterEncoding("UTF-8");
			errorOut.println("<meta http-equiv='Content-Type' content='text/html; charset=utf-8' />");
			errorOut.println("<script charset='utf-8'>alert('파일이 존재하지 않습니다.');");
			errorOut.println("history.go(-1);");
			errorOut.println("</script>");
			return;
		}
		
		response.setHeader("Cache-Control", "no-cache");
		response.addHeader("Content-disposition", "attachment; fileName=" + fileName);
		
		FileInputStream in = new FileInputStream(f);
		
		// 8KB
		byte[] buffer = new byte[1024 * 8];
		while(true) {
			int count = in.read(buffer);
			
			if(count == -1) {
				break;
			}
			
			out.write(buffer, 0, count);
		}
		
		in.close();
		out.close();
	}

}
