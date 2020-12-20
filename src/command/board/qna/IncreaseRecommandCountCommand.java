package command.board.qna;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import action.ActionResult;
import command.Command;

public class IncreaseRecommandCountCommand extends Command {
	@Override
	public ActionResult execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		result.start(request, response);
		
		int postNumber = Integer.parseInt( request.getParameter("postNumber") );
		HttpSession session = request.getSession();
		String receiverId = (String)session.getAttribute("id");
		
		if(receiverId == null) {
			
			response.setCharacterEncoding("text/html; charset=utf-8");
			request.setAttribute("_status", "error");
			request.setAttribute("errorMessage", "로그인이 되어있지 않습니다.");
			request.setAttribute("url", "/pages/board-default.jsp");
			
			result.forward("/pages/error.jsp");
			
			return result;
		}
		
		boolean isSuccess = getDAO().increaseRecommendCount(postNumber, receiverId);
		
		if(isSuccess) {
			response.setContentType("text/html; charset=utf-8");
			PrintWriter out = response.getWriter();
			out.println("success");
			
			return null;
			
		} else {
			response.setContentType("text/html; charset=utf-8");
			PrintWriter out = response.getWriter();
			out.println("duplicate");
			return null;
		}
	}
}
