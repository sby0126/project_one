package core.board.qna.command;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import core.board.qna.BoardDAO;

public class Command {
	protected BoardDAO boardMgr;
	private boolean isReady = false;
	
	public Command(BoardDAO boardDAO)  {
		this.boardMgr = boardDAO;
		this.isReady = true;
	}
	
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException  {
		
	}
}