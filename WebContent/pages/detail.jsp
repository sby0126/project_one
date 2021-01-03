<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="dao.ContentDAO, command.DetailInputCommand, vo.CartNPayVO"%>
<%@ page import="com.google.gson.Gson, java.util.*" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>상품 정보 페이지</title>
    <link rel="stylesheet" type="text/css" href="/css/detail.css">
    <link rel="stylesheet" type="text/css" href="/css/style.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/underscore@1.11.0/underscore-min.js"></script>
</head>

<body>

	<div id="fb-root"></div>
	<script async defer crossorigin="anonymous" src="https://connect.facebook.net/ko_KR/sdk.js#xfbml=1&version=v9.0&appId=1607859732738975&autoLogAppEvents=1" nonce="61MSR8tI"></script>

    <jsp:include page="/header.jsp">
       <jsp:param value="true" name="isHide"/>
    </jsp:include>


    <div class="container">   
        <!-- 본문의 시작 -->
        <section>
            <!-- 본문이 들어가는 래퍼 -->
            <div class="contents-wrapper">  
		
		        <div class="item_content" id="item_content">
		            <!-- 상품이미지, 상품정보 -->
		            <div class="productArea">
		                <!-- 상품이미지 -->
		                <div class="imgArea">
		                    <img src="https://dummyimage.com/480x500/000/fff">
		
		                </div>
		                <!-- 상품구매정보 -->
		                <div class="infoArea">
		                    <ul>
		                        <!-- 1. 상품명 -->
		                        <li>
		                            <h2 id="detail-item-title">후드후드후드(데이터)</h2>
		                        </li>
		                        <!-- 2. 가격 -->
		                        <li><span>가격</span><span id="detail-item-price">가격(데이터)</span></li>
		                        <!-- 3. 색상 -->
		                        <li><span>색상</span>
		                            <div class="color_option">
			                            <button data-type="color" data-value="B" class="detail_button" value="1">블랙(데이터)</button>
			                            <button data-type="color" data-value="W" class="detail_button" value="2">화이트(데이터)</button>
		                            </div>
		                        </li>
		                        <!-- 4. 사이즈 -->
		                        <li><span>사이즈</span>
		                            <div class="size_option">
			                            <button data-type="size" data-value="M" disabled="true" value="1" class="detail_button">FREE(데이터)</button>
			                            <button data-type="size" data-value="L" disabled="true" value="2" class="detail_button">100(데이터)</button>
			                        </div>
		                        </li>
		                        <!-- 5. 색상버튼과 사이즈버튼클릭시 동적으로 li태그와 버튼생성 -->
		                        <li>
		                            <ul class="productlist_add">
		                                <!-- <li><span>상품 색상과 사이즈를 선택해주세요 (최소주문1개이상)</span></li>
		                                    <li><span>상품 색상과 사이즈를 선택해주세요 (최소주문1개이상)</span></li>
		                                    <li><span>상품 색상과 사이즈를 선택해주세요 (최소주문1개이상)</span></li>
		                                    <li><span>상품 색상과 사이즈를 선택해주세요 (최소주문1개이상)</span></li>
		                                    <li><span>상품 색상과 사이즈를 선택해주세요 (최소주문1개이상)</span></li> -->
		                 
		                                </ul>
		                            </li>
		                            <!-- 6. 총상품금액 -->
		                            <li><span>총상품금액</span><span class="allPrice">종합가격(데이터)</span></li>
		                            <!-- 7. 구매하기.장바구니 버튼 -->
		                            <li>
		                               <div>
		                                  <button id="pay-button" class="detail_button" type="button" onClick="javascript:pay();">구매하기</button>
		                                  <button id="cart-button" class="detail_button" type="button" onClick="javascript:cart();">장바구니</button>
		                               </div>
		                            </li>
		                        </ul>
		                    </div>
		                    
		                </div>
		
		            </div>
		       
		            <div class="productArea2">
		                <!-- 중간 상품상세설명, 상품결제정보 버튼 -->
		                <div class="productArea2_button">
		                    <ul class="productArea2_buttonList">
		                        <li><a href="#productArea2_detailImg">상품상세설명</a></li>
		                        <li><a href="#productPayInfo">상품결제정보</a></li>
		                    </ul>
		                </div>
		                <!-- 상품상세설명 -->
		                <div class="productArea2_detailImg" id="productArea2_detailImg">
		                    <img src="../images/img1info.jpg">
		                </div>
		                <!-- 페이스북으로 댓글 달기 -->
		                <div class="facebook-comments" style="display:flex; justify-contents: center;">
		                	<div class="fb-comments" data-href="http://localhost:9988/pages/detail.jsp" data-width="1200" data-numposts="5"></div>
		                </div>
		                
		                <!-- 상품결제정보 -->
		                <div class="productPayInfo" id="productPayInfo">
		                    <h3>교환/반품</h3>
		                    <div>
		                        <h4>교환 및 환불이 가능한 경우</h4>
		                        <span>상품을 공급 받으신 날로부터 7일이내 단, 가전제품의
		                            경우 포장을 개봉하였거나 포장이 훼손되어 상품가치가 상실된 경우에는 교환/반품이 불가능합니다.
		                        </span>
		                        <h4>교환 및 반품이 불가능한 경우</h4>
		                        <span>고객님의 책임 있는 사유로 상품등이 멸실 또는 훼손된 경우. 단,상품의 내용을 확인하기 위하여
		                            포장 등을 훼손한 경우는 제외
		                        </span>
		                    </div>
		                    <div>
		                        <h3>결제안내</h3>
		                        <span>고객결제의 경우 안전을 위해 카드사에서 확인전화를 드릴 수도 있습니다. 확인과정에서 도난 카드의 사용이나 타인 명의의 주문등 정상적인 주문이
		                            아니라고 판단될 경우 임의로 주문을 보류 또는 취소할 수 있습니다.
		                        </span>
		                    </div>
		                    <div>
		                        <h3>배송정보</h3>
		                        <span>배송 방법: 택배<br>
		                            배송 지역: 전국지역<br>
		                            배송 비용: 2,500원<br>
		                            배송 기간: 3일 ~ 7일<br>
		                            배송 안내 : 산간벽지나 도서지방은 별도의 추가금액을 지불하셔야 하는 경우가 있습니다.
		                        </span>
		                    </div>
		
		                </div>
		                <!-- 오른쪽하단 top.bottom 버튼 -->
		                <div class="item_top_bottom">
		                    <a href="#item_content" id="item_top"><span class="item_top_button">top</span></a>
		                    <a href="#productPayInfo" id="item_bottom"><span class="item_bottom_button">bottom</span></a>
		                </div>
		                <form id="needVal" method="post" action="" target="">
		                   <input type="hidden" name="title" id="title" value="">
		                   <input type="hidden" name="price" id="price" value="">
		                   <input type="hidden" name="amount" id="amount" value="">
		                   <input type="hidden" name="productId" id="productId" value="">
		                   <!-- <input type="hidden" name="img" id="img" value="">  -->
		                   <!-- <input type="submit"/>  -->
		                </form>
		            </div>
		            <!-- 장바구니 이동을 방지할 수 있는 iframe 활용 -->
		            <iframe name="iframe" style="width:1px; height:1px; border:0; visibility:hidden;"></iframe>
		            <!-- 오른쪽하단 top.bottom 버튼 -->
		            <div class="item_top_bottom">
		                <a href="#item_content" id="item_top"><span class="item_top_button">top</span></a>
		                <a href="#productPayInfo" id="item_bottom"><span class="item_bottom_button">bottom</span></a>
		            </div>
				<!-- 라이트 박스-->                
            </div>
        </section>    

	</div>
	<div id="light-box-container"></div>
	<script src="/js/itemPayCart.js"></script>
    <script>
    pdcode = "";
    // 색상버튼은(ul태그안에 li목록중 3번째줄), 사이즈버튼은(ul태그안에 li목록중 4번째줄)
	
    // 상품 색상버튼(3) 클릭시 발생하는 이벤트
    $(".infoArea > ul > li:nth-child(3) button").on("click", function () {
        var idx = $(this).index();
        var sameYn = true;
        var clickYn = false;

        // 색상버튼(3)을 반복하면서 클릭여부 확인
        $.each($(".infoArea > ul > li:nth-child(3) button"), function (i) {
            if ($(this).hasClass("click_button")) {
                $(this).removeClass("click_button");
                if (idx == i) {
                    sameYn = false;
                }
            }
        });


        // 색상버튼(3)을 클릭시 클래스 추가
        if (sameYn) {
            $(this).addClass("click_button");
            clickYn = true;
        }

        // 색상버튼(3) 클릭시 사이즈버튼(4) 활성화 
        if (clickYn) {
            $(".infoArea > ul > li:nth-child(4) button").prop("disabled", false);
        } else {
            $(".infoArea > ul > li:nth-child(4) button").removeClass("click_button");
            $(".infoArea > ul > li:nth-child(4) button").prop("disabled", true);
        }
    })

    //사이즈버튼(4) 클릭시 발생하는 이벤트
    $(".infoArea > ul > li:nth-child(4) button").on("click", function () {
        var idx = $(this).index();
        var sameYn = true;
        var clickYn = false;

        // 사이즈버튼(4)을 반복하면서 클릭여부 확인
        $.each($(".infoArea > ul > li:nth-child(4) button"), function (i) {
            if ($(this).hasClass("click_button")) {
                $(this).removeClass("click_button");
                if (idx == i) {
                    sameYn = false;
                }
            }
        });

        //사이즈버튼(4) 클릭시 클래스 추가 
        if (sameYn) {
            $(this).addClass("click_button");
            clickYn = true;
        }

        // 사이즈버튼(4)은 색상버튼이 클릭되어야 활성화된다

        //사이즈버튼(4)이 클릭되면 색상과 사이즈(텍스트)를 받는 li태그와 버튼을 동적으로 생성
        if (clickYn) {

            var colorNm = ""; //선택된 컬럼명
            var sizeNm = ""; //선택된 사이즈명
            var val = ""; //선택된 버튼의 value값
            var NcolorNm = ""; //data-value 조합값 중 색상
            var NsizeNm = ""; //data-value 조합값 중 사이즈
            var qty = ""; //수량 임시값
            
            //색상 버튼과 사이즈 버튼의 value값은 현재 앞에서부터 순서대로 1,2로 되어있다. 상품중복체크를 위해 사용되는변수

            $.each($(".infoArea > ul > li:nth-child(3) button"), function (i) {
                if ($(this).hasClass("click_button")) {
                    colorNm = $(this).text();
                    val = $(this).val(); //선택된 컬러버튼(4)의 value 값
                    
                    NcolorNm = $(this).attr("data-value");
                    console.log(NcolorNm);
                }
            });

            sizeNm = $(this).text();
            NsizeNm = $(this).attr("data-value");
            console.log(NsizeNm);
            val = val + $(this).val(); //선택한 색상과 사이즈버튼의 values값 ex) 11 , 12,  21 , 22 ...

            var existYn = true; //이미 존재하는 상품인지 확인하기 위한 변수


            //이미 존재하는 상품인 경우 alert 처리
            $.each($(".productlist_add > li > .del_button"), function (i) {
                if ($(this).val() == val) {
                    alert("이미 넣으신 상품입니다.");
                    existYn = false;
                }
            });


            //이전에 없었던 상품을 넣을 경우 ul 태그에 새롭게 추가 , 최초에는 버튼의 value값을 1로 준다(최초에는 상품 1개를 넣는것이기 때문에 )
            if (existYn) {
                //사이즈를 클릭시 선택한 색상과 사이즈 정보를 동적으로 생성
                var price = $("#detail-item-price").text().replace(",", "");
                console.log(price);
//                 str = `
//                 	<li>
//                 		<span>${colorNm},${sizeNm} 1개</span>
//                 		<button class='add_button' value='1' onclick=add($(this));> + </button>
//                 		<button class='minus_button' value = '1' onclick=minus($(this));> - </button>
//                 		<button value = ${val}  class='del_button' onclick=del($(this));> x </button>
//                 	</li>
//                 `;
                var str = "<li><span data-value='" + NcolorNm + "" + NsizeNm + "'>" + colorNm + "," + sizeNm + " " + "1개" + "</span> <button class='add_button' value='1' onclick=add($(this));> + </button><button class='minus_button' value = '1' onclick=minus($(this));> - </button><button value = " + val + "  class='del_button' onclick=del($(this));> x </button></li>";
                var child = $(str);
                $(".productlist_add").append(child);
                // 상품 목록 추가 시 동적으로 히든 입력 영역에 각각 옵션값, 수량값에 해당하는 input 생성 
                $("#needVal").append("<div data-value='pd_" + NcolorNm + "" + NsizeNm + "'>");
				$("#needVal div[data-value=pd_" + NcolorNm + "" + NsizeNm + "]").append("<input type='hidden' name='pdoption' id='pdoption' value='" + NcolorNm + "" + NsizeNm + "'>");
				$("#needVal div[data-value=pd_" + NcolorNm + "" + NsizeNm + "]").append("<input type='hidden' name='pdoption' id='pdqty_" + NcolorNm + "" + NsizeNm + "' value='1'>");
				pdcode = child.find("span").data("value");
				
				console.log(">>>> %s", pdcode);
				
				price = Number(price);
                var curPrice = Number($(".allPrice").text());
                /* var qty = Number($(".productlist_add li").length); */
                

                $(".allPrice").text(price + curPrice);
                
            }
        }

    });

    // 상품명 옆에 x버튼 클릭시 목록제거
    function add(obj) {
        var text = obj.parent("li").text(); // 선택한 버튼태그의 부모 li 에 있는 text 가져온다.
        // ex) 블랙,Free 1개
        var cnt = Number(obj.val()) + 1; // 선택한 버튼태그의 value값을 가져와서 1 증가시켜준다.
        // ex) 현재 1이면 2로 된다.
        text = text.split(" ")[0] + " " + cnt + "개"; // text 를 공백으로 나누어서 0번째 데이터 가져온 뒤 새롭게 증가된 개수를 적용시킨다.
        // ex)가져온 text가  '블랙,Free 1개' 라면 공백으로 나누면 블랙,Free 와 1개로 나뉘고 0번째 데이터는 블랙,Free
        // text.split(" ")[0]  +  " " + cnt + "개"   --> 블랙,Free 2개 로 됨
        obj.val(cnt); // 선택한 버튼태그의 value값을 가져와서 1 증가시켜 준뒤 그 값을 다시 value 값으로 설정한다. 
        obj.parent("li").find(".minus_button").val(cnt); // 마이너스 버튼 태그의 value 값도 똑같이 1증가된 상태를 value값으로 설정한다. 
        obj.parent("li").find("span").text(text); // span 태그에 블랙,Free 2개 를 넣는다.
        pdcode = obj.parent("li").find("span").data("value"); // 버튼의 data-value값을 조합해 코드를 생성
		qty = cnt;
        $("#needVal div[data-value=pd_"+ pdcode + "]" + " #pdqty_" + pdcode).val(qty); // 해당 코드에 맞는 수량값을 처리
        
        var price = $("#detail-item-price").text().replace(",", "");
        price = Number(price);

        var curPrice = Number($(".allPrice").text());
        $(".allPrice").text(curPrice + price);
    }

    // 상품명 옆에 -버튼 클릭시 상품 개수를 내려주는 함수
    function minus(obj) {
        var text = obj.parent("li").text(); // 선택한 버튼태그의 부모 li 에 있는 text 가져온다.
        var cnt = Number(obj.val()) - 1; // 선택한 버튼태그의 value값을 가져와서 1 감소시켜준다.
        if (cnt > 0) {
            text = text.split(" ")[0] + " " + cnt + "개";
            obj.val(cnt);
            obj.parent("li").find(".add_button").val(cnt);
            obj.parent("li").find("span").text(text);
            pdcode = obj.parent("li").find("span").data("value");
            qty = cnt;	
            $("#needVal div[data-value=pd_"+ pdcode + "]" + " #pdqty_" + pdcode).val(qty) // 해당 코드에 맞는 수량값을 처리

            var price = $("#detail-item-price").text().replace(",", "");
            price = Number(price);

            var curPrice = Number($(".allPrice").text());
            $(".allPrice").text(curPrice - price);
        }
    }

    // 상품명 옆에 x버튼 클릭시 목록제거
    function del(obj) {
        var cancel = Number(obj.parent("li").find(".add_button").val());
        var curPrice = Number($(".allPrice").text());
        var price = $("#detail-item-price").text().replace(",", "");
        price = Number(price);
        curPrice = curPrice - cancel * price;
        $(".allPrice").text(curPrice);
		pdcode = obj.parent("li").find("span").data("value");
        obj.parent("li").remove();
        $("#needVal #pdqty_" + pdcode).parent().remove();
    }

    Array.prototype.add = function (...param) {
        this.push(...param);
		console.log(this);
        return this;
    };

    function pay() {
        var lilength = $(".productlist_add li").length;
        console.log(lilength);

        if (lilength == 0) {
            alert("선택된 상품이 없습니다");
            return false;
        }

        if (lilength != null) {
            for (var i = 0; i < lilength; i++) {
                var str = $(".productlist_add > li > span").text();
                console.log(str);

                var array = new Array();
                array = str.split(" ");
                console.log(array);
                console.log(array[i]);

                // var title = $("#detail-item-title").text()
                // var price = $(".allPrice").text();
            	// var amount = $(".add_button").attr("value");
            	// var productId = new URLSearchParams(location.search).get("id");

            	
            	// //List list = new List();
            	
                // list.add("title", title);
                // list.add("price", price/amount);
                // list.add("amount", amount);
            	
                // var json_arr = {};
                //     json_arr["'product" + i + "'"] = list;

                // var json_string = JSON.stringify(json_arr);
                
                // console.log(json_string);
                
                var title = $("#detail-item-title").text()
	            var allprice = $(".allPrice").text();
	        	var amount = $(".add_button").attr("value");
	        	var productId = new URLSearchParams(location.search).get("id");
	        	var price = $("#detail-item-price").text();
	        	
	        	console.log(title, price, amount, productId);
	        	console.log(allprice);
                
                if(($(".productlist_add li").length > 0)) {
                    
                    var code;

                    var dataArray = new Array();

                    var orderInfo = new Object();
                    
                    for(var i = 0; i < $(".productlist_add li").length; i++) {
                    
                    	orderInfo = {
                        
                    			"title" : title,
                    			"amount" : amount,
                            	"price" : price,
                            	"productId" : productId,
                            	"allprice" : allprice
                        };                    
                    
                    	code = "";
                        opt_code = $("#needVal input[id=pdoption]:eq("+i+")").val();
                        qty_code = $("#needVal > div[data-value=pd_" + opt_code + "]").find("input:last").val();
                        
                        code = opt_code + "," +qty_code;
                        orderInfo.pdoption = code;
                    
                        dataArray.push(orderInfo);
                        console.log(dataArray);
                        
                    }              
                    
                    
            
                } 

                Manager.processPay();
                
                
                /* 
                
				int choice;
                
                choice = JOptionPane.showConfirmDialog(null, 
						"상품이 장바구니에 담겼습니다 \n" 
			 			 + "장바구니로 이동 하시겠습니까?", null, 1);
                
                switch(choice) {
				case 0 : request.setAttribute("title", title);
						 request.setAttribute("price", price);
						 request.setAttribute("amount", amount);
						 break;
						 
				case 1 : $("#needVal").attr("target","iframe");
						 break;
				} */

				// 출처: https://unikys.tistory.com/215 [All-round programmer]
				
				

            }
        }
    }
    
    function processSubmit(data, url) {
        $("#needVal").attr("action", url);
        
        /* var titleInput = $("#needVal").find("input[name='title']");
        var amountInput = $("#needVal").find("input[name='amount']");
        var productIdInput = $("#needVal").find("input[name='productId']");
        var priceInput = $("#needVal").find("input[name='price']");
        
        if (titleInput.length > 0) priceInput.val(data.title);
        if (amountInput.length > 0) priceInput.val(data.amount); 
        if (productIdInput.length > 0) priceInput.val(data.productId);
        if (priceInput.length > 0) priceInput.val(data.price); */

        $("#needVal").submit();
    }

    function cart() {
        var lilength = $(".productlist_add li").length;
        console.log(lilength);

        if (lilength == 0) {
            alert("선택된 상품이 없습니다");
            return false;
        }

        if (lilength != null) {
            for (var i = 0; i < lilength; i++) {
                var str = $(".productlist_add > li > span").text();
                console.log(str);

                var array = new Array();
                array = str.split(" ");
                console.log(array);
                console.log(array[i]);

                // var title = $("#detail-item-title").text()
                // var price = $(".allPrice").text();
            	// var amount = $(".add_button").attr("value");
            	// var productId = new URLSearchParams(location.search).get("id");

            	
            	// //List list = new List();
            	
                // list.add("title", title);
                // list.add("price", price/amount);
                // list.add("amount", amount);
            	
                // var json_arr = {};
                //     json_arr["'product" + i + "'"] = list;

                // var json_string = JSON.stringify(json_arr);
                
                // console.log(json_string);
                
                var title = $("#detail-item-title").text()
	            var allprice = $(".allPrice").text();
	        	var amount = $(".add_button").attr("value");
	        	var productId = new URLSearchParams(location.search).get("id");
	        	var price = $("#detail-item-price").text();
	        	
	        	console.log(title, price, amount, productId);
	        	console.log(allprice);
                
                if(($(".productlist_add li").length > 0)) {
                    
                    var code;

                    var dataArray = new Array();

                    var orderInfo = new Object();
                    
                    for(var i = 0; i < $(".productlist_add li").length; i++) {
                    
                    	orderInfo = {
                        
                    			"title" : title,
                    			"amount" : amount,
                            	"price" : price,
                            	"productId" : productId,
                            	"allprice" : allprice
                        };                    
                    
                        code = "";
                        opt_code = $("#needVal input[id=pdoption]:eq("+i+")").val();
                        qty_code = $("#needVal > div[data-value=pd_" + opt_code + "]").find("input:last").val();
                        
                        code = opt_code + "," +qty_code;
                        orderInfo.pdoption = code;
                    
                        dataArray.push(orderInfo);
                        console.log(dataArray);
                        
                    }                
            
                } 
                
                Manager.processCart();
                
                // processSubmit(dataArray, "/contents/cart.do");
                /* 
                
				int choice;
                
                choice = JOptionPane.showConfirmDialog(null, 
						"상품이 장바구니에 담겼습니다 \n" 
			 			 + "장바구니로 이동 하시겠습니까?", null, 1);
                
                switch(choice) {
				case 0 : request.setAttribute("title", title);
						 request.setAttribute("price", price);
						 request.setAttribute("amount", amount);
						 break;
						 
				case 1 : $("#needVal").attr("target","iframe");
						 break;
				} */

				// 출처: https://unikys.tistory.com/215 [All-round programmer]
				
				

            }
        }
    }
        </script>
        
        <jsp:include page="/pages/login.jsp"></jsp:include>
        <script type="module" src="/js/ItemDetailPage.js"></script>
        <script type="module" src="/js/CookieServiceForItem.js"></script>
    </body>
</html>