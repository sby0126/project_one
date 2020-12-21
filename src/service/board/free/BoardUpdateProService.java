package service.board.free;

import static utils.JdbcUtil.close;
import static utils.JdbcUtil.commit;
import static utils.JdbcUtil.getConnection;
import static utils.JdbcUtil.rollback;

import java.sql.Connection;

import dao.board.free.BoardDAODeok;
import vo.board.free.BoardBean;
import vo.board.free.Re_boardBean;

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
	
	public boolean updateReArticle(Re_boardBean re_article) throws Exception{
	      // TODO Auto-generated method stub
	      
	      boolean updateSuccess = false;
	      Connection con = getConnection();
	      BoardDAODeok boardDAO = BoardDAODeok.getInstance();
	      boardDAO.setConnection(con);
	      int insertCount = boardDAO.updateReArticle(re_article);
	      
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
