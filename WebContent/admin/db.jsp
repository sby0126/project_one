<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>DB 에디터</title>
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
	<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
	<script src="https://bossanova.uk/jexcel/v3/jexcel.js"></script>
	<link rel="stylesheet" href="https://bossanova.uk/jexcel/v3/jexcel.css" type="text/css" />
	<script src="https://bossanova.uk/jsuites/v2/jsuites.js"></script>
	<link rel="stylesheet" href="https://bossanova.uk/jsuites/v2/jsuites.css" type="text/css" />
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>	
</head>
<body data-spy="scroll" data-target=".navbar" data-offset="50">
<div class="well">
	<nav class="navbar navbar-fixed-top">
		<ul class="nav nav-tabs nav-justified">
		  <li class="active"><a href="#db-editor">DB 에디터</a></li>
		  <li><a href="#">Menu 1</a></li>
		  <li><a href="#">Menu 2</a></li>
		  <li><a href="#">Menu 3</a></li>
		</ul>	
	</nav>		
</div>
<div id="db-editor" class="well well-lg">
	<div id="spreadsheet"></div>
	<p><button id="add" class="btn btn-default">DB에 업로드하기</button></p>
</div>	
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

// 추가하기 버튼을 클릭하면 DB에 시트 내용을 삽입합니다.
$("#add").on("click", (ev) => {
	// 데이터만 뽑아냅니다.
	$("#spreadsheet tr").siblings().each((index, elem) => {
	    const raw = $(elem).find("td:gt(0)").map((i, e) => $(e).text());
	    const rs = raw.toArray();
	    	    
	    alert(rs);
	    
	    $.post("/myadmin/uploadProduct.do", {
	    	id: rs[0],
	    	title: rs[1],
	    	price: rs[2],
	    	stock: rs[3],
	    	regdate: rs[4]
	    }, function(result, status) {
	    	if(status === "success") {
	    		console.log(result);	
	    	} else if(status === "error") {
	    		// 오류 처리
	    	}
	    });
	    
	});	
})

</script>
</body>
</html>