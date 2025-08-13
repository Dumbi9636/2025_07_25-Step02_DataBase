<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>/WEB-INF/views/fortune.jsp</title>
</head>
<body>
	<div class="container">
		<p>
			오늘의 운세 : <strong>${fortune}</strong>
			<a href="${pageContext.request.contextPath}/">인덱스로</a>
		</p>
	</div>
</body>
</html>