package bbsnotice.svc;

import static bbsnotice.JdbcUtil.*;

import java.sql.Connection;

import bbsnotice.BoardBean;
import bbsnotice.BoardDAO;

public class BoardDetailService {

	public BoardBean getArticle(int board_num) throws Exception{
	      	      
	      BoardBean article = null;
	      Connection con = getConnection();
	      BoardDAO boardDAO = BoardDAO.getInstance();
	      boardDAO.setConnection(con);
	      int updateCount = boardDAO.updateReadCount(board_num);
	      
	      if(updateCount > 0){
	         commit(con);
	      }
	      else{
	         rollback(con);
	      }
	      
	      article = boardDAO.selectArticle(board_num);
	      close(con);
	      
	      return article;	      
	   }
}
