<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<!DOCTYPE html>
<%@ page import="vo.board.free.BoardBean"%>
<%@ page import="vo.board.free.Re_boardBean"%>
<%@ page import="java.io.PrintWriter" %>
<%@ page import="java.util.*"%>
<%

	BoardBean article = (BoardBean)request.getAttribute("article");
	ArrayList<Re_boardBean> re_articleList=(ArrayList<Re_boardBean>)request.getAttribute("re_articleList");
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
    <link rel="stylesheet" href="css/read.css">
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
            		<div class="readBox">
      
		<ul class="readBox2">
			
			<li><span><%=article.getName() %></span><span><%=article.getDate() %></span> </li>
			<li><%=article.getSubject() %></li>
			
			<li><%=article.getContent() %></li>
			
		</ul>
		<div class="readBox3">
			<a href="boardList.abc?page=<%=nowPage%>"><button>이전으로</button></a>
			
			<%if(userId != null ){%>
			<a href="boardReplyForm.abc?board_num=<%=article.getNum() %>&page=<%=nowPage%>"><button>답변</button></a>
				<%if(userId.equals(article.getName())) {%>
				<a href="boardUpdateForm.abc?board_num=<%=article.getNum() %>&page=<%=nowPage%>"><button>수정</button></a>
				<a href="boardDeleteForm.abc?board_num=<%=article.getNum() %>&page=<%=nowPage%>"><button>삭제</button></a>
				<% } else { %>
				<button onclick='alert("권한이 없습니다.")'>수정</button>
				<button onclick='alert("권한이 없습니다.")'>삭제</button>
				<%} %>
			<%} else { %>
			<button onclick='alert("로그인이 필요합니다.")'>답변</button>
			<button onclick='alert("로그인이 필요합니다.")'>수정</button>
			<button onclick='alert("로그인이 필요합니다.")'>삭제</button>
			<%} %>
				
		</div>
		<div class="after">
		</div>
		
		<div class="replyBox">
			<% if(re_articleList.size() > 0 ){
				for(int i=0; i<re_articleList.size();i++) { %>
					<!-- 댓글의 댓글들경우 -->
					<%if(re_articleList.get(i).getRe_re_lev()!=0){ %>
					
						<div class="flexBox">
						<%  
						for(int a=1; a<=re_articleList.get(i).getRe_re_lev(); a++){ %>
							<div id="nulll"></div> 
						<%} %> 
							<!-- 삭제된 댓글일 경우 -->
							<% if("Y".equals(re_articleList.get(i).getDel_yn())){%>
						
							<div id="re">re</div> <div class="read-deleted-div" id="read-deleted-div2">삭제된 게시물입니다.</div>
						
							<!-- 삭제되지 않은 경우-->
							<%}else{ %>
								<div id="re">re</div> <div class="read-exit-div" id="read-exit-div2"><span><%=re_articleList.get(i).getRe_name() %></span><span>(<%=re_articleList.get(i).getRe_date() %>)</span><span><%=re_articleList.get(i).getRe_content() %></span> 
								<!-- 세션에 아이디값이 있을 경우 -->
								<%if(userId !=null){ %>
									  <!-- 세션에 아이디값이 본인 댓글일 경우 -->
									  
									  <%if(userId.equals(re_articleList.get(i).getRe_name())){ %>
 									    <button id="replyButton" onclick="reply('<%=re_articleList.get(i).getRe_name() %>','<%=re_articleList.get(i).getRe_num() %>','<%=re_articleList.get(i).getRe_num2() %>','<%=re_articleList.get(i).getRe_re_ref() %>','<%=re_articleList.get(i).getRe_re_lev() %>','<%=re_articleList.get(i).getRe_re_seq() %>','<%=re_articleList.get(i).getRe_date() %>','<%=userId %>','<%=i %>','<%=nowPage %>','<%=article.getNum()%>')" class="rerere<%=i %>">댓글달기</button>
										<button id="updateButton" onclick="updateReple('<%=re_articleList.get(i).getRe_name() %>','<%=re_articleList.get(i).getRe_num() %>','<%=re_articleList.get(i).getRe_content() %>','<%=re_articleList.get(i).getRe_date() %>','<%=i %>','<%=nowPage %>','<%=article.getNum()%>')" class="upupup<%=i %>">수정</button>
										<button onclick="location.href='boardReDeletePro.abc?re_num=<%=re_articleList.get(i).getRe_num() %>&num=<%=article.getNum() %>&nowPage=<%=nowPage %>'">삭제</button>
									  <!-- 세션에 아이디값이 본인 댓글이 아닐 경우 -->
									  <%}else{ %>
									  	<button id="replyButton" onclick="reply('<%=re_articleList.get(i).getRe_name() %>','<%=re_articleList.get(i).getRe_num() %>','<%=re_articleList.get(i).getRe_num2() %>','<%=re_articleList.get(i).getRe_re_ref() %>','<%=re_articleList.get(i).getRe_re_lev() %>','<%=re_articleList.get(i).getRe_re_seq() %>','<%=re_articleList.get(i).getRe_date() %>','<%=userId %>','<%=i %>','<%=nowPage %>','<%=article.getNum()%>')" class="rerere<%=i %>">댓글달기</button>
									    <button onclick='alert("권한이 없습니다.")'>수정</button>
									   	<button onclick='alert("권한이 없습니다.")'>삭제</button>
									  <%}%>
								<!-- 세션에 아이디값이 없을 경우 -->	 
								<%}else{ %>
									<button onclick='alert("로그인이 필요합니다.")'>댓글달기</button>
									<button onclick='alert("로그인이 필요합니다.")'>수정</button>
									<button onclick='alert("로그인이 필요합니다.")'>삭제</button> 
								<%} %>
								</div>
							<%} %>
							</div>
					<!-- 댓글일경우 -->
					<%}else{%> 
							<div class="flexBox">
							<!-- 삭제된 댓글일 경우 -->
							<% if("Y".equals(re_articleList.get(i).getDel_yn())){%>
								<div class="read-deleted-div" id="read-deleted-div1">삭제된 게시물입니다.</div>
							<!-- 삭제되지 않은 경우 -->
							<%} else { %>
								<div class="read-exit-div" id="read-exit-div1"><span><%=re_articleList.get(i).getRe_name() %></span><span>(<%=re_articleList.get(i).getRe_date() %>)</span><span><%=re_articleList.get(i).getRe_content() %></span> 
								<!-- 세션에 아이디값이 있을 경우 -->
								<% if(userId !=null){%>
									  <!-- 세션에 아이디값이 본인 댓글일 경우 -->
									  <%if(userId.equals(re_articleList.get(i).getRe_name())){ %>
										<button id="replyButton" onclick="reply('<%=re_articleList.get(i).getRe_name() %>','<%=re_articleList.get(i).getRe_num() %>','<%=re_articleList.get(i).getRe_num2() %>','<%=re_articleList.get(i).getRe_re_ref() %>','<%=re_articleList.get(i).getRe_re_lev() %>','<%=re_articleList.get(i).getRe_re_seq() %>','<%=re_articleList.get(i).getRe_date() %>','<%=userId %>','<%=i %>','<%=nowPage %>','<%=article.getNum()%>')" class="rerere<%=i %>">댓글달기</button>
										<button id="updateButton" onclick="updateReple('<%=re_articleList.get(i).getRe_name() %>','<%=re_articleList.get(i).getRe_num() %>','<%=re_articleList.get(i).getRe_content() %>','<%=re_articleList.get(i).getRe_date() %>','<%=i %>','<%=nowPage %>','<%=article.getNum()%>')" class="upupup<%=i %>">수정</button>
										<button onclick="location.href='boardReDeletePro.abc?re_num=<%=re_articleList.get(i).getRe_num() %>&num=<%=article.getNum() %>&nowPage=<%=nowPage %>'">삭제</button>
									  <!-- 세션에 아이디값이 본인 댓글이 아닐 경우 -->
									  <%}else{ %>
									  	<button id="replyButton" onclick="reply('<%=re_articleList.get(i).getRe_name() %>','<%=re_articleList.get(i).getRe_num() %>','<%=re_articleList.get(i).getRe_num2() %>','<%=re_articleList.get(i).getRe_re_ref() %>','<%=re_articleList.get(i).getRe_re_lev() %>','<%=re_articleList.get(i).getRe_re_seq() %>','<%=re_articleList.get(i).getRe_date() %>','<%=userId %>','<%=i %>','<%=nowPage %>','<%=article.getNum()%>')" class="rerere<%=i %>">댓글달기</button>
									   	<button onclick='alert("권한이 없습니다.")'>수정</button>
									   	<button onclick='alert("권한이 없습니다.")'>삭제</button>
									  <%}%> 
								<!-- 세션에 아이디값이 없을 경우 -->
								<%} else{%>
									<button onclick='alert("로그인이 필요합니다")'>댓글달기</button>
									<button onclick='alert("로그인이 필요합니다.")'>수정</button>
									<button onclick='alert("로그인이 필요합니다.")'>삭제</button> 
								<%} %>
								</div>
							<%} %>
							</div>
					<%} %> 
							
					<%}
			
				}%>
		</div>
		
		
		
		<form id="textBox" action="repleform.abc" method="post" >
		<% if(userId != null){ %>
		
			<textarea name="content" rows="5" cols="50" placeholder="댓글달기" class="bbccdd"></textarea>
			<input type="hidden" name="name" value="<%=userId%>">
			<input type="hidden" name="num_2" value="<%=article.getNum() %>" >
		
			<input type="hidden" name="num" value="<%=article.getNum() %>">
			<input type="hidden" name="page" value="<%=nowPage %>">
			<div class="aabbcc2">
			<input type="submit" value="등록" class="read-btn" id="aabbcc">
			</div>
			<% } else {%>
			
			<textarea name="content" rows="5" cols="50" placeholder="로그인을 해주세요" class="bbccdd"></textarea>
			
			<%} %>
		</form>
	
	</div>
		
	
			
	
	<script>
	
	 /* $.each($(".flexBox"),function(i,obj){
		debugger;
		if($(obj).child("div").width() == $(obj).prev().child("div").width()){
			if($(obj).child("div").width() <= $(obj).next().child("div").width()){
					$(obj).css("display","none");
			}else if($(obj).child("div").width() > $(obj).next().child("div").width()){
					return;
			} 
		}
		else if($(obj).child("div").width() > $(obj).prev().child("div").width()){
			$(obj).css("display","none");
		}
		else if($(obj).child("div").width() < $(obj).prev().child("div").width()){
			if($(obj).child("div").width() <= $(obj).next().child("div").width()){
				$(obj).css("display","none");
			}else if($(obj).child("div").width() > $(obj).next().child("div").width()){
				return;
			} 
		} 
	})  */
	
	var clickCtn=0;
	var clickCtn2=0;
		/* 댓글달기 함수 */
		function reply(name,num,num2,ref,lev,seq,date,userId,i,page,articleNum){
			if(clickCtn==0){		
				$(".rerere" + i).parents(".flexBox").after("<br id='replyBr'><div id='replyDiv'><form action='replePlay.abc' method='post'><textarea id='replyArea' rows='5' cols='50' name='content'></textarea>"
						+"<input type='hidden' name='name' value=" + userId + ">"		
						+"<input type='hidden' name='num' value= " + num + ">"
						+"<input type='hidden' name='num2' value=" + num2 +">"
						+"<input type='hidden' name='ref' value=" + ref + ">"
						+"<input type='hidden' name='lev' value=" + lev + ">"
						+"<input type='hidden' name='seq' value=" + seq + ">"
						+"<input type='hidden' name='date' value=" + date + ">"
						+"<input type='hidden' name='page' value=" + page + ">"
						+"<input type='hidden' name='articleNum' value=" + articleNum + ">"
						+"<input class='upupupBtn' type='submit' value='등록'></form></div>");
			}
			clickCtn++;
		};
		
		
		
		// 댓글달기 클릭시 텍스트창 중복열기 제거 / 텍스트창 외에 다른거 클릭하면 텍스트창 닫기
		$('body').click(function(e){
    		var id = e.target.getAttribute('id');
    		
    		if(id != "replyButton"){
    			
    			if(id == "replyArea") {
    				return;
    			}
    			$("#replyBr, #replyDiv").css("display","none");	
    			clickCtn=0;
    		
    		}else{
    		
    			return;
    		}
    
    	});
				
		/* 댓글수정 함수 */
		function updateReple(name,num,content,date,i,page,articleNum){
			if(clickCtn2==0){	
			$(".upupup" + i).parents(".flexBox").after("<br id='updateBr'><div id='updateDiv'><form action='replePlayUpdate.abc' method='post'><textarea id='updateArea' rows='5' cols='50' name='content'>"+content+"</textarea>"
					+"<input type='hidden' name='name' value=" + name + ">"		
					+"<input type='hidden' name='num' value= " + num + ">"
					+"<input type='hidden' name='content' value=" + content +">"
					+"<input type='hidden' name='date' value=" + date + ">"
					+"<input type='hidden' name='page' value=" + page + ">"
					+"<input type='hidden' name='articleNum' value=" + articleNum + ">"
					+"<input class='upupupBtn' type='submit' value='수정'></form></div>");	
			}
			clickCtn2++;
		}
		
		$('body').click(function(e){
    		var id = e.target.getAttribute('id');
    		
    		if(id != "updateButton"){
    			
    			if(id == "updateArea") {
    				return;
    			}
    			$("#updateBr, #updateDiv").css("display","none");	
    			clickCtn2=0;
    		
    		}else{
    		
    			return;
    		}
    
    	});
		
			
		/* 댓글삭제 함수 */
		
		function deleteReple(re_num,num,nowPage){
			
			"<form action='replePlay.abc' method='post'>"
			+"<input type='hidden' name='re_num' value=" + re_num + ">"		
			+"<input type='hidden' name='num' value= " + num + ">"
			+"<input type='hidden' name='nowPage' value=" + nowPage +">"
			+"</form>"
		}
		
		/* var a = $(".readBox2").width();
		var b = $("#aabbcc").width()+7;
		$(".bbccdd").width(a-b);
	 */
		/* var preCnt = 0;
		
		$.each($(".replyBox > #read-exit-div2"),function(i,obj){	
		
			var a = $(".readBox2").width(); 
			var re = $("#re").width()+50;
			var nulll = $("#nulll").width();
			
			var ctn =  $(obj).prevAll("#nulll").length - preCnt;
			if(ctn == 1) {
				$(obj).width(a-re-nulll);
			 }
			else if(ctn > 1) {
				$(obj).width(a-re-nulll*ctn);
			 }	
			preCnt = $(obj).prevAll("#nulll").length;
		})  
 */
			
			
		/*  var preCnt2 = 0;		
		 
		 $(window).resize(function(){
			 var a = $(".readBox2").width();
			 var b = $("#aabbcc").width()+7;
			 $(".bbccdd").width(a-b);				
		 }) */
		 
		 
		 
	
		 
		 
		 
	
		
		
			

	/* 	function loof(){
			var a = $(".readBox2").width();
			var b = $("#aabbcc").width()+7;
			$("#bbccdd").width(a-b);
		};
		
		setInterval(loof, 1000); */

		
		
	</script>
          
           
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