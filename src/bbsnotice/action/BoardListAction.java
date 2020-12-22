package bbsnotice.action;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bbsnotice.ActionForward;
import bbsnotice.BoardBean;
import bbsnotice.PageInfo;
import bbsnotice.svc.BoardListService;

public class BoardListAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		ArrayList<BoardBean> articleList = new ArrayList<BoardBean>();
        int page = 1;
        int limit = 10;
      
        if(request.getParameter("page")!=null){
         page = Integer.parseInt(request.getParameter("page"));
        }
        
        BoardListService boardListService = new BoardListService();
        
        int listCount = boardListService.getListCount();
        
        articleList = boardListService.getArticleList(page,limit);
        
        int maxPage = (int)((double)listCount/limit+0.95); //페이지 수
        int startPage = (((int)((double)page / 10 + 0.9)) - 1) * 10 + 1;
        int endPage = startPage + 10 - 1;
        
        if(endPage > maxPage) endPage = maxPage;
        
        PageInfo pageInfo = new PageInfo();
        pageInfo.setEndPage(endPage);
        pageInfo.setListCount(listCount);
	    pageInfo.setMaxPage(maxPage);
	    pageInfo.setPage(page);
	    pageInfo.setStartPage(startPage);   
	    request.setAttribute("pageInfo", pageInfo);
	    request.setAttribute("articleList", articleList);
	    ActionForward forward= new ActionForward();
        forward.setPath("/bbs/bbsnotice/qna_board_list.jsp");

        return forward;
	}

}
