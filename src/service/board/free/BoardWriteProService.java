package service.board.free;

import static utils.JdbcUtil.*;

import java.sql.Connection;
import java.util.ArrayList;

import dao.board.free.BoardDAODeok;
import vo.board.free.BoardBean;
import vo.board.free.Board_file;
import vo.board.free.Re_boardBean;

public class BoardWriteProService {
	public boolean registArticle(BoardBean boardBean, ArrayList<Board_file> board_file_list) throws Exception{
	      // TODO Auto-generated method stub
	      
		
		
	      boolean isWriteSuccess = false;
	      Connection con = getConnection();
	      BoardDAODeok boardDAO = BoardDAODeok.getInstance();
	      boardDAO.setConnection(con);
	      int insertCount = boardDAO.insertArticle(boardBean,board_file_list);
	      
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
