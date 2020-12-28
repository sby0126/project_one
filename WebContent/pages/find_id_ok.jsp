<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ page import="java.net.URLDecoder, service.SendMailService, dao.CustomerDAO" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>   
<!DOCTYPE html>
<%
	String id = (String)request.getAttribute("id");
	String name = (String)request.getAttribute("name");
%>
<c:set var="id" value="<%=id %>" />
<c:set var="name" value="<%=name %>" />
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Template</title>
    <link rel="stylesheet" href="../css/style.css">
    <link rel="stylesheet" href="../css/find-password.css">
    <link href="https://fonts.googleapis.com/css2?family=Nanum+Gothic&display=swap" rel="stylesheet">
    <script src="https://kit.fontawesome.com/a99df0f94f.js" crossorigin="anonymous"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
</head>
<body>
    <!-- 컨테이너의 시작 -->
    <div class="container">
        <!-- 헤더의 시작 -->
        <jsp:include page="/header.jsp">
        	<jsp:param value="true" name="isHide"/>
        </jsp:include>

        <!-- 본문의 시작 -->
        <section>
            <!-- 본문이 들어가는 래퍼 -->
            <div class="contents-wrapper">
                <div class="find-password-wrapper">
                    <h3>아이디 찾기</h3>
                        <ul>
	                       	<c:choose>
	                       	<c:when test="<%=id != null %>">
	                           <li>
	                           	<p><%=name%>님의 아이디는 <%=id%>입니다.</p>
	                           </li>
	                           </c:when>
	                           <c:otherwise>
	                           <li>
	                           	<p>이메일 또는 이름이 일치하지 않습니다.</p>
	                           </li>                            
	                           </c:otherwise>
	                       	</c:choose>
                        </ul>             
                </div>
            </div>
        </section>
    </div>
    <!-- 라이트 박스-->
    <div id="light-box-container">
    </div>
    <jsp:include page="/pages/login.jsp"></jsp:include> 
    <!-- 스크립트 --> 
    <script type="module" src="../js/MorePage.js"></script>
</body>
</html>
