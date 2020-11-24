<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String errorMessage = (String)request.getAttribute("errorMessage");
	String url = (String)request.getAttribute("url");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<script>
	alert("<%= errorMessage %>");
	location.href = "<%= url %>";
</script>
</body>
</html>