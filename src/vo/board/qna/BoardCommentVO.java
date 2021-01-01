package vo.board.qna;

public class BoardCommentVO {
	private int commentid;
	private int parentArticleID;
	private String authorid;
	private String content;
	private String regdate;
	private int pos;
	private int parentid;
	private int depth;
    private String ctmnm;
    
	public String getCtmnm() {
		return ctmnm;
	}
	public void setCtmnm(String ctmnm) {
		this.ctmnm = ctmnm;
	}
	public int getCommentid() {
		return commentid;
	}
	public void setCommentid(int commentid) {
		this.commentid = commentid;
	}
	public int getParentArticleID() {
		return parentArticleID;
	}
	public void setParentArticleID(int parentArticleID) {
		this.parentArticleID = parentArticleID;
	}
	public String getAuthorid() {
		return authorid;
	}
	public void setAuthorid(String authorid) {
		this.authorid = authorid;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getRegdate() {
		return regdate;
	}
	public void setRegdate(String regdate) {
		this.regdate = regdate;
	}
	public int getPos() {
		return pos;
	}
	public void setPos(int pos) {
		this.pos = pos;
	}
	public int getParentid() {
		return parentid;
	}
	public void setParentid(int parentID) {
		this.parentid = parentID;
	}
	public int getDepth() {
		return depth;
	}
	public void setDepth(int depth) {
		this.depth = depth;
	}
}
