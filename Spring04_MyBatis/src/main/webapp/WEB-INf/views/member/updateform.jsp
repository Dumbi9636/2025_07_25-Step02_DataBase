<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>/member/updateform.jsp</title>
</head>
<body>
	<h1>회원정보 수정폼</h1>
	<form action="${pageContext.request.contextPath}/update" method="post">
		<input type="hidden" name="num" value="${dto.num }" />
		<div>
			<label for="name">이름</label>
			<input type="text" name="name" id="name" value="${dto.name }" />
		</div>
		<div>
			<label for="addr">주소</label>
			<input type="text" name="addr" id="addr" value="${dto.addr }" />
		</div>
		<button class="submit">수정 확인</button>
		<button class="reset">취소</button>
	</form>
	
</body>
</html>