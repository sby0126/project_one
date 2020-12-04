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
	
	public ActionResult execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		result.start(request, response);
		
		int postNumber = Integer.parseInt(request.getParameter("postNumber"));
		JSONArray raw = getDAO().readPost(postNumber);
		JSONObject myJson = (JSONObject)raw.get(0);
		
		String title = (String)myJson.get("title");
		String content = (String)myJson.get("contents");
		
		System.out.println("제목은?         : " + title);
		
		request.setAttribute("type", "modify");
		request.setAttribute("postNumber", postNumber);
		request.setAttribute("title", title);
		request.setAttribute("contents", content);
		
		result.forward("/pages/board-smart-editor.jsp");
	
		return result;
	}
}
