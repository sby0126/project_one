<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Template</title>
    <link rel="stylesheet" href="../css/style.css">
    <link rel="stylesheet" href="../css/map.css">
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
            <div class="contents-wrapper"> 
                <div class="inner-menu">
                    <a href="#" class="active">회사소개</a>
                    <a href="./terms.jsp">이용약관</a>
                    <a href="./infopolicy.jsp">개인정보처리방침</a>
                </div>
                <div class="inner-contents">
                    <div class="inner-banner">
                        <img src="../images/logo_big.jpg">
                    </div>
                        <div class="inner-info-desc">
                            <div class="inner-info-desc-left">
                            <p>회사명</p>
                            <p>사업자번호</p>
                            <p>설립일</p>
                            <p>대표</p>
                            <p>대표전화</p>
                            <p>주소</p>
                        </div>
                        <div class="inner-info-desc-right">
                            <span>(주) 스타일닷컴</span>
                            <span>211-88-68398</span>
                            <span>2003년 6월 9일</span>
                            <span>박규도</span>
                            <span>02.3442.3555</span>
                            <span>서울시 송파구 송파대로 201 송파테라타워2 B동 1712호 (주)스타일닷컴</span>
                        </div>
                    </div>
                    <div class="map-zone">
                        <div id="map"><br>&nbsp;지도 영역</div>
                    </div>
                </div>
            </div>
        </section>
    </div>
    <div id="light-box-container">
    </div>
    <jsp:include page="/pages/login.jsp"></jsp:include> 
    <script>
        let map = null;

        function initMap() {    
            map = new google.maps.Map(document.getElementById("map"), {
                center: { lat: 37.488167, lng: 127.120589 },
                scrollwheel : false,
                zoom: 16
            });
            
            let marker = new google.maps.Marker({
                map : window.map,
                position : {lat: 37.488167, lng: 127.120589},
            });                 
        }
    </script>
    <script type="module" src="../js/MorePage.js"></script>
    <script defer src="https://maps.googleapis.com/maps/api/js?key=AIzaSyBpiW6aUM_rjCKPQ_ZcMNUhtRSCAds9Ttk&callback=initMap"></script>
</script>
    
</body>
</html>
