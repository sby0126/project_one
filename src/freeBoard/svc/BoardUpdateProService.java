package freeBoard.svc;

import static freeBoard.db.JdbcUtil.close;
import static freeBoard.db.JdbcUtil.commit;
import static freeBoard.db.JdbcUtil.getConnection;
import static freeBoard.db.JdbcUtil.rollback;

import java.sql.Connection;

import freeBoard.dao.BoardDAODeok;
import freeBoard.vo.BoardBean;

public class BoardUpdateProService {
	public boolean updateArticle(BoardBean article) throws Exception{
	      // TODO Auto-generated method stub
	      
	      boolean updateSuccess = false;
	      Connection con = getConnection();
	      BoardDAODeok boardDAO = BoardDAODeok.getInstance();
	      boardDAO.setConnection(con);
	      int insertCount = boardDAO.updateArticle(article);
	      
	      if(insertCount > 0){
	         commit(con);
	         updateSuccess = true;
	      }
	      else{
	         rollback(con);
	      }
	      
	      close(con);
	      return updateSuccess;
	      
	   }
}
