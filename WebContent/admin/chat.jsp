<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String sessionId = session.getId();
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Chat</title>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
<style>
	.user-id {
		color: #D8424F;
	}
</style>
</head>
<body>
	<div class="container col-md-12">
		<div class="col-md-8">
			<div class="panel panel-default">
				<div class="panel-body">채팅</div>
			</div>	  
			<div class="col-md-8" id="chat-list">
			</div>	
		</div>
		<div class="col-md-4">
			<div class="panel panel-default">
				<div class="panel-body">유저 목록 (<span id="current-user">0</span>명)</div>
				<div id="user-panel">
				</div>
			</div>	
		</div>			
		<div class="col-md-12">
			<div class="input-group">
				<span class="input-group-addon">대화</span>
			   	<input id="message" type="text" class="form-control" name="message">
			</div>	  				
		</div>
	</div>

	<script>
        const socket = new WebSocket("ws://192.168.0.26:9988/chat");
        const list = $("#chat-list");
        socket.onopen = function(e) {
        	list.append(`<p>서버에 접속되었습니다</p>`);
        };

        socket.onmessage = function(raw) {
//         	const data = JSON.parse(raw);
//         	const {message, users} = data;
        	
//         	$("#user-panel").empty();
//         	users.forEach(i => {
//         		$("#user-panel").append("<p>" + i + "</p>");
//         	})
        	
        	list.append("<p><span class='user-id'>" + "[유저] </span>" + raw.data + "</p>");
        }

        socket.onerror = function(message) {
        	list.append("<p>" + message.data + "</p>");
        }

        socket.onclose = function() {
        	list.append(`<p>서버와의 연결이 끊겼습니다.</p>`);
        }
        
        $("body").keypress(function(e) {
            if(e.keyCode == 13) {
                const message = $("#message").val();
                $("#message").val("");
                const msg = $("<p>" + message + "</p>");
                socket.send(message);
                list.append("<p><span class='user-id'>" + "[나] </span>" + message + "</p>");
            }
        });

	</script>
</body>
</html>