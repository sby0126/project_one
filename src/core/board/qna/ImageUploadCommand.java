package core.board.qna;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;
import com.oreilly.servlet.multipart.FileRenamePolicy;

public class ImageUploadCommand extends Command {
	
	private BoardDAO boardMgr;
	private String filename;
	private boolean isReady = false;
	
	public ImageUploadCommand(BoardDAO boardDAO, String filename)  {
		super(boardDAO);
		this.filename = filename;
	}
	
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException  {
		int maxSize = 1024 * 1024 * 5;
		int filesize = 0;
		String encType = "EUC-KR";
		String filename = null;
		
		int contentLength = Integer.parseInt(request.getHeader("Content-Length"));
		System.out.println(contentLength);
		
		FileRenamePolicy policy = new DefaultFileRenamePolicy(); 
		
		MultipartRequest multi = new MultipartRequest(request, filename, maxSize, policy);
		
		if(multi.getFilesystemName("filename") != null) {
			filename = multi.getFilesystemName("filename");
			filesize = (int)multi.getFile("filename").length();
		}
		
		// 파일 내용
		String content = multi.getParameter("content");
		
		
		
	}
	
}
