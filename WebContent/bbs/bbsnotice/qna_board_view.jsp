<%@page import="bbsnotice.*"%>
<%@page import="java.util.*" %>
<%@page language="java" contentType="text/html; charset=UTF-8"%>

<%
	BoardBean article = (BoardBean)request.getAttribute("article");
	BoardReplyBean reply = (BoardReplyBean)request.getAttribute("reply");
    String nowPage = (String)request.getAttribute("page");
    ArrayList<BoardReplyBean> replyList = (ArrayList<BoardReplyBean>)request.getAttribute("replyList");
    PageInfo pageInfo = (PageInfo)request.getAttribute("pageInfo");
	int listCount = pageInfo.getListCount();
	int now_Page = pageInfo.getPage();
	int maxPage = pageInfo.getMaxPage();
	int startPage = pageInfo.getStartPage();
	int endPage = pageInfo.getEndPage();
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>공지사항 게시판</title>
<style type="text/css">
#articleForm {
	width: 500px;
	height: 500px;
	border: 1px solid red;
	margin: auto;
}

h2 {
	text-align: center;
}

#basicInfoArea {
	height: 40px;
	text-align: center;
}

#articleContentArea {
	background: orange;
	margin-top: 20px;
	height: 350px;
	text-align: center;
	overflow: auto;
}

#commandList {
	margin: auto;
	width: 500px;
	text-align: center;
}
</style>
</head>

<body>
 <jsp:include page="header.jsp"></jsp:include>
	<section id="articleForm">
		<h2>글 내용 상세보기</h2>
		<section id="basicInfoArea">
			제 목 :
			<%=article.getCtitle()%>
			첨부파일 :
			<%if(!(article.getFilename()==null)){ %>
			<a href="file_down?downFile=<%=article.getFilename()%>"> <%=article.getFilename() %>
			</a>
			<%} %>
		</section>
		<section id="articleContentArea">
			<%=article.getCtxt() %>
		</section>
		<section id="replyContentArea">
			<table>
				<%
				if(replyList != null && listCount > 0){
				
					for(int i=0;i<replyList.size();i++){
				
				%>
				<tr>
					<td><%=replyList.get(i).getReplynm() %></td>
	
					<td>
						<%if(replyList.get(i).getDepth()!=0){ %> <%for(int a=0;a<=replyList.get(i).getDepth()*2;a++){ %>
						&nbsp; <%} %> ▶ <%}else{ %> ▶ <%} %> 							
					</td>
					<td><%=replyList.get(i).getReplyctxt() %></td>
					<td><%=replyList.get(i).getReplydate() %></td>
					<td>
						<input type="button" onClick="modifyReply()" value="수정">
						<input type="button" onClick="deleteReply()" value="식제">
					</td>
				</tr>
				<%} }%>
			</table>
		</section>
	</section>
	<section id="commandList">
		<a href="boardModifyForm.bo?board_num=<%=article.getCtxtno() %>"> [수정] </a> 
		<a href="boardDeleteForm.bo?board_num=<%=article.getCtxtno() %>&page=<%=now_Page%>"> [삭제] </a> 
		<a href="boardList.bo?page=<%=now_Page%>">[목록]</a>&nbsp;&nbsp;
	</section>
</body>
</html>