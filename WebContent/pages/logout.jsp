<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login</title>
    <link rel="stylesheet" href="../css/style.css">
    <script src="https://kit.fontawesome.com/a99df0f94f.js" crossorigin="anonymous"></script>
    <script>

        /**
         * 디자인을 보기 위해 넣은 코드입니다.
         * 실제로는 삽입되지 않습니다.
         */
        window.addEventListener("load", () => {
            const loginView = document.querySelector(".floating-login-view-wrapper");
            loginView.style.left = "0";
        });
         
    </script>
    <style>
        
    </style>
</head>
<body>
    <div class="floating-login-view-wrapper">
        <div class="floating-login-view">
            <!-- 닫기 버튼 -->
            <div class="floating-login-view-right">
                <button id="close-login-view">X</button>
            </div>
        </div>
    </div>
</body>
</html>
