package core.board.qna;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;
import com.oreilly.servlet.multipart.FileRenamePolicy;

public class FileDownloadCommand extends Command {
	
	private BoardDAO boardMgr;
	private String filename;
	private boolean isReady = false;
	
	public FileDownloadCommand(BoardDAO boardDAO, String filename)  {
		super(boardDAO);
		this.filename = filename;
	}
	
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException  {
		int maxSize = 0;
		FileRenamePolicy policy = new DefaultFileRenamePolicy(); 
		
		MultipartRequest multi = new MultipartRequest(request, filename, maxSize, policy);
		
	}
	
}
