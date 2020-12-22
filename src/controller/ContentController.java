package controller;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import action.ActionResult;
import command.AllContentsCommand;
import command.Command;
import command.ContentsSearchCommand;
import command.ItemCategoryCommand;
import command.ItemCommand;
import command.RecentlyShopListCommand;
import command.SaleCommand;
import command.SearchShopItemCommand;
import command.ShopCommand;
import command.DetailInputCommand;

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

			case "/pay.do":
			case "/cart.do":
				System.out.println("실행됨");
				command = new DetailInputCommand();

			case "/search.do":
				command = new ContentsSearchCommand();
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
