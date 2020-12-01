<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" session="true"  isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<%@ page import="java.util.List" %>
<%@ page import="core.*" %>
<%@ page import="core.board.qna.*" %>
<%@ page import="org.json.simple.JSONArray, org.json.simple.JSONObject" %>
<!DOCTYPE html>
<%
	String id = (String)session.getAttribute("id");

	if(id == null || id.equals("")) {
		response.sendRedirect("/");
	}
%>
<html>
<head>
<meta charset="UTF-8">
<title>관리자 페이지</title>
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
  <style>
  	.left {
  		position: fixed;
  	}
  	.right {
  		float: right;
  	}
  	.comment {
  		color: orange;
  	}
  	
  	tr:hover {
  		background-color: #F4F4F6;
  	}
  	
  	:root {
  		--mycolor: rgba(0, 0, 0, 0.6);
  	}
  	
  	table a {
  		color: #000000;
/*   		text-shadow: 1px 1px 1px var(--mycolor), -1px -1px 1px var(--mycolor), 1px 0px 1px var(--mycolor), 0px 1px 1px var(--mycolor), 0px -1px 1px var(--mycolor); */
  	}
  	
  </style>
</head>
<body>
    <div class="container col-md-12">
        <section class="col-md-12">
            <div class="left wall col-md-3">
                <ul class="nav nav-pills nav-stacked">
                    <li class="active"><a href="#">메인</a></li>
                    <li class="dropdown">
                      <a class="dropdown-toggle" data-toggle="dropdown" href="#">멤버 관리
                      <span class="caret"></span></a>
                      <ul class="dropdown-menu">
                        <li><a href="#manage-whole-member">전체 멤버 관리</a></li>
                        <li><a href="#manage-forced-secession">강제 탈퇴 관리</a></li>
                        <li><a href="#ip-ban-list">IP 차단 설정</a></li>
                      </ul>
                    </li>
                    <li><a href="#board-manage">게시판 관리</a></li>
                    <li><a href="#all-post">전체 게시글 관리</a></li>
                    <li><a href="#log">접속 로그</a></li>
                  </ul>
            </div>
            <div class="right content-wrapper wall col-md-9">
                <div id="main" class="content jumbotron media">
                    <p>관리자 페이지에 오신 것을 환영합니다.</p>
                    <p><span class="col-md-2"><%=id %></span>님 환영합니다. </p>
                </div>
                <div id="manage-whole-member" class="content jumbotron">
                	<a name="manage-whole-member"></a>
                    <p>전체 멤버 목록입니다.</p>
                    <table class="table">
                    	<thead>
                    		<th>회원 번호</th>
                    		<th>ID</th>
                    		<th>이름</th>
                    		<th>주소</th>
                    		<th>이메일</th>
                    		<th>전화번호</th>
                    		<th>우편번호</th>
                    		<th>회원 정보</th>
                    	</thead>
                   	<%
                   		CustomerDAO customerDAO = new CustomerDAO();
                   		List<CustomerVO> customerList = customerDAO.listMembers();
                   	%>
                   	<c:set var="customerList" value="<%= customerList %>" />
                   	<c:forEach var="vo" items="${customerList}">
                   		<tr>
                   			<td><span>${vo.getNo()}</span></td>
                   			<td><span>${vo.getId()}</span></td>
                   			<td><span>${vo.getName()}</span></td>
                   			<td><span>${vo.getAddress()}</span></td>
                   			<td><span>${vo.getEmail()}</span></td>
                   			<td><span>${vo.getTel()}</span></td>
                   			<td><span>${vo.getZipCode()}</span></td>
                   			<td><button data-number="${vo.getNo()}" class="ban btn btn-primary whole-member">정보 수정하기</button></td>
                   		</tr>
                   	</c:forEach>

                   	<tfoot>
                   		<div class="form-group">
                   			<label for="search">검색 하기</label>
                   			<input type="search" name="search" class="form-control">
                   			<input type="submit" value="검색하기" class="btn btn-default">
                   		</div>
                   	</tfoot>
                    </table>
                </div>
                <div id="manage-forced-secession" class="content jumbotron">
             	   <a name="manage-forced-secession"></a>
                    <p>강제 탈퇴 관리</p>
                	<input class="form-control col-md-3" type="text" maxlength="20">
                 	<button class="btn btn-default">차단 IP 설정</button>
                 	<p></p>
                    <table class="table">
                    	<thead>
                    		<th>회원 번호</th>
                    		<th>ID</th>
                    		<th>이름</th>
                    		<th>주소</th>
                    		<th>이메일</th>
                    		<th>전화번호</th>
                    		<th>우편번호</th>
                    		<th>차단 여부</th>
                    	</thead>     	
                   	<c:forEach var="m" items="${customerList}">
                   		<tr>
                   			<td><span>${m.getNo()}</span></td>
                   			<td><span>${m.getId()}</span></td>
                   			<td><span>${m.getName()}</span></td>
                   			<td><span>${m.getAddress()}</span></td>
                   			<td><span>${m.getEmail()}</span></td>
                   			<td><span>${m.getTel()}</span></td>
                   			<td><span>${m.getZipCode()}</span></td>
                   			<td><button data-number="${m.getNo()}" class="ban btn btn-danger forced-secession">강제 탈퇴</button></td>
                   		</tr>
                   	  </c:forEach>
                   	</table>         	
                </div>
                <div id="manage-forced-secession" class="content jumbotron">
                	<a name="ip-ban-list"></a>
                    <p>IP 차단 설정</p>
                    <p>현재 관리자 IP : <%= request.getRemoteAddr() %> </p>
                </div>
                <div id="board-manage" class="content jumbotron">
                	<a name="board-manage"></a>
                    <p>게시판 관리</p>
                    <table class="table">
                    <%
	            		BoardDAO boardMgr = new BoardDAO();
	            		JSONArray json = boardMgr.getListAll();                    
                    %>
                    	<caption class="well">총 글 갯수 : <%= json.size() %></caption>
                    	<thead>
                    		<th>글 번호</th>
                    		<th>분류</th>
                    		<th>글 제목</th>
                    		<th>작성자</th>
                    		<th>작성일</th>
                    		<th>조회수</th>
                    		<th>추천수</th>
                    		<th>수정</th>
                    		<th>삭제</th>
                    	</thead>
                    	<%                    		
                    		for(int i = 0 ; i < json.size(); i++) {
                    			JSONObject myBoard = (JSONObject)json.get(i);
                    	%>
                    		<tr>
                    			<td><%= myBoard.get("postNumber") %></td>
                    			<td><%= myBoard.get("postType") %></td>
                    			<td><a href="/pages/board-post.jsp?postNumber=<%=myBoard.get("postNumber")%>" target="_blank"><%= myBoard.get("postTitle") %></a></td>
                    			<td><%= myBoard.get("name") %></td>
                    			<td><%= myBoard.get("create_at") %></td>
                    			<td><%= myBoard.get("view") %></td>
                    			<td><%= myBoard.get("recommandCount") %></td>
                    			<td><button data-number="<%= myBoard.get("postNumber") %>" class="btn btn-primary post-modify">글 수정</button></td>
                    			<td><button data-number="<%= myBoard.get("postNumber") %>" class="btn btn-danger post-delete">글 삭제</button></td>
                    		</tr>
                    	<%
                    		}
                    	%>
                    	<tfoot>
                    		<div class="panel panel-default">
                    			<form class="form-inline">                    		
	                    			<div class=" form-group">
	                    				<label for="tablename">테이블 명 :</label>
	                    			 	<input type="text" class="form-control" name="tablename">                    			
	                    			</div>
                    			</form>                    			
                    		<button class="btn btn-primary">새로운 테이블 만들기</button>
                    		</div>
                    	</tfoot>
                    </table>
                </div>
				<div id="all-post" class="content jumbotron">
					<a name="all-post"></a>
                    <p>전체 게시물 관리</p>
                </div>   
				<div id="log" class="content jumbotron">
					<a name="log"></a>
                    <p>접속 로그</p>
                </div>                               
            </div>
        </section>
    </div>
    <script>
    
    	function getPostNumber() {
    		return parseInt( $(this).data("number") ); 
    	}
    
    	$( ".post-modify" ).on("click", function() {		
    		alert("글 수정 번호는 " + getPostNumber.call(this));
    	});
    	
    	$( ".post-delete" ).on("click", function() {
    		alert("글 삭제 번호는 " + getPostNumber.call(this));
    	});
    	
    	$(".whole-member").on("click", function() {
    		alert("정보를 수정할 회원의 번호는 " + getPostNumber.call(this));
    	});
    	
    	$( ".forced-secession" ).on("click", function() {
    		alert("강제로 탈퇴시킬 회원 번호는 " + getPostNumber.call(this));
    	});
    	
    </script>
</body>
</html>