package core.board.notice.svc;

import java.sql.Connection;

import core.board.notice.BoardDAO;
import core.board.notice.BoardReplyBean;
import static core.board.notice.JdbcUtil.*;

public class BoardReplyProService {

	public boolean replyArticle(int board_num, BoardReplyBean article) throws Exception{
	      // TODO Auto-generated method stub
	      
	      boolean isReplySuccess = false;
	      int insertCount = 0;
	      Connection con = getConnection();
	      BoardDAO boardDAO = BoardDAO.getInstance();
	      boardDAO.setConnection(con);
	      insertCount = boardDAO.insertReplyArticle(board_num, article);
	      
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
