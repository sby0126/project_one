<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ page import="java.lang.Integer" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
	// 카카오페이 결제 처리를 위한 페이지입니다.

	// 구매자 정보
	String name = (String)request.getAttribute("name");
	String email = (String)request.getAttribute("email");
	String phone = (String)request.getAttribute("phone");
	String address = (String)request.getAttribute("address");
	String zipcode = (String)request.getAttribute("zipcode");
	int price = (int)request.getAttribute("price"); 
	
	// 상품명
	String productName = (String)request.getAttribute("productName");
	int productId = Integer.parseInt((String)request.getAttribute("productId"));
	
	// 가맹점 식별 코드
	String CID = "imp61240826";
	
	// 이전 정보
	String referer = request.getHeader("referer");
	
%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Payment Form</title>
	<script type="text/javascript" src="https://code.jquery.com/jquery-1.12.4.min.js" ></script>
	<script type="text/javascript" src="https://cdn.iamport.kr/js/iamport.payment-1.1.5.js"></script>
	<script src="https://cdn.jsdelivr.net/npm/axios/dist/axios.min.js"></script>
</head>
<body>  
	<script>
		$(function() {
			
			var IMP = window.IMP; // 생략가능
			IMP.init("<%= CID %>");
			
			IMP.request_pay({
	            pg: 'kakao',
	            pay_method: 'card',
	            merchant_uid: 'merchant_' + new Date().getTime(),
	            name: '주문명 : 주문명 설정',
	            amount: <%=price%>,
	            buyer_email : '<%=email%>',
	            buyer_name : '<%=name%>',
	            buyer_tel : '<%=phone%>',
	            buyer_addr : '<%=address%>',
	            buyer_postcode: '<%=zipcode%>',
	            // m_redirect_url: "<%=request.getContextPath()%>/payments/check", // 카카오 페이는 필요 없음. 
	        }, function (rsp) {
	            
	            if (rsp.success) {
	                var msg = '결제가 완료되었습니다.';
	                msg += '고유ID : ' + rsp.imp_uid;
	                msg += '상점 거래ID : ' + rsp.merchant_uid;
	                msg += '결제 금액 : ' + rsp.paid_amount;
	                msg += '카드 승인번호 : ' + rsp.apply_num;
	                
	                alert(msg);
	                
	                // 결제 검증
	                $.ajax({
	                    type: "GET", 
	                    url: "/payments/check",
	                    data: {
	                    	"imp_uid" : rsp.imp_uid,
	                    	"merchant_uid": merchant_uid,
	                    	"product_name": '<%=productName%>',
	                    	"product_id": <%=productId%>,
	                    	"paid_amount": rsp.paid_amount
	                    },
	                }).done(function(data) { // 응답 처리
	                    switch(data.status) {
	                    case: "vbankIssued":
	                      // 가상계좌 발급 시 로직
	                      break;
	                    case: "success":
	                      // 결제 성공 시 로직
	                      console.log("결제 성공");
	                      break;
	                  }
	                });
	                
	                location.href = '<%=request.getContextPath()%>/payments/success?msg='+msg;
	                
	            } else {
	                var msg = '결제에 실패하였습니다.';
	                msg += '에러내용 : ' + rsp.error_msg;
	                
	                location.href = "<%=request.getContextPath()%>/payments/fail";
	                
	            }
	        });
	    });
			
		})();
	</script>
</body>
</html>