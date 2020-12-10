<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.net.URLDecoder" %>
<!DOCTYPE html>
<%
	String key = (String)session.getAttribute("key");

	String sessionKey = (String)session.getAttribute("key");
	String passedKey = request.getParameter("auth");
	
	if(sessionKey == null) {
		sessionKey = "";
	}
	
	if(passedKey == null) {
		passedKey = "";
	}

%>
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
                    <form>
                        <ul>
                            <li>
                           	<div>
                           	<%
                           		if(sessionKey.equals(passedKey)) {
                           			session.setAttribute("id", session.getAttribute("tempId"));
                           			session.removeAttribute("tempId");
                           	%>
                           		<div>
                           			<p> 인증되었습니다 </p><br>
                           			<p> 변경된 비밀번호는 <strong></strong>입니다</p>
                           		</div>
                           	<%

                           		session.removeAttribute("key");
                           		} else {
                           	%>
                               	<div id="pw-message">
                               		메일로 전송 받은 인증 번호를 입력해주세요.
                               	</div>                            	                             	                     
                            	<div>
                            		<strong>이메일 : </strong>
                            		<span><input type="text" id="auth" name="auth" autocomplete="auth"></span>
                            	</div>
                            <%
                           		}                            	
                            %>
                            </li>
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
                document.querySelector("#auth").value.length > 0,
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
