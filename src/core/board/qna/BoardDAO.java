package core.board.qna;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import core.DBConnectionMgr;
import core.SQLHelper;

/***
 * 테이블을 생성합니다.
 */
public class BoardDAO {
	private DBConnectionMgr pool; 
	private Connection conn;
	private PreparedStatement pstmt;
	
	protected HashMap<String, String> qlList;
	
	/***
	 * 
	 */
	public BoardDAO() {
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
	
	/***
	 * 
	 */
	public void initWithSQL() {
		qlList = new HashMap<String, String>();
		
		qlList.put("count", "select count(*) from tblQNABoard");
		qlList.put("list", 
				"select articleID, authorID, articleType, title, regdate, viewCount, recommandCount" 
					+ " from tblQNABoard"
					+ " order by parentID desc, pos limit ?, ?");
		
		// 게시물 목록을 JSON으로 출력합니다.
		qlList.put("toJSON", "select articleID, articleType, title, authorID, regdate, viewCount, recommandCount from tblQNABoard");
		
		// 조회수 증가
		qlList.put("updatePostViewCount", "update tblQNABoard set viewCount = viewCount + 1 where articleID = ?");
		
		// 글을 읽습니다.
		qlList.put("readPost", "select articleID, articleType, title, authorID, content, regdate, viewCount, recommandCount from tblQNABoard where articleID = ?");
		// 글 수정
		qlList.put("updatePost", "UPDATE tblqnaboard SET title = ?, content=?, regdate = NOW() WHERE articleID = ?");
		
		// 글 삭제
		qlList.put("deleteComments", "delete from tblqnaboardcomments WHERE parent_articleID = ?");
		qlList.put("deletePost", "delete from tblqnaboard where articleID = ?");
		
		// 특정 게시물에 있는 코멘트를 읽습니다.
		qlList.put("readComments", 
					"select authorID, content, regdate, pos, parentID, depth " 
				+ "from tblQNABoardComments "
				+ "where parent_articleID = ? order by parentID desc, pos, commentID");
		
		// 댓글을 작성합니다.
		qlList.put("writeComment", 
				"INSERT INTO tblQNABoardComments(parent_articleID, authorID, content, regdate)"
				+ " VALUES(?, ?, ?, NOW())");
		
		// 댓글을 수정합니다.
		qlList.put("modifyComment", "UPDATE tblqnaboardcomments SET content = ? WHERE commentID = ? AND authorID = ?");
		
		// 글을 작성합니다.
		qlList.put("writePost", "insert into tblQNABoard(authorID, articleType, title, content, regdate, imageFileName) VALUES(?, ?, ?, ?, NOW(), ?)");
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
			
			conn.commit();
			
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
					obj.put("postTitle", vo.getTitle());
					obj.put("name", vo.getAuthorid());
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
				    obj.put("author", vo.getAuthorid());
				    
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
			execute(getQL("updatePostViewCount"), articleID);
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
	public void writeComment(int articleID, String authorID, String contents) {
		
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
				data.put("author", vo.getAuthorid());
				data.put("create_at", vo.getRegdate().toString());
				data.put("contents", vo.getContent());
				
				arr.add(data);
			}
		}
		
		return arr;
	}

}
