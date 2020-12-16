<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="freeBoard.vo.BoardBean"%>
<%@ page import="freeBoard.vo.Re_boardBean"%>
<%@ page import="java.io.PrintWriter" %>
<%@ page import="java.util.*"%>
<%

	BoardBean article = (BoardBean)request.getAttribute("article");
	ArrayList<Re_boardBean> re_articleList=(ArrayList<Re_boardBean>)request.getAttribute("re_articleList");
    String nowPage = (String)request.getAttribute("page");
    String userId = (String)session.getAttribute("id");
    
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="css/read.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
</head>
<body>

	<div class="readBox">
        <h2>읽기</h2>
		<ul class="readBox2">
			<li><span>번호</span><span><%=article.getNum() %></span> </li>
			<li><span>글쓴이</span><span><%=article.getName() %></span> </li>
			<li><span>제목</span><span><%=article.getSubject() %></span> </li>
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
					
					<%if(re_articleList.get(i).getRe_re_lev()!=0){ 
						for(int a=0; a<=re_articleList.get(i).getRe_re_lev()*2; a++){ %>
							&nbsp; 
						<%} %> 
								⇒⇒ <div><span><%=re_articleList.get(i).getRe_name() %></span><span><%=re_articleList.get(i).getRe_content() %></span><button onclick="reply('<%=re_articleList.get(i).getRe_name() %>','<%=re_articleList.get(i).getRe_num() %>','<%=re_articleList.get(i).getRe_num2() %>','<%=re_articleList.get(i).getRe_re_ref() %>','<%=re_articleList.get(i).getRe_re_lev() %>','<%=re_articleList.get(i).getRe_re_seq() %>','<%=re_articleList.get(i).getRe_date() %>','<%=userId %>','<%=i %>','<%=nowPage %>','<%=article.getNum()%>')" class="rerere<%=i %>">댓글달기</button><button>수정</button><button>삭제</button> </div><br>
						<%}else{ %> 	
								<div><span><%=re_articleList.get(i).getRe_name() %></span><span><%=re_articleList.get(i).getRe_content() %></span><button onclick="reply('<%=re_articleList.get(i).getRe_name() %>','<%=re_articleList.get(i).getRe_num() %>','<%=re_articleList.get(i).getRe_num2() %>','<%=re_articleList.get(i).getRe_re_ref() %>','<%=re_articleList.get(i).getRe_re_lev() %>','<%=re_articleList.get(i).getRe_re_seq() %>','<%=re_articleList.get(i).getRe_date() %>','<%=userId %>','<%=i %>','<%=nowPage %>','<%=article.getNum()%>')" class="rerere<%=i %>">댓글달기</button><button>수정</button><button>삭제</button> </div><br>	
						<%} %> 
						
			
										
					<% }
				}%>
		</div>
		
	
		<form action="repleform.abc" method="post" >
		<% if(userId != null){ %>
		
			<textarea name="content" rows="5" cols="50" placeholder="댓글달기"></textarea>
			<input type="hidden" name="name" value="<%=userId%>">
			<input type="hidden" name="num_2" value="<%=article.getNum() %>" >
		
			<input type="hidden" name="num" value="<%=article.getNum() %>">
			<input type="hidden" name="page" value="<%=nowPage %>">
			<input type="submit" value="등록">
			<% } else {%>
			
			<textarea name="content" rows="5" cols="50" placeholder="로그인을 해주세요"></textarea>
			
			<%} %>
		</form>
	
	</div>
		
	
			
	
	<script>
	
		function reply(name,num,num2,ref,lev,seq,date,userId,i,page,articleNum){
			
			$(".rerere" + i).parent().after("<br><div><form action='replePlay.abc' method='post'><textarea rows='5' cols='50' name='content'></textarea>"
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
			
		};
		
			
			
	
		
		
		
	</script>
	
</body>
</html>