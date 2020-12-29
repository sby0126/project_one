package command.customer;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import action.ActionResult;
import command.Command;
import dao.CustomerDAO;
import vo.CustomerVO;

/**
 * 회원 가입을 처리합니다.
 *
 */
public class SignUpCommand extends Command {
	@Override
	public ActionResult execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		result.start(request, response);
		
		request.setCharacterEncoding("UTF-8");
		
		CustomerDAO customerDAO = CustomerDAO.getInstance();
		
		// 회원 가입 폼에서 전달 받은 매개변수를 가져옵니다.
		String id = request.getParameter("id");
		String password = request.getParameter("pw");
		String name = request.getParameter("name");
		
		// 주소 
		StringBuffer buff = new StringBuffer();
		buff.append(request.getParameter("address1"));
		buff.append(' ');
		buff.append(request.getParameter("address2"));
		
		String address = buff.toString();
		
		String tel = request.getParameter("tel");
		String zipcode = request.getParameter("zipcode");
		
		// 이메일
		buff = new StringBuffer();
		buff.append(request.getParameter("email1"));
		buff.append("@");
		buff.append(request.getParameter("email2"));
		
		String email = buff.toString();
		
		String isAdmin = "N";
		
		// 오류로 인해 String 형식으로 변환하였음.
		String joinDate = request.getParameter("joinDate");
		
		boolean isValid = true;
		
		// ID 중복 여부 체크
		if(customerDAO.isInvalidID(id)) {
			// 회원 가입 실패 처리
			request.setAttribute("errorMessage", "[Error 1] 해당 아이디는 이미 사용 중입니다.");
			request.setAttribute("url", "/pages/join.jsp");
			result.forward("/pages/error.jsp");
			
			isValid = false;
		}
		
		if(isValid) {
			CustomerVO c = new CustomerVO();
			
			c.setId(id)
			 .setPassword(password)
			 .setName(name)
			 .setAddress(address)
			 .setTel(tel)
			 .setEmail(email)
			 .setIsAdmin(isAdmin)
			 .setJoinDate(joinDate)
			 .setCtmtype("NORMAL");
			
			c.setZipCode(zipcode);

			customerDAO.addCustomer(c);
							
			response.sendRedirect("/");
			
			return null;				
		} else {
			request.setAttribute("errorMessage", "이미 존재하는 아이디입니다. 다른 아이디로 사용해주세요.");
			request.setAttribute("url", "/pages/join.jsp");
			result.forward("/pages/error.jsp");
		}
		
		return result;
	}
}
