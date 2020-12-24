<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.Arrays"%>
<%@ page import="java.util.*" %>
<%@ page import="service.RecentlyItemService, service.*" %>
<%@ page import="java.net.*" %>
<%@ page import="vo.*" %>
<%
	String requestURI = request.getRequestURI();
	boolean isOpen = false;
	
	if(requestURI == null) {
		isOpen = false;
	} else {
		isOpen = requestURI.indexOf("/pages/") == -1;	
	}
	
	// Create a cookie service.	
	CookieService cookieService = new CookieService();
	HashMap<String, String> cookie = cookieService.getKeyValue(request);
	String value = cookie.get("recentlyItems");
	
	String num = "";
	
	if(value != null) {
		num = URLDecoder.decode(value);
		// num = request.getParameter("recentlyShopItem");
	} else {
		value = "0";
	}
	
	Vector<ProductVO> list = null;
	
	if(!value.equals("0")) {
		RecentlyItemService service = new RecentlyItemService(num);
		list = service.getCards();
	}	
	
%>
<c:set var="isOpen" value="<%= isOpen %>"></c:set>
<c:set var="recentlyShopList" value="<%= list %>" />
<c:choose>
<c:when test="${isOpen == true}">
<link rel="stylesheet" href="/css/recent-product-container.css">
<c:url var="myUrl" value="/contents/searchShopItem.do">
	<c:param name="pageType" value="item"></c:param>
	<c:param name="id" value="28"></c:param>
</c:url>
<div class="recent-product-container">
	<pre>
	</pre>
    <strong>최근 본 상품</strong>
    <span><em id="current-item-count">0</em> / 50</span>
    <img src="https://dummyimage.com/64x64/000/fff">
    <div>
        <button id="btn-prev" onclick="prev()"></button>
        <button id="btn-next" onclick="next()"></button>
    </div>
</div>
<script>

(function() {
	
	var list = null;
	var currentCount = 1;
	var maxCount = 50;

	function next() {
		if(!list) return;
		currentCount = currentCount + 1;
		if(currentCount > maxCount) currentCount = maxCount;
		refresh(list);
	}
	
	function prev() {
		if(!list) return;
		currentCount = currentCount - 1;
		if(currentCount <= 0) currentCount = 0;
		refresh(list);
	} 
	
	window.next = next;
	window.prev = prev;
	
	function refresh(json) {
		if(!json) {
			return;
		}
		
		// 컨텐츠를 가져옵니다.
		var contentData = json.contentData;
		
		// 마지막 컨텐츠를 가져옵니다 
		var content = contentData[currentCount - 1];
		
		// 컨텐츠가 없으면 빠져나갑니다.
		if( !content ) {
			return;
		}
		
		// 메인 URL 주소
		mainUrl = "https://raw.githubusercontent.com/biud436/project_one/main/WebContent";
		
		var gndr = "M";
		var shopType = "S";
		
		const realImagePath = mainUrl + "/images/item/" 
				+ gndr + "/"
					+ shopType + "/"
					+ content.url;
		
		const img = $(".recent-product-container img")[0];
		img.src = realImagePath;
		img.width = 64;
		img.height = 64;
		
		$("#current-item-count").text(currentCount);
	}

	$.ajax({
		url: "${myUrl}",
		method: "GET",
		success: function(data) {
			list = data;
			if(data.contentData) {
				maxCount = data.contentData.length;	
			}
			refresh(list);
		}, 
		error: function(err) {
			console.warn(err);
		}
	});
	
})();


</script>
</c:when>
</c:choose>