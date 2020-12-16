package freeBoard.svc;

import java.sql.Connection;

import freeBoard.dao.BoardDAODeok;
import freeBoard.vo.BoardBean;
import freeBoard.vo.Re_boardBean;

import static freeBoard.db.JdbcUtil.*;

public class BoardWriteProService {
	public boolean registArticle(BoardBean boardBean) throws Exception{
	      // TODO Auto-generated method stub
	      
		
		
	      boolean isWriteSuccess = false;
	      Connection con = getConnection();
	      BoardDAODeok boardDAO = BoardDAODeok.getInstance();
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
	
	public boolean registReArticle(Re_boardBean re_boardBean) throws Exception{
	      // TODO Auto-generated method stub
	      
	      boolean isWriteSuccess = false;
	      Connection con = getConnection();
	      BoardDAODeok boardDAO = BoardDAODeok.getInstance();
	      boardDAO.setConnection(con);
	      int insertCount = boardDAO.insertReArticle(re_boardBean);
	      
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
