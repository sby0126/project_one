package bbsnotice;

import static bbsnotice.JdbcUtil.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.sql.DataSource;

import bbsnotice.BoardBean;

public class BoardDAO {

	DataSource ds;
	Connection con;
	
	private static BoardDAO boardDAO;

	private BoardDAO() {
	      // TODO Auto-generated constructor stub
	}

	public static BoardDAO getInstance(){
	   if(boardDAO == null){
	      boardDAO = new BoardDAO();
	   }
	   return boardDAO;
	}

	public void setConnection(Connection con){
	   this.con = con;
	}
	
	public int selectListCount() {

	      int listCount = 0;
	      PreparedStatement pstmt = null;
	      ResultSet rs = null;

	      try{


	         System.out.println("getConnection");
	         pstmt=con.prepareStatement("select count(*) from bbsnotice");
	         rs = pstmt.executeQuery();

	         if(rs.next()){
	            listCount = rs.getInt(1);
	         }
	      }catch(Exception ex){
	         System.out.println("getListCount 에러: " + ex);         
	      }finally{
	    	 close(rs);
	         close(pstmt);
	      }

	      return listCount;

	   }
	
	public int insertArticle(BoardBean article){

		PreparedStatement pstmt = null;
	    ResultSet rs = null;
	    int num = 0;
	    String sql = "";
	    int insertCount = 0;

	    try{
	       pstmt = con.prepareStatement("select max(ctxtno) from bbsnotice");
	       rs = pstmt.executeQuery();

	       if(rs.next())
	          num = rs.getInt(1)+1;
	       else
	          num = 1;

	       sql="insert into bbsnotice (ctxtno,wrtnm,cpwd,ctitle,";
	       sql+="ctxt, pos, filename, reply, viewcnt, wrtdate) "+
	            "values(?,?,?,?,?,?,?,?,?,now())";

	       pstmt = con.prepareStatement(sql);
	       pstmt.setInt(1, num);
	       pstmt.setString(2, article.getWrtnm());
	       pstmt.setString(3, article.getCpwd());
	       pstmt.setString(4, article.getCtitle());
	       pstmt.setString(5, article.getCtxt());
	       pstmt.setInt(6, 0);
	       pstmt.setString(7, article.getFilename());
	       pstmt.setInt(8, 0);
	       pstmt.setInt(9, 0);

	       insertCount = pstmt.executeUpdate();

	    }catch(Exception ex){
	       System.out.println("boardInsert 에러 : "+ex);
	    }finally{
	       close(rs);
	       close(pstmt);
	    }

	    return insertCount;

	 }
	
	public ArrayList<BoardBean> selectArticleList(int page,int limit){

	      PreparedStatement pstmt = null;
	      ResultSet rs = null;
	      String board_list_sql="select * from bbsnotice order by pos limit ?,10";
	      ArrayList<BoardBean> articleList = new ArrayList<BoardBean>();
	      BoardBean board = null;
	      int startrow=(page-1)*10; //읽기 시작할 row 번호..   

	      try{
	         pstmt = con.prepareStatement(board_list_sql);
	         pstmt.setInt(1, startrow);
	         rs = pstmt.executeQuery();

	         while(rs.next()){
	            board = new BoardBean();
	            board.setCtxtno(rs.getInt("ctxtno"));
	            board.setWrtnm(rs.getString("wrtnm"));
	            board.setCtitle(rs.getString("ctitle"));
	            board.setCtxt(rs.getString("ctxt"));
	            board.setPos(rs.getInt("pos"));
	            board.setCpwd(rs.getString("cpwd"));
	            board.setReply(rs.getInt("reply"));
	            board.setViewcnt(rs.getInt("viewcnt"));
	            board.setWrtdate(rs.getString("wrtdate"));
	            board.setFilename(rs.getString("filename"));
	            articleList.add(board);
	         }

	      }catch(Exception ex){
	         System.out.println("getBoardList 에러 : " + ex);
	      }finally{
	         close(rs);
	         close(pstmt);
	      }

	      return articleList;

	   }
	
	public int updateReadCount(int board_num){

	      PreparedStatement pstmt = null;
	      int updateCount = 0;
	      String sql = "update bbsnotice set viewcnt = "+
	            "viewcnt+1 where ctxtno = "+board_num;

	      try{
	         pstmt = con.prepareStatement(sql);
	         updateCount = pstmt.executeUpdate();
	      }catch(SQLException ex){
	         System.out.println("setReadCountUpdate 에러 : "+ex);
	      }
	      finally{
	         close(pstmt);
	      }

	      return updateCount;

	   }
		
