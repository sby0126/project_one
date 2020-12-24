<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="vo.*, service.*, java.util.*,  javax.servlet.ServletRequest.*" %>
<%
	// 관심 상품 서비스 획득 
	InterestService service = new InterestService();

	// ID 값 획득
	String id = (String)session.getAttribute("id");
	List<InterestVO> list = new ArrayList<InterestVO>();
	
	if(id == null) {
		id = "";
	}
	
// 	if(session.getAttribute("id") == null) {
// 		response.sendRedirect("/");
// 		return;
// 	}
	
	String myList = "";
	
	// 목록 획득
	list = service.getInterest(id);
	
	if(!list.isEmpty()) {
		for(int i = 0; i <list.size(); i++) {
			System.out.println(list.get(i).getProductId());
			myList += list.get(i).getProductId();
			if(i != list.size() - 1) {
				myList += ",";
			}
		}
		System.out.println(myList);
	}	

	
%>
<c:set var="list" value="<%= list %>" />
<c:if test="${ list == null}">
	<script>
		$(".header-right-login-button").trigger("click");
	</script>
</c:if>
<jsp:include page="/pages/recently_viewed_item.jsp">
	<jsp:param value="<%= myList %>" name="myList"/>
</jsp:include>
