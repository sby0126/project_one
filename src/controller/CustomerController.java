package controller;

import java.io.IOException;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import action.ActionResult;
import command.Command;
import command.customer.ListMembersCommand;
import command.customer.LoginCommand;
import command.customer.LogoutCommand;
import command.customer.ModifyMemberCommand;
import command.customer.ModifyMemberFormCommand;
import command.customer.SignUpCommand;
import command.customer.SocialLoginCommand;
import dao.CustomerDAO;

@WebServlet("/members/*")
public class CustomerController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private CustomerDAO customerDAO;
       
	@Override
	public void init() throws ServletException {
		customerDAO = CustomerDAO.getInstance();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doProcess(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doProcess(request, response);
	}
	
	public String generateState()
	{
	    SecureRandom random = new SecureRandom();
	    return new BigInteger(130, random).toString(32);
	}	
	
	@SuppressWarnings("unused")
	protected void doProcess(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String pathInfo = request.getPathInfo();
		Command command = null;
		ActionResult result = null;
		String defaultPage = "/";	
		
		try {
			switch(pathInfo) {
			
			case "/members.do":
				command = new ListMembersCommand();
				break;
			case "/modifyMemberForm.do":
				command = new ModifyMemberFormCommand();
				break;
			case "/modifyMember.do":
				command = new ModifyMemberCommand();
				break;
			case "/login.do":
				command = new LoginCommand();
				break;
			case "/logout.do":
				command = new LogoutCommand();
				break;
			case "/signUp.do":
				command = new SignUpCommand();
				break;
			case "/naverLogin.do":
			case "/kakaoLogin.do":
				command = new SocialLoginCommand();
				break;
			}
			
			result = command.execute(request, response);
			
		} catch(Exception e) {
			e.printStackTrace();
		}

		
		if(result != null) {
			try {
				result.render(defaultPage);	
			} catch(Exception e) {
				e.printStackTrace();
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
