package controller;

import java.io.IOException;

import javax.servlet.AsyncContext;
import javax.servlet.AsyncEvent;
import javax.servlet.AsyncListener;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import action.ActionResult;
import command.Command;
import command.payments.PaymentCheckCommand;

/**
 * Servlet implementation class VertifyPaymentController
 */
@WebServlet(urlPatterns = {"/vertifyPayment"}, asyncSupported = true)
public class VertifyPaymentController extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doHandle(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doHandle(request, response);
	}
	
	public void doHandle(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		final AsyncContext context = request.startAsync(request, response);
		context.setTimeout(1000 * 6);
		context.addListener(new AsyncListener() {
			
			@Override
			public void onTimeout(AsyncEvent event) throws IOException {
				System.out.println("Timeout");
			}
			
			@Override
			public void onStartAsync(AsyncEvent event) throws IOException {

			}
			
			@Override
			public void onError(AsyncEvent event) throws IOException {
				System.out.println(event.getThrowable().getMessage());
			}
			
			@Override
			public void onComplete(AsyncEvent event) throws IOException {
				System.out.println("완료");
			}
		});
		
		context.start(() -> {
			Command command = new PaymentCheckCommand();
			ActionResult result = null;
			
			try {
				result = command.execute(request, response);
			} catch (ServletException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			if(result != null) {
				try {
					result.render("/");
				} catch(Exception e) {
					e.printStackTrace();
				}
			}
		});
		
		try {
			Thread.sleep(3000);
		} catch(InterruptedException e) {
			e.printStackTrace();
		}
		
		context.complete();
		
	}

}
