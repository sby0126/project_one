<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Document</title>
    <link href="https://fonts.googleapis.com/css2?family=Nanum+Gothic&display=swap" rel="stylesheet">
    <script src="https://kit.fontawesome.com/a99df0f94f.js" crossorigin="anonymous"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/underscore@1.11.0/underscore-min.js"></script>
    <link rel="stylesheet" href="../css/style.css">
    <link rel="stylesheet" href="../css/interested_item.css">
</head>
<body>
    <div class="container">
        <!-- 헤더의 시작 -->
        <header>
            <div class="header-wrapper">
                <!-- 헤더 왼쪽 : 로고 -->
                <div class="header-left">
                    <div class="" id="logo">
                        <a href="./"><img src="https://dummyimage.com/64x64/000/fff" alt=""></a>
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
                            <a href="#" class="menu-link">
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
            <!-- 검색 필터 -->
           
        </header>

        <!-- 본문의 시작 -->
        <section>


            <!-- 본문이 들어가는 래퍼 -->
            <div class="contents-wrapper">
                <!-- 아이템 총-->
                <div class="item_selected">
                    <!-- 아이템-->
                    <div class="item_selected_list">
                        <img src="../images/img1.jpg">
                        <div class="item_selected_list_info">
                            <h3>하찌 트임 루즈 니트(데이터)</h3>
                            <p>15000원</p>
                            <p>제이브로스</p>
                            <button></button>
                        </div>
                    </div>
                   <!-- <div class="item_selected">
                        <img src="../images/img1.jpg">
                    </div>
                    <div class="item_selected">
                        <img src="../images/img1.jpg">
                    </div>
                    <div class="item_selected">
                        <img src="../images/img1.jpg">
                    </div>-->
                </div>
                <!-- 비어있을때-->
                <div class="item_selected_none">
                    <button class="item_selected_none_all_del">전체삭제</button>
                    <img class="item_selected_none_img" src="../images/fbb847b.png">
                    <div class="item_selected_none_sp">관심 상품이 없습니다.</div>
                    <a href="../item.jsp"><button class="item_selected_none_button">아이템 메인으로 이동</button></a>
                </div>
            </div>
        </section>
    </div>
    <!-- 라이트 박스-->
    <div id="light-box-container">
    </div>
    <!-- index.js는 메인 용이므로 알맞은 스크립트를 사용해야 합니다-->
    <script type="module" src="../js/MorePage.js"></script>
    
</body>
</html>
