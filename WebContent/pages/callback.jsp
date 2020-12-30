<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isELIgnored="false"%>
<%@ page import="java.net.URLEncoder" %>
<%@ page import="java.net.URL" %>
<%@ page import="java.net.HttpURLConnection" %>
<%@ page import="java.io.BufferedReader" %>
<%@ page import="java.io.InputStreamReader" %>   
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<script type="text/javascript" src="https://static.nid.naver.com/js/naverLogin_implicit-1.0.3.js" charset="utf-8"></script>
<script type="text/javascript" src="http://code.jquery.com/jquery-1.11.3.min.js"></script>
<title>네이버 로그인</title>
</head>
<body>
<form action="/members/naverLogin.do">
	<input type="hidden" name="id">
	<input type="hidden" name="name">
	<input type="hidden" name="email1">
	<input type="hidden" name="email2">
	<input type="hidden" name="joinDate">
	<input type="hidden" name="tel">
	<input type="hidden" name="pw"  value="">
	<input type="hidden" name="address1" value="">
	<input type="hidden" name="address2" value="">
	<input type="hidden" name="zipcode" value="00000">
	
</form>
<script type="text/javascript">
  var naver_id_login = new naver_id_login("7ImHSjL7FtzCQoKe84Jc", "https://biud436.com:9001/members/naverLogin.do");
  // 네이버 사용자 프로필 조회
  naver_id_login.get_naver_userprofile("naverSignInCallback()");
  // 네이버 사용자 프로필 조회 이후 프로필 정보를 처리할 callback function
  function naverSignInCallback() {
	$("input[name='id']").val( naver_id_login.getProfileData('id') );
	var email = naver_id_login.getProfileData('email');
	$("input[name='email1']").val( email.split("@")[0] );
	$("input[name='email2']").val( email.split("@")[1] );
	$("input[name='joinDate']").val( new Date() );
	$("input[name='name']").val( naver_id_login.getProfileData('nickname') );
	
	document.forms[0].submit();
  }  
</script>
</body>
</html>