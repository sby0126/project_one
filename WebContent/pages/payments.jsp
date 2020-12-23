<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="javax.servlet.*" %>
<%@ page import="vo.ProductVO" %>
<%@ page import="com.google.gson.*" %>
<%@ page import="org.json.simple.*"%>
<%@ page import="java.util.*" %>
<%  

	String id = (String)session.getAttribute("id");
	Gson gson = new GsonBuilder().create();
	JSONObject root = (JSONObject)request.getAttribute("root");
	List<ProductVO> list = gson.fromJson(root.toString(), List.class);
	int qty = Integer.parseInt(request.getParameter("qty"));
	int totalPrice = 0;

	
%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Template</title>
    <link rel="stylesheet" href="../css/style.css">
    <link rel="stylesheet" href="../css/payments.css">
    <link href="https://fonts.googleapis.com/css2?family=Nanum+Gothic&display=swap" rel="stylesheet">
    <script src="https://kit.fontawesome.com/a99df0f94f.js" crossorigin="anonymous"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
</head>
<body>
    <!-- 컨테이너의 시작 -->
    <div class="container">
        <!-- 헤더의 시작 -->
        <jsp:include page="/header.jsp">
        	<jsp:param value="true" name="isHide"/>
        </jsp:include>
               
        <!-- 검색 필터 -->
            
        <!-- 본문의 시작 -->
        <section>
            <!-- 본문이 들어가는 래퍼 -->
            <div class="contents-wrapper">
                <div class="contents-page-upper"> <!-- 결제 페이지의 상단 부분. 페이지 설명, 결제 과정 표시 -->
                    <div class="page-info"><h1>주문/결제</h1></div>
                    <div class="progress-step1"><h2>01</h2><p>장바구니</p></div>
                    <div class="progress-step2 active"><h2>02</h2><p>주문/결제</p></div>
                    <div class="progress-step3"><h2>03</h2><p>주문완료</p></div>
                </div>
                <div class="contents-page-middle"> <!-- 주요 약관 및 구매 상품 정보 영역 -->
                    <p>비회원 주문 정보수집 동의</p>
                    <div class="payments-terms">
                        <pre>
    1. 개인정보의 수집 및 이용 목적

    1) 주문자 정보 확보
    2) 물품에 관한 배송지 정보 확보
    3) 불만처리 의사소통 경로 확보
    4) 주문상품에 대한 결제 및 취소
    
    2. 수집하는 개인정보의 항목
    1) 주문자의 이름, 주소, 연락처, 이메일 (단, 주문자와 주문물품의 수취인이 다른 경우에는 수취인의 이름, 주소, 연락처)
    2) 대금 결제에 관한 정보 (청약 철회 시 현금 환불 계좌 정보 등)

    * 서비스이용과정이나 사업처리 과정에서 아래와 같은 정보들이 생성되어 수집될 수 있습니다.    
    - 서비스이용기록, 접속로그, 쿠키, 접속IP정보, 결제기록, 이용정지기록
    
                            
    3. 개인정보의 보유 및 이용 기간
                           
    * 원칙적으로 개인정보의 수집목적 또는 제공받은 목적이 달성되면 지체 없이 파기합니다.                            
    다만, 전자상거래 등에서의 소비자 보호에 관한 법률 등 관계법률에 의해 보존할 필요가 있는 경우에는                            
    일정기간 보존합니다. 이 경우 보관하는 정보를 그 보관 목적으로만 이용하며, 보존기간은 아래와 같습니다.

                            
    1)계약 또는 청약철회 등에 관한 기록                            
    • 보존 이유: 전자상거래 등에서의 소비자 보호에 관한 법률                            
    • 보존 기간: 5년
                           
    2)대금결제 및 재화 등의 공급에 관한 기록
    • 보존 이유: 전자상거래 등에서의 소비자 보호에 관한 법률
    • 보존 기간: 5년
                            
    3)소비자의 불만 또는 분쟁처리에 관한 기록                           
    • 보존 이유: 전자상거래 등에서의 소비자 보호에 관한 법률                           
    • 보존 기간: 3년
                           
    비회원 주문 시 제공하신 모든 정보는 상기 목적에 필요한 용도 이외로는 사용되지 않습니다.   
    보다 자세한 내용은 "개인정보취급방침"을 확인하여 주시기 바랍니다.


    4.동의를 거부할 권리 및 거부에 따른 불이익

    개인 정보 수집, 이용 목적에 관한 사항에 대해 동의를 거부할 수 있으나, 
    거부하실 경우 저희 몰에서 비회원 구매를 하실 수 없습니다.
                        </pre>
                        <input type="checkbox" class="ok-or-not" required> <p>(필수) <span>정보수집 약관에 동의 합니다.</span></p>
                    </div>
                    <div class="hr-bdline"></div> <!-- 경계선 -->
                    <div class="payments-item-info"> <!-- 구매 상품의 표시  wrapper 영역 -->
                        <p>주문하실 상품</p>
                        <button class="cart-go" type="button" onclick="location.href='cart.jsp'">장바구니 가기</button>
                        <div class="item"> <!-- 상품 정보 표시 wrapper 영역 -->
                            <table class="item-table"> <!-- 상품 정보를 table 형식으로 불러오는 영역 -->
                                <thead>
                                    <tr>
                                        <th scope="col" class="item-img-zone">상품이미지</th>
                                        <th scope="col" class="item-name-zone">상품명/옵션</th>
                                        <th scope="col" class="item-num-zone">수량</th>
                                        <th scope="col" class="item-price-zone">가격</th>
                                        <th scope="col" class="item-discount-zone">할인</th>
                                        <th scope="col" class="result-cost-zone">주문금액</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <% 
                                        if(list != null) {
                                            for(int i = 0; i < list.size(); i++) {
                                                
                                                String title = list.get(i).getTitle();
                                                String img = list.get(i).getContenturl();
                                                String uri = list.get(i).getLink();
                                                int price = Integer.parseInt(list.get(i).getPrice());
                                                int discnt = 0;
                                                int rltprice = price - discnt;
                                                totalPrice += rltprice;
                                    %>
                                    <tr>
                                        <td><img src="<%=img%>" class="item-img"></td> <!-- 구매할 상품 이미지 -->
                                        <td><a href="#" class="item-name"><%=title%></a></td> <!-- 구매할 상품 이름 -->
                                        <td class="item-num"><p><%=qty%></p></td> <!-- 구매 갯수 -->
                                        <td class="item-price"><p><%=price%> 원</p></td> <!-- 상품 금액 -->
                                        <td class="item-discount"><p>DIS 원</p></td> <!-- 할인 금액 -->
                                        <td class="result-cost"><p> <%=totalPrice%> 원</p></td> <!-- 최종 가격 -->
                                    </tr>
                                <%
                            		}
                            	}                            	
                                %>
                                </tbody>
                            </table>
                        </div> <!-- item 클래스 종료 -->
                    </div>    <!-- 구매 상품 표시 wrapper인 payments-item-info 클래스 종료 -->
                </div>      <!-- 주요 약관 및 구매 상품 정보 영역인 contents-page-middle 클래스 종료 -->
               <div class="contents-page-footer"> <!-- 최종 결제 정보와 그에 관한 정보를 담은 wrapper 클래스 --> 
                    <section class="page-footer-left">
                        <p>배송정보</p>
                        <div> <!-- 배송에 필요한 고객 정보를 기입받는 영역 -->
                            <table class="logistics-info"> 
                                <tr>
                                    <td>* 주문하시는 분</td>
                                    <td>
                                        <label>이름</label><input type="text">
                                        <label>이메일</label><input type="email">
                                        <label>휴대폰 번호</label><input type="tel">
                                    </td>
                                </tr>
                                <tr>
                                    <td>* 받으시는 분</td>
                                    <td>
                                        <label>이름</label><input type="text"><input type="checkbox" class="chk"><p class="right">주문하시는 분과 동일</p>
                                        <label>전화번호(선택)</label>
                                        <select name='phone-head'>
                                            <option value="" selected>선택</option>
                                            <option value="02">02</option>
                                            <option value="032">032</option>
                                            <option value="033">033</option>
                                            <option value="041">041</option>
                                            <option value="042">042</option>
                                        </select><input type="tel">
                                        <label>휴대폰 번호</label>
                                        <select name='phone-head'>
                                            <option value="" selected>선택</option>
                                            <option value="010">010</option>
                                            <option value="011">011</option>
                                            <option value="016">016</option>
                                            <option value="017">017</option>
                                        </select><input type="tel">
                                        <label>받으실 주소</label><input type="text"><button>주소 찾기</button>
                                    </td>
                                </tr>
                                <tr>
                                    <td>배송메세지</td>
                                    <td>
                                        <select name='ext-order'>
                                            <option value="" selected>요구사항</option>
                                            <option value="call">배송 전 연락주세요</option>
                                            <option value="door">부재시 문 앞에 놓아주세요</option>
                                            <option value="secureguard">부재시 경비실에 맡겨주세요</option>
                                            <option value="becareful">파손에 주의해주세요</option>
                                            <option value="self-input">직접 입력</option>
                                        </select>
                                        <div class="self-input"><input value="직접 입력" type="text"></div>
                                    </td>
                                </tr>
                                <tr>
                                    <td>총 배송비</td><td><p>n</p>원</td>
                                </tr>
                            </table> 
                        </div> <!-- 기입 영역 종료 -->
                        <div class="payment-select">
                            <p>결제 수단</p>
                            <div class="payment-select-right">
                                <span>TEXT</span>
                                <input type="checkbox"><p>지금 선택한 결제수단을 다음에도 사용</p>
                            </div>
                            <div class="payment-btn-zone"> <!-- 결제에 이용할 수단을 button을 이용해 정합니다 -->
                                <button type="radio" value="creditcard">신용카드</button>
                                <button type="radio" value="ontime-sand">실시간 계좌이체</button>
                                <button type="radio" value="non-account">무통장 입금</button>
                                <button type="radio" value="mobile-pay">휴대폰 결제</button>
                                <button type="radio" value="toss">toss(img)</button>
                                <button type="radio" value="naver-pay">naver pay(img)</button>
                                <button type="radio" value="payco">payco(img?)</button>
                                <button type="radio" value="kakao-pay">kakao pay(img)</button>
                            </div>

                            <div class="creditcard-info">
                                <p>무이자할부 안내</p>
                                <table>
                                    <tr>
                                        <td>카드사</td>
                                        <td>적용금액</td>
                                        <td>할부개월</td>
                                        <td>기간</td>
                                    </tr>
                                    <tr>
                                        <td><img src="../images/card_bn_bc.jpg"></td>
                                        <td>5만원 이상</td>
                                        <td>2,3,4,5,6개월</td>
                                        <td>2020.11.01
                                            ~2020.11.30(총 30일)
                                        </td>
                                    </tr>
                                    <tr>
                                        <td><img src="../images/card_bn_kb.jpg"></td>
                                        <td>5만원 이상</td>
                                        <td>2,3,4,5,6개월</td>
                                        <td>2020.11.01
                                            ~2020.11.30(총 30일)</td>
                                    </tr>
                                    <tr>
                                        <td><img src="../images/card_bn_hn.jpg"></td>
                                        <td>5만원 이상</td>
                                        <td>2,3,4,5,6개월</td>
                                        <td>2020.11.01
                                            ~2020.11.30(총 30일)</td>
                                    </tr>
                                    <tr>
                                        <td><img src="../images/card_bn_sam.jpg"></td>
                                        <td>5만원 이상</td>
                                        <td>2,3,4,5,6개월</td>
                                        <td>2020.11.01
                                            ~2020.11.30(총 30일)</td>
                                    </tr>
                                    <tr>
                                        <td><img src="../images/card_bn_sh.jpg"></td>
                                        <td>5만원 이상</td>
                                        <td>2,3,4,5,6개월</td>
                                        <td>2020.11.01
                                            ~2020.11.30(총 30일)</td>
                                    </tr>
                                </table>
                            </div>
                        </div>
                    </section>
                    <aside class="page-footer-right">
                        <div class="cost-info-zone">
                            <p>최종결제정보</p>
                            <div>
                                <table>
                                    <tr>
                                        <td>
                                            <p>상품금액</p>
                                        </td>
                                        <td>
                                            <p>Pr,ice원</p>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td>
                                            <p>쿠폰/할인금액</p>
                                        </td>
                                        <td>
                                            <p>0원</p>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td>
                                            <p>포인트/예치금 사용</p>
                                        </td>
                                        <td>
                                            <p>0원</p>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td>
                                            <p>배송비</p>
                                        </td>
                                        <td>
                                            <p>0원</p>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td>
                                            <p>총 결제금액</p>
                                        </td>
                                        <td>
                                            <span>Pri,ce원</span>
                                        </td>
                                    </tr>
                                </table>
                            </div>
                        </div>
                        <button>구매하기</button>
                        <div></div>
                        <div></div>
                    </aside>
                </div>
            </div>      <!-- contents-wrapper 클래스 종료 -->
        </section>
    </div>
    <!-- 라이트 박스-->
    <div id="light-box-container">
    </div>
    <jsp:include page="/pages/login.jsp"></jsp:include> 
    <!-- 스크립트 --> 
    <script type="module" src="../js/MorePage.js"></script>
</body>
</html>
