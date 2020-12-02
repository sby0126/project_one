<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>  
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
       <!-- 헤더의 시작 -->
       <header>
           <div class="header-wrapper">
               <!-- 헤더 왼쪽 : 로고 -->
               <div class="header-left">
                   <div class="" id="logo">
                       <a href="../index.jsp"><img src="https://dummyimage.com/64x64/000/fff" alt=""></a>
                   </div>
                   <a href="#">WOMEN</a>
                   <a href="#">MEN</a>
               </div>
               <!-- 헤더 중앙 : 메뉴 -->
               <div class="header-center">
                   <a href="../index.jsp">SHOP</a>
                   <a href="../item.jsp" target="_self">ITEM</a>
                   <a href="../sale.jsp">SALE</a>
                   <a href="#">MORE</a>
               </div>
                <!-- 헤더 오른쪽 : 로그인 / 검색 -->
                <div class="header-right">
                	<%
                		String id = (String)session.getAttribute("id");
                	%>
                	<c:set var="id" value="<%=id %>" />
                	<c:choose>
                		<c:when test="${id != null}">
                			<a class="header-right-login-button" href="/members/modifyMemberForm.do?id=${id}">회원 정보 수정</a>
                			<c:if test="${id=='admin'}">
                				<a class="header-right-login-button" href="/admin">관리자 페이지</a>
                			</c:if>
                			<button class="header-right-login-button" id="logout-button" onclick="javascript:location.href='/members/logout.do'">로그아웃</button>
                		</c:when>
                		<c:otherwise>
                			<button class="header-right-login-button">로그인</button>		
                		</c:otherwise>
                	</c:choose>
                    <input type="text" class="input-non-border-box" name="" id="" placeholder="검색어를 입력하세요">
                </div>
           </div>
           <!-- 숨겨진 메뉴 -->
           <div class="header-popup-container">
               <ul class="header-menu-list">
                   <li class="menu">
                       <a href="./Recently_viewed_shop.jsp" class="menu-link">
                           <div class="menu-icon"></div>
                           <p class="menu-title">최근 본 샵 <em>0</em></p>
                       </a>
                   </li>
                   <li class="menu">
                       <a href="./Recently_viewde_item.jsp" class="menu-link">
                           <div class="menu-icon"></div>
                           <p class="menu-title">최근 본 상품 <em>0</em></p>
                       </a>
                   </li>
                   <li class="menu">
                       <a href="#" class="menu-link">
                           <div class="menu-icon"></div>
                           <p class="menu-title">MY SHOP</p>
                       </a>
                   </li>
                   <li class="menu">
                       <a href="./Interested_item.jsp" class="menu-link">
                           <div class="menu-icon"></div>
                           <p class="menu-title">관심 상품</p>
                       </a>
                   </li>
                   <li class="menu">
                       <a href="./board-default.jsp" class="menu-link">
                           <div class="menu-icon"></div>
                           <p class="menu-title">1:1 문의</p>
                       </a>
                   </li>
                   <li class="menu">
                       <a href="./map.jsp" class="menu-link">
                           <div class="menu-icon"></div>
                           <p class="menu-title">소개 및 약관</p>
                       </a>
                   </li>
                   <li class="menu">
                       <a href="./outer-link-page.jsp" class="menu-link">
                           <div class="menu-icon"></div>
                           <p class="menu-title">마케팅 센터</p>
                       </a>
                   </li>
               </ul>
           </div>
       </header>
</body>
</html>