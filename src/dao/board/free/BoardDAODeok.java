package dao.board.free;

import static utils.JdbcUtil.*;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.sql.DataSource;

import utils.DBConnectionMgr;
import vo.board.free.BoardBean;
import vo.board.free.Board_file;
import vo.board.free.Re_boardBean;



public class BoardDAODeok {

   DataSource ds;
   Connection con;
   private static BoardDAODeok boardDAO;
   private DBConnectionMgr pool;

   private BoardDAODeok() {
      // TODO Auto-generated constructor stub
	   try {
		   pool = DBConnectionMgr.getInstance();
	   } catch(Exception e) {
		   e.printStackTrace();
	   }
   }

   public static BoardDAODeok getInstance(){
      if(boardDAO == null){
         boardDAO = new BoardDAODeok();
      }
      return boardDAO;
   }

   public void setConnection(Connection con){
      this.con = con;
   }
   
   
   public int selectListCount(String keyField, String keyWord) {

      int listCount= 0;
      PreparedStatement pstmt = null;
      ResultSet rs = null;

      try{
    	  con = pool.getConnection();
    	  
    	  System.out.println("getConnection");
    	  if (keyWord == null || ("").equals(keyWord)) {
    		  	pstmt = con.prepareStatement("select count(*) from board");
    	  }else {
				pstmt = con.prepareStatement("select count(*) from board where " + keyField + " like ?");
				pstmt.setString(1, "%" + keyWord + "%");
    	  }
    	  	rs = pstmt.executeQuery();
			if (rs.next()) {
				listCount = rs.getInt(1);
			}
        
      }catch(Exception ex){
         System.out.println("getListCount 에러: " + ex);         
      }finally{
         // close(rs);
         // close(pstmt);
         pool.freeConnection(con, pstmt, rs);
      }

      return listCount;

   }
   
   public int insertArticle(BoardBean article, ArrayList<Board_file> board_file_list){
	      
	     
	      PreparedStatement pstmt = null;
	      ResultSet rs = null;
	      int num =0;
	      String sql="";
	      int insertCount=0;

	      try{
	         con = pool.getConnection();
	          pstmt=con.prepareStatement("select max(num) from board");
	          rs = pstmt.executeQuery();

	          if(rs.next())
	             num =rs.getInt(1)+1;
	          else
	             num=1;

	          sql="insert into board (num,name,pass,subject,";
	          sql+="content,re_ref,"+
	                "re_lev,re_seq,readCount,"+
	                "date) values(?,?,?,?,?,?,?,?,?,now())";
	          pstmt = con.prepareStatement(sql);
	          pstmt.setInt(1, num);
	          pstmt.setString(2, article.getName());
	          pstmt.setString(3, article.getPass());
	          pstmt.setString(4, article.getSubject());
	          pstmt.setString(5, article.getContent());
	          pstmt.setInt(6, num);
	          pstmt.setInt(7, 0);
	          pstmt.setInt(8, 0);
	          pstmt.setInt(9, 0);
	          
	          insertCount = pstmt.executeUpdate();
	  
	          
	         
	           sql="insert into board_file (file_real_name, file_server_name, file_board_num, file_seq) values(?,?,?,0)"; 
	           for(int i=0; i<board_file_list.size(); i++) { 
	              pstmt = con.prepareStatement(sql); 
	              pstmt.setString(1, board_file_list.get(i).getFile_real_name()); 
	              pstmt.setString(2, board_file_list.get(i).getFile_server_name()); 
	              pstmt.setInt(3, num);
	              pstmt.executeUpdate();   
	           
	           }
	          
	       
	      }catch(Exception ex){
	         System.out.println("boardInsert 에러 : "+ex);
	      }finally{
	         //close(rs);
	        // close(pstmt);
	         pool.freeConnection(con,pstmt,rs);
	      }
	     
	      return insertCount;

	   }

