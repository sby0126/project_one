<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql" %> 
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/chart.js@2.9.4/dist/Chart.min.js"></script>
</head>
<body>
	<sql:setDataSource var="ds" />
	<div class="container col-md-12">
		<section class="col-md-12">
			<div class="jumbotron">
		        <p>프로토콜 : <%= request.getScheme() %> </p>
	            <p>호스트 주소 : <%= request.getRemoteHost()%> </p>
	            <p>포트 : <%= request.getRemotePort()%> </p>
			</div>
			<div class="col-md-6">
				<form>
				<!-- 송신 메시지를 작성하는 텍스트 박스 -->
				<input id="textMessage" type="text">
				<!-- 메시지 송신을 하는 버튼 -->
				<input onclick="sendMessage()" value="Send" type="button">
				<!-- WebSocket 접속 종료하는 버튼 -->
				<input onclick="disconnect()" value="Disconnect" type="button">
				</form>	
			</div>
			<div class="col-md-6">
			
			</div>			
		</section>
	</div>
</body>
</html>