<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>/member/insertform.jsp</title>
</head>
<body>
	<h1>회원 추가 양식</h1>
	<div class="container">
		<form action="${pageContext.request.contextPath}/member/insert" method="post">
			<input type="hidden" name="num" value="${dto.num }" />
			<div>
				<label for="name">이름</label>
				<input type="text" name="name" id="name" />
			</div>
			<div>
				<label for="addr">주소</label>
				<input type="text" name="addr" id="addr" />
			</div>
			<button type="submit">추가</button>
			<button type="button" onclick="location.href='${pageContext.request.contextPath}/member/list'">취소</button>
		</form>
	</div>
</body>
</html>