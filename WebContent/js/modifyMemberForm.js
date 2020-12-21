function emailCheck() {

	const message = $("#email-check-btn").data("usable");

	// $.ajax({
	// 	url: "/members/emailOverapCheck.do",
	// 	success: 
	// })

	let html = `
		<!DOCTYPE html>
		<html>
			<head>
			<meta charset="utf-8">
			<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
			<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css"
			integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u"
			crossorigin="anonymous">
			<link rel="stylesheet"
			href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css"
			integrity="sha384-rHyoN1iRsVXV4nD0JutlnGaslCJuC7uwjduW9SVrLvRYooPp2bWYgmgJQIXwl/Sp"
			crossorigin="anonymous">
			<script
			src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"
			integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa"
			crossorigin="anonymous"></script>
			<title>이메일 중복 체크</title>
			<style>
			.col-centered {
				width: 100vw;
				float: none;
				margin: 0 auto;
			}			
			</style>
			</head>
			<body>
			<div class="jumbotron col-md-2">
				<p>${(message == "usable") ? "사용 가능한 이메일입니다" : "사용 불가능한 이메일입니다"}</p>
			</div>			
			<div class="col-centered">
				<button class="btn btn-primary" onclick="self.close()">닫기</button>
			</div>			
			</body>
			</html>
		`;

	const blob = new Blob([html], {
		type: 'text/html'
	});

	let url = URL.createObjectURL(blob);

	win = window.open(url, '_blank', "width=320 height=450");
}

function searchAddress(ev) {
	const postCode = new daum.Postcode({
		oncomplete: function (data) {
			const postNo = data.zonecode;
			const address = data.roadAddress;

			$("#zipcode").val(postNo);
			$("#address2").val(address);

		}
	}).open();
}

function submit() {

}

$("#leave-member").on("click", (ev) => {
	/**
	 * @type {HTMLFormElement}
	 */
	const form = document.querySelector("#member-info-form");

	form.action = "/members/secessionMember.do";
	
	const yes = confirm("정말 탈퇴하시겠습니까?");

	if(yes) {
		form.submit();
	}

	return false;
})