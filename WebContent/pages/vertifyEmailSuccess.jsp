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
		<header>
			<div class="header-wrapper">
				<!-- 헤더 왼쪽 : 로고 -->
				<div class="header-left">
					<div class="" id="logo">
						<a href="${contextPath}/index.jsp">
							<img src="https://dummyimage.com/64x64/000/fff" alt="">
						</a>
					</div>
					<a href="#">WOMEN</a> <a href="#">MEN</a>
				</div>
				<!-- 헤더 중앙 : 메뉴 -->
				<div class="header-center">
					<a href="${contextPath}/index.jsp">SHOP</a>
					<a href="${contextPath}/item.jsp" target="_self">ITEM</a>
					<a href="${contextPath}/sale.jsp">SALE</a>
					<a href="#">MORE</a>
				</div>
				<!-- 헤더 오른쪽 : 로그인 / 검색 -->
				<div class="header-right">
					<%
                		String id = (String)session.getAttribute("id");
                	%>
					<c:set var="id" value="<%=id %>" />
					<h2>
						<c:out value='${pageContext.request.contextPath}'></c:out>
					</h2>
					<c:choose>
						<c:when test="${id != null}">
							<a class="header-right-login-button"
								href="/members/modifyMemberForm.do?id=${id}">회원 정보 수정</a>
							<c:if test="${id=='admin'}">
								<a class="header-right-login-button" href="/admin">관리자 페이지</a>
							</c:if>
							<button class="header-right-login-button" id="logout-button"
								onclick="javascript:location.href='/members/logout.do'">로그아웃</button>
						</c:when>
						<c:otherwise>
							<button class="header-right-login-button">로그인</button>
						</c:otherwise>
					</c:choose>
					<input type="text" class="input-non-border-box" name="" id=""
						placeholder="검색어를 입력하세요">
				</div>
			</div>
			<!-- 숨겨진 메뉴 -->
			<div class="header-popup-container">
				<ul class="header-menu-list">
					<li class="menu"><a href="${contextPath}/pages/Recently_viewed_shop.jsp"
						class="menu-link">
							<div class="menu-icon"></div>
							<p class="menu-title">
								최근 본 샵 <em>0</em>
							</p>
					</a></li>
					<li class="menu"><a href="${contextPath}/pages/Recently_viewde_item.jsp"
						class="menu-link">
							<div class="menu-icon"></div>
							<p class="menu-title">
								최근 본 상품 <em>0</em>
							</p>
					</a></li>
					<li class="menu"><a href="#" class="menu-link">
							<div class="menu-icon"></div>
							<p class="menu-title">MY SHOP</p>
					</a></li>
					<li class="menu"><a href="${contextPath}/pages/Interested_item.jsp"
						class="menu-link">
							<div class="menu-icon"></div>
							<p class="menu-title">관심 상품</p>
					</a></li>
					<li class="menu"><a href="${contextPath}/pages/board-default.jsp"
						class="menu-link">
							<div class="menu-icon"></div>
							<p class="menu-title">1:1 문의</p>
					</a></li>
					<li class="menu"><a href="${contextPath}/pages/map.jsp" class="menu-link">
							<div class="menu-icon"></div>
							<p class="menu-title">소개 및 약관</p>
					</a></li>
					<li class="menu"><a href="${contextPath}/pages/outer-link-page.jsp"
						class="menu-link">
							<div class="menu-icon"></div>
							<p class="menu-title">마케팅 센터</p>
					</a></li>
				</ul>
			</div>
		</header>

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
    <!-- 스크립트 --> 
    <script type="module" src="${pageContext.request.contextPath}/js/MorePage.js"></script>
</body>
</html>