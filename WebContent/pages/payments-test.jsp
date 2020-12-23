<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>결제 테스트</title>
</head>
<body>
    <form action="/payments/request.do">
        <label for="productName">상품명 : </label>
        <input type="text" name="productName" placeholder="상품명">
        <label for="productId">상품 ID : </label>
        <input type="text" name="productId" placeholder="상품 ID">
        <input type="submit" value="결제 요청">
    </form>
</body>
</html>