	public BoardBean selectArticle(int board_num){

	      PreparedStatement pstmt = null;
	      ResultSet rs = null;
	      BoardBean boardBean = null;

	      try{
	         pstmt = con.prepareStatement(
	               "select * from bbsnotice where ctxtno = ?");
	         pstmt.setInt(1, board_num);
	         rs = pstmt.executeQuery();

	         if(rs.next()){
	            boardBean = new BoardBean();
	            boardBean.setCtxtno(rs.getInt("ctxtno"));
	            boardBean.setWrtnm(rs.getString("wrtnm"));
	            boardBean.setCtitle(rs.getString("ctitle"));
	            boardBean.setCtxt(rs.getString("ctxt"));
	            boardBean.setPos(rs.getInt("pos"));
	            boardBean.setCpwd(rs.getString("cpwd"));
	            boardBean.setReply(rs.getInt("reply"));
	            boardBean.setViewcnt(rs.getInt("viewcnt"));
	            boardBean.setWrtdate(rs.getString("wrtdate"));
	            boardBean.setFilename(rs.getString("filename"));
	         }
	         
	      }catch(Exception ex){
	         System.out.println("getDetail 에러 : " + ex);
	      }finally{
	         close(rs);
	         close(pstmt);
	      }

	      return boardBean;

	   }
	
	public int insertReplyArticle(int board_num, BoardReplyBean article){

	      PreparedStatement pstmt = null;
	      ResultSet rs = null;
	      String board_max_sql = "select max(replyno) from bbsnoticerpy where replypos = ?";
	      String sql = "";
	      int num = 0;
	      int insertCount = 0;
	      int re_ref = article.getRef();
	      int re_dep = article.getDepth();
	      int re_rps = article.getReplypos();

	      try{
	         pstmt = con.prepareStatement(board_max_sql);
	         pstmt.setInt(1, board_num);
	         rs = pstmt.executeQuery();
	         if(rs.next()) num = rs.getInt(1)+1;
	         else num= 1 ;
	         sql = "update bbsnoticerpy set replypos = replypos + 1 where ref = ? ";
	         sql +="and depth > ?";
	         pstmt = con.prepareStatement(sql);
	         pstmt.setInt(1,re_ref);
	         pstmt.setInt(2,re_dep);
	         int updateCount = pstmt.executeUpdate();

	         if(updateCount > 0){
	            commit(con);
	         }

	         re_rps = re_rps + 1;
	         re_dep = re_dep + 1;
	         sql = "insert into bbsnoticerpy (replyno,replynm,replyctxt,replypos,";
	         sql += "ref,depth,replydate,replypwd) values(?,?,?,?,?,?,now(),?)";
	         pstmt = con.prepareStatement(sql);
	         pstmt.setInt(1, num);
	         pstmt.setString(2, article.getReplynm());
	         pstmt.setString(3, article.getReplyctxt());
	         pstmt.setInt(4, re_rps);
	         pstmt.setInt(5, re_ref);
	         pstmt.setInt(6, re_dep);
	         pstmt.setString(7, article.getReplypwd());
	         insertCount = pstmt.executeUpdate();
	         
	      }catch(SQLException ex){
	         System.out.println("boardReply 에러 : "+ex);
	      }finally{
	         close(rs);
	         close(pstmt);
	      }

	      return insertCount;

	   }
	
	public boolean isArticleBoardWriter(int board_num, String pass){

	      PreparedStatement pstmt = null;
	      ResultSet rs = null;
	      String board_sql ="select * from bbsnotice where ctxtno = ?";
	      boolean isWriter = false;

	      try{
	    	  
	         pstmt=con.prepareStatement(board_sql);
	         pstmt.setInt(1, board_num);
	         rs=pstmt.executeQuery();
	         rs.next();

	         if(pass.equals(rs.getString("cpwd"))){
	            isWriter = true;
	         }
	      }catch(SQLException ex){
	         System.out.println("isBoardWriter 에러 : "+ex);
	      }
	      finally{
	         close(pstmt);
	      }

	      return isWriter;

	   }
	
	public int deleteArticle(int board_num) {
		
		PreparedStatement pstmt = null;
	    String sql = "delete from bbsnotice where ctxtno = ?";
	    int deleteCount = 0;
	      
	    try{
	    	pstmt = con.prepareStatement(sql);
		    pstmt.setInt(1, board_num);
		    deleteCount = pstmt.executeUpdate();
		              
		 } catch(Exception ex){
			 System.out.println("Delete 에러 : " + ex);
		 }finally{
		     close(pstmt);
		 }
		 return deleteCount;
	}
	      
	
	
}
