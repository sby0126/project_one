package vo;

/**
 * 메일 전송을 처리하는 클래스입니다.
 * 
 * @author 어진석
 *
 */
public class MailHandler {
	
	private String to;

	private String subject;
	private String body;

	/**
	 * 
	 * @param to 받는 사람
	 * @param subject 제목
	 * @param body 본문
	 */
	public MailHandler(String to, String subject, String body) {
		this.to = to;
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

}
