<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

	<script src="https://bossanova.uk/jexcel/v3/jexcel.js"></script>
	<link rel="stylesheet" href="https://bossanova.uk/jexcel/v3/jexcel.css" type="text/css" />
	<script src="https://bossanova.uk/jsuites/v2/jsuites.js"></script>
	<link rel="stylesheet" href="https://bossanova.uk/jsuites/v2/jsuites.css" type="text/css" />
<div id="product" class="jumbotron content">
	<p>상품 진열 시스템</p>
	<div class="alert alert-info">
	  <strong>정보!</strong> 상품 진열 시스템은 신규 데이터 삽입만 가능합니다. 번호는 마지막 번호로 삽입됩니다.
	</div>
	<div id="spreadsheet2"></div>
	<p>
		<button id="add2" class="btn btn-default">새로운 행 추가</button>
		<button id="delete2" class="btn btn-default">마지막 행 삭제</button>
		<button id="add-col2" class="btn btn-default">새로운 열 추가</button>
		<button id="delete-col2" class="btn btn-default">마지막 열 삭제</button>	
	</p>
	<div class="form-group">
		<label for="action-src2">액션 타겟: </label>
		<input type="text" class="form-control" id="action-src2" value="/myadmin/uploadItem.do">
	</div>
	<div class="form-group">
		<label for="sql2">실행할 SQL 문: </label>
		<input type="text" class="form-control" id="sql2" value="insert into tblproduct(pageType, genderType, shopType, shopName, texts, contentUrl, title, price, term, link) values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)">
	</div>
	<p>
		<button id="upload2" class="btn btn-default">DB에 업로드하기</button>
		<button id="get-json2" class="btn btn-default">JSON</button>	
	</p>
</div>	
<script>
var data2 = [
    [1, 'shop', 'M', 'S', '제이브로스', '20대,30대트랜드·캐쥬얼', '1562650322639_mi.jpg', "", "", "", 'https://www.sta1.com/shop-redirect?storeSeq=769&pgcd=1002&ptsq=1&chno=&gndr=M'],
    [2, 'shop', 'M', 'S', '꼰스', '10대,20대유니크·빈티지', '1602468427433.jpg', "", "", "", 'https://www.sta1.com/shop-redirect?storeSeq=4975&pgcd=1002&ptsq=1&chno=&gndr=M'],
];

jexcel(document.getElementById('spreadsheet2'), {
    data:data2,
    columns: [
        {
            type: 'text',
            title:'id',
            width:90
        },
        {
        	type: 'dropdown',
            title:'pageType',
            width:90,
            source: [
            	'shop',
            	'item',
            	'sale'
        	]
        },
        {
            type: 'dropdown',
            title:'genderType',
            width:90,
            source: [
            	'M',
            	'F'
            ]
        },        
        {
            type: 'dropdown',
            title:'shopType',
            width:90,
            source: [
            	'S',
            	'B'
            ]            
        },        
        {
        	type: 'text',
            title:'shopName',
            width:90
        },
        {
        	type: 'text',
            title:'texts',
            width:90
        },
        {
        	type: 'image',
            title:'contentUrl',
            width:240
        },
        {
        	type: 'text',
            title:'title',
            width:90
        },
        {
        	type: 'text',
            title:'price',
            width:90
        },
        {
        	type: 'text',
            title:'term',
            width:90
        },
        {
        	type: 'text',
            title:'link',
            width:90
        },
     ]
//      updateTable: function (instance, cell, col, row, val, id) {
//          if (col == 6) {
//              cell.innerHTML = '<img src="' + val + '" style="width:100px;height:100px">';
//          }
//      }     
});

// 새로운 행 추가
$("#add2").on("click", (ev) => {
	$("#spreadsheet2").jexcel("insertRow");
});

// 마지막 행 삭제
$("#delete2").on("click", (ev) => {
	$("#spreadsheet").jexcel("deleteRow");
});
// 새로운 열 추가
$("#add-col2").on("click", (ev) => {
	const columnName = prompt("새로운 컬럼의 이름을 적어주세요");
	$("#spreadsheet2").jexcel('insertColumn', 1, -1, true, {title:columnName});
});
// 마지막 열 삭제
$("#delete-col2").on("click", (ev) => {
	$("#spreadsheet2").jexcel("deleteColumn");
});

$("#get-json2").on("click", ev => {
	alert(JSON.stringify($("#spreadsheet2").jexcel('getJson')));
})

// 추가하기 버튼을 클릭하면 DB에 시트 내용을 삽입합니다.
$("#upload2").on("click", (ev) => {
	
	const raw = $("#spreadsheet2").jexcel('getJson');
	raw.forEach(e => {
		
		const rs = e;
		
		$.ajax({
			url: $("#action-src2").val(),
			method: "POST",
			data: {
		    	sql: $("#sql2").val(),
		    	id: rs[0],
		    	pageType: rs[1],
		    	genderType: rs[2],
		    	shopType: rs[3],
		    	shopName: rs[4],
		    	texts: rs[5],
		    	contentUrl: rs[6],
		    	title: rs[7],
		    	price: rs[8],
		    	term: rs[9],
		    	link: rs[10],
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