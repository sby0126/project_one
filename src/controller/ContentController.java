package controller;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import action.ActionResult;
import command.AddInterestCommand;
import command.AddMyShopCommand;
import command.AllContentsCommand;
import command.CartCommand;
import command.Command;
import command.ContentsSearchCommand;
import command.DeleteAllMyShopCommand;
import command.DeleteCartCommand;
import command.DeleteInterestCommand;
import command.DeleteMyShopCommand;
import command.InterestListCommand;
import command.ItemCategoryCommand;
import command.ItemCommand;
import command.ItemUploaderCommand;
import command.PaymentOrderCommand;
import command.RecentlyItemsCommand;
import command.RecentlyShopListCommand;
import command.SaleCommand;
import command.SearchShopItemCommand;
import command.ShopCommand;

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
				break;
			case "/item.do":
				command = new ItemCommand();			
				break;
			case "/sale.do":
				command = new SaleCommand();
				break;
			default:
			case "/all.do":
				command = new AllContentsCommand();
				break;
			case "/item_category.do":
				command = new ItemCategoryCommand();
				break;
			case "/searchShopItem.do":
				command = new SearchShopItemCommand();
				break;
			case "/recentlyShopList.do":
				command = new RecentlyShopListCommand();
				break;
			case "/addInterest.do":
				command = new AddInterestCommand();
				break;
			case "/deleteInterest.do":
				command = new DeleteInterestCommand();
				break;
			case "/getInterest.do":
				command = new InterestListCommand();
				break;
			case "/recentlyItems.do":
				command = new RecentlyItemsCommand();
				break;
			case "/pay.do":
				command = new PaymentOrderCommand();
				break;
			case "/cart.do":
				command = new CartCommand();
				break;
			case "/deleteCart.do":
				command = new DeleteCartCommand();
				break;
			case "/search.do":
				command = new ContentsSearchCommand();
				break;
			case "/addMyShop.do":
				command = new AddMyShopCommand();
				break;
			case "/deleteMyShop.do":
				command = new DeleteMyShopCommand();
				break;
			case "/deleteAllMyShop.do":
				command = new DeleteAllMyShopCommand();
				break;
			case "/itemUploader.do":
				command = new ItemUploaderCommand();
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
