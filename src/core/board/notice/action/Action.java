package core.board.notice.action;

import javax.servlet.http.*;

import core.board.notice.ActionForward;

public interface Action {
	public ActionForward execute(HttpServletRequest request,HttpServletResponse response) throws Exception;
}
