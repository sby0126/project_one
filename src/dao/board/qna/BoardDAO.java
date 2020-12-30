package dao.board.qna;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import core.ImageFile;
import core.SQLHelper;
import dao.IDAO;
import sql.BoardSQL;
import utils.DBConnectionMgr;
import vo.board.qna.BoardCommentVO;
import vo.board.qna.BoardVO;

/***
 * 테이블을 생성합니다.
 */
public class BoardDAO implements IDAO {
	private DBConnectionMgr pool; 
	private Connection conn;
	private PreparedStatement pstmt;
	
	private static BoardDAO instance = null;
	
	protected BoardSQL qlList;
	
	
	public static synchronized BoardDAO getInstance() {
		if(instance == null) {
			instance = new BoardDAO();
		}
		
		return instance;
	}
	
	/***
	 * 
	 */
	private BoardDAO() {
		connect();
		initWithSQL();
	}
	
	/***
	 * 
	 */
	public void connect() {	
		try {
			pool = DBConnectionMgr.getInstance();
		} catch(Exception e) {
			e.printStackTrace();
		}		
	}
	
	public boolean isExistsTable() {
		boolean ret = false;
		try {
			conn = pool.getConnection();
			DatabaseMetaData dbm = conn.getMetaData();
			
			ResultSet tables = dbm.getTables(null, null, "tblQNABoard", null);
			
			if (tables.next()) {
				// 테이블이 존재합니다.
				ret = true;
			}
			else {
				// 테이블이 존재하지 않습니다.
				ret = false;
			}
			
		} catch(SQLException e) {
			e.printStackTrace();
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return ret;
	}
	
	/**
	 * MYSQL과 호환되는 시간 형식을 만듭니다.
	 * @return
	 */
	public String getLocalTime() {
		LocalDateTime dateTime = LocalDateTime.now();
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		String formatedDate = dateTime.format(formatter);
		
		return formatedDate;
	}
	
	/***
	 * 
	 */
	public void initWithSQL() {
		qlList = new BoardSQL();
	}
	
	/***
	 * 
	 * @param commandName
	 * @return
	 */
	public String getQL(String commandName) {
		return qlList.get(commandName);
	}
	
	/***
	 * 
	 * @param query
	 * @return
	 */
	public boolean execute(String query, int... args) {
		boolean ret = false;
		
		try {
			conn = pool.getConnection();
						
			pstmt = conn.prepareStatement(query);
			
			for(int i = 0; i < args.length; i++) {
				pstmt.setInt(i + 1, args[i]);
			}			
			
			ret = pstmt.execute();
			
		} catch(SQLException e) {
			e.printStackTrace();
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(conn, pstmt);
		}
		
		return ret;
	}
	
	/***
	 * 
	 * @return
	 */
	public int getCount() {
		ResultSet rs = null;
		int count = 0;
		
		try {
			conn = pool.getConnection();
			pstmt = conn.prepareStatement(getQL("count"));
			
			rs = pstmt.executeQuery();
			
			count = rs.getInt(1);
			
		} catch(SQLException e) {
			e.printStackTrace();
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(conn, pstmt, rs);
		}
		
		return count;
	}
	
	/***
	 * 
	 * @param start
	 * @param end
	 * @return
	 */
	public List<BoardVO> getList(int start, int end) {
		ResultSet rs = null;
		List<BoardVO> list = null;
		
		try {
			conn = pool.getConnection();
			pstmt = conn.prepareStatement(getQL("count"));
			pstmt.setInt(1, start);
			pstmt.setInt(2, end);
			rs = pstmt.executeQuery();
			list = SQLHelper.putResult(rs, BoardVO.class);
			
		} catch(SQLException e) {
			e.printStackTrace();
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(conn, pstmt, rs);
		}
		
		return list;
		
	}
	
	/***
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public JSONArray getListAll() {
		ResultSet rs = null;
		List<BoardVO> list = null;
		JSONArray arr = new JSONArray();
		
		try {
			conn = pool.getConnection();
			pstmt = conn.prepareStatement(getQL("toJSON"));
			rs = pstmt.executeQuery();
			list = SQLHelper.putResult(rs, BoardVO.class);
			
			if(list != null) {
				
				for(BoardVO vo : list) {
					JSONObject obj = new JSONObject();
					obj.put("postNumber", String.valueOf(vo.getArticleid()));
					obj.put("postType", vo.getArticletype());
					
					StringBuilder sb = new StringBuilder();
					sb.append(vo.getTitle());
					
					// 코멘트를 읽습니다.
					List<BoardCommentVO> comments = this.readAllComments(vo.getArticleid());
					int commentsCount = comments.size();
					
					
					sb.append("&nbsp<span class='comment'>[");
					sb.append(commentsCount);
					sb.append("]</span>");
					
					obj.put("postTitle", sb.toString());
					obj.put("name", vo.getCtmnm());
					obj.put("create_at", vo.getRegdate().toString());
					obj.put("view", vo.getViewcount());
					obj.put("recommandCount", vo.getRecommandcount());
					

					
					arr.add(obj);
				}
			}
			
		} catch(SQLException e) {
			e.printStackTrace();
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(conn, pstmt, rs);
		}
		
		return arr;		
	}	
	/***
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public JSONArray getListAllForFilter(String searchQuery) {
		ResultSet rs = null;
		List<BoardVO> list = null;
		JSONArray arr = new JSONArray();
		
		try {
			conn = pool.getConnection();
			pstmt = conn.prepareStatement(getQL("toJSONFilter"));
			pstmt.setString(1, "%" + searchQuery + "%");
			
			rs = pstmt.executeQuery();
			list = SQLHelper.putResult(rs, BoardVO.class);
			
			if(list != null) {
				
				for(BoardVO vo : list) {
					JSONObject obj = new JSONObject();
					obj.put("postNumber", String.valueOf(vo.getArticleid()));
					obj.put("postType", vo.getArticletype());
					
					StringBuilder sb = new StringBuilder();
					sb.append(vo.getTitle());
					
					// 코멘트를 읽습니다.
					List<BoardCommentVO> comments = this.readAllComments(vo.getArticleid());
					int commentsCount = comments.size();
					
					
					sb.append("&nbsp<span class='comment'>[");
					sb.append(commentsCount);
					sb.append("]</span>");
					
					obj.put("postTitle", sb.toString());
					obj.put("name", vo.getCtmnm());
					obj.put("create_at", vo.getRegdate().toString());
					obj.put("view", vo.getViewcount());
					obj.put("recommandCount", vo.getRecommandcount());
					
					
					
					arr.add(obj);
				}
			}
			
		} catch(SQLException e) {
			e.printStackTrace();
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(conn, pstmt, rs);
		}
		
		return arr;		
	}	
	
	/***
	 * 
	 * @param postNumber
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public JSONArray readPost(int postNumber) {
		ResultSet rs = null;
		List<BoardVO> list = null;
		JSONArray arr = new JSONArray();
		
		try {
			conn = pool.getConnection();
			pstmt = conn.prepareStatement(getQL("readPost"));
			pstmt.setInt(1, postNumber);
			rs = pstmt.executeQuery();
			list = SQLHelper.putResult(rs, BoardVO.class);
			
			if(list != null) {
				
				
				for(BoardVO vo : list) {
					JSONObject obj = new JSONObject();
					obj.put("boardType", "qna");
				    obj.put("postNumber", String.valueOf(vo.getArticleid()));
				    obj.put("title", vo.getTitle());
				    obj.put("contents", vo.getContent());
				    obj.put("view", vo.getViewcount());
				    obj.put("create_at", vo.getRegdate().toString());
				    obj.put("author", vo.getCtmnm());
				    
				    // 코멘트를 생성합니다.
				    JSONArray comments = readAllCommentsToJson(vo.getArticleid());
				    obj.put("comments", comments);
				    
					arr.add(obj);
				}
			}
						
			
		} catch(SQLException e) {
			e.printStackTrace();
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(conn, pstmt, rs);
		}
		
		return arr;	
	}
		
	/***
	 * 
	 * @param authorID
	 * @param articleType
	 * @param title
	 * @param content
	 * @param imageFileName
	 * @return
	 */
	public boolean writePost(String authorID, String articleType, String title, String content, String imageFileName) {
		boolean ret = false;
				
		try {
			conn = pool.getConnection();
			pstmt = conn.prepareStatement(getQL("writePost"));
			pstmt.setString(1, authorID);
			pstmt.setString(2, articleType);
			pstmt.setString(3, title);
			pstmt.setString(4, content);
			pstmt.setString(5, imageFileName);
			
			if(pstmt.executeUpdate() > 0) {
				ret = true;
			}
			
		} catch(SQLException e) {
			e.printStackTrace();
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(conn, pstmt);
		}
		
		return ret;
	}
	
	public boolean updatePost(String title, String content, int postNumber) {

		boolean ret = false;
		
		try {
			conn = pool.getConnection();
			pstmt = conn.prepareStatement(getQL("updatePost"));
			pstmt.setString(1, title);
			pstmt.setString(2, content);
			pstmt.setInt(3, postNumber);
			
			if(pstmt.executeUpdate() > 0) {
				ret = true;
				conn.commit();
			}
			
		} catch(SQLException e) {
			e.printStackTrace();
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(conn, pstmt);
		}
		
		return ret;
	}
	
	public ImageFile uploadFile(int articleID, String newFileName) {
		boolean ret = false;
		ResultSet rs = null;
		ImageFile imageFile = null;
		
		try {
			conn = pool.getConnection();
						
			// 새로운 이미지 파일을 업로드 합니다.
			pstmt = conn.prepareStatement(getQL("uploadFile"));
			pstmt.setString(1, newFileName);
			pstmt.setInt(2, articleID);
			
			if(pstmt.executeUpdate() > 0) {
				ret = true;
			}
			
			pstmt.close();
			pstmt = conn.prepareStatement(getQL("readFile"));
			pstmt.setInt(1, articleID);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				imageFile = new ImageFile( rs.getString("imageFileName") );
				imageFile.start();
			}
			
			
		} catch(SQLException e) {
			e.printStackTrace();
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(conn, pstmt, rs);
		}
		
		return imageFile;
	}
	
