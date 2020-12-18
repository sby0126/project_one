<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<!DOCTYPE html>
<% String userId = (String)session.getAttribute("id"); %>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>쇼핑몰 클론 프로젝트</title>
    <link rel="stylesheet" href="<%=application.getContextPath()%>/css/style.css">
    <link href="https://fonts.googleapis.com/css2?family=Nanum+Gothic&display=swap" rel="stylesheet">
    <script src="https://kit.fontawesome.com/a99df0f94f.js" crossorigin="anonymous"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/underscore@1.11.0/underscore-min.js"></script>
    <link rel="stylesheet" href="css/post.css">
</head>
<body>
    <!-- 컨테이너의 시작 -->
    <div class="container">
        <jsp:include page="/header.jsp">
        	<jsp:param value="true" name="isHide"/>
        </jsp:include>
        	
	 		
        <!-- 본문의 시작 -->
        <section>
            <!-- 본문이 들어가는 래퍼 -->
            <div class="contents-wrapper">
            		<div class="post">
    	<h2>쓰기</h2>
		<form name="postFrm" method="post" action="boardWritePro.abc">
			<ul>
				<li><input readonly placeholder="<%=userId%>"></li>
				<input type="hidden" name="name" value="<%=userId%>">
				<li><input name ="subject" placeholder="제목"></li>
				<li><textarea name="content" rows="10" cols="50" placeholder="내용"></textarea></li>
				<li><input type="password" name ="pass" placeholder="비밀번호"></li>
				<li>
					<input type="submit" value="등록" class="post-btn">
					<input type="reset" value="다시쓰기" class="post-btn">	
					<a href="boardList.abc"><input type="button" value="리스트" class="post-btn"></a>	<!-- 경로수정하기 -->
				</li>
		
		
			</ul>
	
		</form>
	</div>
          
           
            </div>
        </section>
    </div>
    <!-- 라이트 박스-->
    <div id="light-box-container">
    </div>
    <jsp:include page="/pages/login.jsp"></jsp:include>
    <!-- index.js는 메인 용이므로 알맞은 스크립트를 사용해야 합니다-->
    <script type="module" src="<%=application.getContextPath()%>/js/MorePage.js"></script>
</body>
</html>
