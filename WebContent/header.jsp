<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<%@ page import="java.net.URLEncoder" %>
<%@ page import="dao.*, vo.*, service.*, java.util.*" %>
<%
	// 최근 검색어 & 인기 검색어
	BestItemListService service = new BestItemListService();
	List<SearchVO> searchList = service.getList();
	
%>		
		<c:set var="searchList" value="<%= searchList %>" />
        <!-- 헤더의 시작 -->
        <header>
       	
        	<%
        		String hide = (String)request.getParameter("isHide");
        		if(hide == null) hide = "";
        		boolean isHide = hide.equals("true");
        	%>
        	<c:set var="isHide" value="<%= isHide %>" />
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
		                		MyShopService myShopService = new MyShopService();
		                		List<Integer> idList = myShopService.getIdList(id);
							%>             
							<c:set var="isSNS" value="<%= dao.isSNSMember(id) %>" />
							<a class="header-right-login-button" href="/pages/basket-tunnel.jsp">장바구니</a>
                			<a class="header-right-login-button" href="${pageContext.request.contextPath}/members/modifyMemberForm.do?id=${id}">회원 정보 수정</a>
                			<c:if test="${id=='admin'}">
                				<a class="header-right-login-button" href="${pageContext.request.contextPath}/admin">관리자 페이지</a>
                			</c:if>
                			<a class="header-right-login-button" href="${pageContext.request.contextPath}/pages/myshop.jsp">마이샵 <span style="color: #FDA568;"><%=idList.size()%></span></a>
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
		                        <c:choose>
		                        <c:when test="${id != null}">
		                            <a href="${pageContext.request.contextPath}/pages/interested_item.jsp" class="menu-link">
		                                <div class="menu-icon"></div>
		                                <p class="menu-title">관심 상품</p>
		                            </a>
		                        </c:when>
		                        <c:otherwise>
		                            <a href="javascript:openLoginModal()" class="menu-link">
		                                <div class="menu-icon"></div>
		                                <p class="menu-title">관심 상품</p>
		                            </a>		                        
		                        </c:otherwise>
		                        </c:choose>
	                        </li>                        
                        	

                     <!--    <li class="menu">
                            <a href="#" class="menu-link">
                                <div class="menu-icon"></div>
                                <p class="menu-title">공지 사항</p>
                            </a>
                        </li>  -->                        
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
                       <%--  <li class="menu">
                            <a href="${pageContext.request.contextPath}/pages/outer-link-page.jsp" class="menu-link">
                                <div class="menu-icon"></div>
                                <p class="menu-title">마케팅 센터</p>
                            </a>
                        </li> --%>
                    </ul>
                </div>      
            <c:if test="${isHide != true}">
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
	                    <div style="margin-top: 4px; text-align: center; font-size: 0.8em;">
	                    <input type="hidden" id="contextPath" value="${pageContext.request.contextPath}">
	                    이 사이트는 포트폴리오 용으로 개발된 사이트로 실제 사이트가 아닙니다.
	                    </div>
	                </div>
	            </div>
            </c:if>
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
   		<script type="text/javascript" src="//wcs.naver.net/wcslog.js"></script>
   		<script type="text/javascript">
   		if(!wcs_add) var wcs_add = {};
   		wcs_add["wa"] = "a7387d0c713560";
   		if(window.wcs) {
   		wcs_do();
   		}
   		</script>           
        
   	    <c:if test="${isHide != true}">
   	    	<div style="display:block; height: 4em; width:100%;">
   	    	</div>
        </c:if>             