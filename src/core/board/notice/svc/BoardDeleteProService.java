package core.board.notice.svc;

import java.sql.Connection;

import core.board.notice.BoardDAO;
import static core.board.notice.JdbcUtil.*;

public class BoardDeleteProService {

	public boolean isArticleWriter(int board_num, String pass) throws Exception {
	      
	      boolean isArticleWriter = false;
	      Connection con = getConnection();
	      BoardDAO boardDAO = BoardDAO.getInstance();
	      boardDAO.setConnection(con);
	      isArticleWriter = boardDAO.isArticleBoardWriter(board_num, pass);
	      close(con);
	      return isArticleWriter;
	      
	   }

	   public boolean removeArticle(int board_num) throws Exception{
	      
	      boolean isRemoveSuccess = false;
	      Connection con = getConnection();
	      BoardDAO boardDAO = BoardDAO.getInstance();
	      boardDAO.setConnection(con);
	      int deleteCount = boardDAO.deleteArticle(board_num);
	      
	      if(deleteCount > 0){
	         commit(con);
	         isRemoveSuccess = true;
	      }
	      else{
	         rollback(con);
	      }
	      
	      close(con);
	      return isRemoveSuccess;
	   }
}
