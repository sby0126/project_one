package controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import action.ActionResult;
import command.Command;
import command.payments.PaymentCheckCommand;
import command.payments.PaymentFailCommand;
import command.payments.PaymentRequestCommand;
import command.payments.PaymentSuccessCommand;

/**
 * Servlet implementation class PaymentsController
 */
@WebServlet("/payments/*")
public class PaymentsController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doHandle(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doHandle(request, response);
	}
	
	public void doHandle(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String pathInfo = request.getPathInfo();
		Command command = null;
		ActionResult result = null;
		String defaultPage = "/";
		
		try {
			switch(pathInfo) {
			case "/request.do":
				command = new PaymentRequestCommand();
				break;
			case "/check.do":
				command = new PaymentCheckCommand();
				break;
			case "/success.do":
				command = new PaymentSuccessCommand();
				break;
			case "/fail.do":
				command = new PaymentFailCommand();
				break;
			}			
			
			result = command.execute(request, response);
			
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
