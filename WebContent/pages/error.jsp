<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%
	String errorMessage = request.getAttribute("errorMessage");
	String url = request.getAttribute("url");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>Insert title here</title>
</head>
<body>
<script>
	alert("<%= errorMessage %>");
	location.href = "<%= url %>";
</script>
</body>
</html>