	public void deletePost(int articleID) {
		try {
			execute(getQL("deleteComments"), articleID);
			execute(getQL("deletePost"), articleID);			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void updatePostViewCount(int articleID) {
		try {
			execute(getQL("조회수증감"), articleID);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/***
	 * 
	 * @param articleID
	 * @param authorID
	 * @param contents
	 */
	public boolean writeComment(int articleID, String authorID, String contents) {
		boolean isOK = false;
		ResultSet rs = null;
		
		try {
			int nextCommentID = nextCommentID();
			System.out.println("다음 코멘트 번호 : " + nextCommentID);
			
			conn = pool.getConnection();
					
			pstmt = conn.prepareStatement(getQL("writeComment"));
			pstmt.setInt(1, articleID);
			pstmt.setString(2, authorID);
			pstmt.setString(3, contents);
			pstmt.setInt(4, nextCommentID);
			
			if(pstmt.executeUpdate() > 0) {
				isOK = true;
			}
			
		} catch(SQLException e) {
			e.printStackTrace(); 
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(conn, pstmt, rs);
		}
		
		return isOK;
	}
	
	public int nextCommentID() {
		ResultSet rs = null;
		int count = 0;
		try {
			conn = pool.getConnection();
			pstmt = conn.prepareStatement(getQL("maxCommentID"));
			
			rs = pstmt.executeQuery();
			if(rs.next()) {
				count = rs.getInt(1);	
			}
					
		} catch(SQLException e) {
			e.printStackTrace(); 
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(conn, pstmt, rs);
		}
		
		return count;
		
	}
	
	public boolean writeChildComment(int articleID, String authorID, String contents, int pos, int parentCommentID, int depth) {
		boolean isOK = false;
		ResultSet rs = null;
				
		try {
			conn = pool.getConnection();
			
			pstmt = conn.prepareStatement(getQL("updateCommentPos"));
			pstmt.setInt(1, parentCommentID);
			pstmt.setInt(2, pos);
			
			if(pstmt.executeUpdate() > 0) {
				isOK = true;
			}			
			
			pstmt = conn.prepareStatement(getQL("writeChildComment"));
			pstmt.setInt(1, articleID);
			pstmt.setString(2, authorID);
			pstmt.setString(3, contents);
			pstmt.setInt(4, pos + 1 );
			pstmt.setInt(5, parentCommentID);
			pstmt.setInt(6, depth + 1);
			
			if(pstmt.executeUpdate() > 0) {
				isOK = true;
			}
			
		} catch(SQLException e) {
			e.printStackTrace(); 
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(conn, pstmt, rs);
		}
		
		return isOK;
	}
	
	public List<BoardCommentVO> readAllComments(int articleID) {
		ResultSet rs = null;
		List<BoardCommentVO> list = null;
		
		try {
			conn = pool.getConnection();
			pstmt = conn.prepareStatement(getQL("readComments"));
			pstmt.setInt(1, articleID);
			rs = pstmt.executeQuery();
			list = SQLHelper.putResult(rs, BoardCommentVO.class);
						
		} catch(SQLException e) {
			e.printStackTrace();
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(conn, pstmt);
		}
		
		return list;
	}
	
	/**
	 * 댓글 목록을 JSON 데이터로 반환합니다.
	 * 
	 * @param articleID
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public JSONArray readAllCommentsToJson(int articleID) {
		JSONArray arr = new JSONArray();
		
		List<BoardCommentVO> list = readAllComments(articleID);
		
		if(list != null) {
			for(BoardCommentVO vo : list) {
				JSONObject data = new JSONObject();
				data.put("commentID", vo.getCommentid());
				data.put("pos", vo.getPos());
				data.put("parentID", vo.getParentid());
				data.put("depth", vo.getDepth());
				data.put("author", vo.getCtmnm());
				data.put("create_at", vo.getRegdate().toString());
				data.put("contents", vo.getContent());
				
				arr.add(data);
			}
		}
		
		return arr;
	}
	
	public boolean updateComment(String content, int commentID, String authorID) throws SQLException
	{
		boolean isOK = false;
		ResultSet rs = null;
		
		try {
			conn = pool.getConnection();
			pstmt = conn.prepareStatement(getQL("modifyComment"));
			pstmt.setString(1, content);
			pstmt.setInt(2, commentID);
			pstmt.setString(3, authorID);
			
			if(pstmt.executeUpdate() > 0) {
				isOK = true;
			}
			
		} catch(Exception e) {
			e.printStackTrace(); 
		} finally {
			pool.freeConnection(conn, pstmt, rs);
		}
		
		return isOK;
	}
	
	/**
	 * 해당 파일을 가지고 있는 글을 검색하여 글 번호를 반환합니다.
	 * 
	 * @param filename
	 * @return
	 */
	public int getFileNameToPostNumber(String filename) {
		int postNumber = 0;
		ResultSet rs = null;
		try {
			
			conn = pool.getConnection();
			pstmt = conn.prepareStatement(getQL("searchPostNumber"));
			pstmt.setString(1, "%" + filename + "%");
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				postNumber = rs.getInt("articleID");
			}
			
		} catch(SQLException e) {
			e.printStackTrace();
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(conn, pstmt, rs);
		}
		
		return postNumber;
	}
	
	/**
	 * 전체 코멘트 갯수를 획득합니다.
	 * 
	 * @return
	 */
	public int getCommentCount() {
		int count = 0;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			conn = pool.getConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery("select count(*) from tblQNABoardComments");
			
			boolean ret = rs.next();
			
			if(ret) {
				count = rs.getInt(1);
			}

		} catch(SQLException e) {
			e.printStackTrace();
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(conn, stmt, rs);
		}
		
		return count;
	}
	
	public boolean checkValidByAuthorIDForComment(int parentArticleID, int commentOrder, String authorID) {
		boolean ret = false;
		ResultSet rs = null;
		
		try {
			execute("SET @rownum := 0");
			
			String query = getQL("searchCertainComment");
			
			conn = pool.getConnection();
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, parentArticleID);
			pstmt.setInt(2, commentOrder);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				String targetAuthorID = rs.getString("authorID");
				
				if(targetAuthorID.equals(authorID)) {
					ret = true;
				}
			}
			
		} catch(SQLException e) {
			e.printStackTrace();
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(conn, pstmt, rs);
		}
		
		return ret;
	}
	
