<%@page import="java.io.File"%>
<%@page import="java.nio.file.*"%>
<%@page import="java.nio.file.Paths"%>
<%@page import="java.nio.file.Files"%>
<%@page import="javax.sound.midi.Patch"%>
<%@page import="java.io.IOException" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" session="true" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.List"%>
<%@ page import="core.*"%>
<%@ page import="core.board.qna.*"%>
<%@ page import="org.json.simple.JSONArray, org.json.simple.JSONObject"%>
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
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
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
  	
  	.window {
  		position: fixed;
  		left: 50%;
  		right: 0;
  		top: 50%;
  		bottom: 0;
  		transform: translate(-50%, -50%);
  		width: 80%;
  		height: 80%;
  		display: none;  	
  	}
  	
  	.member-information-form {
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
                    <li><a href="#uploads">파일 관리</a></li>
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
                    <table class="table" style="height: 200px; overflow:scroll;">
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
				<div id="uploads" class="content jumbotron">
					<a name="uploads"></a>
                    <p>업로드된 파일 관리</p>
                    <table class="table">
                    <thead>
                    	<th>파일명</th>
                    	<th>파일 크기</th>
                    	<th>게시물 번호</th>
                    	<th>다운로드</th>
                    	<th>파일 삭제</th>
                    </thead>
                    <tbody>
    
					<%
						Path uploadFolder = Path.of(request.getRealPath("uploads"));
					
                	try (DirectoryStream<Path> dirs = Files.newDirectoryStream(uploadFolder)) {
                		for(Path path : dirs) {
					%>
						<tr>
						<c:set var="filename" value="<%=path.getFileName() %>" />
						<td><%=path.getFileName()%></td>
						<td><%= path.toFile().length() / 1024 %> KB</td>
						
						<%
							int postNumber = boardMgr.getFileNameToPostNumber(path.getFileName().toString());
						%>
						<c:set var="postNumber" value="<%=postNumber%>" />
						<c:choose>
							<c:when test="${postNumber > 0}">
								<c:set var="isValid" value="가능" />
								<c:set var="btnName" value="btn-default" />
								<td><%= postNumber %></td>
							</c:when>
							<c:otherwise>
								<c:set var="isValid" value="불가능" />
								<c:set var="btnName" value="btn-danger" />
								<td>글이 존재하지 않습니다</td>
							</c:otherwise>
						</c:choose>
						<td>
							<p>${ isValid }</p>
<!-- 							<form action="/myAdmin/openFileBrowser.do"> -->
<%-- 								<input type="hidden" name="filename" value="<%= request.getRealPath("uploads/" + path.getFileName()) %>"> --%>
<%-- 								<input type="submit" class="btn ${btnName}" onclick="openImageView('/uploads/${filename}')" value="파일 보기"> --%>
<!-- 							</form> -->
							<button class="btn ${btnName}" onclick="openImageView('/uploads/${filename}')">파일 보기</button>
						</td>
						<td>
							<form action="/myAdmin/fileDelete.do">
								<input type="hidden" name="filename" value="<%= request.getRealPath("uploads/" + path.getFileName()) %>">
								<input type="submit" class="btn ${btnName}" data-filename="${filename}" value="파일 삭제">
							</form>
						</td> 
						</tr>
					<%
                		}
                	} catch(IOException e) {
                		e.printStackTrace();
                	}
					%>
					</tbody>
					</table>
                </div>                               
            </div>
        </section>
    </div>
    <!-- 회원 정보 수정 창  -->
    <div class="modal window member-information-form col-md-12 panel panel-default">
    	<%
    		String name = "";
    	%>
    	<div class="jumbotron"><h2><em>회원 정보 수정</h2></div>
    	<div class="panel panel-body">
    		<form class="form-group">
    			<div class="form-group col-md-8">
    				<label for="name">이름 : </label>
    				<input type="text" class="form-control" placeholder="회원명을 입력해주세요">
    			</div>
    			<div class="form-group col-md-8">
    				<label for="name">전화번호 : </label>
    				<input type="text" class="form-control" placeholder="전화번호를 입력해주세요">
    			</div>
 			    <div class="form-group col-md-8">
    				<label for="name">주소 : </label>
    				<input type="text" class="form-control" placeholder="주소를 입력해주세요">
    			</div>    			
    		</form>
    		<div class="col-md-12">
    			<center>
    				<button class="btn btn-primary">수정 완료</button>
    				<button class="btn btn-danger" onclick="closeMemberInformation()">취소</button>
    			</center>
    		</div>
    	</div>
    </div>
 
    <div id="image-view" class="modal window col-md-12 panel panel-default">
    	<div class="jumbotron"><h2><em>파일 미리 보기</h2>
    		<button class="btn btn-primary" onclick="hideImageView()">닫기</button>
    	</div>
    	<div class="panel panel-body" style="overflow:scroll">
    		<img src="">
    	</div>
    </div>
    <script>
    
    	function closeMemberInformation() {
    		$(".member-information-form").hide();
    	}
    	
    	function showMemberInformation(postNumber) {
    		$(".member-information-form").show();
    	}
    	
    	function openImageView(src) {    		
    		$("#image-view").show();
    		$("#image-view img").attr("src", src);
    	}
    	
    	function hideImageView() {
    		$("#image-view").fadeOut();
    	}
    
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
    		showMemberInformation(getPostNumber.call(this));
    	});
    	
    	$( ".forced-secession" ).on("click", function() {
    		alert("강제로 탈퇴시킬 회원 번호는 " + getPostNumber.call(this));
    	});
    	
    	$(window).on("click", function(ev) {
    		if(ev.target.classList.contains("modal")) {
    			$(".modal").hide();
    		}
    	});
    	
    </script>
</body>
</html>