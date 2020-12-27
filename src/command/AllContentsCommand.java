package command;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;

import action.ActionResult;
import service.ItemService;
import service.SaleService;
import service.ShopService;

public class AllContentsCommand extends Command {

	@Override
	public ActionResult execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		result.start(request, response);
				
		ShopService shopService = new ShopService();
		ItemService itemService = new ItemService();
		SaleService saleService = new SaleService();

		String[] TYPE_GNDR = {"M", "F"};
		String[] TYPE_PAGE = {"shop", "item", "sale"};
		String[] TYPE_SHOP = {"S", "B"};
		
		JSONArray array = new JSONArray();
		
		// 최악의 성능 N*N*N..... 
		for(int i = 0; i < TYPE_GNDR.length; i++) {
			for(int j = 0; j < TYPE_PAGE.length; j++) {
				for(int k = 0; k < TYPE_SHOP.length; k++) {
					switch(TYPE_PAGE[j]) {
					case "shop":
						array.add ( shopService.getShop(TYPE_PAGE[j], TYPE_GNDR[i], TYPE_SHOP[k], "100", "all", null) );
						break;
					case "item":
						array.add( itemService.getItem(TYPE_PAGE[j], TYPE_GNDR[i], TYPE_SHOP[k], "100", "all", null) );
						break;
					case "sale":
						array.add( saleService.getSale(TYPE_PAGE[j], TYPE_GNDR[i], TYPE_SHOP[k], null) );						
						break;
					}
				}
			}
		}

	
		response.setCharacterEncoding("UTF-8");
		
		PrintWriter out = response.getWriter();
		out.println(array.toJSONString());
		
		return null;
	}
}
