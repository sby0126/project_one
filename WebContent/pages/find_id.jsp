<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>ID 찾기</title>
    <link rel="stylesheet" href="../css/style.css">
    <link rel="stylesheet" href="../css/find-password.css">
	<link href="https://fonts.googleapis.com/css2?family=Nanum+Gothic&display=swap" rel="stylesheet">
	<script src="https://kit.fontawesome.com/a99df0f94f.js" crossorigin="anonymous"></script>
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
	<script src="https://cdn.jsdelivr.net/npm/underscore@1.11.0/underscore-min.js"></script>
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css" integrity="sha384-rHyoN1iRsVXV4nD0JutlnGaslCJuC7uwjduW9SVrLvRYooPp2bWYgmgJQIXwl/Sp" crossorigin="anonymous">
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js" integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>
	<script src="https://t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
	<link rel="stylesheet" href="../css/style.css">
</head>
<body>
	<!-- 컨테이너의 시작 -->
	<div class="container">
		<!-- 헤더의 시작 -->
        <jsp:include page="/header.jsp">
        	<jsp:param value="true" name="isHide"/>
        </jsp:include>
        
		<!-- 본문의 시작 -->
		<section>
			<!-- 본문이 들어가는 래퍼 -->
			<div class="contents-wrapper">
				<div class="member-form-container col-md-10 col-md-offset-2">
					<div class="jumbotron col-md-8">
						<p class="lead">아이디 찾기</p>
					</div>		
					
					<form id="member-info-form" method="POST" class="form-horizontal col-sm-8" action="/members/findId.do">
						<div class="form-group">
							<label class="control-label col-sm-2" for="name">이름</label>
							<div class="col-sm-6">
								<input type="text" name="name" class="form-control" id="name"
									placeholder="이름을 입력하세요" autocomplete="name">
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="email">이메일:</label>
							<div class="col-sm-6">
								<input type="email" class="form-control" name="email" id="email" autocomplete="email" placeholder="이메일을 입력하세요." value="">
							</div>
						</div>
						<div class="form-group">
							<div class="col-sm-offset-3 col-sm-2">
								<input type="submit" class="btn btn-default" value="확인">
							</div>

						</div>												
					</form>
								
				</div>
			</div>
		</section>
    </div>
	<!-- 라이트 박스-->
	<div id="light-box-container"></div>
	<jsp:include page="/pages/login.jsp"></jsp:include> 
	<!-- index.js는 메인 용이므로 알맞은 스크립트를 사용해야 합니다-->
	<script type="module" src="/js/MorePage.js"></script>
	<script src="/js/modifyMemberForm.js"></script>    

</body>
</html>