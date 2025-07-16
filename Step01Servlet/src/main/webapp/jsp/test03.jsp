<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%-- jsp 페이지가 해석할 내용이 없어도 페이지를 html 로 만들지 않고 모든 페이지를 jsp 페이지로 만드는것이 일반적이다 --%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>test03.jsp</title>
</head>
<body>
	<form action="save.jsp" method="post">
		<input type="text" name="inputNum" placeholder="숫자 입력..."/>
		<button type="submit">저장</button>
	</form>
</body>
</html>