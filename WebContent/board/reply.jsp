<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<!DOCTYPE html>

<%@ page import="vo.board.free.BoardBean"%>
<%
	BoardBean article = (BoardBean)request.getAttribute("article");
    String nowPage = (String)request.getAttribute("page");
    String userId = (String)session.getAttribute("id");
%>

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
    <link rel="stylesheet" href="css/reply.css">
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
            		<div class="reply">
    
		<form name="replyFrm" method="post" action="boardReplyPro.abc">
			<ul>
				<input type="hidden" name="page" value="<%=nowPage %>" > 
				<input type="hidden" name="BOARD_NUM" value="<%=article.getNum() %>">
        		<input type="hidden" name="BOARD_RE_REF" value="<%=article.getRe_ref() %>"> 
        		<input type="hidden" name="BOARD_RE_LEV" value="<%=article.getRe_lev() %>"> 
        		<input type="hidden" name="BOARD_RE_SEQ" value="<%=article.getRe_seq() %>">
				<li><input placeholder="<%=userId %>" readonly></li>
				<input type="hidden" name="name" value="<%=userId %>">
				<li><input name ="subject" placeholder="제목"></li>
				<li><textarea id="popContent" name="content" rows="10" cols="50" placeholder="내용" style="display:none"></textarea></li>
				<li><input type="password" name ="pass" placeholder="비밀번호"></li>
				<li>
					<input id="registBtn" type="submit" value="등록" class="reply-btn">
					<input type="reset" value="다시쓰기" class="reply-btn">	
					<a href="boardList.abc?page=<%=nowPage%>"><input type="button" value="리스트" class="reply-btn"></a> <!-- 경로수정하기 -->
					<a href="boardDetail.abc?board_num=<%=article.getNum()%>&page=<%=nowPage%>"><input type="button" value="이전으로" class="reply-btn"></a>	
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
    <!-- index.js는 메인 용이므로 알맞은 스크립트를 사용해야 합니다-->
    <script type="module" src="<%=application.getContextPath()%>/js/MorePage.js"></script>
</body>
</html>
