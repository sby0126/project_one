package core.board.notice;

public class BoardBean {
	
	private	int ctxtno;
	private String wrtnm;
	private String ctitle;
	private String ctxt;
	private int pos;
	private String wrtdate;
	private String cpwd;
	private int viewcnt;
	private String filename;
	public int getCtxtno() {
		return ctxtno;
	}
	public void setCtxtno(int num) {
		this.ctxtno = num;
	}
	public String getWrtnm() {
		return wrtnm;
	}
	public void setWrtnm(String name) {
		this.wrtnm = name;
	}
	public String getCtitle() {
		return ctitle;
	}
	public void setCtitle(String title) {
		this.ctitle = title;
	}
	public String getCtxt() {
		return ctxt;
	}
	public void setCtxt(String ctxt) {
		this.ctxt = ctxt;
	}
	public int getPos() {
		return pos;
	}
	public void setPos(int pos) {
		this.pos = pos;
	}
	public String getWrtdate() {
		return wrtdate;
	}
	public void setWrtdate(String date) {
		this.wrtdate = date;
	}
	public String getCpwd() {
		return cpwd;
	}
	public void setCpwd(String pass) {
		this.cpwd = pass;
	}
	public int getViewcnt() {
		return viewcnt;
	}
	public void setViewcnt(int cnt) {
		this.viewcnt = cnt;
	}
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	
	
	
	
}
