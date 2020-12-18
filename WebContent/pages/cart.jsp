<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<%  
    int totalPrice = 0;
%>

<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Template</title>
    <link rel="stylesheet" href="../css/style.css">
    <link rel="stylesheet" href="../css/cart.css">
    <link href="https://fonts.googleapis.com/css2?family=Nanum+Gothic&display=swap" rel="stylesheet">
    <script src="https://kit.fontawesome.com/a99df0f94f.js" crossorigin="anonymous"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
</head>
<body>
    <div class="container">
        <jsp:include page="/header.jsp">
        	<jsp:param value="true" name="isHide"/>
        </jsp:include>
        
        <section>
            <div class="contents-wrapper"> <!-- 장바구니 정보 영역 -->
                <h2>장바구니</h2>
                <div class="up-line-bd"></div>
                <div class="product-info-zone"> <!-- 실 상품 정보 영역 -->
                    <form class="product-info">
                        <table class="product">
                            <tr class="label">
                                <td><input type="checkbox" name="chk" id="chk" onClick="allChk()"></td>
                                <td><p name="product-img"> 상품 이미지 </td> <!-- 구매할 상품 이미지 -->
                                <td><p name="product-name"> 상품명</p></td> <!-- 구매할 상품 이름 -->
                                <td><p name="product-num"> 구매 갯수 </p></td> <!-- 구매 갯수 -->
                                <td><P name="product-price"> 가격 </P></td> <!-- 상품 가격-->
                                <td><P name="product-price"> 할인 </P></td> <!-- 할인 -->
                                <td><P name="product-price"> 총 금액 </P></td> <!-- 상품 금액 -->
                            </tr>
                            <tr>
                                <td><input type="checkbox" name="chk" class="chkbox"></td>
                                <td><img src='' class="product-img"></td> <!-- 구매할 상품 이미지 -->
                                <td><p class="product-name"><a href="#">title</a></p> </td> <!-- 구매할 상품 이름 -->
                                <td><input type="number" class="product-num" placeholder="1" min="1"></td> <!-- 구매 갯수 -->
                                <td><P class="product-price"> price </P></td> <!-- 상품 가격-->
                                <td><P class="product-discnt"> 할인 </P></td> <!-- 할인 -->
                                <td><P class="product-rltprice"> <%=totalPrice%> </P> </td> <!-- 상품 금액 -->
                            </tr>
                            <tr>
                                <td><input type="checkbox" name="chk" class="chkbox"></td>
                                <td><img src='' class="product-img"></td> <!-- 구매할 상품 이미지 -->
                                <td><p class="product-name"><a href="#">title</a></p> </td> <!-- 구매할 상품 이름 -->
                                <td><input type="number" class="product-num" placeholder="1" min="1"></td> <!-- 구매 갯수 -->
                                <td><P class="product-price"> price </P></td> <!-- 상품 가격-->
                                <td><P class="product-discnt"> 할인1 </P></td> <!-- 할인 -->
                                <td><P class="product-rltprice"> <%=totalPrice%> </P> </td> <!-- 상품 금액 -->
                            </tr>
                        </table>
                        <br>
                        <div class="page-pooter">
		                	<table class="page-pooterbox">
		                		<tr>
		                			<td class='total-num'><p>총</p><span>X 개</span></td>
		                			<td class='total-price'><span>XXX원</span></td>
		                		</tr>
		                		<tr class="btn-zone">
		                			<td><button class="buy-product" onclick="">구매</button></td>
		                			<td><button class="del-product" onclick="del()">삭제</button></td>
		                		</tr>
			                </table>
			            </div>
                    </form>
                </div>
	         </div>
        </section>
    </div>
    <!-- 라이트 박스-->
    <div id="light-box-container">
    </div>
    <jsp:include page="/pages/login.jsp"></jsp:include> 
    <!-- 스크립트 --> 
    <script type="module" src="../js/MorePage.js"></script>
    <script>
	    $(document).ready(function() {
	    		$('#chk').on("click", function(){
	    			$('.chkbox').prop('checked', this.checked);
	    		});
	    	});
	    
        function del(){
           $(this).on('click', function() {
        	   $('.chkbox:checked').parent().parent().remove();
           });      
        }        
    </script>
</body>
</html>
