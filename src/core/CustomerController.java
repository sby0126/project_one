package core;

import java.io.IOException;
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

@WebServlet("/members/*")
public class CustomerController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private CustomerDAO customerDAO;
       
	@Override
	public void init() throws ServletException {
		customerDAO = new CustomerDAO();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doHandle(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doHandle(request, response);
	}
	
	private void doHandle(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html; utf-8");
		
		String act = request.getPathInfo();
		String nextPage = null;
		
		System.out.println(act);
		
		if(act == null || act.equals("/members.do")) {
			
			List<CustomerVO> customerList = customerDAO.listMembers();  
			request.setAttribute("customerList", customerList);		
			nextPage = "/pages/members.jsp";
		} else if(act.equals("/login.do")) { 
			
			// 로그인 처리
			
			System.out.println("login.do 가 실행되었습니다");
			
			String id = request.getParameter("id");
			String pw = request.getParameter("pw");
			
			boolean isValidLogin = customerDAO.processLogin(id, pw);
			
			// 로그인 성공 처리
			if(isValidLogin) {
				HttpSession session  = request.getSession();
				session.setAttribute("id", id);
				response.sendRedirect("/");
				return;				
			} else {
				request.setAttribute("errorMessage", "[Error 4] 아이디 또는 비밀번호가 틀렸습니다.");
				request.setAttribute("url", "/");
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
			
//			// 비밀번호 유효성 검사
//			if(!isValidPassword(password)) {
//				// 회원 가입 실패 처리
//				request.setAttribute("errorMessage", "[Error 2] 특수 문자나 영어 및 대 소문자 섞어야 합니다");
//				request.setAttribute("url", "/pages/join.jsp");
//				nextPage = "/pages/error.jsp";
//				isValid = false;
//			}
			
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

		RequestDispatcher dispatcher = request.getRequestDispatcher(nextPage);
		dispatcher.forward(request, response);
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
