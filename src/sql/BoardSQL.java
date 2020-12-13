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
				"select articleID, authorID, articleType, title, regdate, viewCount, recommandCount" 
					+ " from tblQNABoard q, tblCutsomer c"
					+ " where c.ctmid == q.authorID"
					+ " order by parentID desc, pos limit ?, ?");
		
		// 게시물 목록을 JSON으로 출력합니다.
		qlList.put("toJSON", "select articleID, articleType, title, authorID, regdate, viewCount, recommandCount, ctmnm from tblQNABoard q, tblCustomer c where ctmid = authorID");
		
		// 조회수 증가
		qlList.put("updatePostViewCount", "update tblQNABoard set viewCount = viewCount + 1 where articleID = ?");
		
		// 글을 읽습니다.
		qlList.put("readPost", "select articleID, articleType, title, authorID, content, regdate, viewCount, recommandCount, ctmnm from tblQNABoard q, tblCustomer c where articleID = ? and ctmid = authorID");

		// 글을 수정합니다.
		qlList.put("updatePost", "UPDATE tblQNABoard SET title = ?, content=?, regdate = NOW() WHERE articleID = ?");
		
		// 글 삭제
		qlList.put("deleteComments", "delete from tblQNABoardComments WHERE parent_articleID = ?");
		qlList.put("deletePost", "delete from tblQNABoard where articleID = ?");
		
		// 특정 게시물에 있는 코멘트를 읽습니다.
		qlList.put("readComments", 
					"select q.*, ctmnm " 
				+ "from tblQNABoardComments q, tblCustomer c "
				+ "where parent_articleID = ? and ctmid = authorID order by parentID desc, pos, commentID");
		
		// 댓글을 작성합니다.
		qlList.put("writeComment", 
				"INSERT INTO tblQNABoardComments(parent_articleID, authorID, content, regdate)"
				+ " VALUES(?, ?, ?, NOW())");
		
		// 댓글의 댓글을 작성합니다.
		qlList.put("writeChildComment", "INSERT INTO tblQNABoardComments(parent_articleID, authorID, content, regdate, pos, parentID, depth) "
				+ "VALUES(?, ?, ?, NOW(), ?, ?, ?)");
		
		// 댓글을 수정합니다.
		qlList.put("modifyComment", "UPDATE tblQNABoardComments SET content = ? WHERE commentID = ? AND authorID = ?");
		
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
		
	}
	
	public String get(String command) {
		return qlList.get(command);
	}
	
}
