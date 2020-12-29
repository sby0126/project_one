package command.customer;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import action.ActionResult;
import command.Command;
import utils.AdminUtil;

/**
 * 로그 아웃 기능입니다.
 *
 */
public class LogoutCommand extends Command {
	
	@Override
	public ActionResult execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		result.start(request, response);
		
		// 로그아웃 처리
		HttpSession session  = request.getSession();
		String id = (String)session.getAttribute("id");
		AdminUtil.getInstance().eraseIPFromLog(id);
		
		session.invalidate();
		
		String referer = request.getHeader("referer");
		
		response.sendRedirect(referer);			
		
		return null;
	}
}
