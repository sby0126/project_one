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
        <h2>읽기</h2>
		<ul class="readBox2">
			<li><span>번호 </span><span><%=article.getNum() %></span> </li>
			<li><span>글쓴이</span><span><%=article.getName() %></span> </li>
			<li><span>제목 </span><span><%=article.getSubject() %></span> </li>
			<li><span>등록날짜</span><span><%=article.getDate() %></span> </li>
			<li><%=article.getContent() %></li>
			
		</ul>
		<div class="readBox3">
			<a href="boardList.abc?page=<%=nowPage%>"><button>리스트</button></a>
			
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
		
		<div class="replyBox">
			<% if(re_articleList.size() > 0 ){
				for(int i=0; i<re_articleList.size();i++) { %>
					<!-- 댓글의 댓글들경우 -->
					<%if(re_articleList.get(i).getRe_re_lev()!=0){ 
						
						for(int a=0; a<=re_articleList.get(i).getRe_re_lev()*2; a++){ %>
							&nbsp; 
						<%} %> 
							<!-- 삭제된 댓글일 경우 -->
							<% if("Y".equals(re_articleList.get(i).getDel_yn())){%>
							⇒ <div class="read-deleted-div">삭제된 게시물입니다.</div><br>
							<!-- 삭제되지 않은 경우-->
							<%}else{ %>
								⇒ <div class="read-exit-div"><span><%=re_articleList.get(i).getRe_name() %></span><span>(<%=re_articleList.get(i).getRe_date() %>)</span><span><%=re_articleList.get(i).getRe_content() %></span> 
								<!-- 세션에 아이디값이 있을 경우 -->
								<%if(userId !=null){ %>
									  <!-- 세션에 아이디값이 본인 댓글일 경우 -->
									  
									  <%if(userId.equals(re_articleList.get(i).getRe_name())){ %>
 									    <button id="xx" onclick="reply('<%=re_articleList.get(i).getRe_name() %>','<%=re_articleList.get(i).getRe_num() %>','<%=re_articleList.get(i).getRe_num2() %>','<%=re_articleList.get(i).getRe_re_ref() %>','<%=re_articleList.get(i).getRe_re_lev() %>','<%=re_articleList.get(i).getRe_re_seq() %>','<%=re_articleList.get(i).getRe_date() %>','<%=userId %>','<%=i %>','<%=nowPage %>','<%=article.getNum()%>')" class="rerere<%=i %>">댓글달기</button>
										<button onclick="updateReple('<%=re_articleList.get(i).getRe_name() %>','<%=re_articleList.get(i).getRe_num() %>','<%=re_articleList.get(i).getRe_content() %>','<%=re_articleList.get(i).getRe_date() %>','<%=i %>','<%=nowPage %>','<%=article.getNum()%>')" class="upupup<%=i %>">수정</button>
										<button onclick="location.href='boardReDeletePro.abc?re_num=<%=re_articleList.get(i).getRe_num() %>&num=<%=article.getNum() %>&nowPage=<%=nowPage %>'">삭제</button>
									  <!-- 세션에 아이디값이 본인 댓글이 아닐 경우 -->
									  <%}else{ %>
									  	<button id="xx" onclick="reply('<%=re_articleList.get(i).getRe_name() %>','<%=re_articleList.get(i).getRe_num() %>','<%=re_articleList.get(i).getRe_num2() %>','<%=re_articleList.get(i).getRe_re_ref() %>','<%=re_articleList.get(i).getRe_re_lev() %>','<%=re_articleList.get(i).getRe_re_seq() %>','<%=re_articleList.get(i).getRe_date() %>','<%=userId %>','<%=i %>','<%=nowPage %>','<%=article.getNum()%>')" class="rerere<%=i %>">댓글달기</button>
									    <button onclick='alert("권한이 없습니다.")'>수정</button>
									   	<button onclick='alert("권한이 없습니다.")'>삭제</button>
									  <%}%>
								<!-- 세션에 아이디값이 없을 경우 -->	 
								<%}else{ %>
									<button onclick='alert("로그인이 필요합니다.")'>댓글달기</button>
									<button onclick='alert("로그인이 필요합니다.")'>수정</button>
									<button onclick='alert("로그인이 필요합니다.")'>삭제</button> 
								<%} %>
								</div><br>
							<%} %>
					<!-- 댓글일경우 -->
					<%}else{%> 
							<!-- 삭제된 댓글일 경우 -->
							<% if("Y".equals(re_articleList.get(i).getDel_yn())){%>
								<div class="read-deleted-div">삭제된 게시물입니다.</div><br>
							<!-- 삭제되지 않은 경우 -->
							<%} else { %>
								<div class="read-exit-div"><span><%=re_articleList.get(i).getRe_name() %></span><span>(<%=re_articleList.get(i).getRe_date() %>)</span><span><%=re_articleList.get(i).getRe_content() %></span> 
								<!-- 세션에 아이디값이 있을 경우 -->
								<% if(userId !=null){%>
									  <!-- 세션에 아이디값이 본인 댓글일 경우 -->
									  <%if(userId.equals(re_articleList.get(i).getRe_name())){ %>
										<button id="xx" onclick="reply('<%=re_articleList.get(i).getRe_name() %>','<%=re_articleList.get(i).getRe_num() %>','<%=re_articleList.get(i).getRe_num2() %>','<%=re_articleList.get(i).getRe_re_ref() %>','<%=re_articleList.get(i).getRe_re_lev() %>','<%=re_articleList.get(i).getRe_re_seq() %>','<%=re_articleList.get(i).getRe_date() %>','<%=userId %>','<%=i %>','<%=nowPage %>','<%=article.getNum()%>')" class="rerere<%=i %>">댓글달기</button>
										<button onclick="updateReple('<%=re_articleList.get(i).getRe_name() %>','<%=re_articleList.get(i).getRe_num() %>','<%=re_articleList.get(i).getRe_content() %>','<%=re_articleList.get(i).getRe_date() %>','<%=i %>','<%=nowPage %>','<%=article.getNum()%>')" class="upupup<%=i %>">수정</button>
										<button onclick="location.href='boardReDeletePro.abc?re_num=<%=re_articleList.get(i).getRe_num() %>&num=<%=article.getNum() %>&nowPage=<%=nowPage %>'">삭제</button>
									  <!-- 세션에 아이디값이 본인 댓글이 아닐 경우 -->
									  <%}else{ %>
									  	<button id="xx" onclick="reply('<%=re_articleList.get(i).getRe_name() %>','<%=re_articleList.get(i).getRe_num() %>','<%=re_articleList.get(i).getRe_num2() %>','<%=re_articleList.get(i).getRe_re_ref() %>','<%=re_articleList.get(i).getRe_re_lev() %>','<%=re_articleList.get(i).getRe_re_seq() %>','<%=re_articleList.get(i).getRe_date() %>','<%=userId %>','<%=i %>','<%=nowPage %>','<%=article.getNum()%>')" class="rerere<%=i %>">댓글달기</button>
									   	<button onclick='alert("권한이 없습니다.")'>수정</button>
									   	<button onclick='alert("권한이 없습니다.")'>삭제</button>
									  <%}%> 
								<!-- 세션에 아이디값이 없을 경우 -->
								<%} else{%>
									<button onclick='alert("로그인이 필요합니다")'>댓글달기</button>
									<button onclick='alert("로그인이 필요합니다.")'>수정</button>
									<button onclick='alert("로그인이 필요합니다.")'>삭제</button> 
								<%} %>
								</div><br>	
							<%} %>
					<%} %> 
						
					<%}
				}%>
		</div>
		
		
		
	
		<form action="repleform.abc" method="post" >
		<% if(userId != null){ %>
		
			<textarea name="content" rows="5" cols="50" placeholder="댓글달기" ></textarea>
			<input type="hidden" name="name" value="<%=userId%>">
			<input type="hidden" name="num_2" value="<%=article.getNum() %>" >
		
			<input type="hidden" name="num" value="<%=article.getNum() %>">
			<input type="hidden" name="page" value="<%=nowPage %>">
			<input type="submit" value="등록" class="read-btn" >
			<% } else {%>
			
			<textarea name="content" rows="5" cols="50" placeholder="로그인을 해주세요"></textarea>
			
			<%} %>
		</form>
	
	</div>
		
	
			
	
	<script>
	var clickCtn=0;
		/* 댓글달기 함수 */
		function reply(name,num,num2,ref,lev,seq,date,userId,i,page,articleNum){
			if(clickCtn==0){
				
			
				$(".rerere" + i).parent().after("<br id='yy'><div id='zz'><form action='replePlay.abc' method='post'><textarea id='tt' rows='5' cols='50' name='content'></textarea>"
						+"<input type='hidden' name='name' value=" + userId + ">"		
						+"<input type='hidden' name='num' value= " + num + ">"
						+"<input type='hidden' name='num2' value=" + num2 +">"
						+"<input type='hidden' name='ref' value=" + ref + ">"
						+"<input type='hidden' name='lev' value=" + lev + ">"
						+"<input type='hidden' name='seq' value=" + seq + ">"
						+"<input type='hidden' name='date' value=" + date + ">"
						+"<input type='hidden' name='page' value=" + page + ">"
						+"<input type='hidden' name='articleNum' value=" + articleNum + ">"
						+"<input type='submit' value='등록'></form></div>");
			}
			clickCtn++;
		};
		
		// 댓글달기 클릭시 텍스트창 중복열기 제거 / 텍스트창 외에 다른거 클릭하면 텍스트창 닫기
		$('body').click(function(e){
    		var id = e.target.getAttribute('id');
    		
    		if(id != "xx"){
    			
    			if(id == "tt") {
    				return;
    			}
    			$("#yy, #zz").css("display","none");	
    			clickCtn=0;
    		
    		}else{
    		
    			return;
    		}
    
    	});
		
		
		/* 댓글수정 함수 */
		function updateReple(name,num,content,date,i,page,articleNum){
			
			$(".upupup" + i).parent().after("<br><div><form action='replePlayUpdate.abc' method='post'><textarea rows='5' cols='50' name='content'>"+content+"</textarea>"
					+"<input type='hidden' name='name' value=" + name + ">"		
					+"<input type='hidden' name='num' value= " + num + ">"
					+"<input type='hidden' name='content' value=" + content +">"
					+"<input type='hidden' name='date' value=" + date + ">"
					+"<input type='hidden' name='page' value=" + page + ">"
					+"<input type='hidden' name='articleNum' value=" + articleNum + ">"
					+"<input type='submit' value='수정'></form></div>");
			
		}
		
		
		
		
		
		
		
		
		
		/* 댓글삭제 함수 */
		
		function deleteReple(re_num,num,nowPage){
			
			"<form action='replePlay.abc' method='post'>"
			+"<input type='hidden' name='re_num' value=" + re_num + ">"		
			+"<input type='hidden' name='num' value= " + num + ">"
			+"<input type='hidden' name='nowPage' value=" + nowPage +">"
			+"</form>"
		}
				
		
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