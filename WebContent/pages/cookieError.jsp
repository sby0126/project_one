<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page isErrorPage="true" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>오류 리다이렉션</title>
</head>
<body>
	<script>
		alert("데이터를 찾을 수 없습니다.");
		location.href = "/index.jsp";
	</script>
	<%
		// 쿠키를 삭제합니다.
		
		Cookie cookie = new Cookie("recentShopItems", null);
		cookie.setMaxAge(0);
		
		response.addCookie(cookie);
	%>
	
</body>
</html>