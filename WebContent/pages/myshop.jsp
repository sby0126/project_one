<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.List, vo.ProductVO, dao.ContentDAO" %>
<%@ page import="java.net.URLDecoder" %>
<%@ page import="service.*" %>
<%
	MyShopService service = new MyShopService();
	boolean isLoginOK = session.getAttribute("id") != null;
%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>My Shop</title>
    <link href="https://fonts.googleapis.com/css2?family=Nanum+Gothic&display=swap" rel="stylesheet">
    <script src="https://kit.fontawesome.com/a99df0f94f.js" crossorigin="anonymous"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/underscore@1.11.0/underscore-min.js"></script>
    <link rel="stylesheet" href="../css/style.css">
    <link rel="stylesheet" href="../css/Recently_viewed_shop.css">
  
</head>
<body>
    <div class="container">
    	<c:set var="loginOK" value="<%= isLoginOK %>" />
        <!-- 헤더의 시작 -->
        <jsp:include page="/header.jsp">
        	<jsp:param value="true" name="isHide"/>
        </jsp:include>

        <!-- 본문의 시작 -->
        <section>
            <!-- 본문이 들어가는 래퍼 -->
            <div class="contents-wrapper">
			<c:choose>
			<c:when test="${ loginOK }">
	                <div class="item_selected_none">
	                    <button class="item_selected_none_all_del" id="delete-all-my-shop">전체삭제</button>
	                    <img class="item_selected_none_img" src="../images/b527471.png">
	                    <div class="item_selected_none_sp">마이샵이 설정되어있지 않습니다.</div>
	                    <a href="../index.jsp"><button class="item_selected_none_button">샾 메인으로 이동</button></a>
	                </div>
			</c:when>
			<c:otherwise>
				<div class="item_selected_none_sp">로그인이 필요합니다.</div>
			</c:otherwise>       
			</c:choose> 
            </div>
        </section>
    </div>
    <!-- 라이트 박스-->
    <div id="light-box-container">
    </div>
    <jsp:include page="/pages/login.jsp"></jsp:include> 
    
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
    <!-- index.js는 메인 용이므로 알맞은 스크립트를 사용해야 합니다-->
    <script type="module" src="../js/MorePage.js"></script>
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
</body>
</html>
