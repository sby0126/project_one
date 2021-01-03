<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>   
<%@ page import="dao.ContentDAO, org.json.simple.*" %>
<%@ page import="dao.*, vo.*, service.*, java.util.*" %>
<%@ page import="java.net.URLEncoder" %>
<%
	BestItemListService service = new BestItemListService();
	List<SearchVO> searchList = service.getList();
%>	    
<%
	response.setHeader("Cache-Control","no-store"); 
	response.setHeader("Pragma","no-cache"); 
	response.setDateHeader("Expires",0); 
	if (request.getProtocol().equals("HTTP/1.1"))
	        response.setHeader("Cache-Control", "no-cache");
	
%>
<c:set var="searchList" value="<%= searchList %>" />
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Template</title>
    <link rel="stylesheet" href="css/style.css">
    <link href="https://fonts.googleapis.com/css2?family=Nanum+Gothic&display=swap" rel="stylesheet">
    <script src="https://kit.fontawesome.com/a99df0f94f.js" crossorigin="anonymous"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/underscore@1.11.0/underscore-min.js"></script>
</head>
<body>
    <!-- 컨테이너의 시작 -->
    <div class="container">
        <!-- 헤더의 시작 -->
        <header>
            <div class="header-wrapper">
                <!-- 헤더 왼쪽 : 로고 -->
                <div class="header-left">
                    <div class="" id="logo">
                        <a href="${pageContext.request.contextPath}/index.jsp"><img src="https://dummyimage.com/64x64/000/fff" alt=""></a>
                    </div>
                    <a href="#">WOMEN</a>
                    <a href="#">MEN</a>
                </div>
                <!-- 헤더 중앙 : 메뉴 -->
                <div class="header-center">
                    <a href="${pageContext.request.contextPath}/index.jsp">SHOP</a>
                    <a href="${pageContext.request.contextPath}/item.jsp" target="_self">ITEM</a>
                    <a href="${pageContext.request.contextPath}/sale.jsp">SALE</a>
                    <a href="#">MORE</a>
                </div>
                <!-- 헤더 오른쪽 : 로그인 / 검색 -->
                <div class="header-right">
                	<%
                		String id = (String)session.getAttribute("id");
                	CustomerDAO dao = CustomerDAO.getInstance();
                	%>
                	<c:set var="id" value="<%=id %>" />
                	<c:choose>
                		<c:when test="${id != null}">
							<%
								CustomerVO vo = dao.getMember(id);
							%>                   		
							<c:set var="isSNS" value="<%= dao.isSNSMember(id) %>" />
							<a class="header-right-login-button" href="/pages/basket-tunnel.jsp">장바구니</a>						
                			<a class="header-right-login-button" href="${pageContext.request.contextPath}/members/modifyMemberForm.do?id=${id}">회원 정보 수정</a>
                			<c:if test="${id=='admin'}">
                				<a class="header-right-login-button" href="${pageContext.request.contextPath}/admin">관리자 페이지</a>
                			</c:if>
                			<button class="header-right-login-button" id="logout-button" onclick="javascript:location.href='${pageContext.request.contextPath}/members/logout.do'">로그아웃</button>
                		</c:when>
                		<c:otherwise>
                			<button class="header-right-login-button">로그인</button>		
                		</c:otherwise>
                	</c:choose>
                    <input type="text" class="input-non-border-box" name="" id="my-search-box" placeholder="검색어를 입력하세요">
                </div>
            </div>
                <!-- 숨겨진 메뉴 -->
                <div class="header-popup-container">
                    <ul class="header-menu-list">
                        <li class="menu">
                            <a href="${pageContext.request.contextPath}/pages/recently_viewed_shop.jsp" class="menu-link">
                                <div class="menu-icon"></div>
                                <p class="menu-title">최근 본 샵 <em id="recently-shop-count">0</em></p>
                            </a>
                        </li>
                        <li class="menu">
                            <a href="${pageContext.request.contextPath}/pages/recently_viewed_item.jsp" class="menu-link">
                                <div class="menu-icon"></div>
                                <p class="menu-title">최근 본 상품 <em id="recently-item-count">0</em></p>
                            </a>
                        </li>
                        <li class="menu">
                            <a href="${pageContext.request.contextPath}/pages/myshop.jsp" class="menu-link">
                                <div class="menu-icon"></div>
                                <p class="menu-title">MY SHOP</p>
                            </a>
                        </li>
                        <li class="menu">
                            <a href="${pageContext.request.contextPath}/pages/interested_item.jsp" class="menu-link">
                                <div class="menu-icon"></div>
                                <p class="menu-title">관심 상품</p>
                            </a>
                        </li>
                        <!-- <li class="menu">
                            <a href="#" class="menu-link">
                                <div class="menu-icon"></div>
                                <p class="menu-title">공지 사항</p>
                            </a>
                        </li> -->                         
                        <li class="menu">
                            <a href="/pages/board-default.jsp" class="menu-link">
                                <div class="menu-icon"></div>
                                <p class="menu-title">1:1 문의</p>
                            </a>
                        </li>                          
                        <li class="menu">
                            <a href="/boardList.abc" class="menu-link">
                                <div class="menu-icon"></div>
                                <p class="menu-title">자유 게시판</p>
                            </a>
                        </li>     
                        <li class="menu">
                            <a href="${pageContext.request.contextPath}/pages/map.jsp" class="menu-link">
                                <div class="menu-icon"></div>
                                <p class="menu-title">소개 및 약관</p>
                            </a>
                        </li>
                        <%-- <li class="menu">
                            <a href="${pageContext.request.contextPath}/pages/outer-link-page.jsp" class="menu-link">
                                <div class="menu-icon"></div>
                                <p class="menu-title">마케팅 센터</p>
                            </a>
                        </li> --%>
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
                                        <%
                                        	JSONArray categories = ContentDAO.getInstance().getItemCategories();
                                        %>
                                        <c:set var="categories" value="<%= categories %>" />
                                        <c:forEach var="category" items="${categories}">
                                        	<dd><label><input class="header-filter-box-left-shop-categories" type="checkbox" name="categories"><span>${ category }</span></label></dd>
                                        </c:forEach>
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
                    <div style="margin-top: 4px; text-align: center; font-size: 0.8em;">
                    <input type="hidden" id="contextPath" value="${pageContext.request.contextPath}">
                    이 사이트는 포트폴리오 용으로 개발된 사이트로 실제 사이트가 아닙니다.
                    </div>
                </div>
            </div>
           <ul class="popular-search">
				<h4>인기검색어</h4>
				<c:set var="i" value="0"></c:set>
				<c:set var="flag" value="false"></c:set>
				<c:forEach var="m" items="${searchList}" varStatus="stat">
					<c:if test="${i <= 8}">									
						<li> 
							<a href="/item.jsp?pageType=item&shopType=S&gndr=M&ages=all&keyword=${ URLEncoder.encode(m.getKeyword()) }">
								<i>${stat.count}.</i> <span>${ m.getKeyword() }</span>&nbsp;<span>${ m.getCount() }</span>
							</a>
						</li>
						<c:set var="i" value="${i + 1}" />
					</c:if>
				</c:forEach>
			</ul>     
        </header>   
        <script>
        	
	    	// 인기 검색어 창을 표시합니다. 
	    	$('body').click(function(e){
	    		var id = e.target.getAttribute('id');
	    		
	    		if(id != "my-search-box"){
	    			$(".popular-search").css("display","none");	
	    		}else{
	    			$(".popular-search").css("display","block");
	    		}
	    	});
	
	        // 로그인 창을 표시합니다.
			function openLoginModal() {
	    		setTimeout(function() {
	    			$(".header-right-login-button").trigger("click");	
	    		}, 200);
			}
    
        </script>                       
        <!-- 본문의 시작 -->
        <section>
            <!-- 본문이 들어가는 래퍼 -->
            <div class="contents-wrapper">
            </div>
        </section>
    </div>
    <!-- 라이트 박스-->
    <div id="light-box-container">
    </div>
    <jsp:include page="/pages/login.jsp"></jsp:include>
    <!-- 스크립트 --> 
    <script type="module" src="js/ItemPage.js"></script>
</body>
</html>
