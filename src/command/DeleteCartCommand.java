package command;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import action.ActionResult;
import vo.OrderVO;

public class DeleteCartCommand extends Command {

	@Override
	public ActionResult execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		result.start(request, response);
		
		HttpSession session = request.getSession();
		
		// String to int 변환
		String[] ids = request.getParameterValues("idList");
		List<Integer> list = Arrays.asList(ids)
					.stream()
					.mapToInt(Integer::parseInt)
					.boxed()
					.collect(Collectors.toList());
		
		System.out.println(ids);
		
		List<OrderVO> cartList = (List<OrderVO>)session.getAttribute("cartList");
		
		if(cartList != null && !cartList.isEmpty()) {
			cartList.forEach(cart -> {				
				int index = cartList.indexOf(cart);
				
				if(list.contains(index)) {
					cartList.remove(cart);
					System.out.println("장바구니에서 " + cart + "를 제거했습니다.");
				}
						
			});
		}
		
		session.setAttribute("cartList", cartList);
		
		PrintWriter out = response.getWriter();
		out.println("<script>");
		out.println("location.href = '/pages/basket-tunnel.jsp");
		out.println("</script>");
		
		return null;
	}
	
}
