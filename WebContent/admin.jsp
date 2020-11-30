<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" session="true"%>
<%@ page import="java.util.List" %>
<%@ page import="core.*" %>
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
                        <li><a href="#">전체 멤버 관리</a></li>
                        <li><a href="#">강제 탈퇴 관리</a></li>
                        <li><a href="#">IP 차단 설정</a></li>
                      </ul>
                    </li>
                    <li><a href="#">게시판 관리</a></li>
                    <li><a href="#">접속 로그</a></li>
                  </ul>
            </div>
            <div class="content-wrapper wall col-md-9">
                <div id="main" class="content jumbotron">
                    <p>관리자 페이지에 오신 것을 환영합니다.</p>
                    <p><span><%=id %></span>님 환영합니다. </p>
                </div>
                <div id="manage-whole-member" class="content jumbotron">
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
                    	</thead>
                   	<%
                   		CustomerDAO customerDAO = new CustomerDAO();
                   		List<CustomerVO> customerList = customerDAO.listMembers();
                   		for(CustomerVO vo : customerList) {
                   			String address = vo.getAddress();
                   	%>
                   		<tr>
                   			<td><span><%=vo.getNo()%></span></td>
                   			<td><span><%=vo.getId()%></span></td>
                   			<td><span><%=vo.getName()%></span></td>
                   			<td><span><%=address%></span></td>
                   			<td><span><%=vo.getEmail()%></span></td>
                   			<td><span><%=vo.getTel()%></span></td>
                   			<td><span><%=vo.getZipCode()%></span></td>
                   		</tr>
                   	<%
                   		}
                   	%>
                    </table>
                </div>
                <div id="manage-forced-secession" class="content jumbotron">
                    <p>강제 탈퇴 관리</p>
                    <div class="col-md-6">
                    	<div class="col-md-3 form-group" style="display:flex">
                    		<input type="text" maxlength="20">
                    		<button class="btn btn-default">차단 IP 설정</button>
                    	</div>
                    </div>
                </div>
                <div id="manage-forced-secession" class="content jumbotron">
                    <p>IP 차단 설정</p>
                    <p>현재 관리자 IP : <%= request.getRemoteAddr() %> </p>
                </div>

            </div>
        </section>
        
    </div>
</body>
</html>