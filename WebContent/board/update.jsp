<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="freeBoard.vo.BoardBean"%>
<%
request.setCharacterEncoding("UTF-8");


	BoardBean article = (BoardBean)request.getAttribute("article");
	String nowPage = (String)request.getAttribute("page");
%>
<html>
<head>
<title>JSP Board</title>

<link rel="stylesheet" href="css/update.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
</head>
<body>

	<div class="updateBox">
    	<h2>수정</h2>
		<form name="updateFrm" id="updateFrm" method="post" action="boardUpdatePro.abc">
			<ul class="readBox2">
				<li><span>이름</span><span><input placeholder="<%=article.getName() %>" readonly></span> </li>
				<li><span>제목</span><span><input name="subject" value="<%=article.getSubject() %>"></span> </li>
				<li><span>등록날짜</span><span><input placeholder="<%=article.getDate() %>" readonly></span> </li>
				<li><textarea rows="10" cols="50" name="content"><%=article.getContent() %></textarea></li>
			</ul>
				<input type="submit" value="수정">
				<input type="hidden" name="num" value="<%=article.getNum()%>">
				<input type="hidden" name="pass" value="<%=article.getPass()%>">
				<input type="hidden" name="ref" value="<%=article.getRe_ref()%>">
				<input type="hidden" name="lev" value="<%=article.getRe_lev()%>">
				<input type="hidden" name="seq" value="<%=article.getRe_seq()%>">
				<input type="hidden" name="page" value="<%=nowPage%>">
				<input type="hidden" name="name" value="<%=article.getName() %>" >
				<input type="hidden" name="regDate" value="<%=article.getDate() %>" >
				
		</form>
	</div>
	
</body>
</html>