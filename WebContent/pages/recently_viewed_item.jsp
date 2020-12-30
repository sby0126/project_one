<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*" %>
<%@ page import="service.RecentlyItemService, service.*" %>
<%@ page import="java.net.*" %>
<%@ page import="vo.*" %>
<%@ page import="java.util.stream.Collectors" %>
<%
	
	String myList = request.getParameter("myList");
	String num = myList;
	String value = myList;
	
	if(myList == null || myList.isEmpty()) {
		// Create a cookie service.	
		CookieService cookieService = new CookieService();
		HashMap<String, String> cookie = cookieService.getKeyValue(request);
		value = cookie.get("recentlyItems");
		// String value = "0";
		
		if(value != null) {
			num = URLDecoder.decode(value);
			// num = request.getParameter("recentlyShopItem");
		} else {
			value = "0";
		}		
	}
	
	Vector<ProductVO> list = null;
	
	if(!value.equals("0")) {
		RecentlyItemService service = new RecentlyItemService(num);
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
		 		
// 		 		String imgUrl = mainUrl + "/images/item/" 
// 		 							+ gndr + "/"
// 		 							+ shopType + "/"
// 		 							+ contentUrl;
		 		String imgUrl = "/images/item/" 
		 							+ gndr + "/"
		 							+ shopType + "/"
		 							+ contentUrl;
		 %>
		<style>
		
            .card {
                display: flex;
                flex-wrap: wrap;
                flex: auto auto auto;
                flex-direction: column;
                border: 1px solid #F2F5F9;
            }
            
            p[d-<%=vo.getId()%>]::before {
                content: "";
                width: 100%;
                height: 78%;
                background: url("<%=imgUrl %>") left top;
                background-size: cover;
                background-repeat: no-repeat;
                position: absolute;
                border-radius: 0;
                left: 0;
                top: 0;
                z-index: 0;
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
				                    <p d-<%= vo.getId()%> onclick="javascript:window.open('${card.getLink()}', '_blank')"></p>	                    
				                    <a href="${card.getLink()}" target='_blank'>
				                        <i class="shop-hot-icon"></i>
				                        <div class="item-button-container"> 
				                            <h2>${card.getTitle()}</h2>
				                            <p>${card.getPrice()}</p>
				                            <p>${card.getShopname()}</p>
				                            <button class="like-button"></button>
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
		                    <div class="item_selected_none_sp">최근 본 샾이 없습니다.</div>
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
