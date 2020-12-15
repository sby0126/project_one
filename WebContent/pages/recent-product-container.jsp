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
<div class="recent-product-container">
    <strong>최근 본 상품</strong>
    <span><em>0</em> / 50</span>
    <div>
        <button id="btn-prev"></button>
        <button id="btn-next"></button>
    </div>
</div>
</c:when>
</c:choose>