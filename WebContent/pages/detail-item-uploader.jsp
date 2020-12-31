<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="ko">
<!-- 
작성자 : 어진석
상품 상세 정보를 업로드 하는 에디터입니다. 
-->

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Template</title>
    <link rel="stylesheet" href="../css/style.css">
    <link rel="stylesheet" href="../css/find-password.css">
    <link href="https://fonts.googleapis.com/css2?family=Nanum+Gothic&display=swap" rel="stylesheet">
    <script src="https://kit.fontawesome.com/a99df0f94f.js" crossorigin="anonymous"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <link rel="stylesheet" href="https://cdn.quilljs.com/1.3.6/quill.core.css">
    <link href="https://cdn.quilljs.com/1.3.6/quill.snow.css" rel="stylesheet">
    <link href="https://cdn.quilljs.com/1.3.6/quill.bubble.css" rel="stylesheet">
    <link rel="stylesheet" href="../css/detail-item-uploader.css">
</head>

<body>
    <!-- 컨테이너의 시작 -->
    <div class="container">
        <!-- 헤더의 시작 -->
        <jsp:include page="/header.jsp">
        	<jsp:param value="true" name="isHide"/>
        </jsp:include>

        <!-- 본문의 시작 -->
        <section>
            <!-- 본문이 들어가는 래퍼 -->
            <div class="contents-wrapper">
                <div class="contents-header">
                    <div class="contents-item-information">
                    	<form id="uploader" method="POST" action="/contents/itemUploader.do" enctype="multipart/form-data">
	                        <div class="item">
	                        
	                            <div class="contents-item-title">
	                                <span>페이지 타입</span>                            
	                                <select style="margin-right: 1em;" name="pageType">
	                                	<option value="shop" selected>SHOP</option>
	                                	<option value="item">ITEM</option>
	                                	<option value="sale">SALE</option>
	                                </select>
	                            </div>    
	                             
	                            <div class="contents-item-title">
	                                <span>성별 : </span>                            
	                                <select style="margin-right: 1em;" name="genderType">
	                                	<option value="M" selected>남자</option>
	                                	<option value="F">여자</option>
	                                </select>
	                            </div>   	
	                                                        
	                            <div class="contents-item-title">
	                                <span>상점 타입</span>                            
	                                <select style="margin-right: 1em;" name="shopType">
	                                	<option value="S" selected>소호</option>
	                                	<option value="B">브랜드</option>
	                                </select>
	                            </div>  
	                            
	                            <div class="contents-item-title">
	                            	<input type="text" name="shopName" value="">
	                                <span>상점 이름</span>  
	                            </div>     	 
	                            
	                            <div class="contents-item-title">
	                            	<input type="text" name="texts" value="">
	                                <span>유형(예: 20대,30대트랜드·캐쥬얼)</span>  
	                            </div>
	                            
	                            <div class="contents-item-title">
	                                <span style="margin-right: 1em;">상품 썸네일</span>                            
	                                <input type="file" id="input-item-image" name="contentUrl" onchange="updateImage(this)">
	                            </div>      	                                          	                                                       
	                            	                                                         	                        
	                            <div class="contents-item-title">
	                                <input type="text" name="title" id="input-item-title">
	                                <span>제목</span>                            
	                            </div>
	                            
	                            <div class="contents-item-title">
	                                <input type="number" name="price" id="input-item-price" step="1000" min="1000">
	                                <span>상품 가격</span>                            
	                            </div>        
     
	                            <div class="contents-item-title">
	                                <input type="text" name="term" id="input-item-term">
	                                <span>Term</span>                            
	                            </div>

	                            <div class="contents-item-title">
	                            	<input type="text" name="link" value="">
	                                <span>링크</span>  
	                            </div>        
                    
	                        </div>
	                        <div class="item">
	                            <div class="contents-item-title content-item-thum">
	                                <p><span style="margin-right: 1em;">상품 미리보기</span></p>
	                                <img src="https://dummyimage.com/196x196/000/fff" alt="">
	                            </div>          
	                        </div>                    	
                    	</form>
                    </div>
                </div>
                <div class="editor"></div>
                <div class="contents-tail">
                    <button id="upload-ok">작성</button>
                    <button id="upload-cancel">취소</button>
                </div>
            </div>

        </section>
    </div>
    <!-- 라이트 박스-->
    <div id="light-box-container">
    </div>
    <jsp:include page="/pages/login.jsp"></jsp:include> 
    <!-- 스크립트 -->
    <script type="module" src="../js/MorePage.js"></script>
    <script src="https://cdn.quilljs.com/1.3.6/quill.js"></script>
    <script src="//cdnjs.cloudflare.com/ajax/libs/KaTeX/0.7.1/katex.min.js"></script>

    <script src="https://cdn.quilljs.com/1.3.6/quill.min.js"></script>
    <script>

        // 게시물 에디터의 인스턴스를 만듭니다.
        var quill = new Quill('.editor', {
            modules: {
                toolbar: [
                    ['bold', 'italic', 'underline', 'strike'], // toggled buttons
                    ['blockquote', 'code-block'],

                    [{
                        'header': 1
                    }, {
                        'header': 2
                    }], // custom button values
                    [{
                        'list': 'ordered'
                    }, {
                        'list': 'bullet'
                    }],
                    [{
                        'script': 'sub'
                    }, {
                        'script': 'super'
                    }], // superscript/subscript
                    [{
                        'indent': '-1'
                    }, {
                        'indent': '+1'
                    }], // outdent/indent
                    [{
                        'direction': 'rtl'
                    }], // text direction

                    [{
                        'size': ['small', false, 'large', 'huge']
                    }], // custom dropdown
                    [{
                        'header': [1, 2, 3, 4, 5, 6, false]
                    }],

                    [{
                        'color': []
                    }, {
                        'background': []
                    }], // dropdown with defaults from theme
                    [{
                        'font': []
                    }],
                    [{
                        'align': []
                    }],
                    ['image'],
                    ['clean'] // remove formatting button
                ]
            },
            placeholder: '상품 상세 정보를 작성하세요.',
            theme: 'snow',
        });
        
    	
        /**
         * 이미지를 업로드 합니다.
         */
