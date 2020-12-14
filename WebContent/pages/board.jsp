<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang='en'>

<head>
    <meta charset='UTF-8'>
    <meta name='viewport' content='width=device-width, initial-scale=1.0'>
    <title></title>
    <link rel="stylesheet" href="../css/style.css">
    <link href="https://fonts.googleapis.com/css2?family=Nanum+Gothic&display=swap" rel="stylesheet">
    <script src="https://kit.fontawesome.com/a99df0f94f.js" crossorigin="anonymous"></script>

    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
    <script type="text/javascript" charset="utf8" src="https://cdn.datatables.net/1.10.22/js/jquery.dataTables.js">
    </script>

    <link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/1.10.22/css/jquery.dataTables.css">
    <link rel="stylesheet" href="https://code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">

    <script src="https://cdn.datatables.net/1.10.22/js/jquery.dataTables.min.js"></script>
    <script src="https://cdn.datatables.net/buttons/1.6.4/js/dataTables.buttons.min.js"></script>
    <link rel="stylesheet" href="https://cdn.datatables.net/buttons/1.6.4/css/buttons.dataTables.min.css">

    <style>
        .primary-button {
            outline: none;
            color: #333 !important;
            border: 1px solid #979797;
            background-color: white;
            background: linear-gradient(to bottom, white 0%, #dcdcdc 100%);

            box-sizing: border-box;
            display: inline-block;
            min-width: 1.5em;
            padding: 0.5em 1em;
            margin-left: 2px;
            text-align: center;      

            cursor: pointer;
        }
        header {
            height: calc(var(--header-box-height) - 5em);
        }
        .contents-wrapper {
            margin-top: 9em;
            height: 540px;
        }
    </style>
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
                <table id="example" class="display">
                    <thead>
                        <tr>
                            <th style="width:4em">글 번호</th>
                            <th>글 제목</th>
                            <th style="width:4em">날짜</th>
                            <th style="width:4em">글쓴이</th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr>
                            <td>18</td>
                            <td><a class="click-post"  href="#">반품 문의 합니다.</a></td>
                            <td>2020.11.20</td>
                            <td>kjkj****</td>
                        </tr>
                        <tr>
                            <td>17</td>
                            <td><a class="click-post"  href="#">반품 관련으로 문의합니다.</a></td>
                            <td>2020.11.20</td>
                            <td>kjkj****</td>
                        </tr>
                        <tr>
                            <td>16</td>
                            <td><a class="click-post"  href="#">걱정입니다.</a></td>
                            <td>2020.11.20</td>
                            <td>kjkj****</td>
                        </tr>
                        <tr>
                            <td>15</td>
                            <td><a class="click-post"  href="#">반품 문의 합니다.</a></td>
                            <td>2020.11.20</td>
                            <td>kjkj****</td>
                        </tr>
                        <tr>
                            <td>14</td>
                            <td><a class="click-post"  href="#">반품 문의 합니다.</a></td>
                            <td>2020.11.20</td>
                            <td>kjkj****</td>
                        </tr>
                        <tr>
                            <td>13</td>
                            <td><a class="click-post"  href="#">반품 관련으로 문의합니다.</a></td>
                            <td>2020.11.20</td>
                            <td>kjkj****</td>
                        </tr>
                        <tr>
                            <td>12</td>
                            <td><a class="click-post"  href="#">걱정입니다.</a></td>
                            <td>2020.11.20</td>
                            <td>kjkj****</td>
                        </tr>
                        <tr>
                            <td>11</td>
                            <td><a class="click-post"  href="#">반품 문의 합니다.</a></td>
                            <td>2020.11.20</td>
                            <td>kjkj****</td>
                        </tr>
                        <tr>
                            <td>10</td>
                            <td><a class="click-post"  href="#">반품 관련으로 문의합니다.</a></td>
                            <td>2020.11.20</td>
                            <td>kjkj****</td>
                        </tr>
                        <tr>
                            <td>9</td>
                            <td><a class="click-post"  href="#">걱정입니다.</a></td>
                            <td>2020.11.20</td>
                            <td>kjkj****</td>
                        </tr>
                        <tr>
                            <td>8</td>
                            <td><a class="click-post"  href="#">반품 문의 합니다.</a></td>
                            <td>2020.11.20</td>
                            <td>kjkj****</td>
                        </tr>
                        <tr>
                            <td>7</td>
                            <td><a class="click-post"  href="#">반품 관련으로 문의합니다.</a></td>
                            <td>2020.11.20</td>
                            <td>kjkj****</td>
                        </tr>
                        <tr>
                            <td>6</td>
                            <td><a class="click-post"  href="#">걱정입니다.</a></td>
                            <td>2020.11.20</td>
                            <td>kjkj****</td>
                        </tr>
                        <tr>
                            <td>5</td>
                            <td><a class="click-post"  href="#">반품 문의 합니다.</a></td>
                            <td>2020.11.20</td>
                            <td>kjkj****</td>
                        </tr>
                        <tr>
                            <td>4</td>
                            <td><a class="click-post"  href="#">반품 관련으로 문의합니다.</a></td>
                            <td>2020.11.20</td>
                            <td>kjkj****</td>
                        </tr>
                        <tr>
                            <td>3</td>
                            <td><a class="click-post"  href="#">걱정입니다.</a></td>
                            <td>2020.11.20</td>
                            <td>kjkj****</td>
                        </tr>
                        <tr>
                            <td>2</td>
                            <td><a class="click-post"  href="#">반품 문의 합니다.</a></td>
                            <td>2020.11.20</td>
                            <td>kjkj****</td>
                        </tr>
                        <tr>
                            <td>1</td>
                            <td><a class="click-post" href="#">반품 관련으로 문의합니다.</a></td>
                            <td>2020.11.20</td>
                            <td>kjkj****</td>
                        </tr>
                    </tbody>
                </table>
            </div>
        </section>
    </div>
    <!-- 라이트 박스-->
    <div id="light-box-container">
    </div>
    <jsp:include page="/pages/login.jsp"></jsp:include> 
    <!-- 스크립트 -->
    <script src="../js/boardMain.js"></script>   
    <script type="module" src="../js/MorePage.js"></script>
</body>

</html>
