package action;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ActionResult {
	private boolean isRedirect;
	private boolean isError;
	private int code;
	private String message;
		
	public int getCode() {
		return code;
	}
	
	public void setCode(int code) {
		this.code = code;
	}
	
	public String getMessage() {
		return message;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}	
		
	public boolean isError() {
		return isError;
	}

	public void sendError(int code, String message) {
		this.isError = true;
		
		setCode(code);
		setMessage(message);
	}

	private String path;
	
	HttpServletRequest request;
	HttpServletResponse response;
	
	public boolean isRedirect() {
		return isRedirect;
	}
	
	public void setRedirect(boolean isRedirect) {
		this.isRedirect = isRedirect;
	}
	
	public void sendRedirect(String path) {
		setRedirect(true);
		setPath(path);
	}
	
	public void forward(String path) {
		setRedirect(false);
		setPath(path);
	}
	
	public String getPath() {
		return path;
	}
	
	public void setPath(String path) {
		this.path = path;
	}
	
	public ActionResult() {
		this.isRedirect = false;
		this.path = "";
	}
	
	public ActionResult(boolean isRedirect, String path) {
		super();
		this.isRedirect = isRedirect;
		this.path = path;
	}
	
	public void start(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		this.request = req;
		this.response = res;
	}
		
	public void render(String defaultPath) throws ServletException, IOException {
		
		// 오류 처리
		if(isError) {
			response.sendError(code, message);
			return;
		}
		
		if(isRedirect) {
			if(path != null)
				response.sendRedirect(path);
			
			return;
		} else {
			if(path == null) {
				path = defaultPath;
			}
			RequestDispatcher dispatcher = request.getRequestDispatcher(path);
			dispatcher.forward(request, response);					
		}
	}
	
}
