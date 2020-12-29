<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	response.setHeader("Cache-Control","no-store"); 
	response.setHeader("Pragma","no-cache"); 
	response.setDateHeader("Expires",0); 
	if (request.getProtocol().equals("HTTP/1.1"))
	        response.setHeader("Cache-Control", "no-cache");

	%>
<!DOCTYPE html>
<html lang="ko">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>쇼핑몰 클론 프로젝트</title>
    <link rel="stylesheet" href="../css/style.css">
    <link rel="stylesheet" href="../css/join.css">
    <link href="https://fonts.googleapis.com/css2?family=Nanum+Gothic&display=swap" rel="stylesheet">
    <script src="https://kit.fontawesome.com/a99df0f94f.js" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/underscore@1.11.0/underscore-min.js"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script src="https://t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
    <script type="text/javascript" src="https://cdn.jsdelivr.net/npm/emailjs-com@2/dist/email.min.js"></script>
	<script type="text/javascript">
		(function() {
			emailjs.init("user_X7j6z2JYAPbyb2W32qUSv");
		})();
	</script>
</head>
<body>
<% request.setCharacterEncoding("UTF-8"); %>
<!-- 컨테이너의 시작 -->
<div class="container">
    <jsp:include page="/header.jsp">
    	<jsp:param value="true" name="isHide"/>
    </jsp:include>

    <!-- 본문의 시작 -->
    <section>
        <!-- 본문이 들어가는 래퍼 -->
        <div class="contents-wrapper">
            <div class="header"></div>

            <div class="main">
                <div class="content_login">
                    <h2 class="title">회원가입</h2>
                    <span>회원정보를 입력해주세요.</span>
                    <!-- <hr class="line_top"> -->
                    <form id="join_form" name="user_form" method="post" action="/members/signUp.do" accept-charset="utf-8">
                        <ul class="form_list">
                            <li>
                                <!-- 아이디 --> 
                                <label for="id" class="label1"> 
                                    <span>ID를 입력해주세요.</span>
                                    <span>띄어쓰기없이 영/숫자 6~14자</span>
                                </label> <input type="text" id="login_id" name="id" maxlength="14">
                                <div id="idMsg" class="ability_chk">...........</div>
                            </li>
                            <li>
                                <!-- 비밀번호 --> <label for="pw" class="label1"> <span>비밀번호를
                                        입력해주세요.</span> <span>띄어쓰기없이 영/숫자 6~14자</span>
                                </label> <input type="password" autocomplete="new-password" name="pw" id="password"
                                    maxlength="15" aria-autocomplete="list">
                                <div id="pwd1Msg" class="ability_chk"></div>
                            </li>
                            <li>
                                <!-- 비밀번호 확인 --> <label for="pw" class="label1"> <span>비밀번호를 다시 입력해주세요
                                </span> <span>띄어쓰기없이 영/숫자 6~14자</span>
                                </label> <input type="password" autocomplete="new-password" id="password-check"
                                    maxlength="15">
                                <div id="pwd1Msg2" class="ability_chk"></div>
                            </li>
                            <li>
                                <!-- 이름 --> <label for="name" class="label1"><span>이름을
                                        입력해주세요.</span></label> <input type="text" name="name" id="user_name"
                                    maxlength="30">
                                <div id="userNameMsg" class="ability_chk"></div>
                            </li>
                            <li>
                                <!-- 휴대폰 --> <label for="tel" class="label1"><span>휴대폰번호를
                                        입력해주세요.</span></label> <input type="text" name="tel" id="user_phoneNum"
                                    maxlength="30">
                                <div id="userNameMsg" class="ability_chk"></div>
                            </li>
                            <li>
                                <!-- 주소 --> <input type="text" name="zipcode" readonly="true" id="user_adr1"
                                    maxlength="40" placeholder="21500">
                                <button type="button" id="bt_adr">주소검색하기</button> <input type="text" name="address1"
                                    readonly="true" id="user_adr2" maxlength="40"
                                    placeholder="인천 남동구 백범로467번길 51 (간석동, OO빌라)">

                                <label for="address2" class="label1"> <span>상세주소를
                                        입력해주세요.</span>
                                </label> <input type="text" name="address2" id="user_adr3" maxlength="40">

                            </li>
                            <li>
                                <!-- 이메일 --> <label for="email1" class="label1"> <span>이메일을
                                        입력해주세요.</span>
                                </label> <label for="email2" class="label1"></label> <input type="text" name="email1"
                                    id="user_email1" maxlength="30"> <span id="sp"></span> <input type="text"
                                    name="email2" id="user_email2" maxlength="30">
                                <button type="button" id="bt_email" onclick="sendEmail()">이메일인증</button>

                                <div id="userEmailMsg" class="ability_chk"></div>
                            </li>

                        </ul>
                        <input id="join_button" type="submit" value="회원가입">

                        <script>
                        	var random; //이메일 인증을 위한 랜덤값 4자리 전송
                        	var checkYnyn = "N"; // 회원가입을 위한 변수
                        	var reg_email = /^([0-9a-zA-Z_\.-]+)@([0-9a-zA-Z_-]+)(\.[0-9a-zA-Z_-]+){1,2}$/; //올바른 이메일형식 체크를 위한 변수
                            var clickCount = 0; 
                        	var sendmeil = false; // 이메일인증이 확인되면 더이상 sendEmail 함수를 실행시키지않는다.
                        	
                        	
                        	// 이메일 인증 api 
                        	
                        	
                        	function sendEmail(){
                        		if(sendmeil) {
                        			alert('인증이 완료되었습니다.');
                        			return false;
                        		}
                        		var str = $("#user_email1").val() + "@" + $("#user_email2").val();
                        		if (!reg_email.test(str)) {	
                                    alert("이메일형식이 잘못됬습니다.");
                                    return false;
                                }else{
                        		random = Math.floor(Math.random()*9000) + 1000;
                        		var user = $("#user_email1").val()+"@"+$("#user_email2").val();
                        		clickCount++;
                        		 
                        		var templateParams = {
                        				random : random,
                        	            user: user                        				 
                        		 };                        		                        		                       		          
                        		 emailjs.send('service_wc9sij4', 'template_e47qy7i', templateParams)
                            
                                 	    .then(function(response) {
                                 	    	alert("인증번호가 발송되었습니다.");
                                 	    	if(clickCount == 1){
                                  	    	$("#bt_email").after("<br><input id='checkNum' name='check' style='width:100px; display:inline' placeholder='인증번호입력'><button type='button' id='checkButton' style='position:relative' onclick='checkYn()'>확인</button>")
                                 	    	}
                                 	      
                                 	    }, function(error) {
                                 	       console.log('FAILED...', error);
                                 	    });
                                }
                        	}
                        	
                        	// 인증번호가 맞는지 확인 
                        	function checkYn(){
                        		
                        		if($("#checkNum").val() == random){
                        			sendmeil = true;
                        			$("#checkNum, #checkButton").css("display","none");
                        			alert('인증되었습니다.')
                        			checkYnyn = "Y";    //   checkYnyn = Y면 회원가입 가능 
                        			clickCount = 0;      
                        			
                        		}else {
                        			alert('인증번호가 틀렸습니다.');
                        			checkYnyn = "N";              //    checkYnyn = N면 회원가입 불가능
                        		}
                        	
                        	}
                        	
                        	
                        
                        	function processMouseDown(e) {
                                $(this).find("label").hide();
                        	}
                        	
                        	$(".content_login li").on({
                        		"click": processMouseDown
                        	});
                        
                            // 주소  api
                            $("#bt_adr").click(function () {
                                new daum.Postcode({
                                    oncomplete: function (data) {
                                        var postNo = data.zonecode;
                                        var address = data.roadAddress;

                                        $("#user_adr1").val(postNo);
                                        $("#user_adr2").val(address);

                                    }
                                }).open();
                            });

                            // 올바른 이메일형식 체크
                           /*  $("#bt_email").click(function () {
                                var reg_email = /^([0-9a-zA-Z_\.-]+)@([0-9a-zA-Z_-]+)(\.[0-9a-zA-Z_-]+){1,2}$/;
                                var str = $("#user_email1").val() + "@" + $("#user_email2").val();
                                if (!reg_email.test(str)) {
                                    alert("이메일형식이 잘못됬습니다.");
                                }
                            }); */

                            $(".main .content_login input").focus(function () {
                                var read = $(this).prop("readonly");
                                if (!read) {
                                    $(this).parent().find(".label1").css("display", "none")
                                }
                            }).blur(function () {
                                var value = $(this).val()
                                if (value == "")
                                    $(this).parent().find(".label1").css("display", "block")
                            });

                            $("#user_email1,#user_email2").focus(function () {
                                $("#sp").html("@")
                            }).blur(function () {
                                $("#sp").html(" ")
                            });

                            $(".main .content_login input").focus(function () {
                                var read = $(this).prop("readonly");
                                if (!read) {
                                    $(this).parent().find(".label1").css("display", "none")
                                }
                            }).blur(function () {
                                var value = $(this).val()
                                if (value == "")
                                    $(this).parent().find(".label1").css("display", "block");
                            })

                            $("#user_email1,#user_email2").focus(function () {
                                $("#sp").html("@")
                            }).blur(function () {
                                $("#sp").html(" ")
                            });

                            $("#join_button").on("click", function (ev) {
                                if ($("#login_id").val() == "") {
                                    alert("아이디를 입력하세요");
                                    return false;
                                }

                                if ($("#password").val() == "") {
                                    alert("비밀번호를 입력하세요");
                                    return false;
                                }

                                if( $("#password").val() !== 
                                    $("#password-check").val()) {
                                        alert("비밀번호가 맞지 않습니다.");
                                        $("#password-check").focus();
                                    return false;
                                }

                                if ($("#user_name").val() == "") {
                                    alert("이름을 입력하세요");
                                    return false;
                                }
                                if ($("#user_phoneNum").val() == "") {
                                    alert("전화번호를 입력하세요");
                                    return false;
                                }
                                if ($("#user_adr1").val() == "") {
                                    alert("우편번호를 입력하세요");
                                    return false;
                                }
                                if ($("#user_adr2").val() == "") {
                                    alert("기본주소를 입력하세요");
                                    return false;
                                }
                                if ($("#user_adr3").val() == "") {
                                    alert("상세주소를 입력하세요");
                                    return false;
                                }
                                if ($("#user_email1, user_email2").val() == "") {
                                    alert("이메일을 입력하세요");
                                    return false;
                                }
                                
                                if (checkYnyn == "N"){
                                	alert("이메일을 인증해주세요")
                                	return false;
                                }

                                $(".main .content_login input").trigger("blur");

                            });
                        </script>
                    </form>

                </div>

            </div>
        </div>
    </section>
</div>
<!-- 라이트 박스-->
<div id="light-box-container"></div>
<jsp:include page="/pages/login.jsp"></jsp:include> 
<!-- 스크립트 -->
<!-- <script src="../js/boardMain.js"></script>     -->
<script type="module" src="../js/MorePage.js"></script>
</body>
</html>