package controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import action.ActionResult;
import command.Command;
import command.SendVertifyEmailCommand;
import command.VertifyEmailCheckCommand;

@WebServlet("/vertify/*")
public class VertifyToken extends HttpServlet {
	private static final long serialVersionUID = 1L;


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doHandle(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doHandle(request, response);
	}
	
	@SuppressWarnings("unused")
	public void doHandle(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String pathInfo = request.getPathInfo();
		ActionResult result = null;
		String defaultPage = "/";
		
		Command command = null;
		
		switch(pathInfo) {
		case "/email.do":
			command = new SendVertifyEmailCommand();
			result = command.execute(request, response);
			break;
		case "/vertifyEmail.do":
			command = new VertifyEmailCheckCommand();
			result = command.execute(request, response);
			break;
		}
		
		if(result != null) {
			try {
				result.render(defaultPage);	
			} catch(Exception e) {
				e.printStackTrace();
			}
			
		}
		
	}
	

}
