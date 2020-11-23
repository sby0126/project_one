<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Template</title>
    <link rel="stylesheet" href="../css/style.css">
    <link rel="stylesheet" href="../css/find-password.css">
    <link href="https://fonts.googleapis.com/css2?family=Nanum+Gothic&display=swap" rel="stylesheet">
    <script src="https://kit.fontawesome.com/a99df0f94f.js" crossorigin="anonymous"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
</head>
<body>
    <!-- 컨테이너의 시작 -->
    <div class="container">
        <!-- 헤더의 시작 -->
        <jsp:include page="header.jsp"></jsp:include>

        <!-- 본문의 시작 -->
        <section>
            <!-- 본문이 들어가는 래퍼 -->
            <div class="contents-wrapper">
                <div class="find-password-wrapper">
                    <h3>비밀번호 찾기</h3>
                    <form action="findPassword.jsp">
                        <ul>
                            <li>
                                <strong>회원 유형</strong>
                                <span>
                                    <select name="searchType" id="searchType">
                                        <option value="public" selected="selected">개인 회원</option>
                                        <option value="corp" selected="selected">법인 사원자 회원</option>
                                    </select>
                                </span>
                            </li>
                            <li>
                                <strong>아이디</strong>
                                <span><input type="text" id="id"></span>
                            </li>
                            <li><strong>이름</strong><span><input type="text" id="name"></span></li>
                            <li><strong>이메일로 찾기</strong><span><input type="text" id="email"></span></li>
                        </ul>
                        <div class="find-password-button-container">
                            <input type="submit" value="확인" id='ok'>
                        </div>
                    </form>
                </div>
            </div>
        </section>
    </div>
    <!-- 라이트 박스-->
    <div id="light-box-container">
    </div>
    <!-- 스크립트 --> 
    <script type="module" src="../js/MorePage.js"></script>
    <script>        

        // 작성되지 않은 항목 체크
        $("#ok").on("click", (ev) => {
            let list = [
                document.querySelector("#id").value.length > 0,
                document.querySelector("#name").value.length > 0,
                document.querySelector("#email").value.length > 0,
            ];

            const isValid = list.every(e => !!e == true);

            if(!isValid) {
                ev.preventDefault();
                ev.stopPropagation();

                alert("작성되지 않은 항목이 있습니다.");
            }
        })
        
    </script>
</body>
</html>
