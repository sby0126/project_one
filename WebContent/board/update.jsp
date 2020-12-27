<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ page import="vo.board.free.BoardBean"%>
<%
request.setCharacterEncoding("UTF-8");


	BoardBean article = (BoardBean)request.getAttribute("article");
	String nowPage = (String)request.getAttribute("page");
%>
<!DOCTYPE html>

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
    <script src="../js/smarteditor2/js/HuskyEZCreator.js"></script>
    <link rel="stylesheet" href="css/update.css">
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
            		<div class="updateBox">
    	
		<form name="updateFrm" id="updateFrm" method="post" action="boardUpdatePro.abc">
			<ul class="readBox2">
				<li><input placeholder="<%=article.getName() %>" readonly> </li>
				<li><input name="subject" value="<%=article.getSubject() %>"> </li>
				<li><input placeholder="<%=article.getDate() %>" readonly> </li>
				<li><textarea id ="popContent" rows="10" cols="50" name="content" style="display:none"><%=article.getContent() %></textarea></li>
			</ul>
				<div class="updateBtn2">
					<input type="submit" id="registBtn" value="수정" class="update-btn">
					<input type="button" class="update-btn" value="이전으로" onclick="location.href='boardDetail.abc?board_num=<%=article.getNum()%>&page=<%=nowPage%>'">
					<input type="button" class="update-btn" value="리스트" onclick="location.href='boardList.abc?page=<%=nowPage%>'">
				</div>	
					<input type="hidden" name="num" value="<%=article.getNum()%>">
					<input type="hidden" name="pass" value="<%=article.getPass()%>">
					<input type="hidden" name="ref" value="<%=article.getRe_ref()%>">
					<input type="hidden" name="lev" value="<%=article.getRe_lev()%>">
					<input type="hidden" name="seq" value="<%=article.getRe_seq()%>">
					<input type="hidden" name="page" value="<%=nowPage%>">
					<input type="hidden" name="name" value="<%=article.getName() %>" >
					<input type="hidden" name="regDate" value="<%=article.getDate() %>" >
				
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
    <script>
	    var oEditors = [];
		nhn.husky.EZCreator.createInIFrame({
		    oAppRef: oEditors,
		    elPlaceHolder: "popContent",  //textarea ID
		    sSkinURI: "../js/smarteditor2/SmartEditor2Skin.html",  //skin경로
		    fCreator: "createSEditor2",
		    htParams : {
		    	bUseToolbar : true,
		    	bUseVerticalResizer : true,
		    	bUseModeChanger : true
		    }
		});
	
	
			$("#registBtn").click(function(){
				oEditors.getById["popContent"].exec("UPDATE_CONTENTS_FIELD",[]);
			});
    </script>
    <script type="module" src="<%=application.getContextPath()%>/js/MorePage.js"></script>
</body>
</html>
