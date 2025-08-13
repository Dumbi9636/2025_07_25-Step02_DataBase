<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>/webapp/WEB-INF/views/home.jsp</title>
</head>
<body>
	<div class="container">
		<h1>인덱스 페이지 입니다</h1>
		<%--
			아래의 링크를 눌렀을때 
			1. FortuneController 의 특정 메소드가 동작하고 
			2. 오늘의 운세를 HttpServletRequest 객체에 담고 
			3. /WEB-INF/view/fortune.jsp 페이지에서 오늘의 운세를 클라이언테에게 응답하도록 해보세요.
		 --%>
		<ul>
			<li><a href="${pageContext.request.contextPath }/fortune">오늘의 운세</a></li>
		</ul>
		<h2>공지사항</h2>
		<ul>
			<c:forEach var="tmp" items="${requestScope.notice }">
				<li>${tmp }</li>
			</c:forEach>
			
		</ul>
	</div>
</body>
</html>