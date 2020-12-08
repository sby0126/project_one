package sql;

import java.lang.reflect.Field;
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
					+ " from tblQNABoard"
					+ " order by parentID desc, pos limit ?, ?");
		
		// 게시물 목록을 JSON으로 출력합니다.
		qlList.put("toJSON", "select articleID, articleType, title, authorID, regdate, viewCount, recommandCount from tblQNABoard");
		
		// 조회수 증가
		qlList.put("updatePostViewCount", "update tblQNABoard set viewCount = viewCount + 1 where articleID = ?");
		
		// 글을 읽습니다.
		qlList.put("readPost", "select articleID, articleType, title, authorID, content, regdate, viewCount, recommandCount from tblQNABoard where articleID = ?");

		// 글을 수정합니다.
		qlList.put("updatePost", "UPDATE tblqnaboard SET title = ?, content=?, regdate = NOW() WHERE articleID = ?");
		
		// 글 삭제
		qlList.put("deleteComments", "delete from tblqnaboardcomments WHERE parent_articleID = ?");
		qlList.put("deletePost", "delete from tblqnaboard where articleID = ?");
		
		// 특정 게시물에 있는 코멘트를 읽습니다.
		qlList.put("readComments", 
					"select * " 
				+ "from tblQNABoardComments "
				+ "where parent_articleID = ? order by parentID desc, pos, commentID");
		
		// 댓글을 작성합니다.
		qlList.put("writeComment", 
				"INSERT INTO tblQNABoardComments(parent_articleID, authorID, content, regdate)"
				+ " VALUES(?, ?, ?, NOW())");
		
		// 댓글의 댓글을 작성합니다.
		qlList.put("writeChildComment", "INSERT INTO tblqnaboardcomments(parent_articleID, authorID, content, regdate, pos, parentID, depth) "
				+ "VALUES(?, ?, ?, NOW(), ?, ?, ?)");
		
		// 댓글을 수정합니다.
		qlList.put("modifyComment", "UPDATE tblqnaboardcomments SET content = ? WHERE commentID = ? AND authorID = ?");
		
		// 댓글을 삭제합니다.
		qlList.put("deleteComments", "delete from tblqnaboardcomments WHERE parent_articleID = ?");
		qlList.put("deleteComment", "delete from tblqnaboardcomments WHERE commentID = ?");
		
		// 글을 작성합니다.
		qlList.put("writePost", "insert into tblQNABoard(authorID, articleType, title, content, regdate, imageFileName) VALUES(?, ?, ?, ?, NOW(), ?)");
		
		// 특정 글에 업로드된 파일을 가져옵니다.
		qlList.put("readFile", "SELECT imageFileName FROM tblqnaboard WHERE articleID = ?");
		
		// 파일을 업로드 합니다.
		qlList.put("uploadFile", "UPDATE tblqnaboard SET imageFileName = concat(ifnull(imageFileName, ''), ',', ?) WHERE articleID = ?");
		
		// 이미지를 검색하여 글 번호를 획득합니다.
		qlList.put("searchPostNumber", "SELECT * FROM tblqnaboard WHERE content LIKE ?");
		
		// 특정 댓글을 삭제합니다.
		qlList.put("deleteCertainComment",
		  "DELETE FROM tblqnaboardcomments" 
		+ " where commentID = (SELECT commentID FROM (SELECT ROW_NUMBER() OVER w AS 'row_number', t.* "
		+ " FROM tblqnaboardcomments t"
		+ " WHERE parent_articleID = ?"
		+ " WINDOW w AS (order by parentID desc, pos, commentID)) AS mytbl"
		+ " WHERE mytbl.row_number = ?)");
		
		// 특정 순번의 댓글이 있는지 검색합니다.
		qlList.put("searchCertainComment", "SELECT * FROM (SELECT ROW_NUMBER() OVER w AS 'row_number', t.* FROM tblqnaboardcomments t"
				+ " WHERE parent_articleID = ?"
				+ " WINDOW w AS (order by parentID desc, pos, commentID)) AS mytbl"
				+ " WHERE mytbl.row_number = ?");
		
	}
	
	public String get(String command) {
		return qlList.get(command);
	}
	
}
