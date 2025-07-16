<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>/user/loginform.jsp</title>
<jsp:include page="/WEB-INF/include/resource.jsp"></jsp:include>
</head>
<body>
	<jsp:include page="/WEB-INF/include/navbar.jsp">
		<jsp:param value="index" name="thisPage"/>
	</jsp:include>
	
	<div class="container">
		<h1>로그인 페이지</h1>
		<form action="login.jsp" method="post">
			<div>
				<label for="userName">아이디</label>
				<input type="text" name="userName" id="userName"/>
			</div>
			<div>
				<label for="password">비밀번호</label>
				<input type="password" name="password" id="password"/> 	<!-- input type 을 password로 하면 화면에 보이지 않음.  -->
			</div>
			<button type="submit">로그인</button>
		</form>
	</div>
	<jsp:include page="/WEB-INF/include/footer.jsp"></jsp:include>
</body>
</html>