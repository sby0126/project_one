<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
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
        <header>
            <div class="header-wrapper">
                <div class="header-left">
                    <div class="" id="logo">
                        <a href="../index.jsp"><img src="https://dummyimage.com/64x64/000/fff" alt=""></a>
                    </div>
                    <a href="#">WOMEN</a>
                    <a href="#">MEN</a>
                </div>
                <div class="header-center">
                    <a href="../index.jsp">SHOP</a>
                    <a href="../item.jsp">ITEM</a>
                    <a href="../sale.jsp">SALE</a>
                    <a href="#">MORE</a>
                </div>
                <div class="header-right">
                    <button class="header-right-login-button">로그인</button>
                    <input type="text" name="" class="input-non-border-box" id="" placeholder="검색어를 입력하세요">
                </div>
            </div>
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
        <section>
            <div class="contents-wrapper"> <!-- 장바구니 정보 영역 -->
                <h2>장바구니</h2>
                <div class="up-line-bd"></div>
                <div class="product-info-zone"> <!-- 실 상품 정보 영역 -->
                    <form class="product-info">
                        <table class="product">
                            <tr class="label">
                                <td><input type="checkbox" name="chk"></td>
                                <td><p name="product-img"> 상품 이미지 </td> <!-- 구매할 상품 이미지 -->
                                <td><a href="#"><p name="product-name"> 상품명</p></a></td> <!-- 구매할 상품 이름 -->
                                <td><p name="product-num"> 구매 갯수 </p></td> <!-- 구매 갯수 -->
                                <td><P name="product-price"> 가격 </P></td> <!-- 상품 가격-->
                                <td><P name="product-price"> 할인 </P></td> <!-- 할인 -->
                                <td><P name="product-price"> 총 금액 </P></td> <!-- 상품 금액 -->
                            </tr>
                            <tr>
                                <td><input type="checkbox" class="chkbox"></td>
                                <td><img src="#" class="product-img"></td> <!-- 구매할 상품 이미지 -->
                                <td><a href="#"><p class="product-name">상품명 1234</p></a> </td> <!-- 구매할 상품 이름 -->
                                <td><input type="number" class="product-num" placeholder="1"></td> <!-- 구매 갯수 -->
                                <td><P name="product-price"> 가격 </P></td> <!-- 상품 가격-->
                                <td><P name="product-price"> 할인 </P></td> <!-- 할인 -->
                                <td><P class="product-price">price 원</P> </td> <!-- 상품 금액 -->
                            </tr>
                        </table>
                    </form>
                </div>
                <div class="down-line-bd"></div>
                <div class="cart-result"> <!-- 장바구니 내 구매할 상품에 대한 총 정보 -->
                    <div class="result-buy-cost">
                        <p>총 </p>
                        <p class="total-num">n개 상품</p>
                        <p class="total-price">XXX원</p>
                    </div>
                </div>
                <div class="button-zone"> <!-- 장바구니 내 추가 액션에 대한 버튼 -->
                    <button class="buy-product">구매</button>
                    <button class="del-product">삭제</button>
                </div>
            </div>
        </section>
    </div>
    <!-- 라이트 박스-->
    <div id="light-box-container">
    </div>
    <!-- 스크립트 --> 
    <script type="module" src="../js/MorePage.js"></script>
</body>
</html>
