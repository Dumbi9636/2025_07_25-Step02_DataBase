<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>/jstl/hello.jsp</title>
</head>
<body>
	<h1>Java Standard Tag Library</h1>
	<c:forEach var="i" begin="0" end="4">
		<p>
			Hello JSTL <strong>${i }</strong>
		</p>
	</c:forEach>
	<hr />
	<%for(int i=0; i<4; i++){ %>
		<p>
			Hello JSTL <strong>${i }</strong>
		</p>
	<%} %>
</body>
</html>