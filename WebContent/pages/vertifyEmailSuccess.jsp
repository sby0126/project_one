<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ page import="java.net.URLDecoder, service.SendMailService, dao.CustomerDAO" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>   
<c:set var="contextPath" value="${ pageContext.request.contextPath }" />
<!DOCTYPE html>
<%
	String tempPassword = (String)session.getAttribute("tempPassword");
	session.removeAttribute("tempPassword");
%>
<c:set var="tempPassword" value="<%= tempPassword %>" />
<c:if test="${tempPassword == null }">
	<script>
		alert("잘못된 값이 들어왔습니다");
		history.go(-1);
	</script>
</c:if>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Template</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/find-password.css">
    <link href="https://fonts.googleapis.com/css2?family=Nanum+Gothic&display=swap" rel="stylesheet">
    <script src="https://kit.fontawesome.com/a99df0f94f.js" crossorigin="anonymous"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
</head>
<body>
    <!-- 컨테이너의 시작 -->
    <div class="container">
        <jsp:include page="/header.jsp">
        	<jsp:param value="true" name="isHide"/>
        </jsp:include>

        <!-- 본문의 시작 -->
        <section>
            <!-- 본문이 들어가는 래퍼 -->
            <div class="contents-wrapper">
                <div class="email-vertify-success-wrapper">
					<p>본인 인증이 완료되었습니다</p>
					<p>변경된 임시 비밀번호는 <strong><%= tempPassword %></strong> 입니다. </p>
					<p>변경된 비밀번호로 로그인해주시기 바랍니다.</p>
                </div>
            </div>
        </section>
    </div>
    <!-- 라이트 박스-->
    <div id="light-box-container">
    </div>
    <jsp:include page="/pages/login.jsp"></jsp:include> 
    <!-- 스크립트 --> 
    <script type="module" src="${pageContext.request.contextPath}/js/MorePage.js"></script>
</body>
</html>
