package command.customer;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import action.ActionResult;
import command.Command;
import dao.CustomerDAO;
import vo.CustomerVO;

/**
 * 멤버 조회
 */
public class ListMembersCommand extends Command {
	
	@Override
	public ActionResult execute(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		result.start(request, response);
		
		// 인스턴스를 가져옵니다.
		CustomerDAO customerDAO = CustomerDAO.getInstance();
		
		// 멤버 리스트를 가져옵니다.
		List<CustomerVO> customerList = customerDAO.listMembers();  
		request.setAttribute("customerList", customerList);		
		
		// 경로를 설정합니다.
		result.setPath("/pages/members.jsp");
		PrintWriter out = response.getWriter();
		
		for(CustomerVO vo : customerList) {
			out.println(vo.getId());
		}
		
		// 리다이렉션할 페이지를 매개변수로부터 가져와 설정합니다.
		if(request.getParameter("nextPage") != null) {
			result.setPath(request.getParameter("nextPage"));
		}		
		
		return result;
	}
}
