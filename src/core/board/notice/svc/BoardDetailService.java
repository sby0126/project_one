package core.board.notice.svc;

import java.sql.Connection;

import core.board.notice.BoardDAO;
import core.board.notice.BoardBean;
import static core.board.notice.JdbcUtil.*;

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
