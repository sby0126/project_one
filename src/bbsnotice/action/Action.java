package bbsnotice.action;

import javax.servlet.http.*;

import bbsnotice.ActionForward;

public interface Action {
	public ActionForward execute(HttpServletRequest request,HttpServletResponse response) throws Exception;
}
