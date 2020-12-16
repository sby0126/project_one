package freeBoard.svc;

import static freeBoard.db.JdbcUtil.close;
import static freeBoard.db.JdbcUtil.getConnection;

import java.sql.Connection;
import java.util.ArrayList;

import freeBoard.dao.BoardDAODeok;
import freeBoard.vo.BoardBean;
import freeBoard.vo.Re_boardBean;

public class BoardListService {
	public int getListCount(String KeyField, String KeyWord) throws Exception{
	      // TODO Auto-generated method stub
	      
	      int listCount = 0;
	      Connection con = getConnection();
	      BoardDAODeok boardDAO = BoardDAODeok.getInstance();
	      boardDAO.setConnection(con);
	      listCount = boardDAO.selectListCount(KeyField,KeyWord);
	      close(con);
	      return listCount;
	      
	   }

	public ArrayList<BoardBean> getArticleList(int page, int limit, String keyField, String keyWord) throws Exception{
	      
	      ArrayList<BoardBean> articleList = null;
	      Connection con = getConnection();
	      BoardDAODeok boardDAO = BoardDAODeok.getInstance();
	      boardDAO.setConnection(con);
	      articleList = boardDAO.selectArticleList(page,limit,keyField,keyWord);
	      close(con);
	      return articleList;
	      
	   }
	
	public ArrayList<Re_boardBean> getReArticleList(int board_num) throws Exception{
	      
	      ArrayList<Re_boardBean> re_articleList = null;
	      Connection con = getConnection();
	      BoardDAODeok boardDAO = BoardDAODeok.getInstance();
	      boardDAO.setConnection(con);
	      re_articleList = boardDAO.selectRearticleList(board_num);
	      close(con);
	      return re_articleList;
	      
	   }
}