//         function selectImage() {
//             const input = document.createElement("input");
//             input.type = "file";
//             input.onchange = function () {
//                 const formData = new FormData();
//                 const file = $(this)[0].files[0];
//                 formData.append('image', file);
                
//                 const pageType = $("*[name='pageType']").val();
//                 const genderType = $("*[name='genderType']").val();
//                 const shopType = $("*[name='shopType']").val();
                
//                 formData.append("pageType", pageType);
//                 formData.append("genderType", genderType);
//                 formData.append("shopType", shopType);

//                 const filename = file.name || "";
//                 let mimeType = '';

//                 if(filename.toLowerCase().indexOf(".png") >= 0) {
//                     console.log("이미지입니다.")
//                     mimeType = "image";
//                 }

//                 // AJAX를 통해 이미지를 서버에 업로드합니다.
//                 $.ajax({
//                     type: "POST",
//                     enctype: "multipart/form-data",
//                     url: "/board/qna/imageUpload.do",
//                     data: formData,
//                     processData: false,
//                     contentType: false,
//                     success: function (data) {
//                         const range = quill.getSelection();
//                         if(mimeType == "image") {
//                             quill.insertEmbed(range.index, 'image', "/images/" + pageType + "/" + genderType + "/" + shopType + "/" + data.url);
//                         }
//                     },
//                     error: function (err) {
//                         console.warn(err);
//                     }
//                 })
//             }

//             input.click();
//         }       
        
	    /**
	     * 이미지를 업로드 합니다.
	     */
	    function selectImage() {
	        const input = document.createElement("input");
	        input.type = "file";
	        input.onchange = function () {
	            const formData = new FormData();
	            const file = $(this)[0].files[0];
	            formData.append('image', file);
	
	            const filename = file.name || "";
	            let mimeType = '';
	
	            if(filename.toLowerCase().indexOf(".png") >= 0) {
	                console.log("이미지입니다.")
	                mimeType = "image";
	            }
	
	            // AJAX를 통해 이미지를 서버에 업로드합니다.
	            $.ajax({
	                type: "POST",
	                enctype: "multipart/form-data",
	                url: "/board/qna/imageUpload.do",
	                data: formData,
	                processData: false,
	                contentType: false,
	                success: function (data) {
	                    const range = quill.getSelection();
	                    if(mimeType == "image") {
	                        quill.insertEmbed(range.index, 'image', "/uploads/" + data.url);
	                    }
	                },
	                error: function (err) {
	                    console.warn(err);
	                }
	            })
	        }
	
	        input.click();
	    }        
        
        quill.getModule("toolbar").addHandler('image', () => {
            selectImage();
        });

        // 썸네일 이미지 업데이트
        function updateImage(src) {
            const file = document.querySelector('#input-item-image').files[0];
            const reader = new FileReader();
            
            reader.addEventListener("load", function () {
                const img = document.querySelector(".content-item-thum img");
                img.src = reader.result;

                localStorage.setItem("thum", reader.result);

            }, false);

            if(file) {
                reader.readAsDataURL(file);
            }
        }

        // 업로드 버튼 클릭 시 
        document.querySelector("#upload-ok").onclick = function() {

            if($("#input-item-image").val().length == 0) {
                alert("썸네일 이미지가 업로드 되지 않았습니다.");
                return false;
            }

            if($("#input-item-price, #input-item-price").val().length == 0) {
                alert("제목 또는 상품 가격을 적지 않았습니다.");
                return false;
            }                            

            const result = {
                title: $("#input-item-title").val(),
                price: $("#input-item-price").val(),
                src: localStorage.getItem("thum"),
                contents: quill.root.innerHTML
            };

            navigator.clipboard.writeText(JSON.stringify(result));

            if(localStorage.getItem("thum")) {
                localStorage.removeItem("thum");
            }
            
            const uploader = document.querySelector("#uploader");
            uploader.submit();
        }

        // 상품 제목 입력 시, placeholder 제어
        $("#input-item-title").on("change", (ev) => {
            /**
             * @type {HTMlInputElement
             */
            const target = ev.target;
            
            if(target.value && target.value.length > 0) {
                $("#input-item-title ~ span").css("visibility", "hidden");
            } else {
                $("#input-item-title ~ span").css("visibility", "visible");
            }
            
        });

        // 상품 가격 입력 시, placeholder 제어
        $("#input-item-price").on("change", (ev) => {
            /**
             * @type {HTMlInputElement
             */
            const target = ev.target;
            
            if(target.value && target.value.length > 0) {
                $("#input-item-price ~ span").css("visibility", "hidden");
            } else {
                $("#input-item-price ~ span").css("visibility", "visible");
            }
        });

        $("#upload-cancel").on("click", () => {
            history.back();
        });

    </script>
</body>

</html>
