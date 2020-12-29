package command.customer;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import action.ActionResult;
import command.Command;
import service.FindIdService;

public class FindIdCommand extends Command {
	
	@Override
	public ActionResult execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		result.start(request, response);
		
		request.setCharacterEncoding("UTF-8");
		String name = request.getParameter("name");
		String email = request.getParameter("email");
		
//		System.out.println(name);
//		System.out.println(email);
		
		FindIdService service = new FindIdService();
		String id = service.getId(name, email);
		
//		System.out.println("찾아낸 ID는 " + id + "입니다");
		
		if(id != null) {
			
			request.setAttribute("id", id);
			request.setAttribute("name", name);
			
			result.forward("/pages/find_id_ok.jsp");
		} else {
			request.setAttribute("error", true);
			
			result.forward("/pages/find_id_ok.jsp");
		}
		
		return result;
	}
	
}
