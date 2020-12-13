<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" value="${ pageContext.request.contextPath }" />  
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login</title>
    <link rel="stylesheet" href="../css/style.css">
    <script src="https://kit.fontawesome.com/a99df0f94f.js" crossorigin="anonymous"></script>
</head>
<body>
    <div class="floating-login-view-wrapper">
        <div class="floating-login-view">
            <!-- 닫기 버튼 -->
            <div class="floating-login-view-right">
                <button id="close-login-view">X</button>
            </div>
            <!-- 로그인 폼 -->
            <form method="POST" action="/members/login.do">
                <p class="floating-login-view-input-group">
                    <label for="id"><i class="fas fa-user-alt"></i></label><input type="text" name="id" autocomplete="id" class="input-non-border-box" id="floating-login-view-login-id">
                </p>
                <p class="floating-login-view-input-group"><label for="pw"><i class="fas fa-lock"></i></label><input type="password" name="pw" class="input-non-border-box" id="floating-login-view-login-pw"></p>
                <input class="input-button" type="submit" value="Login">
                <div class="floating-login-view-button-panel floating-login-view-input-group">
                    <input class="input-button" id="join-button" type="button" value="회원가입">
                    <input class="input-button" id="find-password-button" type="button" value="비밀번호 찾기" onclick="location.href='${contextPath}/pages/find_password.jsp'">
                </div>
            </form>
        </div>
    </div>
    <img src='data:image/gif;base64,R0lGODlhAQABAIAAAAAAAP///yH5BAEAAAAALAAAAAABAAEAAAIBRAA7' onload='console.log("로그인 창이 바인드 되었어요.");this.parentNode.removeChild(this);'>
</body>
</html>
