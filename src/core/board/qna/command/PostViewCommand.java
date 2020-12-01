package core.board.qna.command;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import core.board.qna.BoardDAO;

public class PostViewCommand extends Command {
	
	public PostViewCommand(BoardDAO boardDAO)  {
		super(boardDAO);
	}
	
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 게시물을 JSON으로 읽어옵니다. 
		
		int postNumber = Integer.valueOf(request.getParameter("postNumber"));
		
		// 조회수를 1 증가시킵니다.
		boardMgr.updatePostViewCount(postNumber);
		
		// 데이터를 읽어옵니다.
		JSONArray arr = boardMgr.readPost(postNumber);
		
		if(arr == null) {
			response.sendError(404, "Can't find a data");
			return;
		}
		
		JSONObject json = ((JSONObject)arr.get(0));
		response.setContentType("application/json");
		response.setCharacterEncoding("EUC-KR");
		
		PrintWriter out = response.getWriter();
		
		out.println(json.toJSONString());
	}
	
}
