<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.List, vo.ProductVO, dao.ContentDAO" %>
<%@ page import="java.net.URLDecoder" %>
<%@ page import="service.*" %>
<%
	MyShopService service = new MyShopService();

	boolean isLoginOK = session.getAttribute("id") != null;
%>
<c:set var="loginOK" value="<%= isLoginOK %>" />
<!DOCTYPE html>
<html lang="ko">
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<title>마이샵 - 쇼핑몰 클론 프로젝트</title>
	<link rel="stylesheet" href="../css/style.css">
	<link href="https://fonts.googleapis.com/css2?family=Nanum+Gothic&display=swap" rel="stylesheet">
	<script src="https://kit.fontawesome.com/a99df0f94f.js" crossorigin="anonymous"></script>
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
	<script src="https://cdn.jsdelivr.net/npm/underscore@1.11.0/underscore-min.js"></script>
	<link rel="stylesheet" href="../css/shop-detail.css">
	<link rel="stylesheet" href="../css/Recently_viewed_shop.css">
	
	<style>
		.card-container {
			display: flex;
			flex-direction: row;
			justify-content: left;
			align-items: center;
			background-color: #fff;
		}

		.item_selected_none {
			width: 100%;
		}
	</style>
	
	<c:if test="${loginOK}">
		<%
			String customerID = (String)session.getAttribute("id");
			List<Integer> idList = service.getIdList(customerID);
		%>
		<c:forEach var="id2" items="<%= idList %>">
			<%
				int id = (int)pageContext.getAttribute("id2");
				ShopDetailService myService = new ShopDetailService(id, "item");
				List<ProductVO> list = myService.getList();	
				
				String mainURL = "https://github.com/biud436/project_one/raw/main/WebContent/images";
			%>

			<c:if test="<%= list != null %>">
				<%
				 	for(int i = 0; i < list.size(); i++) {
				 		ProductVO vo = list.get(i);
				 		String imgUrl = mainURL
				 					+ "/" + vo.getPagetype()
				 					+ "/" + vo.getGendertype()
				 					+ "/" + vo.getShoptype()
				 					+ "/" + vo.getContenturl();
				 %>
				<style>
					.card {
						display: flex;
						flex-wrap: wrap;
						flex: auto auto auto;
						flex-direction: column;
						border: 1px solid #F2F5F9;
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
		</c:forEach>		
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
	            <div class="card-container">
					<c:choose>
					<c:when test="${ loginOK }">
							<%
								String customerID = (String)session.getAttribute("id");
								List<Integer> idList2 = service.getIdList(customerID);
							%>				
							<c:choose>
							<c:when test="<%= idList2.size() > 0 %>">
							<c:forEach var="id" items="<%= idList2 %>">
								<%
									// jstl에 정의된 변수를 scriptlet으로 가져오기
									int id = (int)pageContext.getAttribute("id");
									ShopDetailService myService = new ShopDetailService(id, "item");
									List<ProductVO> list = myService.getList();									
								%>
								<c:if test="<%= list != null %>">
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
										<!-- <button class="like-button"></button> -->
									</div>
								</div>
								<script>
									$("p[d-<%=card.getId()%>]").on("click", function() {
										window.open("<%= card.getLink() %>", "_blank");
									});
								</script>
								<%
	                    			}
	                    		%>		
	                    		</c:if>								
							</c:forEach>
							<button class="item_selected_none_all_del" id="delete-all-my-shop">전체삭제</button>						
							</c:when>
							<c:otherwise>
				                <div class="item_selected_none">
				                    <img class="item_selected_none_img" src="../images/b527471.png">
				                    <div class="item_selected_none_sp">마이샵이 설정되어있지 않습니다.</div>
				                    <a href="../index.jsp"><button class="item_selected_none_button">샾 메인으로 이동</button></a>
				                </div>							
							</c:otherwise>
							</c:choose>

					</c:when>
					<c:otherwise>
						<div class="item_selected_none_sp">로그인이 필요합니다.</div>
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
    <script>
    	// 전체 삭제
    	$("#delete-all-my-shop").on("click", () => {
    		const yesNo = confirm("정말 삭제하시겠습니까?");
    		const YES = true;
    		
    		if(yesNo === YES) {
    			$.ajax({
    				url: "/contents/deleteAllMyShop.do",
    				success: function(data) {
    					if(data.status === "success") {
    						alert("마이샵이 삭제되었습니다.");
    						location.reload();
    					}
    				}, 
    				error: function(err) {
    					const code = err.status;
    					if(code === 401) {
    						console.warn("로그인이 되어있지 않아 마이샵 삭제에 실패했습니다.");
    					} else if(code === 402) {
    						console.warn("마이샵이 이미 비어있습니다.");
    					}
    					
    					console.warn(err);
    				}
    			});	
    		}
    		
    	});
    </script>
    
<%
	if(session.getAttribute("id") == null) {
%>
<script>
	setTimeout(function() {
		$(".header-right-login-button").trigger("click");	
	}, 200);
</script>    
<%
	}
%>     
</body>
</html>
