package command.board.qna;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import action.ActionResult;
import command.Command;
import manager.DataManager;

public class PostViewCommand extends Command {
	
	public PostViewCommand()  {
		super();
	}
	
	public ActionResult execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		result.start(request, response);
		
		HttpSession session = request.getSession();
		
		DataManager dataMan = DataManager.getInstance();
		
		int postNumber = Integer.parseInt(request.getParameter("postNumber"));
		String id = (String)session.getAttribute("id");		
		
		boolean isAuthority = false;
		
		if(id != null && getDAO().checkWithAuthority(postNumber, id)) {
			isAuthority = true;
		}
		
		if(!dataMan.staffMembers.contains(id) && !isAuthority) {
			result.sendError(406, "");
			
			return result;
		}
		
		// 조회수를 1 증가시킵니다.
		getDAO().updatePostViewCount(postNumber);
		
		// 데이터를 읽어옵니다.
		JSONArray arr = getDAO().readPost(postNumber);
		
		if(arr == null) {
			result.sendError(403, "데이터가 없습니다.");
			return result;
		}
		
		JSONObject json = ((JSONObject)arr.get(0));
		response.setContentType("application/json charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		
		String data = json.toJSONString();; 

		PrintWriter out = response.getWriter();

		out.println(data);
		
		return null;
	}
	
}
