<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="org.json.simple.*"%>
<%@ page import="org.json.*" %>
<%@ page import="java.util.*, java.util.Arrays" %>
<%@ page import="javax.servlet.*" %>
<%@ page import="vo.ProductVO" %>
<%@ page import="vo.CartNPayVO" %>
<%@ page import="com.google.gson.*" %>
<%@ page import="dao.ContentDAO, command.DetailInputCommand" %>

<!DOCTYPE html>
<%  

	String id = (String)session.getAttribute("id");
	
	List dataList = Arrays.asList(request.getParameter("dataArray"));
	
	CartNPayVO cartlist = (CartNPayVO)dataList; 
	
	String ctmId = cartlist.getCtmId();
	
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
                                <td><p name="product-img"> 상품 이미지</p></td> <!-- 구매할 상품 이미지 -->
                                <td><p name="product-name"> 상품명</p></td> <!-- 구매할 상품 이름 -->
                                <td><p name="product-num"> 구매 갯수 </p></td> <!-- 구매 갯수 -->
                                <td><P name="product-price"> 가격 </P></td> <!-- 상품 가격-->
                                <td><P name="product-price"> 할인 </P></td> <!-- 할인 -->
                                <td><P name="product-price"> 총 금액 </P></td> <!-- 상품 금액 -->
                            </tr>
                            
                            <% 
                            	int allQty = 0;
                            	int perprice = 0;
                            	int rltprice = 0;
                            	if(cartlist != null) {
                            		for(int i = 0; i < dataList.size(); i++ ) {
                            			
                            			String title = cartlist.getTitle();
                            			int price = cartlist.getPrice();
                            			int amount = cartlist.getAmount();
                            			
                            			String link = cartlist.getLink();
                            			String contentUrl = cartlist.getContentUrl();
                            			int discnt = 0;
                            			perprice = price - discnt;
                            			allQty += amount;
                            			rltprice += perprice;
                            			
                            %>
                            <tr>
                                <td><input type="checkbox" name="chk" class="chkbox"></td>
                                <td><a href="<%=link%>"><img src="<%=contentUrl%>" class="product-img"></a></td> <!-- 구매할 상품 이미지 -->
                                <td><p class="product-name"><a href="<%=link%>"><%=title%></a></p> </td> <!-- 구매할 상품 이름 -->
                                <td><input type="number" class="product-num" placeholder="<%=amount%>" min="1"></td> <!-- 구매 갯수 -->
                                <td><P class="product-price"> <%=price%> </P></td> <!-- 상품 가격-->
                                <td><P class="product-discnt"> <%=discnt%> </P></td> <!-- 할인 -->
                                <td><P class="product-rltprice"> <%=perprice%> </P> </td> <!-- 상품 금액 -->
                            </tr>
                            <%
                            		}
                            	}
                            
                            	if(cartlist == null) {
                            	
                            %>
                            	  <h2> 장바구니에 상품이 없습니다 </h2>
                            	  <button type="button" value="메인화면으로 가기" action="index.jsp"> 메인화면으로 가기</button>
                            <%
                            	}
                            %>
                            
                            
                        </table>
                        <br>
                        <div class="page-pooter">
		                	<table class="page-pooterbox">
		                		<tr>
		                			<td class='total-num'><p>총</p><span><%=allQty%> 개</span></td>
		                			<td class='total-price'><span><%=rltprice%>원</span></td>
		                		</tr>
		                		<tr class="btn-zone">
                                    <td><button type="button" class="buy-product" onclick="">구매</button></td>
		                			<td><button type="button" class="del-product" onclick="del()">삭제</button></td>
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
    <script type="module" src="../js/MorePage.js"></script>
</body>
</html>
