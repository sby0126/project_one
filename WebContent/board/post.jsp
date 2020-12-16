<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<% String userId = (String)session.getAttribute("id"); %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<link rel="stylesheet" href="css/post.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<body>


	<div class="post">
    	<h2>쓰기</h2>
		<form name="postFrm" method="post" action="boardWritePro.abc">
			<ul>
				<li><input readonly placeholder="<%=userId%>"></li>
				<input type="hidden" name="name" value="<%=userId%>">
				<li><input name ="subject" placeholder="제목"></li>
				<li><textarea name="content" rows="10" cols="50" placeholder="내용"></textarea></li>
				<li><input type="password" name ="pass" placeholder="비밀번호"></li>
				<li>
					<input type="submit" value="등록">
					<input type="reset" value="다시쓰기">	
					<a href="boardList.abc"><input type="button" value="리스트" ></a>	<!-- 경로수정하기 -->
				</li>
		
		
			</ul>
	
		</form>
	</div>
      
	
</body>
</html>