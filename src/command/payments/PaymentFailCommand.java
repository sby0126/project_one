package command.payments;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import action.ActionResult;
import command.Command;

/**
 * 결제 실패 처리입니다.
 */
public class PaymentFailCommand extends Command {
	
	@Override
	public ActionResult execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		result.start(request, response);
		
		String referer = request.getHeader("referer");
		
		response.setContentType("text/html; charset=utf-8");
		response.setCharacterEncoding("UTF-8");
		
		PrintWriter out = response.getWriter();
		out.println("<script>");
		out.println("alert('결제에 실패하였습니다');");
		out.println("location.href = '" +  "/index.jsp" + "'");
		out.println("</script>");
		
		return null;
	}
	
}