   public ArrayList<BoardBean> selectArticleList(int page,int limit,String keyField,String keyWord){

	      PreparedStatement pstmt = null;
	      ResultSet rs = null;
	      ArrayList<BoardBean> articleList = new ArrayList<BoardBean>();
	      BoardBean board = null;
	      int startrow=(page-1)*15; //읽기 시작할 row 번호..  
	      String sql = "";

	      try{
	    	  con = pool.getConnection();
	    	  if (keyWord == null || keyWord.equals("")) {
					
			 pstmt = con.prepareStatement("select * from board order by re_ref desc, re_seq asc limit ?,15");
	         pstmt.setInt(1, startrow);
	        
	    	  }else {
	    		  
	    		    sql = "select * from  board where " + keyField + " like ? ";
					sql += "order by re_ref desc, re_seq asc limit ? , 15";
					pstmt = con.prepareStatement(sql);
					pstmt.setString(1, "%" + keyWord + "%");
					pstmt.setInt(2, startrow);
					
	    	  }
	    	  rs = pstmt.executeQuery();
	         while(rs.next()){
	            board = new BoardBean();
	            board.setNum(rs.getInt("num"));
	            board.setName(rs.getString("name"));
	            board.setSubject(rs.getString("subject"));
	            board.setContent(rs.getString("content"));
	            board.setRe_ref(rs.getInt("re_ref"));
	            board.setRe_lev(rs.getInt("re_lev"));
	            board.setRe_seq(rs.getInt("re_seq"));
	            board.setReadCount(rs.getInt("readCount"));
	            board.setDate(rs.getDate("date"));
	            articleList.add(board);
	         }

	      }catch(Exception ex){
	         System.out.println("getBoardList 에러 : " + ex);
	      }finally{
	        // close(rs);
	        // close(pstmt);
	         pool.freeConnection(con,pstmt,rs);
	      }

	      return articleList;

	   }
   
   public int updateReadCount(int board_num){

	      PreparedStatement pstmt = null;
	      int updateCount = 0;
	      String sql="update board set readCount = "+
	            "readCount+1 where num = "+board_num;

	      try{
	    	  con = pool.getConnection();
	         pstmt=con.prepareStatement(sql);
	         updateCount = pstmt.executeUpdate();
	      }catch(SQLException ex){
	         System.out.println("setReadCountUpdate 에러 : "+ex);
	      }catch(Exception e) {
	    	  e.printStackTrace();
	      }
	      finally{
	         //close(pstmt);
	         pool.freeConnection(con,pstmt);
	      }

	      return updateCount;
	   }
  
    public BoardBean selectArticle(int board_num){

	      PreparedStatement pstmt = null;
	      ResultSet rs = null;
	      BoardBean boardBean = null;

	      try{
	    	  con = pool.getConnection();
	         pstmt = con.prepareStatement(
	               "select * from board where num = ?");
	         pstmt.setInt(1, board_num);
	         rs= pstmt.executeQuery();

	         if(rs.next()){
	            boardBean = new BoardBean();
	            boardBean.setNum(rs.getInt("num"));
	            boardBean.setName(rs.getString("name"));
	            boardBean.setSubject(rs.getString("subject"));
	            boardBean.setContent(rs.getString("content"));
	            boardBean.setRe_ref(rs.getInt("re_ref"));
	            boardBean.setRe_lev(rs.getInt("re_lev"));
	            boardBean.setRe_seq(rs.getInt("re_seq"));
	            boardBean.setReadCount(rs.getInt("readCount"));
	            boardBean.setDate(rs.getDate("date"));
	         }
      }catch(Exception ex){
	         System.out.println("getDetail 에러 : " + ex);
	      }finally{
	         //close(rs);
	        // close(pstmt);
	    	  pool.freeConnection(con,pstmt,rs);
	      }

	      return boardBean;

	   }
   
   public int insertReplyArticle(BoardBean article){

	   PreparedStatement pstmt = null;
	      ResultSet rs = null;
	      String board_max_sql="select max(num) from board";
	      String sql="";
	      int num=0;
	      int insertCount=0;
	      int re_ref=article.getRe_ref();
	      int re_lev=article.getRe_lev();
	      int re_seq=article.getRe_seq();

	      try{
	    	  con = pool.getConnection();
	         pstmt=con.prepareStatement(board_max_sql);
	         rs = pstmt.executeQuery();
	         if(rs.next())num =rs.getInt(1)+1;
	         else num=1;
	         sql="update board set re_seq=re_seq+1 where re_ref=? ";
	         sql+="and re_seq>?";
	         pstmt = con.prepareStatement(sql);
	         pstmt.setInt(1,re_ref);
	         pstmt.setInt(2,re_seq);
	         int updateCount=pstmt.executeUpdate();

	         if(updateCount > 0){
	            commit(con);
	         }

	         re_seq = re_seq + 1;
	         re_lev = re_lev+1;
	         sql="insert into board (num,name,pass,subject,";
	         sql+="content,re_ref,re_lev,re_seq,";
	         sql+="readCount,date) values(?,?,?,?,?,?,?,?,?,now())";
	         pstmt = con.prepareStatement(sql);
	         pstmt.setInt(1, num);
	         pstmt.setString(2, article.getName());
	         pstmt.setString(3, article.getPass());
	         pstmt.setString(4, article.getSubject());
	         pstmt.setString(5, article.getContent());
	         pstmt.setInt(6, re_ref);
	         pstmt.setInt(7, re_lev);
	         pstmt.setInt(8, re_seq);
	         pstmt.setInt(9, 0);
	         insertCount = pstmt.executeUpdate();
	      }catch(SQLException ex){
	         System.out.println("boardReply 에러 : "+ex);
	         
	      }catch(Exception e) {
	    	  e.printStackTrace();
	      }
	      finally{
	         //close(rs);
	        //close(pstmt);
	    	  pool.freeConnection(con,pstmt,rs);
	      }

	      return insertCount;

	   }
   
   
   

