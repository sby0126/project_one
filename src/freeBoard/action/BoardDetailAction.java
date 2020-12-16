package freeBoard.action;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import freeBoard.svc.BoardDetailService;
import freeBoard.svc.BoardListService;
import freeBoard.vo.ActionForward;
import freeBoard.vo.BoardBean;
import freeBoard.vo.Re_boardBean;

public class BoardDetailAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
	
		int board_num=Integer.parseInt(request.getParameter("board_num"));
	      String page = request.getParameter("page");
	      BoardDetailService boardDetailService = new BoardDetailService();
	      BoardBean article = boardDetailService.getArticle(board_num);
	      ArrayList<Re_boardBean> re_articleList = new  ArrayList<Re_boardBean>();
	      BoardListService boardListService = new BoardListService();
	      re_articleList = boardListService.getReArticleList(board_num);
	      
	    
	      
	      ActionForward forward = new ActionForward();	
	      
	      request.setAttribute("page", page);
	         request.setAttribute("article", article);
	         request.setAttribute("re_articleList", re_articleList);
	         forward.setPath("/board/read.jsp");
	         return forward;
	}
	
}
