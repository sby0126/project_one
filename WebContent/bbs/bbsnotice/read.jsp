<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@page import="java.util.Vector"%>
<%@ page import="core.board.notice.BoardBean"%>
<%@ page import="core.board.notice.BoardReplyBean"%>
<jsp:useBean id="bMgr" class="core.board.notice.BoardMgr" />
<%
	request.setCharacterEncoding("EUC-KR");
	int num = Integer.parseInt(request.getParameter("num"));
	String nowPage = request.getParameter("nowPage");
	String keyField = request.getParameter("keyField");
	String keyWord = request.getParameter("keyWord");
	bMgr.upCount(num);
	BoardBean bean = bMgr.getBoard(num);
	String name = bean.getWrtnm();
	String title = bean.getCtitle();
	String ctxt = bean.getCtxt();
	String date = bean.getWrtdate();
	int reply = bean.getReply();
	int count = bean.getViewcnt();
	String filename = bean.getFilename();
	session.setAttribute("bean", bean);
	
	Vector<BoardReplyBean> vlist = null;
	BoardReplyBean rbean = bMgr.getReply(num); 
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>JSP Board</title>
<script type="text/javascript">
	function list() {
		document.listFrm.submit();
	}


	function down(filename) {
		document.downFrm.filename.value = filename;
		document.downFrm.submit();
	}
</script>
</head>
<body bgcolor="#FFFFCC">
	<br/><br/>
	<table align="center" width="70%" border="0" cellspacing="3" cellpadding="0">
		<tr>
			<td bgcolor="#9CA2EE" height="25" align="center">글읽기</td>
		</tr>
		<tr>
			<td colspan="2">
				<table border="0" cellpadding="3" cellspacing="0" width="100%">
					<tr>
						<td align="center" bgcolor="#DDDDDD" width="10%">이 름</td>
						<td bgcolor="#FFFFE8"><%=name%></td>
						<td align="center" bgcolor="#DDDDDD" width="10%">등록날짜</td>
						<td bgcolor="#FFFFE8"><%=date%></td>
					</tr>
					<tr>
						<td align="center" bgcolor="#DDDDDD">제 목</td>
						<td bgcolor="#FFFFE8" colspan="3"><%=title%></td>
					</tr>
					<tr>
						<td align="center" bgcolor="#DDDDDD">첨부파일</td>
						<td bgcolor="#FFFFE8" colspan="3">
						<% if(filename != null && !filename.equals("")) { %>
							<a href="javascript:down('<%=filename%>')"></a>&nbsp;&nbsp;
							<% } else { %> 등록된 파일이 없습니다. <% } %>
						</td>
					</tr>
					<tr>
						<td colspan="4"><br/><pre><%=ctxt%></pre><br/></td>
					</tr>
					<tr>
						<td colspan="4" align="right">
							 / 조회수 : <%=count%>
						</td>
					</tr>
					<tr>
						<td colspan="4">
							<table align="center" border="0" cellspacing="0" cellpadding="3">
								<tr>
									<td align="center" colspan="5" rowspan="5">
									<%
										vlist = bMgr.getReplyList(num);
										int listSize = vlist.size();			// 화면에 출력될 게시물 수
										
										
										if(!vlist.isEmpty()) {
											
										} else {
									%>
						
								<%
									for (int i = 0 ; i != listSize; i++) {
										if(i == listSize) break;	
										BoardReplyBean rbean = vlist.get(i);
										int num = bean.getCtxtno();
										String name = bean.getWrtnm();
										String title = bean.getCtitle();
										int reply = bean.getReply();
										String date = bean.getWrtdate();
										int count = bean.getViewcnt();
										
								%>
								<tr>
									<td align="center"><%=totalRecord - ((nowPage - 1) * numPerPage) - i%></td>
									<td>
										<a href="javascript:read('<%=num%>')"><%=title%></a>
									</td>
									<td align="center"><%=name%></td>
									<td align="center"><%=reply%></td>
									<td align="center"><%=date%></td>
									<td align="center"><%=count%></td>
								</tr>
							<% } %>			<!-- 115 line for 종료 -->
						</table>
						<%	} %>
					</tr> 
				</table>
			</td>
		</tr>
		<tr>
			<td align="center" colspan="2">
				<hr/>
				 [ <a href="javascript:list()">리스트</a> |
				  <a href="update.jsp?nowPage=<%=nowPage%>&num=<%=num%>">수 정</a> |
				  <a href="delete.jsp?nowPage=<%=nowPage%>&num=<%=num%>">삭 제</a>]<br/>
			</td>
		</tr>
	</table>
	<form name="downFrm" action="download.jsp" method="post">
		<input type="hidden" name="filename">
	</form>
	<form name="listFrm" method="post" action="list.jsp">
		<input type="hidden" name="nowPage" value="<%=nowPage%>">
		<%if (!(keyWord == null || keyWord.equals(""))) { %>
		<input type="hidden" name="keyField" value="<%=keyField%>">
		<input type="hidden" name="keyWord" value="<%=keyWord%>">
		<% } %>
	</form>
</body>
</html>