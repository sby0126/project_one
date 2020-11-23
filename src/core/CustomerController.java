package core;

import java.io.IOException;
import java.util.List;

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
			String id = request.getParameter("id");
			String hashedPassword = request.getParameter("password");
			String no = request.getParameter("no");
			String name = request.getParameter("name");
			String address = request.getParameter("address");
			String tel = request.getParameter("tel");
			String email = request.getParameter("email");
			String isAdmin = "N";
			String joinDate = request.getParameter("joinDate");
			
			CustomerVO c = new CustomerVO();
			
			c
				.setId(id)
				.setPassword(hashedPassword)
				.setNo(no)
				.setName(name)
				.setAddress(address)
				.setTel(tel)
				.setEmail(email)
				.setIsAdmin(isAdmin)
				.setJoinDate(joinDate);		
			
			customerDAO.addCustomer(c);
			nextPage = "/pages/members.jsp";
		} else {
			response.sendRedirect("/");
		}

		RequestDispatcher dispatcher = request.getRequestDispatcher(nextPage);
		dispatcher.forward(request, response);
	}

}
