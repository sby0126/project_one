package core.board.notice;

import java.io.*;
import java.sql.*;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import core.DBConnectionMgr;

public class BoardMgr {
	
	private DBConnectionMgr pool;
	private static final String SAVEFOLDER = "E:/work/filestorage";
	private static final String ENCTYPE = "EUC-KR";
	private static final int MAXSIZE = 5 * 1024 * 1024;
	
	public BoardMgr() {
		
		try {
			pool = DBConnectionMgr.getInstance();
		} catch(Exception e) {
			e.getMessage();
		}		
	}
		
	// 게시판 리스트
	public Vector<BoardBean> getBoardList(String keyField, String keyWord, int start, int end) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		Vector<BoardBean> vlist = new Vector<BoardBean>();
		
		try {
			conn = pool.getConnection();
			if(keyWord.equals("null") || keyWord.equals("")) {
				
				sql = "select * from bbsNotice order by wrtdate, pos limit ?, ?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, start);
				pstmt.setInt(2, end);
				
			} else {
				
				sql = "select * from bbsNotice where " + keyField + " like ?";
				sql += "order by wrtdate, pos limit ?, ?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, "%" + keyWord + "%");
				pstmt.setInt(2, start);
				pstmt.setInt(3, end);
			}
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				
				BoardBean bean = new BoardBean();
				bean.setCtxtno(rs.getInt("ctxtno"));
				bean.setWrtnm(rs.getString("wrtnm"));
				bean.setCtitle(rs.getString("ctitle"));
				bean.setPos(rs.getInt("pos"));
				bean.setReply(rs.getInt("reply"));				
				bean.setViewcnt(rs.getInt("viewcnt"));
				bean.setWrtdate(rs.getString("wrtdate"));
				vlist.add(bean);
			}
			
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(conn, pstmt, rs);
		}
		return vlist;
	}
	
	// 총 게시물 수
	public int getTotalCount(String keyField, String keyWord) {
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		int totalCount = 0;
		
		try {
			conn = pool.getConnection();
			
			if(keyWord.equals("null") || keyWord.equals("")) {
				sql = "select count(ctxtno) from bbsnotice";
				pstmt = conn.prepareStatement(sql);
			} else {
				sql = "select count(ctxtno) from bbsnotice where " + keyField + " like ? ";
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, "%" + keyWord + "%");
			}
			
			rs = pstmt.executeQuery();
			if(rs.next()) {
				totalCount = rs.getInt(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(conn, pstmt, rs);
		}
		return totalCount;
	}
	
	
	// 게시판 입력
	public void insertBoard(HttpServletRequest req) {
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		MultipartRequest multi = null;
		String filename = null;
		ResultSet rs = null;
		
		try {
			conn = pool.getConnection();
			sql = "select max(ctxtno) from bbsNotice";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			File file = new File(SAVEFOLDER);
				
			if(!file.exists())
				file.mkdirs();
				
			multi = new MultipartRequest(req, SAVEFOLDER, MAXSIZE, ENCTYPE, new DefaultFileRenamePolicy());

			if(multi.getFilesystemName("filename") != null) {
				filename = multi.getFilesystemName("filename");
			}
				
			String content = multi.getParameter("ctxt");
				
			if(multi.getParameter("contentType").equalsIgnoreCase("TEXT")) {
				content = UtilMgr.replace(content, "<", "&lt;");
			}
			
			sql = "insert bbsnotice(wrtnm,ctitle,ctxt,pos,cpwd,reply,viewcnt,wrtdate,filename) ";
			sql += "values(?,?,?,0,?,0,0,now(),?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, multi.getParameter("wrtnm"));
			pstmt.setString(2, multi.getParameter("ctitle"));
			pstmt.setString(3, content);
			pstmt.setString(4, multi.getParameter("cpwd"));
			pstmt.setString(5, filename);
			pstmt.executeUpdate();

		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(conn, pstmt, rs);
		}
	}
	
	
	// 게시물 리프레싱 및 재출력
	public BoardBean getBoard(int num) {
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		BoardBean bean = new BoardBean();
		
		try {
			conn = pool.getConnection();
			sql = "select * from bbsnotice where ctxtno = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, num);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				bean.setCtxtno(rs.getInt("ctxtno"));
				bean.setWrtnm(rs.getString("wrtnm"));
				bean.setCtitle(rs.getString("ctitle"));
				bean.setCtxt(rs.getString("Ctxt"));
				bean.setPos(rs.getInt("pos"));
				bean.setCpwd(rs.getString("cpwd"));
				bean.setReply(rs.getInt("reply"));
				bean.setViewcnt(rs.getInt("viewcnt"));
				bean.setWrtdate(rs.getString("wrtdate"));
				bean.setFilename(rs.getString("filename"));
			}
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(conn, pstmt, rs);
		}
		return bean;
	}
	
	// 조회수 증가
	public void upCount(int num) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		
		try {
			conn = pool.getConnection();
			sql = "update bbsnotice set viewcnt = viewcnt+1 where ctxtno = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, num);
			pstmt.executeUpdate();
			
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(conn, pstmt);
		}
	}
	
	// 게시물 삭제 
	public void deleteBoard(int num) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		
		try {
			conn = pool.getConnection();
			sql = "select filename from bbsnotice where ctxtno = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, num);
			rs = pstmt.executeQuery();
			
			if(rs.next() && rs.getString(1) != null) {
				if(!rs.getString(1).equals("")) {
					
					File file = new File(SAVEFOLDER + "/" + rs.getString(1));
					if(file.exists())
						UtilMgr.delete(SAVEFOLDER + "/" + rs.getString(1));
				}				
			} 
			
			sql = "delete from bbsnotice where ctxctno = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, num);
			pstmt.executeUpdate();			
			
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(conn, pstmt, rs);
		}		
	}
	
	
	// 파일 다운로드
	public void downLoad(HttpServletRequest req, HttpServletResponse res, JspWriter out, PageContext pageContext) {
		
		try {
			String filename = req.getParameter("filename");
			File file = new File(UtilMgr.con(SAVEFOLDER) + File.separator + filename);
			byte b[] = new byte[(int) file.length()];
			
			res.setHeader("Accept-Ranges", "bytes");
			
			String strClient = req.getHeader("User-Agent");
			
			if(strClient.indexOf("MSIE6.0") != -1) {
				res.setContentType("application/smnet;charset=euc-kr");
				res.setHeader("Content-Disposition", "filename=" + filename + ";");
			} else {
				res.setContentType("application/smnet;charset=euc-kr");
				res.setHeader("Content-Disposition", "attachment;filename=" + filename + ";");				
			}
			out.clear();
			out = pageContext.pushBody();
			
			if(file.isFile()) {
				BufferedInputStream fin = new BufferedInputStream(new FileInputStream(file));
				BufferedOutputStream outs = new BufferedOutputStream(res.getOutputStream());
				
				int read = 0;
				while((read = fin.read(b)) != -1) {
					outs.close();
					fin.close();
				}				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} 		
	}
	
	// 댓글리스트 출력
	public Vector<BoardReplyBean> getReplyList(int num) {
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		Vector<BoardReplyBean> vlist = new Vector<BoardReplyBean>();
		
		try {
			conn = pool.getConnection();
			
				
			sql = "select * from bbsNoticeRpy where ref = ? order by wrtdate";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, num);
			rs = pstmt.executeQuery();

					
			while(rs.next()) {
				
				BoardReplyBean rbean = new BoardReplyBean();
				rbean.setRpyno(rs.getInt("Rpyno"));
				rbean.setRprnm(rs.getString("rprnm"));
				rbean.setRpyctxt(rs.getString("rpyctxt"));
				rbean.setRpos(rs.getInt("rpos"));
				rbean.setRef(rs.getInt("ref"));
				rbean.setDepth(rs.getInt("depth"));
				rbean.setRpydate(rs.getString("rpydate"));
				rbean.setRpwd(rs.getString("rpwd"));
				vlist.add(rbean);
			}
			
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(conn, pstmt, rs);
		}
		return vlist;
	}
	
	
	// 댓글 리스트 리프레시
	public BoardReplyBean getReply(int num) {
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		BoardReplyBean rbean = new BoardReplyBean();
		
		
		try {
			conn = pool.getConnection();
			sql = "select * from bbsnoticerpy where ref = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, num);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				rbean.setRpyno(rs.getInt("rpyno"));
				rbean.setRprnm(rs.getString("rprnm"));
				rbean.setRpyctxt(rs.getString("pryctxt"));
				rbean.setRpos(rs.getInt("rpos"));
				rbean.setRef(rs.getInt("ref"));
				rbean.setDepth(rs.getInt("depth"));
				rbean.setRpydate(rs.getString("prydate"));
				rbean.setRpwd(rs.getString("rpwd"));
			}
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(conn, pstmt, rs);
		}
		return rbean;
	}
	
	// 댓글 입력
		public void writeReply(int num, HttpServletRequest request) {
			
			Connection conn = null;
			PreparedStatement pstmt = null;
			String sql = null;
			ResultSet rs = null;
			
						
			try {
				conn = pool.getConnection();
				sql = "select max(rpyno) from bbsnoticerpy";
				pstmt = conn.prepareStatement(sql);
				rs = pstmt.executeQuery();
										
				String ReplyContent = request.getParameter("rpyctxt");
				
				sql = "insert bbsnoticerpy(rprnm,rpyctxt,rpos,ref,depth,rpydate,rpwd) ";
				sql += "values(?,?,?,?,?,now(),?)";
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, request.getParameter("rprnm"));
				pstmt.setString(2, ReplyContent);				
				pstmt.setInt(3, Integer.parseInt(request.getParameter("rpos")));
				pstmt.setInt(4, Integer.parseInt(request.getParameter("num")));
				pstmt.setInt(5, Integer.parseInt(request.getParameter("depth")));
				pstmt.setString(6, request.getParameter("rpwd"));
				pstmt.executeUpdate();
					
			} catch(Exception e) {
				e.printStackTrace();
			} finally {
				pool.freeConnection(conn, pstmt);
			}
		}
		
		// 댓글 삭제 
		public void deleteReply(int ref,int num) {
			
			Connection conn = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			String sql = null;
			
			try {
				
				conn = pool.getConnection();				
				sql = "delete from bbsnoticerpy where ref = ? and rpyno = ?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, ref);
				pstmt.setInt(2, num);
				pstmt.executeUpdate();			
				
			} catch(Exception e) {
				e.printStackTrace();
			} finally {
				pool.freeConnection(conn, pstmt, rs);
			}		
		}
	
	
	
}
