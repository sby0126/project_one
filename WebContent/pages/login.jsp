<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" value="${ pageContext.request.contextPath }" />
    <script src="https://developers.kakao.com/sdk/js/kakao.js"></script>
    <script>
	    Kakao.init('57f13bd28ed3ed731017136a2a67ec40');
	    Kakao.isInitialized();
    </script>
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
                    <input class="input-button" id="find-password-button" type="button" value="아이디 찾기" onclick="location.href='${contextPath}/pages/find_id.jsp'">
                    <input class="input-button" id="find-password-button" type="button" value="비밀번호 찾기" onclick="location.href='${contextPath}/pages/find_password.jsp'">
                </div>
                <div id="naver_id_login"></div>
				<a id="custom-login-btn" href="javascript:kakaoLogin()">
				  <img
				    src="//k.kakaocdn.net/14/dn/btqCn0WEmI3/nijroPfbpCa4at5EIsjyf0/o.jpg"
				    width="185"
				  />
				</a>        
				<div id="fb-root"></div>
				<script async defer crossorigin="anonymous" src="https://connect.facebook.net/ko_KR/sdk.js#xfbml=1&version=v9.0&appId=1607859732738975&autoLogAppEvents=1" nonce="nUSXN8Z4"></script>				
				<fb:login-button 
				  scope="public_profile,email"
				  onlogin="checkLoginState();">
				</fb:login-button>
				<script src="/js/components/kakaoLogin.js"></script>
				<script src="/js/components/facebookLogin.js"></script>
				<script src="/js/components/facebookCallback.js"></script>
            </form>
        </div>
    </div>
    <img src='data:image/gif;base64,R0lGODlhAQABAIAAAAAAAP///yH5BAEAAAAALAAAAAABAAEAAAIBRAA7' onload='console.log("로그인 창이 바인드 되었어요.");this.parentNode.removeChild(this);'>
<%--     <jsp:include page="/pages/recent-product-container.jsp"> --%>
<%--     	<jsp:param value="${ pageContext.request.contextPath }" name="contextPath"/> --%>
<%--     </jsp:include> --%>