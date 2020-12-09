package vo;

public class MailHandler {
	
	private String to;
	private String username;

	private String subject;
	private String body;
	
	public MailHandler(String to, String username, String subject, String body) {
		this.to = to;
		this.username = username;
		this.subject = subject;
		this.body = body;
	}

	public String getTo() {
		return to;
	}
	
	public void setTo(String to) {
		this.to = to;
	}
	
	public String getSubject() {
		return subject;
	}
	
	public void setSubject(String subject) {
		this.subject = subject;
	}
	
	public String getBody() {
		return body;
	}
	
	public void setBody(String body) {
		this.body = body;
	}
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}	

}
