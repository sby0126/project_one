package service.board.free;

import static utils.JdbcUtil.*;

import java.sql.Connection;

import dao.board.free.BoardDAODeok;
public class BoardDeleteProService {
	public boolean isArticleWriter(int board_num, String pass) throws Exception {
	      // TODO Auto-generated method stub
	      
	      boolean isArticleWriter = false;
	      Connection con = getConnection();
	      BoardDAODeok boardDAO = BoardDAODeok.getInstance();
	      boardDAO.setConnection(con);
	      isArticleWriter = boardDAO.isArticleBoardWriter(board_num, pass);
	      close(con);
	      return isArticleWriter;
	      
	   }

	   public boolean removeArticle(int board_num) throws Exception{
	      // TODO Auto-generated method stub
	      
	      boolean isRemoveSuccess = false;
	      Connection con = getConnection();
	      BoardDAODeok boardDAO = BoardDAODeok.getInstance();
	      boardDAO.setConnection(con);
	      int deleteCount = boardDAO.deleteArticle(board_num);
	      
	      if(deleteCount > 0){
	         commit(con);
	         isRemoveSuccess=true;
	      }
	      else{
	         rollback(con);
	      }
	      
	      close(con);
	      return isRemoveSuccess;
	   }
	   
	   public boolean removeReArticle(String re_num) throws Exception{
		      // TODO Auto-generated method stub
		      
		      boolean isRemoveSuccess = false;
		      Connection con = getConnection();
		      BoardDAODeok boardDAO = BoardDAODeok.getInstance();
		      boardDAO.setConnection(con);
		      int deleteCount = boardDAO.deleteReArticle(re_num);
		      
		      if(deleteCount > 0){
		         commit(con);
		         isRemoveSuccess=true;
		      }
		      else{
		         rollback(con);
		      }
		      
		      close(con);
		      return isRemoveSuccess;
		   }
	   
}
