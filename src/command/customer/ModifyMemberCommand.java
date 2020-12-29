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

/**
 * 회원 정보를 수정하는 기능입니다.
 * 
 * 관리자도 다른 사람의 회원 정보를 수정할 수 있습니다.
 *
 */
public class ModifyMemberCommand extends Command {
	@Override
	public ActionResult execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		result.start(request, response);
		
		CustomerDAO customerDAO = CustomerDAO.getInstance(); 
		
		// 회원 정보 수정
		HttpSession session = request.getSession();
		
		String id = (String)session.getAttribute("id");
		String email = request.getParameter("email");
		String pw = request.getParameter("pw");
		String name = request.getParameter("name");
		String tel = request.getParameter("tel");
		String zipcode = request.getParameter("zipcode");
		String address = request.getParameter("address");
		
		CustomerVO vo = customerDAO.getMember(id);
		
		if(!customerDAO.isInvalidID(id)) {
			request.setAttribute("errorMessage", "아이디가 존재하지 않습니다.");
			request.setAttribute("url", "/");
			
			result.forward("/pages/error.jsp");
			return result;
		}
		
		// vo.setEmail(email); (이메일 변경 불가능)
		vo.setPassword(pw);
		vo.setName(name);
		vo.setTel(tel);
		vo.setZipCode(zipcode);
		vo.setAddress(address);
		
		if(customerDAO.updateCustomer(vo)) {
			response.sendRedirect(request.getContextPath() + "/index.jsp");
			return null;
		} 
		
		result.forward("/");
		
		return result;
	}
}
