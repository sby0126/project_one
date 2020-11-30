package core.board.qna;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class FileDownloadCommand {
	
	private BoardDAO boardMgr;
	private String filename;
	private boolean isReady = false;
	
	public FileDownloadCommand(BoardDAO boardDAO, String filename)  {
		this.boardMgr = boardDAO;
		this.filename = filename;
		this.isReady = true;
	}
	
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException  {
		
	}
	
}
