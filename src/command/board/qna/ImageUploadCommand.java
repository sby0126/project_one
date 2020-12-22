package command.board.qna;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;
import com.oreilly.servlet.multipart.FileRenamePolicy;

import action.ActionResult;
import command.Command;

public class ImageUploadCommand extends Command {
	
	private String filename;
	private boolean isReady = false;
	
	public ImageUploadCommand(String filename)  {
		super();
		this.filename = filename;
	}
	
	public ActionResult execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException  {
		
		result.start(request, response);
		
		int maxSize = 1024 * 1024 * 5;
		int filesize = 0;
		String encType = "EUC-KR";
		String filename = null;
        String saveFolder = "/uploads";
        int fileSize = 5 * 1024 * 1024;
        ServletContext context = request.getServletContext();
        String realFolder = context.getRealPath(saveFolder);		
		
		int contentLength = Integer.parseInt(request.getHeader("Content-Length"));
		System.out.println(contentLength);
		
		FileRenamePolicy policy = new DefaultFileRenamePolicy(); 
		
		// request 객체를 전송하여 파일을 서버로 업로드 한다.
		MultipartRequest multi = new MultipartRequest(
				request, 
				realFolder, 
				fileSize,
				"UTF-8",
				policy);
				
		response.setContentType("application/json");
		response.setCharacterEncoding("EUC-KR");
		
		PrintWriter out = response.getWriter();
		
		JSONObject json = new JSONObject();
		json.put("url", multi.getOriginalFileName("image"));
		
		out.println(json.toJSONString());
		
		return null;
		
	}
	
}
