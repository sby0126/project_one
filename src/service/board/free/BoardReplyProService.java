package service.board.free;

import static utils.JdbcUtil.*;

import java.sql.Connection;

import dao.board.free.BoardDAODeok;
import vo.board.free.BoardBean;
import vo.board.free.Re_boardBean;

public class BoardReplyProService {
	public boolean replyArticle(BoardBean article) throws Exception{
	      // TODO Auto-generated method stub
	      
	      boolean isReplySuccess = false;
	      int insertCount = 0;
	      Connection con = getConnection();
	      BoardDAODeok boardDAO = BoardDAODeok.getInstance();
	      boardDAO.setConnection(con);
	      insertCount = boardDAO.insertReplyArticle(article);
	      
	      if(insertCount > 0){
	         commit(con);
	         isReplySuccess = true;
	      }
	      else{
	         rollback(con);
	      }
	      
	      close(con);
	      return isReplySuccess;
	      
	   }
	
	public boolean replyReArticle(Re_boardBean re_article) throws Exception{
	      // TODO Auto-generated method stub
	      
	      boolean isReplySuccess = false;
	      int insertCount = 0;
	      Connection con = getConnection();
	      BoardDAODeok boardDAO = BoardDAODeok.getInstance();
	      boardDAO.setConnection(con);
	      insertCount = boardDAO.insertReplyReArticle(re_article);
	      
	      if(insertCount > 0){
	         commit(con);
	         isReplySuccess = true;
	      }
	      else{
	         rollback(con);
	      }
	      
	      close(con);
	      return isReplySuccess;
	      
	   }
}
