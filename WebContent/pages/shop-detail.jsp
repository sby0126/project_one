<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib  prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page import="java.util.List, vo.ProductVO, dao.ContentDAO" %>
<%@ page import="java.net.URLDecoder, service.ShopDetailService" %>
<%

	int id = Integer.parseInt(request.getParameter("id"));
	String pageType = "item";
	
	ShopDetailService service = new ShopDetailService(id, pageType);
	
	List<ProductVO> list = service.getList();
%>
<c:set var="list" value="<%= service.getList() %>" />
<c:set var="thumbNailImage" value="<%= service.getThumbNailImage() %>" />
<c:set var="shopName" value="<%= service.getShopName() %>" />
<c:if test="${ list == null or shopName == null }">
	<script>
		// 		alert("DB에서 상품을 찾지 못했습니다.");
		// 		history.go(-1);
	</script>
</c:if>
<!DOCTYPE html>
<html lang="ko">
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<title>쇼핑몰 클론 프로젝트</title>
	<link rel="stylesheet" href="../css/style.css">
	<link href="https://fonts.googleapis.com/css2?family=Nanum+Gothic&display=swap" rel="stylesheet">
	<script src="https://kit.fontawesome.com/a99df0f94f.js" crossorigin="anonymous"></script>
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
	<script src="https://cdn.jsdelivr.net/npm/underscore@1.11.0/underscore-min.js"></script>
	<link rel="stylesheet" href="../css/shop-detail.css">
	<link rel="stylesheet" href="../css/Recently_viewed_shop.css">
	<link rel="stylesheet" href="../libs/themes/wrunner-default-theme.css">
	<script src="../libs/wrunner-jquery.js"></script>
	<style>
	
		.card-container {
			display: flex;
			flex-direction: row;
			justify-content: left;
			align-items: center;
			background-color: #fff;
			padding-bottom: 6%;
		
		}

		.item_selected_none {
			width: 100%;
			/* 			display: flex; */
			/* 			flex-direction: row; */
			/* 			justify-content: center; */
			/* 			align-items: center; */
		}
	</style>
	<!-- 카드 목록이 있다면 스타일을 지정합니다.  -->
	<c:if test="${ list != null}">
		<%
		 	for(int i = 0; i < list.size(); i++) {
		 		ProductVO vo = list.get(i);
		 		
		 		String gndr = vo.getGendertype();
		 		String shopType = vo.getShoptype();
		 		String contentUrl = vo.getContenturl();		 		
		 		
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
				margin-left: 0.4em;
			}
			
			.card:nth-child(0) {
				margin-left: 1em;
			}

			.card p[d-<%=vo.getId()%>]::before {
				content: "";
				width: 100%;
				height: 78%;
				background: url(<%=imgUrl%>) left top;
				background-size: cover;
				background-repeat: no-repeat;
				position: absolute;
				border-radius: 0;
				left: 0;
				top: 0;
				z-index: 0;
				pointer-events: all;
			}

			.card p[d-<%=vo.getId()%>]:hover::before {
				filter: brightness(1.1);
				border-radius: 0;
				transition: all .2s linear;
			}
		</style>
		<%
		 	}
		 %>
	</c:if>
	<style>
		/* 가로로 배치, 한 줄로 표시, 왼쪽 */
		.card-container {
			display: flex;
			flex-direction: row;
			flex-wrap: no-wrap;
			justify-content: left;
		}
		
		/* 가로로 배치, 한 줄로 표시, 중앙 */
		.card-container > section {
			display: flex;
			flex-direction: row;
			flex-wrap: no-wrap;
			justify-content: center;
		}

		.face-area {
			column-gap: 0.5em;
		}

		/* 가로로 배치, 한 줄로 표시 */
		.shop-detail {
			display: flex;
			flex-direction: row;
			flex-wrap: no-wrap;
			justify-content: space-around;
		}		
		
		/* 세로로 배치, 여러줄로 표시 */
		.detail-trailer-list {
			display: flex;
			flex-direction: row;
			flex-wrap: wrap;
			width: 50px;
		}


		/* 세로로 배치, 여러줄로 표시, 간격 0.5em */
		.list-container {
			display: flex;
			flex-direction: column;
			flex-wrap: wrap;
			row-gap: 0.5em
		}

		.btn {
			display: inline-block;
			border: 1px solid #e6e9eb;
			line-height: 28px;
			color: #ff6b00;
			margin: 0 3px;
			padding: 0 10px;
			font-size: 11px;
			text-align: center;
			vertical-align: middle;
			cursor: pointer;
			border: 1px solid #E6E9EB;
			position: relative;
			width: 7em;
			height: 2.5em;
		}

		.plus::after {
			content: "";
			display: block;
			position: absolute;
			left: 50%;
			top: 50%;
			transform: translate(-50%, -50%);
			width: 14px;
			height: 14px;
			background-image: url(data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAA0AAAANCAYAAABy6+R8AAAAGXRFWHRTb2Z0d2FyZQBBZG9iZSBJbWFnZVJlYWR5ccllPAAAACxJREFUeNpiXLBgAQMO8B+IGbFJMDGQAYajJhZoKOELQayaGEeDnExNAAEGAKtFBf2k2wluAAAAAElFTkSuQmCC);
			background-repeat: no-repeat;
			background-size: cover;
		}

		.shop-info {
			width: 100%;
			white-space: nowrap;
			overflow: visible;
		}
		
		.shop-info div > h2 {
			position: relative;
			min-width: 800%;
			width: auto;
			left: -1.4em;
			padding-right: 1.6em;
			font-size: 1.8em;
			font-family: 'Nanum Gothic', sans-serif;
			text-align: right;
			
		}

		.shop-info div > p {
			position: relative;
			min-width: 600%;
			width: auto;
			left: -1.6em;
			font-family: 'Nanum Gothic', sans-serif;
			font-size: 0.85em;
			margin-top: 1em;
		}
		
		.blank {
			width: 100%;
			margin: 0;
			height: 10%;
			background-color: #F2F5F9;
		}
	</style>
