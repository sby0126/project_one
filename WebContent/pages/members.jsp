<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
 <%@ page import="java.util.List, core.*" %>
<%@ page import="core.*"%>
<%@ page import="controller.*"%>
<%@ page import="dao.*"%>
<%@ page import="service.*"%>
<%@ page import="vo.*"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>Insert title here</title>
</head>
<body>
	<%
		List<CustomerVO> customerList = (List<CustomerVO>)request.getAttribute("customerList");
	%>
	
	<%
		if(customerList != null) 
		{
			for(CustomerVO c : customerList) {
	%>
		
		회원 ID : <%= c.getId() %><br>
		회원 주소 : <%= c.getAddress() %><br>
		
	<%
			}
		}
	%>
</body>
</html>