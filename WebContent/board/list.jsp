<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>	
<%@ page import="freeBoard.vo.PageInfo"%>
<%@ page import="freeBoard.vo.BoardBean"%>
<%@ page import="java.util.*"%>
<%@ page import="java.io.PrintWriter" %>
<% request.setCharacterEncoding("UTF-8"); %>
<%
	ArrayList<BoardBean> articleList=(ArrayList<BoardBean>)request.getAttribute("articleList");
    PageInfo pageInfo = (PageInfo)request.getAttribute("pageInfo");
  
	int listCount=pageInfo.getListCount();
	int nowPage=pageInfo.getPage();
	int maxPage=pageInfo.getMaxPage();
	int startPage=pageInfo.getStartPage();
	int endPage=pageInfo.getEndPage();
	String userId = (String)session.getAttribute("id");
%> 
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
 
<link rel="stylesheet" href="css/list.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
</head>

<body>
  	
  			<div class="listBox">
	
		<span class="listTitle">자유게시판</span>
		
		<ul class="listTitle2" style= "font-weight:bold; background-color:orange;">
			<li>번호</li>		
			<li>글쓴이</li>	
			<li>제목</li>
			<li>날짜</li>	
			<li>조회수</li>
		</ul>
		
		<% 
			if(articleList.size() > 0){
				for(int i=0; i<articleList.size();i++){%>		
		<ul class="listTitle2" >
			<li><%=articleList.get(i).getNum()%></li>		
			<li><%=articleList.get(i).getName()%></li>	
			<li><%if(articleList.get(i).getRe_lev()!=0){ %> <%for(int a=0;a<=articleList.get(i).getRe_lev()*2;a++){ %>
					&nbsp; <%} %> 답글: <%}else{ %>  <%} %> <a
					href="boardDetail.abc?board_num=<%=articleList.get(i).getNum()%>&page=<%=nowPage%>">
						<%=articleList.get(i).getSubject()%>
				</a></li>
			<li><%=articleList.get(i).getDate()%></li>
			<li><%=articleList.get(i).getReadCount()%></li>			
		</ul>
			<%}
		}%>
		
		<div class="listPM">
			<div class="listPMPage">
				<%if(nowPage<=1){ %>
				[이전]&nbsp;
				<%}else{ %>
				<a href="boardList.abc?page=<%=nowPage-1 %>">[이전]</a>&nbsp;
				<%} %>
		
				<%for(int a=startPage;a<=endPage;a++){
						if(a==nowPage){%>
				[<%=a %>]
				<%}else{ %>
				<a href="boardList.abc?page=<%=a %>">[<%=a %>]
				</a>&nbsp;
				<%} %>
				<%} %>
		
				<%if(nowPage>=maxPage){ %>
				[다음]
				<%}else{ %>
				<a href="boardList.abc?page=<%=nowPage+1 %>">[다음]</a>
				<%} %>
				
			</div>
			<div class="listPMMove">
				<%if(userId== null) {%>
				<a href="javascript:abc()">글쓰기</a>
			 	<% }else {%>	
		 		<a href="boardWriteForm.abc">글쓰기</a>
		 		<% }%>
				
		 		<a href="#">처음으로</a>
		 	</div>
		 	
		
		
		</div>
			
		
		 
		 <div class="listSearch">
		 	<form class="listSearchFrm" method="post" action="boardList.abc">
		 		<select name="keyField">
		 			<option value="NAME">글쓴이</option>
		 			<option value="subject">제목</option>
		 		</select>
		 		<input type="text" name="keyWord"><input type="submit" value="검색">
		 	</form>
		 </div>	
		

    </div>
  			
  			
  			
  	
  			
  		
  		

      
	
	<script>
		function abc(){
			alert("로그인이 필요합니다.");
		}
		
		
		var c = $(".listPMPage").height();
		var d = $(".listPMMove").height();
		if(c - d >= 0){
			$(".listPM").height(c);
		}else{
			$(".listPM").height(d);
		}
	
	
		var a = $(".listSearch").offset().top;
		var b = $(".listSearch").height();
		$(".listBox").height(a+b);
	</script>
</body>
</html>