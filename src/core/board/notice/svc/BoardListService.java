package core.board.notice.svc;

import java.sql.Connection;
import java.util.ArrayList;

import core.board.notice.BoardDAO;
import core.board.notice.BoardBean;

import static core.board.notice.JdbcUtil.*;

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
