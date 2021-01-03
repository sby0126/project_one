<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>   
<%@ page import="vo.*, java.util.*, service.*, dao.*" %>
<%
// request.setAttribute("customer", userVO);
// request.setAttribute("orderList", orderList);

// session.setAttribute("cartList", orderList);

// request.setAttribute("price", totalPrice[0]);
// request.setAttribute("productName", productName);
// request.setAttribute("productId", productId);

	CustomerVO customerVO = (CustomerVO)request.getAttribute("customer");
	List<OrderVO> cartList = (List<OrderVO>)session.getAttribute("cartList");
	
%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>장바구니</title>
    <link href="https://fonts.googleapis.com/css2?family=Nanum+Gothic&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/semantic-ui/2.4.1/semantic.min.css"
        integrity="sha512-8bHTC73gkZ7rZ7vpqUQThUDhqcNFyYi2xgDgPDHc+GXVGHXq+xPjynxIopALmOPqzo9JZj0k6OqqewdGO3EsrQ=="
        crossorigin="anonymous" />
    <script src="https://kit.fontawesome.com/a99df0f94f.js" crossorigin="anonymous"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/underscore@1.11.0/underscore-min.js"></script>
    <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/semantic-ui/2.4.1/semantic.min.css"
        integrity="sha512-8bHTC73gkZ7rZ7vpqUQThUDhqcNFyYi2xgDgPDHc+GXVGHXq+xPjynxIopALmOPqzo9JZj0k6OqqewdGO3EsrQ=="
        crossorigin="anonymous" />
    <style>
        body {
            background: #F3F7FA;
        }

        .my-container {
            width: 100vw;
            height: 100vh;
            margin-top: 6em;
        }
    </style>
</head>

<body>
    <div class="my-container">
        <div class="ui container">
            <h2 class="ui header">
                <i class="barcode icon"></i>
                <div class="content">
                    장바구니
                    <div class="sub header">상품 장바구니입니다.</div>
                </div>
            </h2>

            <div class="ui divided items">
                <div class="ui center checkbox">
                    <input type="checkbox" id="all-select-checkbox" name="example">
                    <label></label>
                </div>
                <c:set var="cartList" value="<%= cartList %>" />
                
                <c:choose>
                <c:when test="<%= cartList == null || !cartList.isEmpty() %>">
	                <c:forEach var="cart" items="${cartList}" varStatus="cartStatus">
	                	<%
	                		ItemService service = new ItemService();
	                		OrderVO vo = (OrderVO)pageContext.getAttribute("cart");
	                		ProductVO productVO = service.getDAO().findShopDataAsID(vo.getProductId());
	                	%>
		                <div class="item" data-id="${ cartStatus.index }">
		                    <div class="ui center checkbox">
		                        <input type="checkbox" name="example">
		                        <label></label>
		                    </div>
		                    <div class="image">
		                        <img src="/images/<%=productVO.getPagetype()%>/<%=productVO.getGendertype()%>/<%=productVO.getShoptype()%>/<%=productVO.getContenturl()%>">
		                    </div>
		                    <div class="content">
		                        <a class="header" target="_parent" href="/pages/detail.jsp${cart.getLink()}">${cart.getProductName()}</a>
		                        <div class="meta">
		                            <span class="cinema"><%= productVO.getShopname() %></span>
		                        </div>
		                        <div class="description">
		                            <p></p>
		                        </div>
		                        <div class="extra">
		                            <div class="ui right floated animated fade button" tabindex="0">
		                                <div class="visible content">상품 보기</div>
		                                <div class="hidden content">
		                                	<a target="_parent" href="/pages/detail.jsp${cart.getLink()}">${ cart.getPrice() }</a>
		                                </div>
		                            </div>
		                            <div class="ui huge star rating"></div>
		                        </div>
		                    </div>
		                </div>
	                </c:forEach>
	            </c:when>
	            <c:otherwise>
	            	<div class="item">
	            		<p>장바구니가 비어있습니다</p>
	            	</div>
	            </c:otherwise>
            	</c:choose>
	                <div class="item">
	                    <button id="delete-selection-cart" class="ui right floated primary button">
	                    전체 삭제
	                    </button>
<!-- 	                    <button class="ui right floated button"> -->
<!-- 	                    전체 구매 -->
<!-- 	                    </button>                 -->
	                </div>                
	            </div>                

        </div>
    </div>

    <script>
        // 전체 선택
	    $("#all-select-checkbox").on("click", () => {
	    	var c = $("#all-select-checkbox").prop("checked");
	    	
	        $("input[type='checkbox']").prop("checked", !c);  
	        
        });
        
        // 장바구니 삭제
        $("#delete-selection-cart").on("click", () => {
            const form = document.createElement("form");
            form.action = "/contents/deleteCart.do";
            form.method = "POST";
            form.name = "content_form";
            
            $(".item").each((index, elem) => {
            	const input = $(elem).find("input[type=checkbox]");
            	if(input.is(":checked")) {
            		const id = document.createElement("input");
            		id.name = "idList";
            		id.value = $(elem).data("id");
            		form.appendChild(id);
            	}
            });
            
            document.body.appendChild(form);
            document.content_form.target="_parent"; 
            form.submit();
        });

    </script>

</body>

</html>