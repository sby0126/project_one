<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ page import="java.net.URLEncoder"%>
<%@ page import="java.net.URLDecoder"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<%
	String id = (String)session.getAttribute("id");	
	String type = (String)request.getAttribute("type");
	String title = (String)request.getAttribute("title");
	String contents = (String)request.getAttribute("contents");
	String contextPath = request.getContextPath();
%>
<c:set var="contextPath" value="<%=contextPath %>" />
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Template</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/find-password.css">
    <link href="https://fonts.googleapis.com/css2?family=Nanum+Gothic&display=swap" rel="stylesheet">
    <script src="https://kit.fontawesome.com/a99df0f94f.js" crossorigin="anonymous"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <link rel="stylesheet" href="https://cdn.quilljs.com/1.3.6/quill.core.css">
    <link href="https://cdn.quilljs.com/1.3.6/quill.snow.css" rel="stylesheet">
    <link href="https://cdn.quilljs.com/1.3.6/quill.bubble.css" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/detail-item-uploader.css">
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
        <jsp:include page="/header.jsp">
        	<jsp:param value="true" name="isHide"/>
        </jsp:include>

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
                	
                	<input type="hidden" id="type" value="<%=type%>">
                	<input type="hidden" id="postNumber" value="<%= request.getParameter("postNumber") %>">
                	<input type="hidden" id="title" value="<%=title %>">
                	<input type="hidden" id="contents" value="<%=URLEncoder.encode( contents )%>">
                	
                    <button class="button" id="upload-ok">작성</button>
                    <button class="button" id="upload-cancel">취소</button>

                </div>
            </div>

        </section>
    </div>
    <!-- 라이트 박스-->
    <div id="light-box-container">
    </div>
    <jsp:include page="/pages/login.jsp"></jsp:include> 
    <!-- 스크립트 -->
    <script type="module" src="${pageContext.request.contextPath}/js/MorePage.js"></script>
    <script src="https://cdn.quilljs.com/1.3.6/quill.js"></script>
    <script src="//cdnjs.cloudflare.com/ajax/libs/KaTeX/0.7.1/katex.min.js"></script>

    <script src="${pageContext.request.contextPath}/js/board-smart-editor.js"></script>
</body>

</html>
