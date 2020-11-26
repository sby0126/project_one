<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="ko">
<!-- 
작성자 : 어진석
상품 상세 정보를 업로드 하는 에디터입니다. 
-->
<%
	String id = (String)session.getAttribute("id");	
%>
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
    <style>
        .editor {
            width: 100%;
            height: 450px;
        }
        .contents-header .contents-item-information .item {
            width: 98%;
        }        
        .contents-header {
            margin-bottom: 0;
            padding-bottom: 1em;
  
        }
        .contents-tail {
            display: flex;
            justify-content: center;
        }        
        .button {
            width: 6ch;
            height: 2em;
            line-height: 2em;
            text-align: center;
            background-color: #F6F6F6;
            border: 1px solid #E9E9E9;     
            border-bottom: 1px solid #CACACA;
            display: block;       
        }
    </style>     
</head>
<body>   
	<% if(id == null) { %>
		<script>
			setTimeout(function() {
				alert("글 작성을 하려면 먼저 로그인이 되어있어야 합니다. 로그인을 해주십시오.");
				location.href = "/";
			}, 0);
		</script>
	<% 
	} %>
    <!-- 컨테이너의 시작 -->
    <div class="container">
        <!-- 헤더의 시작 -->
        <jsp:include page="header.jsp"></jsp:include>

        <!-- 본문의 시작 -->
        <section>
            <!-- 본문이 들어가는 래퍼 -->
            <div class="contents-wrapper">
                <div class="contents-header">

                    <div class="contents-item-information">
                        <div class="item">
                            <div class="contents-item-title">
                                <input type="text" id="input-item-title">
                                <span>글 제목</span>                            
                            </div>                                 
                        </div>
                    </div>
                </div>
                <div class="editor"></div>
                <div class="contents-tail">
                    <button class="button" id="upload-ok">작성</button>
                    <button class="button" id="upload-cancel">취소</button>

                </div>
            </div>

        </section>
    </div>
    <!-- 라이트 박스-->
    <div id="light-box-container">
    </div>
    <!-- 스크립트 -->
    <script type="module" src="../js/MorePage.js"></script>
    <script src="https://cdn.quilljs.com/1.3.6/quill.js"></script>
    <script src="//cdnjs.cloudflare.com/ajax/libs/KaTeX/0.7.1/katex.min.js"></script>

    <script src="https://cdn.quilljs.com/1.3.6/quill.min.js"></script>
    <script>

        (function() {

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
                placeholder: '내용을 작성하세요.',
                theme: 'snow',
            });

            // 업로드 버튼 클릭 시 
            document.querySelector("#upload-ok").onclick = function() {

                if($("#input-item-title").val().length == 0) {
                    alert("글 제목을 적지 않았습니다.");
                    return false;
                }                            

                const result = {
                    title: $("#input-item-title").val(),
                    src: localStorage.getItem("thum"),
                    contents: quill.root.innerHTML
                };
                                
                let prevJson = JSON.stringify(result);
                
                prevJson = prevJson.replace(/\</g, "&lt;");
                prevJson = prevJson.replace(/\>/g, "&gt;");

                alert("클립보드에 복사되었습니다.");

                navigator.clipboard.writeText(prevJson);

                const param = URLSearchParams(location.search);
                const postNumber = param.get("postNumber");

                // 폼을 동적으로 생성합니다.
                const form = document.createElement("form");
                form.setAttribute("postNumber", postNumber);
                form.setAttribute("data", prevJson);
                form.setAttribute("action", url);

                document.body.appendChild(form);
                form.submit();
            }

            // placeholder 제어
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

            $("upload-cancel").on("click", () => {
                history.back();
            });

            const App = {
                init() {
                    const params = new URLSearchParams(location.search);
                    const postNumber = params.get("postNumber");
                },

                async load() {
                    return new Promise((resolve, reject) => {
                        $.post("", (data, textStatus, jqXHR) => {

                        });
                    });
                }
            }

        })();

    </script>
</body>

</html>
