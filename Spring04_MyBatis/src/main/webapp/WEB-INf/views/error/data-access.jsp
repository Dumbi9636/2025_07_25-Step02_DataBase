<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>/views/error/data-access.jsp</title>
</head>
<body>
	<div class="container">
		<h1>${title }</h1>
		<p>${message }</p>
		<p>상태 코드: <strong>${status }</strong></p>
		<a href="${pageContext.request.contextPath}/">인덱스로 돌아가기</a>
	</div>
</body>
</html>