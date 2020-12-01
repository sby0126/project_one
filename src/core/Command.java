package core;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Command {
	
	private CustomerDAO customerMgr;
	
	public Command(CustomerDAO dao) {
		this.customerMgr = dao;
	}
	
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}
}
