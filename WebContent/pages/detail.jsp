<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Document</title>
        <link rel= "stylesheet" type="text/css" href="../css/detail.css">
        <link rel= "stylesheet" type="text/css" href="../css/style.css">
        
        

        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    </head>
    <body>
        <header>
            <div class="container">
                <div class="header-wrapper">
                    <!-- 헤더 왼쪽 : 로고 -->
                    <div class="header-left">
                        <div class="" id="logo">
                            <a href="../index.jsp"><img src="https://dummyimage.com/64x64/000/fff" alt=""></a>
                        </div>
                        <a href="#">WOMEN</a>
                        <a href="#">MEN</a>
                    </div>
                    <!-- 헤더 중앙 : 메뉴 -->
                    <div class="header-center">
                        <a href="../index.jsp">SHOP</a>
                        <a href="../item.jsp" target="_self">ITEM</a>
                        <a href="../sale.jsp">SALE</a>
                        <a href="#">MORE</a>
                    </div>
                    <!-- 헤더 오른쪽 : 로그인 / 검색 -->
                    <div class="header-right">
                        <button class="header-right-login-button">로그인</button>
                        <input type="text" class="input-non-border-box" name="" id="" placeholder="검색어를 입력하세요">
                    </div>
                </div>
                    <!-- 숨겨진 메뉴 -->
                    <div class="header-popup-container">
                        <ul class="header-menu-list">
                            <li class="menu">
                                <a href="./Recently_viewed_shop.jsp" class="menu-link">
                                    <div class="menu-icon"></div>
                                    <p class="menu-title">최근 본 샵 <em>0</em></p>
                                </a>
                            </li>
                            <li class="menu">
                                <a href="./Recently_viewde_item.jsp" class="menu-link">
                                    <div class="menu-icon"></div>
                                    <p class="menu-title">최근 본 상품 <em>0</em></p>
                                </a>
                            </li>
                            <li class="menu">
                                <a href="#" class="menu-link">
                                    <div class="menu-icon"></div>
                                    <p class="menu-title">MY SHOP</p>
                                </a>
                            </li>
                            <li class="menu">
                                <a href="./Interested_item.jsp" class="menu-link">
                                    <div class="menu-icon"></div>
                                    <p class="menu-title">관심 상품</p>
                                </a>
                            </li>
                            <li class="menu">
                                <a href="./board-default.jsp" class="menu-link">
                                    <div class="menu-icon"></div>
                                    <p class="menu-title">1:1 문의</p>
                                </a>
                            </li>
                            <li class="menu">
                                <a href="./map.jsp" class="menu-link">
                                    <div class="menu-icon"></div>
                                    <p class="menu-title">소개 및 약관</p>
                                </a>
                            </li>
                            <li class="menu">
                                <a href="./outer-link-page.jsp" class="menu-link">
                                    <div class="menu-icon"></div>
                                    <p class="menu-title">마케팅 센터</p>
                                </a>
                            </li>
                        </ul>
                    </div>            
               
            </header>
            
            <div class="item_content" id="item_content">
                <!-- 상품이미지, 상품정보 -->
                <div class="productArea">
                    <!-- 상품이미지 -->
                    <div class="imgArea">
                        <img src="https://dummyimage.com/480x500/000/fff">
    
                    </div>
                    <!-- 상품구매정보 -->
                    <div class="infoArea">
                        <ul class>
                            <!-- 1. 상품명 -->
                            <li><h2 id="detail-item-title">후드후드후드(데이터)</h2></li>
                            <!-- 2. 가격 -->
                            <li><span>가격</span><span id="detail-item-price">가격(데이터)</span></li> 
                            <!-- 3. 색상 -->
                            <li><span>색상</span><div class="color_option"><button class="detail_button" value="1">블랙(데이터)</button><button class="detail_button" value="2">화이트(데이터)</button></div></li>
                            <!-- 4. 사이즈 -->
                            <li><span>사이즈</span><div class="size_option"><button disabled="true" value="1" class="detail_button">FREE(데이터)</button><button disabled="true" value="2" class="detail_button">100(데이터)</button></div></li>
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
                            <li><div><button class="detail_button" type="button" onclick="location.href='pages/payment.jsp?date=${Date.now()}&title=${title}&price=${price}&shop=${shop}&dataId=${dataId}'">구매하기</button><button class="detail_button" type="button" onclick="location.href='pages/cart.jsp?date=${Date.now()}&title=${title}&price=${price}&shop=${shop}&dataId=${dataId}'">장바구니</button></div></li>
                        </ul>
                    </div>
                    
                </div>
    
                
                <div class="productArea2">
                    <!-- 중간 상품상세설명, 상품결제정보 버튼 -->
                    <div class="productArea2_button">
                        <ul class="productArea2_buttonList">
                            <li><a href="#productArea2_detailImg">상품상세설명</a></li>
                            <li><a href="#productPayInfo" >상품결제정보</a></li>
                        </ul>
                    </div>
                    <!-- 상품상세설명 -->
                    <div class="productArea2_detailImg" id="productArea2_detailImg">
                        <img src="../images/img1info.jpg">
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
                            <h3 >결제안내</h3>
                            <span>고객결제의 경우 안전을 위해 카드사에서 확인전화를 드릴 수도 있습니다. 확인과정에서 도난 카드의 사용이나 타인 명의의 주문등 정상적인 주문이
                            아니라고 판단될 경우 임의로 주문을 보류 또는 취소할 수 있습니다.
                            </span>
                        </div>
                        <div>
                            <h3 >배송정보</h3>
                            <span>배송 방법: 택배<br>
                            배송 지역: 전국지역<br>
                            배송 비용: 2,500원<br>
                            배송 기간: 3일 ~ 7일<br>
                            배송 안내 : 산간벽지나 도서지방은 별도의 추가금액을 지불하셔야 하는 경우가 있습니다.
                            </span>
                        </div>
                        
                    </div>
                </div>
                <!-- 오른쪽하단 top.bottom 버튼 -->
                <div class="item_top_bottom">
                    <a href="#item_content" id="item_top"><span class="item_top_button">top</span></a>
                    <a href="#productPayInfo" id="item_bottom"><span class="item_bottom_button">bottom</span></a>
                </div>
                
    
                
            </div>
            </div>

        <script>
            // 색상버튼은(ul태그안에 li목록중 3번째줄), 사이즈버튼은(ul태그안에 li목록중 4번째줄)

            // 상품 색상버튼(3) 클릭시 발생하는 이벤트
            $(".infoArea > ul > li:nth-child(3) button").on("click",function(){
                var idx = $(this).index();  // 상품 색상버튼클릭한거에 인덱스값
                var sameYn = true;                      
                var clickYn = false;

                // 색상버튼(3)을 반복하면서 클릭여부 확인
                $.each($(".infoArea > ul > li:nth-child(3) button"),function(i){ 
                    // 색상버튼(3)을 반복하면서 클래스 모두 제거
                    if($(this).hasClass("click_button")){
                        $(this).removeClass("click_button");
                        // 클릭한 버튼이 기존에 클릭된 버튼과 같다면 sameYn = false(클릭된 버튼을 한번더 클릭하면 클릭되지 않은 상태로 두어야함)
                        if(idx == i){
                            sameYn = false;
                        }
                    }
                });
                
                // 색상버튼(3)을 클릭시 클래스 추가
                if(sameYn){
                    $(this).addClass("click_button");
                    clickYn = true;
                }

                // 색상버튼(3) 클릭시 사이즈버튼(4) 활성화 
                if(clickYn){
                    $(".infoArea > ul > li:nth-child(4) button").prop("disabled",false);
                // 색상버튼(3) 클릭된걸 다시 클릭시 사이즈버튼(4) 비활성화 
                }else{
                    $(".infoArea > ul > li:nth-child(4) button").removeClass("click_button");
                    $(".infoArea > ul > li:nth-child(4) button").prop("disabled",true);
                }
            })
            
            //사이즈버튼(4) 클릭시 발생하는 이벤트
            $(".infoArea > ul > li:nth-child(4) button").on("click",function(){
                var idx = $(this).index();
                var sameYn = true;
                var clickYn = false;

                // 사이즈버튼(4)을 반복하면서 클릭여부 확인
                $.each($(".infoArea > ul > li:nth-child(4) button"),function(i){
                    //사이즈버튼 다 돌면서 클래스 전부 제거
                    if($(this).hasClass("click_button")){
                        $(this).removeClass("click_button");
                        // 클릭한 버튼이 기존에 클릭된 버튼과 같다면 sameYn = false(클릭된 버튼을 한번더 클릭하면 클릭되지 않은 상태로 두어야함)
                        if(idx == i){
                            sameYn = false;
                        }
                    }
                });
                
                //사이즈버튼(4) 클릭시 클래스 추가 
                if(sameYn){
                    $(this).addClass("click_button");
                    clickYn = true;
                }

                // 사이즈버튼(4)은 색상버튼이 클릭되어야 활성화된다
                
                //사이즈버튼(4)이 클릭되면 색상과 사이즈(텍스트)를 받는 li태그와 버튼을 동적으로 생성
                if(clickYn){

                    var colorNm = ""; //선택된 컬럼명
                    var sizeNm  = ""; //선택된 사이즈명
                    var val = "";     //선택된 버튼의 value값
                                      //색상 버튼과 사이즈 버튼의 value값은 현재 앞에서부터 순서대로 1,2로 되어있다. 상품중복체크를 위해 사용되는변수
                    $.each($(".infoArea > ul > li:nth-child(3) button"),function(i){
                        if($(this).hasClass("click_button")){
                            colorNm = $(this).text();
                            val = $(this).val();    //선택된 컬러버튼(4)의 value 값
                        }
                    });

                    sizeNm = $(this).text();
                    val = val + $(this).val();     //선택한 색상과 사이즈버튼의 values값 ex) 11 , 12,  21 , 22 ...

                    var existYn = true; //이미 존재하는 상품인지 확인하기 위한 변수

                    //이미 존재하는 상품인 경우 alert 처리
                    $.each($(".productlist_add > li > .del_button"),function(i){     
                        if($(this).val() == val){
                            alert("이미 넣으신 상품입니다.");
                            existYn = false; 
                        }
                    });
                    

                    //이전에 없었던 상품을 넣을 경우 ul 태그에 새롭게 추가 , 최초에는 버튼의 value값을 1로 준다(최초에는 상품 1개를 넣는것이기 때문에 )
                    if(existYn){
                        //사이즈를 클릭시 선택한 색상과 사이즈 정보를 동적으로 생성
                        var str = "<li><span>" + colorNm + "," + sizeNm + " " + "1개"+ "</span> <button class='add_button' value='1' onclick=add($(this));> + </button><button class='minus_button' value = '1' onclick=minus($(this));> - </button><button value = " + val + "  class='del_button' onclick=del($(this));> x </button></li>";
                        $(".productlist_add").append(str);
                    }
                }


            })
            

            // 상품명 옆에 +버튼 클릭시 상품 개수를 올려주는 함수
            function add(obj){
                var text = obj.parent("li").text();             // 선택한 버튼태그의 부모 li 에 있는 text 가져온다.
                                                                // ex) 블랙,Free 1개
                var cnt = Number(obj.val()) + 1;                // 선택한 버튼태그의 value값을 가져와서 1 증가시켜준다.
                                                                // ex) 현재 1이면 2로 된다.
                text = text.split(" ")[0] + " " + cnt + "개";   // text 를 공백으로 나누어서 0번째 데이터 가져온 뒤 새롭게 증가된 개수를 적용시킨다.
                                                                // ex)가져온 text가  '블랙,Free 1개' 라면 공백으로 나누면 블랙,Free 와 1개로 나뉘고 0번째 데이터는 블랙,Free
                                                                // text.split(" ")[0]  +  " " + cnt + "개"   --> 블랙,Free 2개 로 됨
                obj.val(cnt);                                   // 선택한 버튼태그의 value값을 가져와서 1 증가시켜 준뒤 그 값을 다시 value 값으로 설정한다. 
                obj.parent("li").find(".minus_button").val(cnt);// 마이너스 버튼 태그의 value 값도 똑같이 1증가된 상태를 value값으로 설정한다. 
                obj.parent("li").find("span").text(text);       // span 태그에 블랙,Free 2개 를 넣는다.
            }

            // 상품명 옆에 -버튼 클릭시 상품 개수를 내려주는 함수
            function minus(obj){
                var text = obj.parent("li").text();             // 선택한 버튼태그의 부모 li 에 있는 text 가져온다.
                var cnt = Number(obj.val()) - 1;                // 선택한 버튼태그의 value값을 가져와서 1 감소시켜준다.
                if(cnt > 0){
                    text = text.split(" ")[0] + " " + cnt + "개";
                    obj.val(cnt);
                    obj.parent("li").find(".add_button").val(cnt);
                    obj.parent("li").find("span").text(text);
                }
            }

            // 상품명 옆에 x버튼 클릭시 목록제거
            function del(obj){
                obj.parent("li").remove();
            }
           
        </script>
        <script type="module" src="../js/ItemDetailPage.js"></script>
    </body>
  
</html>
