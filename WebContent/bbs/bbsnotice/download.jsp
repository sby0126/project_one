<%@ page contentType="application;charset=EUC-KR" %>
<jsp:useBean id="bMgr" class="core.board.notice.BoardMgr"/>
<%
	bMgr.downLoad(request, response, out, pageContext);
%>