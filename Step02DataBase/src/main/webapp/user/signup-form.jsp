<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>/user/signup-form.jsp</title>
<jsp:include page="/WEB-INF/include/resource.jsp"></jsp:include>
</head>
<body>
	<jsp:include page="/WEB-INF/include/navbar.jsp">
		<jsp:param value="index" name="thisPage"/>
	</jsp:include>
	<br />
	<div class="container">
		<h1>회원가입 페이지</h1>
		<br />
		<form action="signup.jsp" method="post">
			<div>
				<label for="userName">아이디</label>
				<input type="text" name="userName" id="userName"/>
			</div>
			<br />
			<div>
				<label for="password">비밀번호</label>
				<input type="text" name="password" id="password"/>
			</div>
			<br />
			<div>
				<label for="email">이메일</label>
				<input type="email" name="email" id="email"/>
			</div>
			<br />
			<button type="submit">가입</button>
		</form>
	</div>
	<jsp:include page="/WEB-INF/include/footer.jsp"></jsp:include>
</body>
</html>







