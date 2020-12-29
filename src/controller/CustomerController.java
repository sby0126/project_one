package controller;

import java.io.IOException;
import java.math.BigInteger;
import java.security.SecureRandom;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import action.ActionResult;
import command.Command;
import command.customer.FindIdCommand;
import command.customer.FoclySecessionMemberCommand;
import command.customer.ListMembersCommand;
import command.customer.LoginCommand;
import command.customer.LogoutCommand;
import command.customer.ModifyMemberCommand;
import command.customer.ModifyMemberFormCommand;
import command.customer.SecessionMemberCommand;
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
			
			case "/members.do": // 멤버 리스트 조회
				command = new ListMembersCommand();
				break;
			case "/modifyMemberForm.do": // 회원 정보 수정 폼
				command = new ModifyMemberFormCommand();
				break;
			case "/modifyMember.do": // 회원 정보 수정
				command = new ModifyMemberCommand();
				break;
			case "/findId.do":
				command = new FindIdCommand();
				break;
			case "/secessionMember.do": // 자진 탈퇴
				command = new SecessionMemberCommand();
				break;
			case "/foclySecessionMember.do": // 강제 탈퇴
				command = new FoclySecessionMemberCommand();
				break;
			case "/login.do": // 로그인
				command = new LoginCommand();
				break;
			case "/logout.do": // 로그아웃
				command = new LogoutCommand();
				break;
			case "/signUp.do": // 회원 가입
				command = new SignUpCommand();
				break;
			case "/naverLogin.do": // 네이버 로그인
				command = new SocialLoginCommand("네이버");
				break;
			case "/kakaoLogin.do": // 카카오 로그인
				command = new SocialLoginCommand("카카오");
				break;
			case "/facebookLogin.do":
				command = new SocialLoginCommand("페북");
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

}
