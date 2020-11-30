<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@page import="core.board.notice.BoardBean"%>
<%@page import="java.util.Vector"%>
<jsp:useBean id="bMgr" class="core.board.notice.BoardMgr" />
<%
	request.setCharacterEncoding("EUC-KR");
	
	int totalRecord = 0;		// 전체 게시물 수
	int numPerPage = 10;		// 페이지 당 글 갯수
	int pagePerBlock = 15;		// 블럭 당 페이지 수
	
	int totalPage = 0;			// 전체 페이지 수
	int totalBlock = 0;			// 전체 블럭 수
	
	int nowPage = 1;			// 현 페이지
	int nowBlock = 1;			// 현 블럭
	int start = 0;				// board 테이블의 select 시작번호
	int end = 10;				// 시작번호로부터 가져올 select 갯수   = number page ?
	
	int listSize = 0; 			// 현재 읽어온 게시물 수
	
	String keyWord = "", keyField = "";
	Vector<BoardBean> vlist = null;
	
	if(request.getParameter("keyWord") != null) {
		keyWord = request.getParameter("keyWord");
		keyField = request.getParameter("keyField");
	}
	
	if(request.getParameter("reload") != null) {
		if(request.getParameter("reload").equals("true")) {
			keyWord = "";
			keyField = "";
		}
	}
	
	if(request.getParameter("nowPage") != null) {
		nowPage = Integer.parseInt(request.getParameter("nowPage"));
	}
	start = (nowPage * numPerPage) - numPerPage;
	end = numPerPage;
	
	totalRecord = bMgr.getTotalCount(keyField, keyWord);
	totalPage = (int)Math.ceil((double)totalRecord/numPerPage);		// 전체 페이지 수
	nowBlock = (int)Math.ceil((double)nowPage/pagePerBlock);		// 현재 블럭 계산
	totalBlock = (int)Math.ceil((double)totalPage/pagePerBlock);	// 전체 블럭 계산	
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>JSP Board</title>
<link href="style.css" rel="stylesheet" type="text/css">
<script>
	function list() {
		document.listFrm.action = "list.jsp";
		document.listFrm.submit();
	}
	
	function paging(page) {
		document.readFrm.nowPage.value = page;
		document.readFrm.submit();
	}
	
	function block(value) {
		document.readFrm.nowPage.value = <%=pagePerBlock%> * (value - 1) + 1;
		document.readFrm.submit();
	}
	
	function read(num) {
		document.readFrm.num.value = num;
		document.readFrm.action = "read.jsp";
		document.readFrm.submit();
	}
	
	function check() {
		if(document.searchFrm.keyWord.value == "") {
			alert("검색어를 입력하세요");
			document.searchFrm.keyWord.focus();
			return;			
		}
		document.searchFrm.submit();
	}	
</script>
</head>
<body bgcolor="#FFFFCC">
	<div align="center">
		<br/>
		<h2>JSP Board</h2>
		<br/>
		<table align="center" border="0" width="80%">
			<tr>
				<td>Total : <%=totalRecord%>개 글(<font color="red"><%=nowPage%>/<%=totalPage%>Pages</font>)</td>
			</tr>
		</table>
		<table align="center" border="0" cellspacing="0" cellpadding="3">
			<tr>
				<td align="center" colspan="2">
					<%
						vlist = bMgr.getBoardList(keyField, keyWord, start, end);
						listSize = vlist.size();			// 화면에 출력될 게시물 수
						if(vlist.isEmpty()) {
							out.println("등록된 게시물이 없습니다.");
						} else {
					%>
					<table border="0" width="100%" cellpadding="2" cellspacing="0">
						<tr align="center" bgcolor="#D0D0D0" height="120%">
							<td width="80">번 호</td>
							<td width="330">제 목</td>
							<td width="70">이 름</td>
							<td width="150">날 짜</td>
							<td width="70">조회수</td>
						</tr>
						<%
							for (int i = 0; i < numPerPage; i++) {
								if(i == listSize) break;	
								BoardBean bean = vlist.get(i);
								int num = bean.getCtxtno();
								String name = bean.getWrtnm();
								String subject = bean.getCtitle();
								String regdate = bean.getWrtdate();
								int count = bean.getViewcnt();
								
						%>
								<tr>
									<td align="center"><%=totalRecord - ((nowPage - 1) * numPerPage) - i%></td>
									<td>
										<a href="javascript:read('<%=num%>')"><%=subject%></a>
									</td>
									<td align="center"><%=name%></td>
									<td align="center"><%=regdate%></td>
									<td align="center"><%=count%></td>
								</tr>
						<% } %>			<!-- 115 line for 종료 -->
					</table>
					<%	} %>		<!-- 104 line else 종료 -->
				</td>
			</tr>
			<tr>
				<td colspan="2"><br/><br/></td>
			</tr>
			<tr>
				<td>
				<!-- 페이징 및 블록 처리 -->
					<%
						int pageStart = (nowBlock - 1) * pagePerBlock + 1;		//페이지 하단 시작 번호
						int pageEnd = ((pageStart + pagePerBlock) < totalPage) ? (pageStart + pagePerBlock) : totalPage + 1; 	// 페이지 하단 끝 번호
						if(totalPage != 0) {
							if (nowBlock > 1) {	%>
								<a href="javascript:block('<%=nowBlock-1%>')">prev...</a> 
							<% } %>&nbsp;
							<%
							for ( ; pageStart < pageEnd; pageStart++) {
							%>
								<a href="javascript:paging('<%=pageStart%>')">
								<%
								if(pageStart == nowPage) { %> <font color="red"> <% } %>
								[<%=pageStart%>]
								<%if(pageStart == nowPage) {%> </font> <% } %>
								</a>
						<% } %><!-- 162 line for 종료 --> &nbsp;	
						<% if(totalBlock > nowBlock) {%>
							<a href="javascript:block('<%=nowBlock + 1%>')">...next</a>
						<% } %> &nbsp;
					<% } %>
				</td>
				<!-- 페이징 밀 블록 처리 종료 -->
				<td align="right">
					<a href="post.jsp">[글쓰기]</a>
					<a href="javascript:list()">[처음으로]</a>
				</td>
			</tr>
		</table>
		<hr width="80%">
		<form name="searchFrm" method="post" action="list.jsp">
			<table border="0" width="527" align="center" cellpadding="4" cellspacing="0">
				<tr>
					<td align="center" valign="bottom">
						<select name=keyField size="1" height="30">
							<option value="name">이름</option>
							<option value="title">제목</option>
							<option value="ctxt">내용</option>
						</select>
						<input size="16" name="keyWord">
						<input type="button" value="찾기" onClick="javascript:check()">
						<input type="hidden" name="nowPage" value="1">
					</td>
				</tr>
			</table>
		</form>
		<form name="listFrm" method="post">
			<input type="hidden" name="reload" value="true">
			<input type="hidden" name="nowPage" value="1">
		</form>
		<form name="readFrm" method="get">
			<input type="hidden" name="num">
			<input type="hidden" name="nowPage" value="<%=nowPage%>">
			<input type="hidden" name="keyField" value="<%=keyField%>">
			<input type="hidden" name="keyWord" value="<%=keyWord%>">
		</form>
	</div>
</body>
</html>