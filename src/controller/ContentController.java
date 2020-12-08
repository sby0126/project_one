package controller;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import action.ActionResult;
import command.Command;
import command.ItemCommand;
import command.SaleCommand;
import command.ShopCommand;

/**
 * Servlet implementation class ContentController
 */
@WebServlet("/contents/*")
public class ContentController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public void init(ServletConfig config) throws ServletException {
		
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doHandle(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		doHandle(request, response);
	}
	
	protected void doHandle(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String pathInfo = request.getPathInfo();
		Command command = null;
		ActionResult result = null;
		String defaultPage = "/";
		
		try {
			switch(pathInfo) {
			case "/shop.do":
				command = new ShopCommand();
				result = command.execute(request, response);
				break;
			case "/item.do":
				command = new ItemCommand();
				result = command.execute(request, response);				
				break;
			case "/sale.do":
				command = new SaleCommand();
				result = command.execute(request, response);
				break;
			}
		} catch(Exception e) {
			e.printStackTrace();
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
