package command;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import action.ActionResult;
import dao.CustomerDAO;
import vo.CustomerVO;
import vo.OrderVO;

public class CartCommand extends Command {
	
	public JSONObject getJson(InputStream stream) {
		JSONObject data = null;
		
		if(stream != null) {
			try {
				BufferedReader reader = new BufferedReader(new InputStreamReader(stream, "UTF-8"));
				JSONParser parser = new JSONParser();	
				data = (JSONObject)parser.parse(reader);
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
		
		return data;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public ActionResult execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		result.start(request, response);
		
		request.setCharacterEncoding("UTF-8");
		
		// ID를 읽습니다.
		HttpSession session = request.getSession();
		
		String userId = (String)session.getAttribute("id");
		
		if(userId == null) {
			
			response.setCharacterEncoding("UTF-8");
			
			PrintWriter out = response.getWriter();
			out.println("<script>alert('로그인이 되어있지 않습니다.')</script>");
			out.println("location.href=" + request.getHeader("referrer"));
			
			return null;
		}
		
		// 매개변수를 읽습니다.
		// InputStream stream = request.getInputStream();
		JSONParser parser = new JSONParser();
		JSONObject data = null;
		try {
			data = (JSONObject)parser.parse( URLDecoder.decode( request.getParameter("data") ));		
		} catch(Exception e) {
			e.printStackTrace();
		}
				
		if(data == null) {
			System.out.println("JSON을 생성할 수 없습니다.");
			result.sendError(401, "");
			return result;
		}
		
		System.out.println(data.toJSONString());
		
		System.out.println("장바구니 처리 시작");
		
		// POST 방식으로 전달했을 때, JSON 데이터를 읽습니다.
		if(data != null) {
			
			// 실제 상품명
			String title = (String)data.get("title");
			// 상품 수량
			int amount = Integer.parseInt((String)data.get("amount"));
			// 상품 가격
			int price = Integer.parseInt(((String)data.get("price")).replaceAll(",", ""));
			// 상품 ID
			int productId = Integer.parseInt((String)data.get("productId"));
			
			String link = (String)data.get("link");
			
			int[] totalPrice = {0};
			
			System.out.println(title);
			System.out.println(amount);
			System.out.println(price);
			System.out.println(productId);
			
			String productName = "";
			List<String> productNameList = new ArrayList<>();
			
			// 색상&사이즈
			JSONArray options = (JSONArray)data.get("options");
			
			List<OrderVO> orderList = new ArrayList<>();
			
			final JSONObject ref = data;
						
			options.forEach(raw -> {
				JSONObject opt = (JSONObject)raw;
				
				// 색상&사이즈 약칭
				String label = (String)opt.get("label");
				
				// 수량
				int qty = Integer.parseInt((String)opt.get("qty"));

				
				for(int i = 0; i < qty; i++) {
					OrderVO order = new OrderVO();
					order.setProductName(title + "-" + label);
					order.setUserId(userId);
					order.setOrderAmount(qty);
					order.setPrice( qty * price );
					order.setProductId(productId);
					order.setLink(link);
					order.setRaw(ref);
					
					orderList.add(order);
					
					totalPrice[0] += qty * price;
					productNameList.add(order.getProductName());
					
				}
					
			});
			
			// 상품명1 + 상품명2
			productName = String.join(" + ", productNameList);
			
			CustomerDAO customerDAO = CustomerDAO.getInstance();
			CustomerVO userVO = customerDAO.getMember(userId);
					
			request.setAttribute("customer", userVO);
			request.setAttribute("orderList", orderList);
			
			session.setAttribute("cartList", orderList);
			
			request.setAttribute("price", totalPrice[0]);
			request.setAttribute("productName", productName);
			request.setAttribute("productId", productId);
			
			result.forward("/pages/basket-tunnel.jsp");
			
			return result;
		}
					
		return null;
	}
}