	public boolean deleteCertainComment(int parentArticleID, int commentOrder) {
		boolean ret = false;
		
		try {
			
			execute("SET @rownum := 0");
			
			conn = pool.getConnection();
			pstmt = conn.prepareStatement(getQL("deleteCertainComment"));
			pstmt.setInt(1, parentArticleID);
			pstmt.setInt(2, commentOrder);
			
			if( pstmt.executeUpdate() > 0) {
				ret = true;
			}
			
		} catch(SQLException e) {
			e.printStackTrace();
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(conn, pstmt);
		}
		
		return ret;
	}
	
	public boolean deleteCertainComment2(int commentID) {
		boolean ret = false;
		String query = getQL("deleteComment");
		
		try {
			
			conn = pool.getConnection();
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, commentID);
			
			if(pstmt.executeUpdate() > 0) {
				ret = true;
			}
			
		} catch(SQLException e) {
			e.printStackTrace();
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(conn, pstmt);
		}
		
		return ret;
	}
	
	public boolean checkWithAuthority(int postNumber, String id) {
		ResultSet rs = null;
		boolean ret = false;
		try {
			conn = pool.getConnection();
			
			pstmt = conn.prepareStatement("select authorID from tblQNABoard where articleID = ? and authorID = ? ");
			pstmt.setInt(1, postNumber);
			pstmt.setString(2, id);
			
			rs = pstmt.executeQuery();
			
			ret = rs.next();
			
		} catch(SQLException e) {
			e.printStackTrace();
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(conn, pstmt);
		}
		
		return ret;
	}
	
	public boolean increaseRecommendCount(int postNumber, String receiverId) {
		
		boolean ret = false;
		
		try {
			conn = pool.getConnection();
			
			pstmt = conn.prepareStatement(getQL("추천"));
			pstmt.setInt(1, postNumber);
			pstmt.setString(2, receiverId);
			
			if(pstmt.executeUpdate() > 0) {
				ret = true;
			}
		} catch(SQLException e) {
			e.printStackTrace();
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(conn, pstmt);
		}
		
		return ret;
		
	}
	
	public int getRecommandCount(int postNumber) {
		
		ResultSet rs = null;
		int count = 0;
		
		try {
			conn = pool.getConnection();
			
			pstmt = conn.prepareStatement(getQL("추천수 집계"));
			pstmt.setInt(1, postNumber);
			
			while(rs.next()) {
				count = rs.getInt(1);
			}
			
		} catch(SQLException e) {
			e.printStackTrace();
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(conn, pstmt);
		}
		
		return count;
		
	}
}
