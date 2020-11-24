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

@WebServlet("/members/*")
public class CustomerController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private CustomerDAO customerDAO;
       
	@Override
	public void init() throws ServletException {
		customerDAO = new CustomerDAO();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doHandle(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doHandle(request, response);
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
			
		} else if(act.equals("/signUp.do")) {
			
			// 이걸 줄이려면 마이바티스라는 프레임워크를 사용해야 한다.
			String id = request.getParameter("id");
			String password = request.getParameter("password");
			String no = request.getParameter("no");
			String name = request.getParameter("name");
			String address = request.getParameter("address");
			String tel = request.getParameter("tel");
			String email = request.getParameter("email");
			String isAdmin = "N";
			String joinDate = request.getParameter("joinDate");
			
			boolean isValid = true;
			
			// ID 중복 여부 체크
			if(customerDAO.isUniqueMember(id)) {
				// 회원 가입 실패 처리
				request.setAttribute("errorMessage", "해당 아이디는 이미 사용 중입니다.");
				request.setAttribute("url", "/pages/join.jsp");
				nextPage = "/pages/error.jsp";
				isValid = false;
			}
			
			// 비밀번호 유효성 검사
			if(!isValidPassword(password)) {
				// 회원 가입 실패 처리
				request.setAttribute("errorMessage", "특수 문자나 영어 및 대 소문자 섞어야 합니다");
				request.setAttribute("url", "/pages/join.jsp");
				nextPage = "/pages/error.jsp";
				isValid = false;
			}
			
			// 이메일 중복 여부 체크
			
			
			if(isValid) {
				CustomerVO c = new CustomerVO();
				
				c
					.setId(id)
					.setPassword(password)
					.setNo(no)
					.setName(name)
					.setAddress(address)
					.setTel(tel)
					.setEmail(email)
					.setIsAdmin(isAdmin)
					.setJoinDate(joinDate);		
				
				customerDAO.addCustomer(c);
				nextPage = "/pages/members.jsp"; // 회원 가입 환영 또는 최신 멤버 목록으로 이동 				
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
