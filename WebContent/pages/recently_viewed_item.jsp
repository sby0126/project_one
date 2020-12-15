<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>최근 본 상품</title>
    <link href="https://fonts.googleapis.com/css2?family=Nanum+Gothic&display=swap" rel="stylesheet">
    <script src="https://kit.fontawesome.com/a99df0f94f.js" crossorigin="anonymous"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/underscore@1.11.0/underscore-min.js"></script>
    <link rel="stylesheet" href="../css/style.css">
    <link rel="stylesheet" href="../css/Recently_viewed_item.css">
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
                </div>
                <!-- 비어있을때-->
                <div class="item_selected_none">
                    <button class="item_selected_none_all_del">전체삭제</button>
                    <img class="item_selected_none_img" src="../images/fbb847b.png">
                    <div class="item_selected_none_sp">최근 본 상품이 없습니다.</div>
                    <a href="../item.jsp"><button class="item_selected_none_button">아이템 메인으로 이동</button></a>
                </div>
            </div>
        </section>
    </div>
    <!-- 라이트 박스-->
    <div id="light-box-container">
    </div>
    <jsp:include page="/pages/login.jsp"></jsp:include> 
    <!-- index.js는 메인 용이므로 알맞은 스크립트를 사용해야 합니다-->
    <script type="module" src="../js/MorePage.js"></script>
    
</body>
</html>
