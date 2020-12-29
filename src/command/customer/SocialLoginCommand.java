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
 * 카카오 및 네이버 로그인을 처리합니다.
 * 
 * ! 페이스북 로그인은 https 환경 도입 문제로 동작하지 않습니다.
 *
 */
public class SocialLoginCommand extends Command {
	
	private String socialType;
	
	public SocialLoginCommand(String socialType) {
		this.socialType = socialType;
	}
	
	@Override
	public ActionResult execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		result.start(request, response);
		
		CustomerDAO customerDAO = CustomerDAO.getInstance();
		
		response.setCharacterEncoding("UTF-8");
		
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
		
		// ID 중복 여부 체크 후 로그인 처리
		if(isValid && customerDAO.isInvalidID(id)) {
			request.getSession().setAttribute("id", id);
			
			response.sendRedirect(request.getContextPath() + "/index.jsp");
			return null;
		}			
		
		if(customerDAO.checkEmail(email)) {
			request.setAttribute("errorMessage", "이미 등록된 이메일입니다. 다른 이메일로 가입해주시기 바랍니다.");
			request.setAttribute("url", "/pages/join.jsp");
			result.forward("/pages/error.jsp");
			
			return result;
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
			 .setCtmtype(socialType);
			
			c.setZipCode(zipcode);
			
			customerDAO.addCustomer(c);
			
			request.getSession().setAttribute("id", id);
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
