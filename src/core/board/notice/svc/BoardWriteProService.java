package core.board.notice.svc;

import java.sql.Connection;

import core.board.notice.BoardDAO;
import core.board.notice.BoardBean;
import static core.board.notice.JdbcUtil.*;

public class BoardWriteProService {
	
	public boolean registArticle(BoardBean boardBean) throws Exception{
	      // TODO Auto-generated method stub
	      
	      boolean isWriteSuccess = false;
	      Connection con = getConnection();
	      BoardDAO boardDAO = BoardDAO.getInstance();
	      boardDAO.setConnection(con);
	      int insertCount = boardDAO.insertArticle(boardBean);
	      
	      if(insertCount > 0){
	         commit(con);
	         isWriteSuccess = true;
	      }
	      else{
	         rollback(con);
	      }
	      
	      close(con);
	      
	      return isWriteSuccess;
	      
	   }
}
