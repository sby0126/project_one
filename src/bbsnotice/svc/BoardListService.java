package bbsnotice.svc;

import static bbsnotice.JdbcUtil.*;

import java.sql.Connection;
import java.util.ArrayList;

import bbsnotice.BoardBean;
import bbsnotice.BoardDAO;

public class BoardListService {

	public int getListCount() throws Exception{
	      
	      int listCount = 0;
	      Connection con = getConnection();
	      BoardDAO boardDAO = BoardDAO.getInstance();
	      boardDAO.setConnection(con);
	      listCount = boardDAO.selectListCount();
	      close(con);
	      return listCount;
	      
	   }
	
	public ArrayList<BoardBean> getArticleList(int page, int limit) throws Exception{
	      
	      ArrayList<BoardBean> articleList = null;
	      Connection con = getConnection();
	      BoardDAO boardDAO = BoardDAO.getInstance();
	      boardDAO.setConnection(con);
	      articleList = boardDAO.selectArticleList(page,limit);
	      close(con);
	      return articleList;
	      
	}
	
}
