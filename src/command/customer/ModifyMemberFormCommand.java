package command.customer;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import action.ActionResult;
import command.Command;
import dao.CustomerDAO;
import vo.CustomerVO;

public class ModifyMemberFormCommand extends Command {
	@Override
	public ActionResult execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		result.start(request, response);
		
		HttpSession session = request.getSession();
		String id = request.getParameter("id");
		String sid = (String)session.getAttribute("id");
		
		if(id == null) {
			response.sendRedirect("/");
			return null;			
		}
		
		if(sid.equals(id) || sid.equals("admin")) {
			CustomerVO member = CustomerDAO.getInstance().getMember(id);
			request.setAttribute("member", member);
			
			result.forward("/pages/modifyMemberForm.jsp");			
		} else {
			result.forward("/pages/join.jsp");
		}		
		
		return result;
		
	}
}
