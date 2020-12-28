<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>DB 에디터</title>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
	<script src="https://bossanova.uk/jexcel/v3/jexcel.js"></script>
	<link rel="stylesheet" href="https://bossanova.uk/jexcel/v3/jexcel.css" type="text/css" />
	<script src="https://bossanova.uk/jsuites/v2/jsuites.js"></script>
	<link rel="stylesheet" href="https://bossanova.uk/jsuites/v2/jsuites.css" type="text/css" />
</head>
<body>
<div id="spreadsheet"></div>
<p><button id="add">추가하기</button></p>
<script>
var data = [
    ['1', '바지', 82000, 99, '2021-01-01'],
    ['2', '바지', 82000, 99, '2021-01-16'],
];

jexcel(document.getElementById('spreadsheet'), {
    data:data,
    columns: [
        {
            type: 'text',
            title:'번호',
            width:90
        },
        {
            type: 'text',
            title:'제목',
            width:90
        },
        {
            type: 'text',
            title:'가격',
            width:90
        },        
        {
            type: 'text',
            title:'재고',
            width:90
        },        
        {
            type: 'calendar',
            title:'regdate',
            width:120
        }
     ]
});
</script>
</body>
</html>