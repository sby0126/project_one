package service.board.free;

import static utils.JdbcUtil.*;

import java.sql.Connection;

import dao.board.free.BoardDAODeok;
import vo.board.free.BoardBean;

public class BoardDetailService {
	public BoardBean getArticle(int board_num) throws Exception{
	      // TODO Auto-generated method stub
	      
	      BoardBean article = null;
	      Connection con = getConnection();
	      BoardDAODeok boardDAO = BoardDAODeok.getInstance();
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