   public boolean isArticleBoardWriter(int board_num,String pass){

	      PreparedStatement pstmt = null;
	      ResultSet rs = null;
	      String board_sql="select * from board where num=?";
	      boolean isWriter = false;

	      try{
	    	  con = pool.getConnection();
	         pstmt=con.prepareStatement(board_sql);
	         pstmt.setInt(1, board_num);
	         rs=pstmt.executeQuery();
	         rs.next();

	         if(pass.equals(rs.getString("pass"))){
	            isWriter = true;
	         }
	      }catch(SQLException ex){
	         System.out.println("isBoardWriter 에러 : "+ex);
	      }catch(Exception e) {
	    	  e.printStackTrace();
	      }finally{
	         //close(pstmt);
	    	  pool.freeConnection(con,pstmt);
	      }

	      return isWriter;

	   }
  
   public int deleteArticle(int board_num){

	      PreparedStatement pstmt = null;
	      String board_delete_sql="delete from board where num=?";
	      String re_board_delete_sql="delete from board_re where re_num2=?";
	      int deleteCount=0;

	      try{
	    	  con = pool.getConnection();
	    	 pstmt=con.prepareStatement(re_board_delete_sql); 
	    	 pstmt.setInt(1, board_num);
	    	 pstmt.executeUpdate();
	         pstmt=con.prepareStatement(board_delete_sql);
	         pstmt.setInt(1, board_num);
	         deleteCount=pstmt.executeUpdate();
	      }catch(Exception ex){
	         System.out.println("boardDelete 에러 : "+ex);
	      }   finally{
	    	  pool.freeConnection(con,pstmt);
	        // close(pstmt);
	      }

	      return deleteCount;

	   }
   
   public int updateArticle(BoardBean article) {
	   
		PreparedStatement pstmt = null;
		String sql = null;
		int updateCount = 0;
		try {
			  con = pool.getConnection();
			sql = "update board set subject=?,content=?,date=now() where num=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, article.getSubject());
			pstmt.setString(2, article.getContent());
			pstmt.setInt(3, article.getNum());	
			updateCount = pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// close(pstmt);
			  pool.freeConnection(con,pstmt);
			 
		}
		return updateCount;
  }
   
   public ArrayList<Re_boardBean> selectRearticleList(int board_num){

	      PreparedStatement pstmt = null;
	      ResultSet rs = null;
	      ArrayList<Re_boardBean> re_articleList = new ArrayList<Re_boardBean>();
	      Re_boardBean board = null;
	      String sql = "";

	      try {		
	    	  con = pool.getConnection();
	    	  	pstmt = con.prepareStatement("select * from board_re where re_num2 = " + board_num + " order by re_re_ref desc, re_re_seq asc");  		  
	    	  	rs = pstmt.executeQuery();
	    	  	while(rs.next()){
	    	  		board = new Re_boardBean();
	    	  		board.setRe_num(rs.getInt("re_num"));
	    	  		board.setRe_name(rs.getString("re_name"));
	    	  		board.setRe_num2(rs.getInt("re_num2"));
	    	  		board.setRe_content(rs.getString("re_content"));
	    	  		board.setRe_re_ref(rs.getInt("re_re_ref"));
	    	  		board.setRe_re_lev(rs.getInt("re_re_lev"));
	    	  		board.setRe_re_seq(rs.getInt("re_re_seq"));
	    	  		board.setRe_date(rs.getDate("re_date"));
	    	  		board.setDel_yn(rs.getString("del_yn"));
	    	  		re_articleList.add(board);
	         }

	      }catch(Exception ex){
	         System.out.println("getBoardList 에러 : " + ex);
	      }finally{
	         //close(rs);
	         //close(pstmt);
	    	  pool.freeConnection(con,pstmt,rs);
	      }

	      return re_articleList;

	   }
   
