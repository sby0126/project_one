package command;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import action.ActionResult;

public class ModifyPostFormCommand extends Command {
	public ModifyPostFormCommand() {
		super();
	}
	
	@Override
	public ActionResult execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		int postNumber = Integer.parseInt(request.getParameter("postNumber"));
		JSONArray raw = getDAO().readPost(postNumber);
		JSONObject myJson = (JSONObject)raw.get(0);
		
		String title = (String)myJson.get("title");
		String content = (String)myJson.get("content");
		
		request.setAttribute("type", "modify");
		request.setAttribute("postNumber", postNumber);
		request.setAttribute("title", title);
		request.setAttribute("content", content);
		
		result.forward(request.getContextPath() + "/pages/board-smart-editor.jsp");
	
		return result;
	}
}
