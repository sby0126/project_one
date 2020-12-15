<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ page import="java.util.Arrays"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
	String requestURI = request.getRequestURI();
	boolean isOpen = false;
	
	if(requestURI == null) {
		isOpen = false;
	} else {
		isOpen = requestURI.indexOf("/pages/") == -1;	
	}
	
%>
<c:set var="isOpen" value="<%= isOpen %>"></c:set>
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
	
	function refresh(json) {
		if(!json) {
			return;
		}
		var contentData = json.contentData;		
		var content = contentData[currentCount - 1];
		
		if( !content ) {
			return;
		}
		
		var imageRoot = json["imageUrl"]
		var imageID = json["imageData"][content.url];
		var realImagePath = imageRoot + imageID;
		
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