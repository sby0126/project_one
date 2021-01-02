package command.payments;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import action.ActionResult;
import command.Command;
import service.OrderService;
import vo.OrderVO;

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
		
		List<OrderVO> orderList = new ArrayList<>();
		
		// 오더 서비스 생성
		OrderService orderService = new OrderService();		
		orderList = (List<OrderVO>)request.getSession().getAttribute("orderList");
		
		orderList.forEach(order -> {
			orderService.processOrder(order);
		});
		
		PrintWriter out = response.getWriter();
		out.println("<script>");
		out.println("alert('구매가 완료되었습니다.');");
		out.println("location.href='" + request.getContextPath() + "/index.jsp" +"'");
		out.println("</script>");
		
		return null;
	}
	
}
