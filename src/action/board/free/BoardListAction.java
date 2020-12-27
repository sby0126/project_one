package action.board.free;

import java.util.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import service.board.free.BoardListService;
import vo.board.free.ActionForward;
import vo.board.free.BoardBean;
import vo.board.free.PageInfo;

 public class BoardListAction implements Action {
    
    public ActionForward execute(HttpServletRequest request,HttpServletResponse response) throws Exception{
       
      ArrayList<BoardBean> articleList=new ArrayList<BoardBean>();
      int page=1;
      int limit=15;
      
      String keyField="";
      String keyWord="";
      
      if(request.getParameter("page")!=null){
         page=Integer.parseInt(request.getParameter("page"));
      }
      if(!("").equals(request.getParameter("keyWord"))) {
    	  keyField= request.getParameter("keyField");
    	  keyWord= request.getParameter("keyWord");
      }
      
      BoardListService boardListService = new BoardListService();
      int listCount=boardListService.getListCount(keyField, keyWord); //총 리스트 수를 받아옴.
      articleList = boardListService.getArticleList(page,limit,keyField,keyWord); //리스트를 받아옴
      //총 페이지 수.
//      articleList = boardListService.getArticleList(page,limit);
      
         int maxPage=(int)((double)listCount/limit+0.95); //0.95를 더해서 올림 처리.
         //현재 페이지에 보여줄 시작 페이지 수(1, 11, 21 등...)
         int startPage = (((int) ((double)page / 5 + 0.9)) - 1) * 5 + 1;
         //현재 페이지에 보여줄 마지막 페이지 수.(10, 20, 30 등...)
              int endPage = startPage+5-1;

         if (endPage> maxPage) endPage= maxPage;

         PageInfo pageInfo = new PageInfo();
         pageInfo.setEndPage(endPage);
         pageInfo.setListCount(listCount);
      pageInfo.setMaxPage(maxPage);
      pageInfo.setPage(page);
      pageInfo.setStartPage(startPage);   
      request.setAttribute("pageInfo", pageInfo);
      request.setAttribute("articleList", articleList);
     
      ActionForward forward= new ActionForward();
         forward.setPath("/board/list.jsp");
         return forward;
         
    }
    
 }