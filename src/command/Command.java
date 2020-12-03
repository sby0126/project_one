package command;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import action.ActionResult;
import dao.BoardDAO;

public class Command {
	
	ActionResult result;
	
	public Command()  {
		result = new ActionResult();
	}
		
	public BoardDAO getDAO() {
		return BoardDAO.getInstance();
	}
	
	public ActionResult execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException  {
		return null;
	}
}
