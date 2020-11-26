<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>쇼핑몰 클론 프로젝트</title>
    <link rel="stylesheet" href="css/style.css">
    <link href="https://fonts.googleapis.com/css2?family=Nanum+Gothic&display=swap" rel="stylesheet">
    <script src="https://kit.fontawesome.com/a99df0f94f.js" crossorigin="anonymous"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/underscore@1.11.0/underscore-min.js"></script>
</head>
<body>
        <!-- 헤더의 시작 -->
        <header>
            <div class="header-wrapper">
                <!-- 헤더 왼쪽 : 로고 -->
                <div class="header-left">
                    <div class="" id="logo">
                        <a href="./"><img src="https://dummyimage.com/64x64/000/fff" alt=""></a>
                    </div>
                    <a href="#">WOMEN</a>
                    <a href="#">MEN</a>
                </div>
                <!-- 헤더 중앙 : 메뉴 -->
                <div class="header-center">
                    <a href="/">SHOP</a>
                    <a href="item.jsp" target="_self">ITEM</a>
                    <a href="sale.jsp">SALE</a>
                    <a href="#">MORE</a>
                </div>
                <!-- 헤더 오른쪽 : 로그인 / 검색 -->
                <div class="header-right">
                	<%
                		String id = (String)session.getAttribute("id");
                		if(id != null) {
                	%>
                    <button class="header-right-login-button" id="logout-button" onclick="javascript:location.href='/members/logout.do'">로그아웃</button>
                    <%
                		} else {
                    %>
                    <button class="header-right-login-button">로그인</button>	
                    <%
                		}
                    %>
                    <input type="text" class="input-non-border-box" name="" id="" placeholder="검색어를 입력하세요">
                </div>
            </div>
                <!-- 숨겨진 메뉴 -->
                <div class="header-popup-container">
                    <ul class="header-menu-list">
                        <li class="menu">
                            <a href="#" class="menu-link">
                                <div class="menu-icon"></div>
                                <p class="menu-title">최근 본 샵 <em>0</em></p>
                            </a>
                        </li>
                        <li class="menu">
                            <a href="#" class="menu-link">
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
                            <a href="#" class="menu-link">
                                <div class="menu-icon"></div>
                                <p class="menu-title">관심 상품</p>
                            </a>
                        </li>
                        <li class="menu">
                            <a href="pages/board-default.jsp" class="menu-link">
                                <div class="menu-icon"></div>
                                <p class="menu-title">1:1 문의</p>
                            </a>
                        </li>
                        <li class="menu">
                            <a href="pages/map.jsp" class="menu-link">
                                <div class="menu-icon"></div>
                                <p class="menu-title">소개 및 약관</p>
                            </a>
                        </li>
                        <li class="menu">
                            <a href="#" class="menu-link">
                                <div class="menu-icon"></div>
                                <p class="menu-title">마케팅 센터</p>
                            </a>
                        </li>
                    </ul>
                </div>            
            <!-- 검색 필터 -->
            <div class="header-filter-box">
                <!-- 검색 필터 래퍼 -->
                <div class="header-filter-box-wrapper">
                    <!-- 검색 필터 : 소호/브랜드 탭 -->
                    <div class="header-filter-box-header">
                        <button class="active">소호</button>
                        <button>브랜드</button>
                    </div>
                    <!-- 검색 필터 하단 : 대/중/소 분류 -->
                    <div class="header-filter-box-footer">
                        <!-- 검색 필터 왼쪽 : 카테고리 선택 -->
                        <div class="header-filter-box-footer-left">
                            <div class="header-filter-box-footer-left-button">
                                <span>상품 카테고리<em>베스트(X)</em></span>
                                <i class="fa fa-caret-down"></i>
                            </div>
                            <div class="header-filter-box-left-dropdown-menu" data-attr="상품 카테고리">
                                <div class="header-filter-box-left-dropdown-menu-content">
                                    <dl>
                                        <dt>샵 카테고리</dt>
                                        <dd><label><input class="header-filter-box-left-shop-categories" type="checkbox" name="categories"><span>ALL</span></label></dd>
                                        <dd><label><input class="header-filter-box-left-shop-categories" type="checkbox" name="categories"><span>트랜드·캐쥬얼</span></label></dd>
                                        <dd><label><input class="header-filter-box-left-shop-categories" type="checkbox" name="categories"><span>댄디·심플</span></label></dd>
                                        <dd><label><input class="header-filter-box-left-shop-categories" type="checkbox" name="categories"><span>유니크·빈티지</span></label></dd>
                                        <dd><label><input class="header-filter-box-left-shop-categories" type="checkbox" name="categories"><span>레플리카·제작</span></label></dd>
                                        <dd><label><input class="header-filter-box-left-shop-categories" type="checkbox" name="categories"><span>스트릿·도매스틱</span></label></dd>
                                        <dd><label><input class="header-filter-box-left-shop-categories" type="checkbox" name="categories"><span>클래식·수트</span></label></dd>
                                        <dd><label><input class="header-filter-box-left-shop-categories" type="checkbox" name="categories"><span>빅사이즈</span></label></dd>
                                        <dd><label><input class="header-filter-box-left-shop-categories" type="checkbox" name="categories"><span>슈즈</span></label></dd>
                                        <dd><label><input class="header-filter-box-left-shop-categories" type="checkbox" name="categories"><span>액세서리·잡화</span></label></dd>
                                    </dl>
                                </div>                                
                            </div>
                        </div>
                        <!-- 검색 필터 중앙 : 검색 -->
                        <div class="header-filter-box-footer-center">
                            <label for="name-search"></label><input class="input-non-border-box" type="text" placeholder="이름으로 검색">
                        </div>
                        <!-- 검색 필터 오른쪽 : 분류 -->
                        <div class="header-filter-box-footer-right">
                            <span class="active">전체</span>
                            <span>10대</span>
                            <span>20대</span>
                            <span>30대</span>                            
                        </div>
                    </div>
                    <div style="margin-top: 4px; text-align: center; font-size: 0.8em;">이 사이트는 포트폴리오 용으로 개발된 사이트로 실제 사이트가 아닙니다.</div>
                </div>
            </div>
        </header>        
</body>
</html>
