<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>상품 업로더</title>
</head>
<body>
	<div class="member-form-container col-md-10 col-md-offset-2">
	
		<div class="jumbotron col-md-8">
			<p class="lead">상품 업로드</p>
		</div>
	
		<form class="form-horizontal col-sm-8"
			action="/members/modifyMember.do">
			<input type="hidden" name="id" value="${member.getId()}">
			<div class="form-group">
				<label class="control-label col-sm-2" for="email">이메일:</label>
				<div class="col-sm-6">
					<input type="email" class="form-control" name="email" id="email" readonly
						autocomplete="email" placeholder="Enter email"
						value="${ member.getEmail() }">
				</div>
			</div>
			<div class="form-group">
				<label class="control-label col-sm-2" for="pwd">비밀번호:</label>
				<div class="col-sm-6">
					<input type="password" name="pw" class="form-control" id="pw"
						placeholder="비밀 번호 입력" autocomplete="current-password" required autofocus>
				</div>
			</div>
			<div class="form-group">
				<label class="control-label col-sm-2" for="pwd">비밀번호 확인:</label>
				<div class="col-sm-6">
					<input type="password" name="pw" class="form-control" id="pw"
						placeholder="비밀 번호 다시 입력" autocomplete="new-password" required>
				</div>
			</div>
			<div class="form-group">
				<label class="control-label col-sm-2" for="name">이름:</label>
				<div class="col-sm-6">
					<input type="text" class="form-control" id="name" name="name"
						placeholder="이름 입력" value="${ member.getName() }" required>
				</div>
			</div>
			<div class="form-group">
				<label class="control-label col-sm-2" for="tel">휴대폰 번호</label>
				<div class="col-sm-6">
					<input type="text" class="form-control" id="tel" name="tel"
						autocomplete="tel" placeholder="휴대폰 번호 입력"
						value="${ member.getTel() }">
				</div>
			</div>
	
			<div class="form-group">
				<label class="control-label col-sm-2" for="zipcode">우편 번호:</label>
				<div class="col-sm-6">
					<input type="text" class="form-control col-sm-2" id="zipcode"
						autocomplete="postal-code" name="zipcode"
						value="${ member.getZipCode() }"> <input type="text"
						class="form-control" id="address2" name="address"
						autocomplete="street-address" value="${member.getAddress() }">
				</div>
				<div class="col-sm-2">
					<button type="button" class="btn btn-default" onclick="searchAddress(this); return false;">주소
						검색 하기</button>
				</div>
			</div>
	
			<div class="form-group">
				<div class="panel">
					<label class="control-label col-sm-2" for="joindate">가입일</label>
					<div class="col-sm-6">
						<p>${ member.getJoinDate() }</p>
					</div>
				</div>
			</div>
	
			<div class="form-group">
				<div class="col-sm-offset-2 col-sm-2">
					<button type="button" id="leave-member" class="btn btn-danger">회원 탈퇴</button>
				</div>
				<div class="col-sm-offset-1 col-sm-2">
					<input type="submit" class="btn btn-default" value="수정 완료">
				</div>
	
			</div>
	
		</form>
	</div>
</body>
</html>