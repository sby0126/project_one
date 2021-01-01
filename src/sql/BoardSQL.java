package sql;

import java.util.HashMap;

public class BoardSQL {
	
	private HashMap<String, String> qlList;
	
	
	public BoardSQL() {
		initWithData();
	}
	
	public void initWithData() {
		qlList = new HashMap<String, String>();
		
		qlList.put("count", "select count(*) from tblQNABoard");
		qlList.put("list", 
				"select articleID, authorID, articleType, title, CONVERT(regdate, DATE) AS regdate, viewCount, recommandCount" 
					+ " from tblQNABoard q, tblCutsomer c"
					+ " where c.ctmid == q.authorID"
					+ " order by parentID desc, pos limit ?, ?");
		
		// 게시물 목록을 JSON으로 출력합니다 (추천수 집계)
		qlList.put("toJSON", "select articleID, articleType, title, authorID, CONVERT(regdate, DATE) AS regdate, viewCount, (select COUNT(*) from tblQnaBoardRec where board_id = q.articleID) as recommandCount, ctmnm"
				+ " from tblQNABoard q, tblCustomer c, tblQnaBoardRec r"
				+ " where ctmid = authorID"
				+ " GROUP BY articleID");
		
		// 게시물 목록을 JSON으로 출력합니다 (추천수 집계)
		qlList.put("toJSONFilter", "select articleID, articleType, title, authorID, CONVERT(regdate, DATE) AS regdate, viewCount, (select COUNT(*) from tblQnaBoardRec where board_id = q.articleID) as recommandCount, ctmnm"
				+ " from tblQNABoard q, tblCustomer c, tblQnaBoardRec r"
				+ " where ctmid = authorID AND title LIKE ?"
				+ " GROUP BY articleID");
		
		// 조회수 증가
		qlList.put("조회수증감", "update tblQNABoard set viewCount = viewCount + 1 where articleID = ?");
		
		// 글을 읽습니다.
		qlList.put("readPost", "select articleID, articleType, title, authorID, content, time_ago(regdate) as regdate, viewCount, recommandCount, ctmnm from tblQNABoard q, tblCustomer c where articleID = ? and ctmid = authorID");

		// 글을 수정합니다.
		qlList.put("updatePost", "UPDATE tblQNABoard SET title = ?, content=?, regdate = NOW() WHERE articleID = ?");
		
		// 글 삭제
		qlList.put("deleteComments", "delete from tblQNABoardComments WHERE parent_articleID = ?");
		qlList.put("deletePost", "delete from tblQNABoard where articleID = ?");
		
		// 특정 게시물에 있는 코멘트를 읽습니다.
//		qlList.put("readComments", 
//					"select q.*, ctmnm, ( (parentID * 5) + depth * 0.015) AS level " 
//				+ "from tblQNABoardComments q, tblCustomer c "
//				+ "where parent_articleID = ? and ctmid = authorID ORDER BY level, pos");
				
		qlList.put("readComments", "select q.*, ctmnm,  time_ago(q.regdate) as regdate" 
				+ " from tblQNABoardComments q, tblCustomer c "
				+ " WHERE parent_articleID = ?"
				+ " and ctmid = authorID "
				+ " ORDER BY parentID asc, pos");
		
		qlList.put("maxCommentID", "SELECT AUTO_INCREMENT FROM information_schema.TABLES WHERE TABLE_SCHEMA = 'mydb' AND TABLE_NAME = 'tblQNABoardComments'");
		
		// 댓글을 작성합니다.
		qlList.put("writeComment", 
				"INSERT INTO tblQNABoardComments(parent_articleID, authorID, content, regdate, parentID)"
				+ " VALUES(?, ?, ?, NOW(), ?)");
		
		// 댓글의 댓글을 작성합니다.
		qlList.put("writeChildComment", "INSERT INTO tblQNABoardComments(parent_articleID, authorID, content, regdate, pos, parentID, depth) "
				+ "VALUES(?, ?, ?, NOW(), ?, ?, ?)");
		
		// 댓글을 수정합니다.
		qlList.put("modifyComment", "UPDATE tblQNABoardComments SET content = ? WHERE commentID = ? AND authorID = ?");
		
		// 댓글 작성 시 댓글 업데이트
		qlList.put("updateCommentPos", "update tblQNABoardComments set pos = pos + 1 WHERE parentID = ? and pos > ?");
		
		// 댓글을 삭제합니다.
		qlList.put("deleteComments", "delete from tblQNABoardComments WHERE parent_articleID = ?");
		qlList.put("deleteComment", "delete from tblQNABoardComments WHERE commentID = ?");
		
		// 글을 작성합니다.
		qlList.put("writePost", "insert into tblQNABoard(authorID, articleType, title, content, regdate, imageFileName) VALUES(?, ?, ?, ?, NOW(), ?)");
		
		// 특정 글에 업로드된 파일을 가져옵니다.
		qlList.put("readFile", "SELECT imageFileName FROM tblQNABoard WHERE articleID = ?");
		
		// 파일을 업로드 합니다.
		qlList.put("uploadFile", "UPDATE tblQNABoard SET imageFileName = concat(ifnull(imageFileName, ''), ',', ?) WHERE articleID = ?");
		
		// 이미지를 검색하여 글 번호를 획득합니다.
		qlList.put("searchPostNumber", "SELECT * FROM tblQNABoard WHERE content LIKE ?");
		
		// 특정 댓글을 삭제합니다.
		qlList.put("deleteCertainComment",
		  "DELETE FROM tblQNABoardComments" 
		+ " where commentID = (SELECT commentID FROM (SELECT ROW_NUMBER() OVER w AS 'row_number', t.* "
		+ " FROM tblQNABoardComments t"
		+ " WHERE parent_articleID = ?"
		+ " WINDOW w AS (order by parentID desc, pos, commentID)) AS mytbl"
		+ " WHERE mytbl.row_number = ?)");
		
		// 특정 순번의 댓글이 있는지 검색합니다.
		qlList.put("searchCertainComment", "SELECT * FROM (SELECT ROW_NUMBER() OVER w AS 'row_number', t.* FROM tblQNABoardComments t"
				+ " WHERE parent_articleID = ?"
				+ " WINDOW w AS (order by parentID desc, pos, commentID)) AS mytbl"
				+ " WHERE mytbl.row_number = ?");
		
		// 추천수를 올립니다.
		qlList.put("추천", "INSERT INTO tblQnaBoardRec(board_id, receiver_id) VALUES(?, ?)");
		
		// 추천수 집계
		qlList.put("추천수 집계", "SELECT COUNT(*) FROM tblQnaBoardRec WHERE board_id = ?");
		
	}
	
	public String get(String command) {
		return qlList.get(command);
	}
	
}
