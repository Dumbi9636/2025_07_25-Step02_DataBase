<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	// Get 방식 파라미터 url 이라는 이름으로 전달되는 값이 있는지 읽어와본다
	String url = request.getParameter("userName");
	// 만일 넘어오는 값이 없다면
	if(url == null){
		// 로그인 후에 인덱스 페이지로 갈 수 있도록 한다
		String cPath=request.getContextPath();
		url=cPath+"/index.jsp";
	}
%>
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
			<%-- 로그인 성공후에 이동할 url 정보를 추가로 form 전송되도록 한다 --%>
			<input type="hidden" name="url" value="<%=url %>" />
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