<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<% request.setCharacterEncoding("UTF-8"); %>
<%@ page import="vo.board.free.PageInfo"%>
<%@ page import="vo.board.free.BoardBean"%>
<%@ page import="java.util.*"%>
<%@ page import="java.io.PrintWriter" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>  
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
    <link rel="stylesheet" href="css/list.css"> 
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
<!--             	여기에 -->	
  	<div class="listBox">
	
		<span class="listTitle">자유게시판</span>
		
		<ul class="listTitle2" id="ListTitle1" style= "font-weight:bold; background-color:#EAEAEA">
			<li>번호</li>		
			<li>글쓴이</li>	
			<li style="text-align:center">제목</li>
			<li>날짜</li>	
			<li>조회수</li>
		</ul>
		
		<% 
			if(articleList.size() > 0){
				for(int i=0; i<articleList.size();i++){%>		
		<ul class="listTitle2" >
			<li><%=articleList.get(i).getNum()%></li>		
			<li><%=articleList.get(i).getName()%></li>	
			<li><%if(articleList.get(i).getRe_lev()!=0){ %> <%for(int a=0;a<=articleList.get(i).getRe_lev()*2; a++){ %>
					&nbsp; <%} %><span id="re"></span><%}else{ %>  <%} %> <a
					href="boardDetail.abc?board_num=<%=articleList.get(i).getNum()%>&page=<%=nowPage%>">
						<%=articleList.get(i).getSubject()%>
				</a></li>
			<li><%=articleList.get(i).getDate()%></li>
			<li><%=articleList.get(i).getReadCount()%></li>			
		</ul>
			<%}
		}%>
		
		<div class="listPM">
			<div class="flexBox1"></div>
			<div class="listPMPage">
				<%if(nowPage<=1){ %>
					[이전]
				<%}else{ %>
					<a href="boardList.abc?page=<%=nowPage-1 %>">[이전]</a>
				<%} %>
		
				<%for(int a=startPage;a<=endPage;a++){
					if(a==nowPage){%>
						<span style="background-color:#D5D5D5;">&nbsp;<%=a %>&nbsp;</span>
					<%}else{ %>
						<a href="boardList.abc?page=<%=a %>">&nbsp;<%=a %>&nbsp;</a>
					<%} %>
			
				<%} %>
		
				<%if(nowPage>=maxPage){ %>
					[다음]
				<%}else{ %>
					<a href="boardList.abc?page=<%=nowPage+1 %>">[다음]</a>
				<%} %>
				
			</div>
			<div class="listPMMove">
				<% if(userId== null) {%>
				<a href="javascript:abc()">글쓰기</a>
			 	<% } else {%>	
		 		<a href="boardWriteForm.abc?page=<%=nowPage%>">글쓰기</a>
		 		<% } %>
				
		 		<a href="boardList.abc">처음으로</a>
		 	</div>
		
		</div>
				 
		 <div class="listSearch">
		 	<form class="listSearchFrm" method="post" action="boardList.abc">
		 		<select name="keyField">
		 			<option value="NAME">글쓴이</option>
		 			<option value="subject">제목</option>
		 		</select>
		 		<input type="text" name="keyWord" ><input type="submit" value="검색" class="list-btn">
		 	</form>
		 </div>	
		
    </div>
            </div>
        </section>
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
    <!-- 라이트 박스-->
    <div id="light-box-container">
    </div>
    <jsp:include page="/pages/login.jsp"></jsp:include>
    <!-- index.js는 메인 용이므로 알맞은 스크립트를 사용해야 합니다-->
    <script type="module" src="<%=application.getContextPath()%>/js/MorePage.js"></script>
</body>
</html>