   public int insertReArticle(Re_boardBean re_article){
	   	
	      PreparedStatement pstmt = null;
	      ResultSet rs = null;
	      int num =0;
	      String sql="";
	      int insertCount=0;

	      try{
	    	  con = pool.getConnection();
	         pstmt=con.prepareStatement("select max(re_num) from board_re");
	         rs = pstmt.executeQuery();

	         if(rs.next())
	            num =rs.getInt(1)+1;
	         else
	            num=1;

	         sql="insert into board_re (re_num,re_name,re_num2,re_content,re_re_ref,re_re_lev,re_re_seq,re_date)";
	         sql+="values(?,?,?,?,?,?,?,now())";
	         pstmt = con.prepareStatement(sql);
	         pstmt.setInt(1, num);
	         pstmt.setString(2, re_article.getRe_name());
	         pstmt.setInt(3, re_article.getRe_num2());
	         pstmt.setString(4, re_article.getRe_content());
	         pstmt.setInt(5, num);
	         pstmt.setInt(6, 0);
	         pstmt.setInt(7, 0);
	         insertCount=pstmt.executeUpdate();

	      }catch(Exception ex){
	         System.out.println("boardInsert 에러 : "+ex);
	      }finally{
	         //close(rs);
	        // close(pstmt);
	         pool.freeConnection(con,pstmt,rs);
	      }

	      return insertCount;

	   }
   
   
   public int insertReplyReArticle(Re_boardBean re_article){

	   PreparedStatement pstmt = null;
	      ResultSet rs = null;
	      String board_max_sql="select max(re_num) from board_re";
	      String sql="";
	      int num=0;
	      int insertCount=0;
	      int re_re_ref=re_article.getRe_re_ref();
	      int re_re_lev=re_article.getRe_re_lev();
	      int re_re_seq=re_article.getRe_re_seq();

	      try{
	    	  con = pool.getConnection();
	         pstmt=con.prepareStatement(board_max_sql);
	         rs = pstmt.executeQuery();
	         if(rs.next())num =rs.getInt(1)+1;
	         else num=1;
	         sql="update board_re set re_re_seq=re_re_seq+1 where re_re_ref=?";
	         sql+=" and re_re_seq>?";
	         pstmt = con.prepareStatement(sql);
	         pstmt.setInt(1,re_re_ref);
	         pstmt.setInt(2,re_re_seq);
	         int updateCount=pstmt.executeUpdate();

	         if(updateCount > 0){
	            commit(con);
	         }

	         re_re_seq = re_re_seq + 1;
	         re_re_lev = re_re_lev+1;
	         sql="insert into board_re (re_num,re_name,re_num2,re_content,re_re_ref,re_re_lev,re_re_seq,re_date)";
	         sql+=" values(?,?,?,?,?,?,?,now())";
	         pstmt = con.prepareStatement(sql);
	         pstmt.setInt(1, num);
	         pstmt.setString(2, re_article.getRe_name());
	         pstmt.setInt(3, re_article.getRe_num2());
	         pstmt.setString(4, re_article.getRe_content());	
	         pstmt.setInt(5, re_re_ref);
	         pstmt.setInt(6, re_re_lev);
	         pstmt.setInt(7, re_re_seq);
	         
	         insertCount = pstmt.executeUpdate();
	      }catch(SQLException ex){
	         System.out.println("boardReply 에러 : "+ex);
	      }catch (Exception e) {
				e.printStackTrace();
			}finally{
	         //close(rs);
	        // close(pstmt);
	         pool.freeConnection(con,pstmt,rs);
	      }

	      return insertCount;

	   }
   
   public int deleteReArticle(String re_num){

	      PreparedStatement pstmt = null;
	      String re_board_delete_sql="update board_re set del_yn='Y' where re_num=?";
	      int deleteCount=0;

	      try{
	    	  con = pool.getConnection();
	    	 pstmt=con.prepareStatement(re_board_delete_sql); 
	    	 pstmt.setString(1, re_num);    
	         deleteCount=pstmt.executeUpdate();
	      }catch(Exception ex){
	         System.out.println("boardDelete 에러 : "+ex);
	      }   finally{
	    	  pool.freeConnection(con,pstmt);
	        // close(pstmt);
	      }

	      return deleteCount;

	   }

   public int updateReArticle(Re_boardBean re_article) {
	   
		PreparedStatement pstmt = null;
		String sql = null;
		int updateCount = 0;
		try {
			  con = pool.getConnection();
			sql = "update board_re set re_content=?,re_date=now() where re_num=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, re_article.getRe_content());
			pstmt.setInt(2, re_article.getRe_num());	
			updateCount = pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// close(pstmt);
			  pool.freeConnection(con,pstmt);
			 
		}
		return updateCount;
   
   
   
   }

}