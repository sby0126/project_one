package command.customer;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;

import action.ActionResult;
import command.Command;
import dao.CustomerDAO;
import manager.DataManager;

/**
 * 강제 탈퇴 기능입니다.
 *
 */
public class FoclySecessionMemberCommand extends Command {
	@SuppressWarnings("unchecked")
	@Override
	public ActionResult execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		result.start(request, response);
		
		CustomerDAO dao = CustomerDAO.getInstance();
		
		// 아이디 값을 가져옵니다.
		String id = (String)request.getParameter("id");
		
		JSONObject status = new JSONObject();
		
		HttpSession session = request.getSession();
		String adminId = (String)session.getAttribute("id");
		
		// 관리자가 아닌 경우를 체크합니다.
		if(!DataManager.getInstance().isSttaff(adminId)) {
			// 회원 가입 실패 처리
			result.sendError(401, "권한이 없습니다");
			return result;			
		}
		
		// 비밀번호 없이 관리자 모드에서 회원 탈퇴 처리를 합니다.
		if(dao.secessionCustomerForAdminMode(id)) {
			status.put("status", "success");
			
			response.setContentType("application/json; charset=utf-8");
			response.setCharacterEncoding("UTF-8");
			
			PrintWriter out = response.getWriter();
			out.println(status.toJSONString());
			
			return null;
			
		} else {
			// 회원 가입 실패 처리
			result.sendError(402, "글이 남아있는 상태입니다.");
			return result;
		}
	}
}
