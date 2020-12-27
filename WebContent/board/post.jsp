<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<!DOCTYPE html>
<% String userId = (String)session.getAttribute("id"); %>
<% String nowPage = (String)request.getAttribute("page"); %>
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
    	
		<form name="postFrm" id = "postFrm" method="post" action="boardWritePro.abc"  enctype="multipart/form-data">
			<ul>
				<li><input readonly placeholder="<%=userId%>"></li>
				<li><input type="hidden" name="name" value="<%=userId%>"></li>
				<li><input name ="subject" placeholder="제목" id="subject"></li>
				<li><textarea style="display:none" name="content" id ="popContent" rows="10" cols="50" style="width: 500px; height: 412px;" placeholder="내용"></textarea></li>
			
				<li><div id="flexDiv1"><button type="button" class="post-btn" onclick="addFile()">파일+1</button> <button type="button" class="post-btn" onclick="minusFile()">파일-1</button></div>
					<div id="flexDiv2"><a href="boardList.abc?page=<%=nowPage%>"><input type="button" value="이전으로" class="post-btn"></a>	
					<input type="button" id="registBtn" value="등록" class="post-btn">
					<input type="reset" value="다시쓰기" class="post-btn">	
				</div>
				</li>
				<li><input type="file" name="file1" onchange ="chchch(this)" id="fileArrays1"></li>	
				
				
			</ul>
			<input type="password" name="pass" placeholder="비밀번호" id="pass">
			<%-- <div><a href="boardList.abc?page=<%=nowPage%>"><input type="button" value="이전으로" class="post-btn"></a>	
				<input type="button" id="registBtn" value="등록" class="post-btn">
				<input type="reset" value="다시쓰기" class="post-btn">	
				</div> --%>
		</form>
	</div>
          
           
            </div>
        </section>
    </div>
    <!-- 라이트 박스-->
    <div id="light-box-container">
    </div>
    <script>
    
    	var clickCount=1;
    	
    	function addFile(){
    		clickCount++;
    		$("#postFrm > ul").append("<li><input type='file' onchange='chchch(this)' name='file"+clickCount+"' id='fileArrays"+clickCount+"' >");
    			
    	}
    	
    	
   		function minusFile(){
    		if(clickCount > 1){
    			clickCount--;
        		$("#postFrm > ul >li:last").remove();
    		}
    	} 
    	
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
    	
		//글쓰기 내용입력 안할시
 		$("#registBtn").click(function(){	
 	    	   	
 			if($("#subject").val()==""){
 				alert("제목을 입력하세요");
 				return;
 			}		
 			if($("#pass").val()==""){
 				alert("비밀번호를 입력하세요");
 				return;
 			}			
 		
 			$("#postFrm").submit();
 		});
 		
 		//이미지 올리기 버튼 생성
 		function chchch(obj){	
			 if((obj.value.split(".")[1]) == 'jpg' || (obj.value.split(".")[1]) == 'png'){
				 alert("이미지를 삽입하고 싶으면 우측상단에 사진 기능을 이용하세요.");
				 /* $('#' + obj.id).parent().append("<button type='button' >이미지올리기</button>"); */
             } 
 		}
 		

    </script>
    <jsp:include page="/pages/login.jsp"></jsp:include>
    <!-- index.js는 메인 용이므로 알맞은 스크립트를 사용해야 합니다-->
    <script type="module" src="<%=application.getContextPath()%>/js/MorePage.js"></script>
</body>
</html>
