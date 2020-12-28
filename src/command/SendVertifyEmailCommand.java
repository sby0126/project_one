package command;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;

import action.ActionResult;
import dao.CustomerDAO;
import service.SendMailService;
import utils.JWTUtils;
import vo.MailHandler;

public class SendVertifyEmailCommand extends Command {
	@SuppressWarnings("unchecked")
	@Override
	public ActionResult execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		result.start(request, response);
		
		String id = request.getParameter("id");
		String email = request.getParameter("email");
		
		boolean isValid = CustomerDAO
							.getInstance()
							.checkWithIdAndEmail(id, email);
		
		if(isValid) {
			JSONObject data = new JSONObject();
			data.put("id", id);
			data.put("email", email);
			
			// 액세스 토큰 생성 (만료 시간 2분)
			String token = JWTUtils.createToken(data, JWTUtils.MINUTE * 2L);
			
			// 서비스 객체 생성
			SendMailService mailService = new SendMailService(request.getServletContext().getRealPath("/mail.properties"));
			
			String tokenURL = "http:/localhost:9988/vertify/vertifyEmail.do?accessToken=" + token;
			
			String body = "<div>"
					+ "<p>본인임을 확인하려면 아래 링크를 클릭해주십시오</p>"
					+ String.format("<a href='%s'>%s</a>", tokenURL, tokenURL)
					+ "</div>";
			
			// 메시지 핸들러 생성
			MailHandler handler = new MailHandler(email, id + "님 본인 확인 이메일입니다.", body);
			
			try {
				mailService.sendMail(handler);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			// 수동으로 오류 처리
			request.setCharacterEncoding("UTF-8");
			response.setContentType("text/html; charset=utf-8");
			
			PrintWriter out = response.getWriter();
			out.println("<html><head>");
			out.println("<meta charset=\"UTF-8\">");
			out.println("<script charset='utf-8'>alert('가입할 때 작성하신 메일로 인증 확인용 이메일을 보냈습니다.');");
			out.println("location.href='" + request.getContextPath() + "/index.jsp" +"'");
			out.println("</script></head></html>");

			result = null;	
			
		} else {
			// 수동으로 오류 처리
			request.setCharacterEncoding("UTF-8");
			response.setContentType("text/html; charset=utf-8");
			
			PrintWriter out = response.getWriter();
			out.println("<html><head>");
			out.println("<meta charset=\"UTF-8\">");
			out.println("<script charset='utf-8'>alert('존재하지 않는 아이디 또는 이메일입니다');");
			out.println("location.href='" + request.getContextPath() + "/pages/find_password.jsp" +"'");
			out.println("</script></head></html>");

			result = null;	
		}
		
		return result;
	}
}
