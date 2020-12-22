<%@ page language="java" contentType="text/html; charset=UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>공지사항 게시판</title>
<style type="text/css">
#registForm {
	width: 500px;
	height: 610px;
	border: 1px solid red;
	margin: auto;
}

h2 {
	text-align: center;
}

table {
	margin: auto;
	width: 450px;
}

.td_left {
	width: 150px;
	background: orange;
}

.td_right {
	width: 300px;
	background: skyblue;
}

#commandCell {
	text-align: center;
}
</style>
</head>
<body>
	<!-- 게시판 등록 -->

	<section id="writeForm">
		<h2>게시판글등록</h2>
		<form action="boardWritePro.bo" method="post"
			enctype="multipart/form-data" name="boardform">
			<table>
				<tr>
					<td class="td_left"><label for="wrtnm">글쓴이</label></td>
					<td class="td_right"><input type="text" name="wrtnm"
						id="wrtnm" required="required" /></td>
				</tr>
				<tr>
					<td class="td_left"><label for="cpwd">비밀번호</label></td>
					<td class="td_right"><input name="cpwd" type="password"
						id="cpwd" required="required" /></td>
				</tr>
				<tr>
					<td class="td_left"><label for="ctitle">제 목</label></td>
					<td class="td_right"><input name="ctitle" type="text"
						id="ctitle" required="required" /></td>
				</tr>
				<tr>
					<td class="td_left"><label for="ctxt">내 용</label></td>
					<td><textarea id="ctxt" name="ctxt"
							cols="40" rows="15" required="required"></textarea></td>
				</tr>
				<tr>
					<td class="td_left"><label for="filename"> 파일 첨부 </label></td>
					<td class="td_right"><input name="filename" type="file"
						id="filename" /></td>
				</tr>
			</table>
			<section id="commandCell">
				<input type="submit" value="등록">&nbsp;&nbsp; <input
					type="reset" value="다시쓰기" />
			</section>
		</form>
	</section>
	<!-- 게시판 등록 -->
</body>
</html>