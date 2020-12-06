package action;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ErrorResult extends ActionResult {
	
	private String errorMessage;
	private String redirectPage;
	
	public ErrorResult(String errorMessage, String redirectPage) {
		super();
		setPath("/pages/error.jsp");
		setErrorMessage(errorMessage);
		setRedirectPageAfterError(redirectPage);
	}
	
	@Override
	public ErrorResult start(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		super.start(req, res);
		req.setAttribute("errorMessage", errorMessage);
		req.setAttribute("url", redirectPage);		
		
		return this;
	}
	
	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	
	public void setRedirectPageAfterError(String redirectPage) {
		this.redirectPage = redirectPage; 
	}
	
}
