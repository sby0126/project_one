package freeBoard.action;

import javax.servlet.http.*;

import freeBoard.vo.ActionForward;

public interface Action {
	public <ActionForward> ActionForward execute(HttpServletRequest request,HttpServletResponse response) throws Exception;
}
