<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="freeBoard.vo.BoardBean"%>
<%
	BoardBean article = (BoardBean)request.getAttribute("article");
    String nowPage = (String)request.getAttribute("page");
    String userId = (String)session.getAttribute("id");
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="css/reply.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
</head>
<body>

	<div class="reply">
    	<h2>답글</h2>
		<form name="replyFrm" method="post" action="boardReplyPro.abc">
			<ul>
				<input type="hidden" name="page" value="<%=nowPage %>" > 
				<input type="hidden" name="BOARD_NUM" value="<%=article.getNum() %>">
        		<input type="hidden" name="BOARD_RE_REF" value="<%=article.getRe_ref() %>"> 
        		<input type="hidden" name="BOARD_RE_LEV" value="<%=article.getRe_lev() %>"> 
        		<input type="hidden" name="BOARD_RE_SEQ" value="<%=article.getRe_seq() %>">
				<li><input placeholder="<%=userId %>" readonly></li>
				<input type="hidden" name="name" value="<%=userId %>">
				<li><input name ="subject" placeholder="제목"></li>
				<li><textarea name="content" rows="10" cols="50" placeholder="내용"></textarea></li>
				<li><input type="password" name ="pass" placeholder="비밀번호"></li>
				<li>
					<input type="submit" value="등록">
					<input type="reset" value="다시쓰기">	
					<a href="boardList.abc?page=<%=nowPage%>"><input type="button" value="리스트" ></a>	<!-- 경로수정하기 -->
				</li>
			</ul>
			
	
		</form>
	</div>
	
	<script>
		
	</script>
	
</body>
</html>