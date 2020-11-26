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
    <link rel="preconnect" href="https://fonts.gstatic.com">
    <link href="https://fonts.googleapis.com/css2?family=Noto+Serif+KR:wght@200;300&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="../css/style.css">
    <link href="https://fonts.googleapis.com/css2?family=Nanum+Gothic&display=swap" rel="stylesheet">
    <script src="https://kit.fontawesome.com/a99df0f94f.js" crossorigin="anonymous"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>   
    <script src="../libs/filter-table.min.js"></script> 
    <script src="../libs/fancyTable.js"></script> 
    <!-- <link rel="stylesheet" href="../libs/table-sortable.css">
    <script src="../libs/table-sortable.js"></script> -->
</head>
<body>
    <style>
        /* 컨텐츠 상단 마진을 줄입니다 */
        .contents-wrapper {
            margin-top: 8em;
        }

        .board-wrapper {
            font-family: 'Noto Serif KR', serif;
            font-size: 0.75em;
            color: #222222;
        }

        tr, td {
            margin: 0;
            padding: 0;
        }

        td {
            font-size: 90%;
        }

        .board-wrapper a {
            font-style: normal;
            text-decoration: none;
            color: #333333;
        }

        tr {
            border-bottom: 1px solid #EEEEEE;
        }

        tr:hover {
            background-color: #F6F6F6;
        }

        tr.notice > td {
            background-color: #EFEFEF;
        }

        .table {
            width: 100%;
            border-collapse: collapse;
            height: 2.25em;
            line-height: 2.25em;
        }
        .tb-header{
            background-color:#fcfcfc;
            border: none;
            border-top: 1px solid #EDEDED;
            border-bottom: 1px solid #CACACA;
            text-align:center;
            vertical-align:middle;
            color: #555555;
            text-rendering: geometricPrecision;
            height: 2.8125em;
            line-height: 2.8125em;
        }

        .title {
            min-width: 500px;
        }

        /* 분류 부분을 파란색으로 강조합니다 */
        tbody tr td:nth-child(2) {
            width: 4.5em;
            color: #2255AA;
        }        

        .notice {
            font-style: normal;
            font-weight: bold;
            font-size: 1.1em;
        }
        
        tbody tr td:not(:nth-child(3)) {
            text-align: center;
        }

        tbody tr td:nth-child(3):hover {
            text-decoration: underline;
        }       

        /* 게시판 상단 헤더 */
        .board-header {
            width: 100%;
            height: 2.25em;
            line-height: 2.25em;
            padding-bottom: 4px;
            margin-bottom: 1em;
            border-bottom: 1px solid #d2d2d2;
            color: #222222;
            font-size: 2em;
            font-family: 'Noto Serif KR', serif;
        }

        .board-footer {
            width: 100%;
            margin-top: 4px;
            height: 2em;
            margin-bottom: 3em;
            display: flex;
            justify-content: space-between;
        }

        .board-footer-pages-buttons-wrapper {
            display: flex;
        }

        .board-footer-pages-buttons-wrapper a {
            font-size: 0.8rem;
        }

        .board-search-box {
            display: flex;
            flex-wrap: nowrap;
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

        .button:hover {
            background-color: #EFEFEF;
        }

        /* 돋보기 아이콘을 표시합니다. */
        .board-search-box input {
            height: 2em;
            position: relative;
            margin: 0;
            padding: 0;
            padding-left: 2.75em;
            outline: none;
            border: 1px solid #EDEFF0;
            color: #999999;
            border-bottom: none;
            background-image: url(data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAC0AAAAtCAYAAAA6GuKaAAAAGXRFWHRTb2Z0d2FyZQBBZG9iZSBJbWFnZVJlYWR5ccllPAAAAt9JREFUeNrMmD9oU1EUh99LoEMDgoHUQXFr6OZUyNYngQzaLUKFECenWoTg5OTYqRSkuHWxFKI0mzoEihkDTt1K3KRZEsggWKSQxN/Vn1Al5+bdP0negY+G3vvO+3jcd965Nxw/C+JGCkSgBAogD1ZAGgxBD3RAGzRBC4ymZj0YB6YRxpC+AbbBDrhtkLurlMAb8N2ndGrKeBV8BbuGwgHn7/L6auAxJOkMqIO3XAIuscI8deadiXQOfAZbgd/YYt6cb2n1JD6C9WA2sc78GZ/ShzGEVYV4BTbALXCTfzf4/04M8UMX6evVo8q1J8UFqIEG0L3yISiDfXBHM+8JOHKpHqqs7WnmnYJ74GSKcMDxE84/1czb432tpbc1L4i68QMwMMw94HWSeI73tVoeSvybUIcv+MQGDkswC86EpaI+QHexREamTzrSfDhqjsJ/n3hN8wGKbJZHSVMlGp5KXUNTVUo20gVh7DjGSxc3xsw3KQo20nlhrOX5wyLly9tIS73FuWfpc01vYiydFsauPEtL+dI20kNhbMmztJRvaCPdE8bWPEtL+Xo20lIpijxLR5rSaizdFsYqbH58RMh8k6JtI93UlKKyJ+myprQ1baRb7AEmxT57B5fIMo+0+W3ZSI+4a54Uqsl571BJlni91FcfmDZL11tTtc3vC3OK4JPFE8/yuqIw3ud9rftpdS7xQjOvyPbyUYyXM+S8M42wipfa8xCDw5p6jF14h81Pi5/mKy6DNZa1Ssx+Qu3MN7E8Ll2lM0w2q924F/H/d+M/wEPwZU7S98GHYCdcdj2s6TPZu6SKS8di6ok/5ja/5yilrn/KpeBFfNoB5BFY5ZveNZTt8rpVHs5s+hIP53w+vfxb7I+g9ctpIu0rnMVTwfzj0nWpLELaWXxR0k7ii5S2Fl+0dMCXzUh88dIW4smQNhN/nRzp+OKVZEnHFE+e9HTx42RK/yuumq2fRP1+/kuAAQAEq9U0xVoDAQAAAABJRU5ErkJggg==);
            background-size: 0.9375em;
            background-repeat: no-repeat;
            background-position-x: 1em;
            background-position-y: calc(50%);
        }

        /* 코멘트 색상을 변경합니다. */
        .comment {
            color: #E44F26;
            margin-left: 0.5em;
        }

        @media screen and (max-width: 720px) {
            .table {
                table-layout: fixed;
            }
            .table td:first-child, .table td:nth-last-child(2), .table td:last-child, 
            table th:first-child, .table th:nth-last-child(2), .table th:last-child  {
                display: none;
            }
        }

    </style>
    <!-- 컨테이너의 시작 -->
    <div class="container">
        <!-- 헤더의 시작 -->
        <jsp:include page="header.jsp"></jsp:include>

        <!-- 본문의 시작 -->
        <section>
            <!-- 본문이 들어가는 래퍼 -->
            <div class="contents-wrapper">    
                <div class="board-wrapper" id="editor">
                    <div class="board-header">
                        <a href="#">게시판</a>
                    </div>
                    <table class="table">
                        <thead>
                            <tr>
                                <th class="tb-header post-no">글 번호</th>
                                <th class="tb-header">분류</th>
                                <th class="tb-header title">글 제목</th>
                                <th class="tb-header">이름</th>
                                <th class="tb-header">날짜</th>
                                <th class="tb-header">조회<br></th>
                                <th class="tb-header">추천</th>
                            </tr>
                        </thead>
                        <tbody>
                            <tr class="notice">
                                <td>18</td>
                                <td>공지</td>
                                <td><a href="#">공지사항입니다.</a></td>
                                <td>관리자</td>
                                <td>2020.11.20</td>
                                <td>0</td>
                                <td>0</td>
                            </tr>
                            <tr>
                                <td>17</td>
                                <td>일반</td>
                                <td><a href="#">반품 관련으로 문의합니다.</a></td>
                                <td>kjkj****</td>
                                <td>2020.11.20</td>
                                <td>0</td>
                                <td>0</td>
                            </tr>
                            <tr>
                                <td>16</td>
                                <td>일반</td>
                                <td><a href="#">걱정입니다.</a></td>
                                <td>kjkj****</td>
                                <td>2020.11.20</td>
                                <td>0</td>
                                <td>0</td>
                            </tr>
                            <tr>
                                <td>15</td>
                                <td>일반</td>
                                <td><a href="#">반품 문의 합니다.</a></td>
                                <td>kjkj****</td>
                                <td>2020.11.20</td>
                                <td>0</td>
                                <td>0</td>
                            </tr>
                            <tr>
                                <td>14</td>
                                <td>일반</td>
                                <td><a href="#">반품 문의 합니다.</a></td>
                                <td>kjkj****</td>
                                <td>2020.11.20</td>
                                <td>0</td>
                                <td>0</td>
                            </tr>
                            <tr>
                                <td>13</td>
                                <td>일반</td>
                                <td><a href="#">반품 관련으로 문의합니다.</a></td>
                                <td>kjkj****</td>
                                <td>2020.11.20</td>
                                <td>0</td>
                                <td>0</td>
                            </tr>
                            <tr>
                                <td>12</td>
                                <td>일반</td>
                                <td><a href="#">걱정입니다.<span class="comment">[2]</span></a></td>
                                <td>kjkj****</td>
                                <td>2020.11.20</td>
                                <td>0</td>
                                <td>0</td>
                            </tr>
                            <tr>
                                <td>11</td>
                                <td>일반</td>
                                <td><a href="#">반품 문의 합니다.</a></td>
                                <td>kjkj****</td>
                                <td>2020.11.20</td>
                                <td>0</td>
                                <td>0</td>
                            </tr>
                            <tr>
                                <td>10</td>
                                <td>일반</td>
                                <td><a href="#">반품 관련으로 문의합니다.</a></td>
                                <td>kjkj****</td>
                                <td>2020.11.20</td>
                                <td>0</td>
                                <td>0</td>
                            </tr>
                            <tr>
                                <td>9</td>
                                <td>일반</td>
                                <td><a href="#">걱정입니다.</a></td>
                                <td>kjkj****</td>
                                <td>2020.11.20</td>
                                <td>0</td>
                                <td>0</td>
                            </tr>
                            <tr>
                                <td>8</td>
                                <td>일반</td>
                                <td><a href="#">반품 문의 합니다.</a></td>
                                <td>kjkj****</td>
                                <td>2020.11.20</td>
                                <td>0</td>
                                <td>0</td>
                            </tr>
                            <tr>
                                <td>7</td>
                                <td>일반</td>
                                <td><a href="#">반품 관련으로 문의합니다.</a></td>
                                <td>kjkj****</td>
                                <td>2020.11.20</td>
                                <td>0</td>
                                <td>0</td>
                            </tr>
                            <tr>
                                <td>6</td>
                                <td>일반</td>
                                <td><a href="#">걱정입니다.</a></td>
                                <td>kjkj****</td>
                                <td>2020.11.20</td>
                                <td>0</td>
                                <td>0</td>
                            </tr>
                            <tr>
                                <td>5</td>
                                <td>일반</td>
                                <td><a href="#">반품 문의 합니다.</a></td>
                                <td>kjkj****</td>
                                <td>2020.11.20</td>
                                <td>0</td>
                                <td>0</td>
                            </tr>
                            <tr>
                                <td>4</td>
                                <td>일반</td>
                                <td><a href="#">반품 관련으로 문의합니다.</a></td>
                                <td>kjkj****</td>
                                <td>2020.11.20</td>
                                <td>0</td>
                                <td>0</td>
                            </tr>
                            <tr>
                                <td>3</td>
                                <td>일반</td>
                                <td><a href="#">걱정입니다.</a></td>
                                <td>kjkj****</td>
                                <td>2020.11.20</td>
                                <td>0</td>
                                <td>0</td>
                            </tr>
                            <tr>
                                <td>2</td>
                                <td>일반</td>
                                <td><a href="#">반품 문의 합니다.</a></td>
                                <td>kjkj****</td>
                                <td>2020.11.20</td>
                                <td>0</td>
                                <td>0</td>
                            </tr>
                            <tr>
                                <td>1</td>
                                <td>일반</td>
                                <td><a href="#">반품 관련으로 문의합니다.</a></td>
                                <td>kjkj****</td>
                                <td>2020.11.20</td>
                                <td>0</td>
                                <td>0</td>
                            </tr>
                            </tbody>
                            <tfoot>
                            </tfoot>
                    </table>
                    <div class="board-footer">
                        <div>
                            <div class="board-search-box">
                                <input type="text" name="" class="search-box">
                                <a href="#" class="button" id="search-button">검색</a>
                            </div>
                        </div>
                        <div class="board-footer-page-buttons">
                            <div class="board-footer-pages-buttons-wrapper">
                                <a href="#" class="button" id="prev-button">이전</a>
                                <a href="#" class="button">1</a>
                                <a href="#" class="button">2</a>
                                <a href="#" class="button">3</a>
                                <a href="#" class="button">4</a>
                                <a href="#" class="button">5</a>
                                <a href="#" class="button">6</a>
                                <a href="#" class="button">7</a>
                                <a href="#" class="button">8</a>
                                <a href="#" class="button">9</a>
                                <a href="#" class="button" id="next-button">다음</a>
                            </div>
                        </div>
                        <div>
                        	<!--  로그인이 되어있지 않으면 글쓰기 불가능 -->
                        	<% 
                        		String id = (String)session.getAttribute("id");
                        		if(id != null) {  
                        	%>
                            <a href="board-smart-editor.jsp" class="button">글쓰기</a>
                            <% } %>
                        </div>
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
    <script src="../js/boardDefault.js"></script>
</body>
</html>
