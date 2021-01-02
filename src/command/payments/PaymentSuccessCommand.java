package command.payments;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import action.ActionResult;
import command.Command;

/**
 * 결제에 성공했을 때 메시지를 띄우는 기능입니다.
 */
public class PaymentSuccessCommand extends Command {
	
	@Override
	public ActionResult execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		result.start(request, response);
		
		response.setContentType("text/html; charset=utf-8");
		response.setCharacterEncoding("UTF-8");
				
		PrintWriter out = response.getWriter();
		out.println("<script>");
		out.println("alert('구매가 완료되었습니다.');");
		out.println("location.href='" + request.getContextPath() + "/index.jsp" +"'");
		out.println("</script>");
		
		return null;
	}
	
}
