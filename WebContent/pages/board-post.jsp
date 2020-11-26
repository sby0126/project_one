<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%
	response.setHeader("Cache-Control","no-store"); 
	response.setHeader("Pragma","no-cache"); 
	response.setDateHeader("Expires",0); 
	if (request.getProtocol().equals("HTTP/1.1"))
	        response.setHeader("Cache-Control", "no-cache");
	
%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Document</title>
    <script src="https://kit.fontawesome.com/a99df0f94f.js" crossorigin="anonymous"></script>
    <link rel="preconnect" href="https://fonts.gstatic.com">
    <link href="https://fonts.googleapis.com/css2?family=Noto+Serif+KR:wght@200;300&display=swap" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css2?family=Nanum+Gothic&display=swap" rel="stylesheet">
    <script src="https://kit.fontawesome.com/a99df0f94f.js" crossorigin="anonymous"></script>
    <!-- <link rel="stylesheet" href="//maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">
    <link rel="stylesheet" href="//maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap-theme.min.css">
    <script src="//maxcdn.bootstrapcdn.com/bootstrap/3.3.2/js/bootstrap.min.js"></script>     -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script> 
    <link rel="stylesheet" href="../css/style.css">
    <link rel="stylesheet" href="../css/board-post.css">
</head>
<body>
    <!-- 컨테이너의 시작 -->
    <div class="container">
        <!-- 헤더의 시작 -->
       <jsp:include page="header.jsp"></jsp:include>
       
        <!-- 본문의 시작 -->
        <section>
            <!-- 본문이 들어가는 래퍼 -->
            <div class="contents-wrapper">    
                <div class="board-post-container">
                    <div class="board-post-header">
                        <!-- 게시판 목록 보기 버튼-->
                        <div class="board-post-sub-detail">
                            <span>문의 게시판</span>
                        </div>
                        <!-- 게시물 제목 -->
                        <div class="board-post-title">
                            <strong>반품 신청합니다...</strong>
                        </div>
                        <!-- 작성자 프로필 / 작성 날짜 / 조회수 / 댓글수 -->
                        <div class="board-post-header-detail-info">
                            <div class="profile-box">
                                <span><i class="fas fa-user-circle fa-3x"></i></span>
                            </div>
                            <div class="detail-area col-sm-1">
                                <div class="detail-area-author-id">
                                    <span>biud436</span>        
                                </div>
                                <div class="detail-area-date-panel">
                                    <span>2020.11.16</span>
                                    <span>조회수 10</span>
                                    <span>댓글수 1개</span>    
                                </div>
                            </div>
                            <!-- 게시물 공유 및 삭제 버튼 -->
                            <div class="board-post-header-function col-sm-1">
                                <a href="" id="post-share-button"><i class="fa fa-share-alt" aria-hidden="true"></i></a>
                                <a href="" id="post-delete-button"><i class="fa fa-trash-o" aria-hidden="true"></i></a>
                                <a href="" id="post-more-function-button"><i class="fas fa-ellipsis-v"></i></a>
                            </div>                            
                        </div>
                    </div>
                    <div class="board-post-body">
                        <div class="board-article">
                            <!-- 글 내용 -->
                            <div class="board-article-contents">
                                <p>글 쓰기 내용</p><br>
                                <p>글 쓰기 내용</p><br>
                                <p>글 쓰기 내용</p><br>
                                <p>글 쓰기 내용</p><br>
                                <p>글 쓰기 내용</p><br>
                                <p>글 쓰기 내용</p><br>
                                <p>글 쓰기 내용</p><br>
                                <p>글 쓰기 내용</p><br>
                                <p>글 쓰기 내용</p><br>
                                <p>글 쓰기 내용</p><br>
                                <p>글 쓰기 내용</p><br>
                                <p>글 쓰기 내용</p><br>
                                <p>글 쓰기 내용</p><br>
                                <p>글 쓰기 내용</p><br>
                                <p>글 쓰기 내용</p><br>
                                <p>글 쓰기 내용</p><br>
                            </div>
                            <div class="board-article-footer">
                                <!-- 추천 버튼 -->
                                <div class="board-article-function-recommand">
                                    <a href="#" id="post-recommand-button"><i class="fa fa-thumbs-up" aria-hidden="true"></i></a>
                                </div>
                                <!-- 게시물 목록 / 공유  -->
                                <div class="board-article-function-share">
                                    <a href="" id="post-share-button2"><i class="fa fa-share-alt" aria-hidden="true"></i></a>
                                </div>
                            </div>
                        </div>
                        <div class="board-post-comment panel panel-info">
                            <div class="comment-header panel-heading">
                                <!-- 댓글 갯수 -->
                                <span><em class="badge">1</em> 개 댓글</span>
                            </div>
                            <div class="comment-area panel-body">
                                <div class="comment-author well">
                                    <div class="profile-box">
                                        <span><i class="fas fa-user-circle fa-3x"></i></span>
                                    </div>
                                    <div class="detail-area col-sm-1">
                                        <div class="detail-area-author-id">
                                            <span>biud436</span>        
                                        </div>
                                        <div class="detail-area-date-panel">
                                            <span>1일 전</span>
                                            <span style="cursor:pointer;"><a href="" class="replay-ok">답글</a></span>
                                            <span style="cursor:pointer;"><a href="" class="replay-delete">삭제</a></span>
                                        </div>
                                        <div class="detail-area-contentss">
                                            <span>테스트1</span>
                                        </div>
                                    </div>       
                                </div>
                                <!-- <form action="commentOK.jsp"> -->
                                    <div class="add-comment-button-area">
                                        <textarea name="" id="comment-textarea" name="text"></textarea>
                                        <button href="#" type="submit" id="comment-ok-button" class="btn btn-default">등록</button>
                                    </div>                                                          
                                <!-- </form>    -->
                            </div>
                        </div>
                    </div>
                    <div class="prev-list-panel btn-group">
                        <a href="./board-default.jsp" id="list-button" class="btn btn-default">목록</a>
                        <a href="./board-smart-editor.jsp" id="modify-button" class="btn btn-default">수정</a>
                        <a href="#" id="delete-button" class="btn btn-default">삭제</a>
                    </div>
                </div>  
            </div>
        </section>
    </div>
    <!-- 라이트 박스-->
    <div id="light-box-container">
    </div>
    <!-- 스크립트 -->
    <!-- <script src="../js/boardMain.js"></script>     -->
    <script type="module" src="../js/MorePage.js"></script>
    <script type="module" src="../js/boardPost.js"></script>
</body>
</html>
