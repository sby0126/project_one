<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!-- <!DOCTYPE html> -->
<!-- <html> -->
<!-- <head> -->
<!-- <meta charset="UTF-8"> -->
<!-- <title>DB 에디터</title> -->
	<script src="https://bossanova.uk/jexcel/v3/jexcel.js"></script>
	<link rel="stylesheet" href="https://bossanova.uk/jexcel/v3/jexcel.css" type="text/css" />
	<script src="https://bossanova.uk/jsuites/v2/jsuites.js"></script>
	<link rel="stylesheet" href="https://bossanova.uk/jsuites/v2/jsuites.css" type="text/css" />
<!-- <div class="well"> -->
<!-- 	<nav class="navbar navbar-fixed-top"> -->
<!-- 		<ul class="nav nav-tabs nav-justified"> -->
<!-- 		  <li class="active"><a href="#db-editor">tblExtItem 테이블</a></li> -->
<!-- 		  <li><a href="#">Menu 1</a></li> -->
<!-- 		</ul>	 -->
<!-- 	</nav>		 -->
<!-- </div> -->
<div id="db-editor" class="jumbotron content">
	<p>상품 입고 시스템</p>
	<div class="alert alert-info">
	  <strong>정보!</strong> 상품 입고 시스템은 신규 데이터 삽입만 가능합니다.
	</div>
	<div id="spreadsheet"></div>
	<p>
		<button id="add" class="btn btn-default">새로운 행 추가</button>
		<button id="delete" class="btn btn-default">마지막 행 삭제</button>
		<button id="add-col" class="btn btn-default">새로운 열 추가</button>
		<button id="delete-col" class="btn btn-default">마지막 열 삭제</button>	
	</p>
	<div class="form-group">
		<label for="action-src">액션 타겟: </label>
		<input type="text" class="form-control" id="action-src" value="/myadmin/uploadProduct.do">
	</div>
	<div class="form-group">
		<label for="sql">실행할 SQL 문: </label>
		<input type="text" class="form-control" id="sql" value="insert into tblExtItem(id, title, price, stock, regdate) values(?, ?, ?, ?, ?)">
	</div>
	<p>
		<button id="upload" class="btn btn-default">DB에 업로드하기</button>
		<button id="get-json" class="btn btn-default">JSON</button>	
	</p>
</div>	
<script>
var data = [
    ['235', '컬러 스탠다드 자켓', 84000, 50, '2021-01-16'],
    ['236', '스튜 청바지', 4000, 30, '2021-01-01'],
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
            title:'등록일',
            options: {format: 'YYYY/MM/DD'},
            width: 120
        }
     ]
});

// 새로운 행 추가
$("#add").on("click", (ev) => {
	$("#spreadsheet").jexcel("insertRow");
});

// 마지막 행 삭제
$("#delete").on("click", (ev) => {
	$("#spreadsheet").jexcel("deleteRow");
});
// 새로운 열 추가
$("#add-col").on("click", (ev) => {
	const columnName = prompt("새로운 컬럼의 이름을 적어주세요");
	$("#spreadsheet").jexcel('insertColumn', 1, -1, true, {title:columnName});
});
// 마지막 열 삭제
$("#delete-col").on("click", (ev) => {
	$("#spreadsheet").jexcel("deleteColumn");
});

$("#get-json").on("click", ev => {
	alert(JSON.stringify($("#spreadsheet").jexcel('getJson')));
})

// 추가하기 버튼을 클릭하면 DB에 시트 내용을 삽입합니다.
$("#upload").on("click", (ev) => {
	
	const raw = $("#spreadsheet").jexcel('getJson');
	raw.forEach(e => {
		
		const rs = e;
		
		$.ajax({
			url: $("#action-src").val(),
			method: "POST",
			data: {
		    	sql: $("#sql").val(),
		    	id: rs[0],
		    	title: rs[1],
		    	price: rs[2],
		    	stock: rs[3],
		    	regdate: rs[4]
		    },
		    success: function(result) {
		    	if(result.status === "success") {
		    		console.log(result);	
		    		alert("DB에 상품이 입고되었습니다");
		    	}
		    },
		    error: function(err) {
		    	const code = err.status;
		    	if(code == 401) {
		    		alert("SQL 문이 잘못되었습니다")
		    	} else {
		    		alert("DB에 추가할 수 없었습니다 :" + err);
		    	}
		    }
		});
	});
})

</script> 