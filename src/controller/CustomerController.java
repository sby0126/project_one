package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.CustomerDAO;
import vo.CustomerVO;

@WebServlet("/members/*")
public class CustomerController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private CustomerDAO customerDAO;
       
	@Override
	public void init() throws ServletException {
		customerDAO = CustomerDAO.getInstance();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doHandle(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doHandle(request, response);
	}
	
	public String generateState()
	{
	    SecureRandom random = new SecureRandom();
	    return new BigInteger(130, random).toString(32);
	}	
	
	private void doHandle(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html; utf-8");
		
		String act = request.getPathInfo();
		String nextPage = null;
		
		if(act == null || act.equals("/members.do")) {
			
			List<CustomerVO> customerList = customerDAO.listMembers();  
			request.setAttribute("customerList", customerList);		
			
			nextPage = "/pages/members.jsp";
			PrintWriter out = response.getWriter();
			
			for(CustomerVO vo : customerList) {
				out.println(vo.getId());
			}
			
			// 리다이렉션할 페이지를 매개변수로부터 가져와 설정합니다.
			if(request.getParameter("nextPage") != null) {
				nextPage = request.getParameter("nextPage");
			}
		} else if(act.equals("/modifyMemberForm.do")) {
			
			HttpSession session = request.getSession();
			String id = request.getParameter("id");
			String sid = (String)session.getAttribute("id");
			
			if(id == null) {
				response.sendRedirect("/");
				return;			
			}
			
			if(sid.equals(id) || sid.equals("admin")) {
				CustomerVO member = customerDAO.getMember(id);
				System.out.println(member.getId());
				request.setAttribute("member", member);
				
				nextPage = "/pages/modifyMemberForm.jsp";				
			} else {
				nextPage = "/pages/join.jsp";
			}
			
		} else if(act.equals("/modifyMember.do")) { 
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
			
			if(customerDAO.checkWithIdAndEmail(id,  email)) {
				request.setAttribute("errorMessage", "아이디와 이메일이 이미 존재합니다.");
				request.setAttribute("url", "/");
				nextPage = "/pages/error.jsp";
				
				RequestDispatcher dispatcher = request.getRequestDispatcher(nextPage);
				dispatcher.forward(request, response);
				
				return;
			}
			
			vo.setEmail(email);
			vo.setPassword(pw);
			vo.setName(name);
			vo.setTel(tel);
			vo.setZipCode(zipcode);
			vo.setAddress(address);
			
			if(customerDAO.updateCustomer(vo)) {
				response.sendRedirect("/");
				return;
			} 
			
			nextPage = "/";
			
			
		}  else if(act.equals("/login.do")) { 
			
			// 로그인 처리
			
			System.out.println("login.do 가 실행되었습니다");
			
			String id = request.getParameter("id");
			String pw = request.getParameter("pw");
			
			boolean isValidLogin = customerDAO.processLogin(id, pw);
			
			// 로그인 성공 처리
			if(isValidLogin) {
				
				// 최근 로그인 시간 업데이트
				customerDAO.updateLastLogin(id);
				
				HttpSession session  = request.getSession();
				session.setAttribute("id", id);
				response.sendRedirect("/index.jsp");
				return;				
			} else {
				request.setAttribute("errorMessage", "[Error 4] 아이디 또는 비밀번호가 틀렸습니다.");
				request.setAttribute("url", "/index.jsp");
				nextPage = "/pages/error.jsp";
			}
			
		} else if(act.equals("/logout.do")) { 
			// 로그아웃 처리
			
			HttpSession session  = request.getSession();
			session.invalidate();
			
			response.sendRedirect("/");	
			return;
			
		} else if(act.equals("/signUp.do")) { 
			
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
				nextPage = "/pages/error.jsp";
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
				 .setJoinDate(joinDate);
				
				c.setZipCode(zipcode);

				customerDAO.addCustomer(c);
				response.sendRedirect("/");
				return;				
			} else {
				request.setAttribute("errorMessage", "[Error 3] 이미 존재하는 아이디입니다. 다른 아이디로 사용해주세요.");
				request.setAttribute("url", "/pages/join.jsp");
				nextPage = "/pages/error.jsp";
			}
		} else if(act.equals("/naverLogin.do")) { 
			
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
			if(customerDAO.isInvalidID(id)) {
				request.getSession().setAttribute("id", id);
				
				response.sendRedirect(request.getContextPath() + "/index.jsp");
				return;
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
				 .setJoinDate(joinDate);
				
				c.setZipCode(zipcode);

				customerDAO.addCustomer(c);
				response.sendRedirect("/");
				return;				
			} else {
				request.setAttribute("errorMessage", "[Error 3] 이미 존재하는 아이디입니다. 다른 아이디로 사용해주세요.");
				request.setAttribute("url", "/pages/join.jsp");
				nextPage = "/pages/error.jsp";
			}
		
		} else {
			response.sendRedirect("/");
		}

		if(nextPage != null) {
			RequestDispatcher dispatcher = request.getRequestDispatcher(nextPage);
			if(dispatcher != null) {
				dispatcher.forward(request, response);
			}			
		}
		
	}

	/**
	 * 하나 이상의 알파벳을 포함해야 함
	 * 하나 이상의 숫자를 포함해야 함
	 * 하나 이상의 특수문자를 포함해야 함
	 * 최소 8글자 이상 입력해야 함
	 * 
	 * @link https://minwoohi.tistory.com/98
	 * @param password
	 * @return
	 */
	public boolean isValidPassword(String password) {
		
		String passwordPolicy = "((?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[^a-zA-Z0-9가-힣]).{8,})";
		
		Pattern pattern = Pattern.compile(passwordPolicy);
		Matcher matcher = pattern.matcher(password);
		return matcher.matches();
	}	
		

}
