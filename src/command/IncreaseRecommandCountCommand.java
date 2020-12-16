package command;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import action.ActionResult;

public class IncreaseRecommandCountCommand extends Command {
	@Override
	public ActionResult execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		result.start(request, response);
		
		int postNumber = Integer.parseInt( request.getParameter("postNumber") );
		String receiverId = request.getParameter("receiverId");
		
		boolean isSuccess = getDAO().increaseRecommendCount(postNumber, receiverId);
		
		if(isSuccess) {
			result.forward("/pages/boardPost.jsp");
		} else {
			result = null;
		}
		
		return result;
	}
}