</head>

<body>
	<!-- 컨테이너의 시작 -->
	<div class="container">
		<!-- 헤더의 시작 -->
		<jsp:include page="/header.jsp">
			<jsp:param value="true" name="isHide" />
		</jsp:include>
		<!-- 본문의 시작 -->
		<section>
			<!-- 본문이 들어가는 래퍼 -->
			<div class="contents-wrapper">
				<div class="card-container">
					<section>
						<div class="shop-detail">
							<div class="detail-trailer-list">
								<div class="shop-info">
									<div>
										<h2><%= service.getShopName() %></h2>
									</div>
									<div>
										<p id="category"><%=service.getTexts() %></p>
									</div>		
									<script>
										$("#category").text($("#category").text().replace(/([\d]+대\,[\d]+대\,[\d]+대)|([\d]+대[ ]*\,[ ]*[\d]+대)/, ""));									
									</script>				
								</div>
								<!-- <ul class="face-area">
									<li>
										<a href="#"><img src="https://dummyimage.com/64x64/000/fff"></a>
									</li>
									<li>
										<a href="#"><img src="https://dummyimage.com/64x64/000/fff"></a>
									</li>
									<li>
										<a href="#"><img src="https://dummyimage.com/64x64/000/fff"></a>
									</li>
									<li>
										<a href="#"><img src="https://dummyimage.com/64x64/000/fff"></a>
									</li>
								</ul> -->
							</div>
							<div class="centered">
								<a href="#"><img src="${ thumbNailImage }"></a>
							</div>
							<div>
								<div class="list-container">
									<div>
										<a class="btn" href="<%= service.getLink() %>">바로가기</a>
										<a class="btn plus" href="<%= service.getLink() %>">&nbsp;</a>
									</div>
								</div>
							</div>
						</div>
					</section>
					<div class="blank"></div>
					<%-- <jsp:include page="/pages/components/filterbox.jsp"></jsp:include> --%>
					<c:choose>
						<c:when test="${list != null}">
							<%
                    			for(ProductVO card : list) {
                   			 %>
							<c:set var="card" value="<%= card %>" />
							<div class="card">
								<p d-<%= card.getId() %>></p>
								<i class="shop-hot-icon"></i>
								<div class="item-button-container">
									<h2>${card.getTitle()}</h2>
									<p>${card.getPrice()}</p>
									<p>${card.getShopname()}</p>
								</div>
							</div>

							<script>
								$("p[d-<%= card.getId() %>]").on('click', function() {
									window.open(${card.getLink()},"_blank");
								})
							</script>
							<%
                    			}
                    		%>
						</c:when>
						<c:otherwise>
							<div class="item_selected_none">
								<!-- 		                    <button class="item_selected_none_all_del">전체삭제</button> -->
								<img class="item_selected_none_img" src="../images/b527471.png">
								<div class="item_selected_none_sp">상품이 없습니다.</div>
								<a href="#" onclick="history.go(-1);">
									<button class="item_selected_none_button">이전으로</button>
								</a>
							</div>
						</c:otherwise>
					</c:choose>
				</div>
			</div>
		</section>
	</div>
	<!-- 라이트 박스-->
	<div id="light-box-container">
	</div>
	<jsp:include page="/pages/login.jsp"></jsp:include>
	<!-- index.js는 메인 용이므로 알맞은 스크립트를 사용해야 합니다-->
	<script src="/js/shopDetailPage.js"></script>
	<script type="module" src="/js/MorePage.js"></script>
	<script type="module" src="/js/CookieService.js"></script>
</body>
</html>