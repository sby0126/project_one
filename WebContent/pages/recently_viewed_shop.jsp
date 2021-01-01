<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page errorPage="/pages/cookieError.jsp" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*" %>
<%@ page import="service.RecentlyShopService, service.*" %>
<%@ page import="java.net.*" %>
<%@ page import="vo.*" %>
<%
	// Create a cookie service.
	CookieService cookieService = new CookieService();
	HashMap<String, String> cookie = cookieService.getKeyValue(request);
	String value = cookie.get("recentShopItems");
	
	String num = "0";
	
	if(value != null) {
		num = URLDecoder.decode(value);
		// num = request.getParameter("recentlyShopItem");
	} else {
		value = "0";
	}
	Vector<ProductVO> list = null;
	
	if(!value.equals("0")) {
		RecentlyShopService service = new RecentlyShopService(num);
		list = service.getCards();
	}
	
%>
<!DOCTYPE html>
<c:set var="list" value="<%= list %>" />
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>쇼핑몰 클론 프로젝트</title>
    <link rel="stylesheet" href="<%=application.getContextPath()%>/css/style.css">
    <link href="https://fonts.googleapis.com/css2?family=Nanum+Gothic&display=swap" rel="stylesheet">
    <script src="https://kit.fontawesome.com/a99df0f94f.js" crossorigin="anonymous"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/underscore@1.11.0/underscore-min.js"></script>
    <c:if test="${ list == null}">
	    <link rel="stylesheet" href="../css/Recently_viewed_shop.css">
    </c:if>
	<c:if test="${ list != null}">
		<%
		 	for(int i = 0; i < list.size(); i++) {
		 		ProductVO vo = list.get(i);
		 		String mainUrl = "https://raw.githubusercontent.com/biud436/project_one/main/WebContent";
		 		
		 		String gndr = vo.getGendertype();
		 		String shopType = vo.getShoptype();
		 		String contentUrl = vo.getContenturl();
		 		
// 		 		String imgUrl = mainUrl + "/images/shop/" 
// 		 							+ gndr + "/"
// 		 							+ shopType + "/"
// 		 							+ contentUrl;
		 		String imgUrl = "/images/shop/" 
		 							+ gndr + "/"
		 							+ shopType + "/"
		 							+ contentUrl;
		 %>
		<style>
			.card {
			    position: relative;
			    flex-basis: 13.8em;
			    height: 18.3em;
			    margin-bottom: 0.5em;
			    background: #fff;
			    border-bottom: 1px solid #E0E4E6;
			    cursor: pointer;
			}
						
			.card-container {
			    display: flex;
			    width: 100%;
			    row-gap: 0.25em;
			    column-gap: 0.25em;
			    flex: 0 1 0;
			    flex-basis: 0;
			    justify-content: space-between;
			    flex-direction: column;
			    flex-flow: wrap;
			}			

			.card div[d-<%=vo.getId()%>]::before {
			    content: "";
			    width: 5.5em;
			    height: 5.5em;
				background: url(<%= imgUrl %>) left top;
				background-size: cover;
			    position: absolute;
			    border-radius: 50%;
			    left: calc(50% - 5.5em / 2);
			    top: 10%;
			    z-index: 0;
			}

			.card div[d-<%=vo.getId()%>]:hover::before {
				filter: brightness(1.1);
				border-radius: 0;
				transition: all .2s linear;
			}
		</style>
		<%
		 	}
		 %>
		 <style>
		 	.card-container {
		 	    justify-content: left;
		 	}
		 </style>		 
	</c:if>
</head>
<body>
    <div class="container">
        <!-- 헤더의 시작 -->
        <jsp:include page="/header.jsp">
        	<jsp:param value="true" name="isHide"/>
        </jsp:include>

        <!-- 본문의 시작 -->
        <section>
            <!-- 본문이 들어가는 래퍼 -->
            <div class="contents-wrapper">
            	<c:choose>
            		<c:when test="${list != null}">
		                <div class="card-container">
		                	<%
		                		for(ProductVO vo : list) {
		                			
		                	%>
		                		<c:set var="card" value="<%= vo %>" />
								<div class="card">
				                    <a href="${card.getLink() }" target='_blank'>
										<div>
											<div d-<%=vo.getId()%>>
					                        	<i class="shop-hot-icon" data-title="HOT"></i>
					                        	<h2 class="contents-shop-name"><%= vo.getShopname()%></h2>
					                        </div>
					                        <p class="shop-contents"><%= vo.getTexts() %></p>
					                        <div class="shop-button-container" data-id="${card.getId()}">
					                            <button class="shop-button all-item-button">전체 상품</button>
					                            <button class="shop-button">
					                                <p class="shop-button-text">마이샵</p>
					                                <i class="shop-button-icon"></i>
					                            </button>
					                        </div>                    
										</div>
				                    </a>									
								</div>		                		
		                	<%
		                		}
		                	%>
		                </div>		
            		</c:when>
            		<c:otherwise>
		                <div class="item_selected_none">
		                    <button class="item_selected_none_all_del">전체삭제</button>
		                    <img class="item_selected_none_img" src="../images/b527471.png">
		                    <div class="item_selected_none_sp">최근 본 상품이 없습니다.</div>
		                    <a href="/index.jsp"><button class="item_selected_none_button">샾 메인으로 이동</button></a>
		                </div>            		
            		</c:otherwise>
            	</c:choose>

            </div>
        </section>
    </div>
    <!-- 라이트 박스-->
    <div id="light-box-container">
    </div>
    <jsp:include page="/pages/login.jsp"></jsp:include> 
    <!-- index.js는 메인 용이므로 알맞은 스크립트를 사용해야 합니다-->
    <script type="module" src="../js/MorePage.js"></script>
</body>
</html>
