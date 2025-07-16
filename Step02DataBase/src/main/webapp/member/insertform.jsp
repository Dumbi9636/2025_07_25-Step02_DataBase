<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>/member/insertform.jsp</title>
<jsp:include page="/WEB-INF/include/resource.jsp"></jsp:include>
</head>
<body>
	<jsp:include page="/WEB-INF/include/navbar.jsp">
		<jsp:param value="index" name="thisPage"/>
	</jsp:include>
	<div class="container">
		<h1>회원 정보 추가 양식</h1>
		<form action="${pageContext.request.contextPath }/member/insert.jsp" method="post">
			<div class="mb-2">
				<label class="form-label" for="name">이름</label>
				<input class="form-control" type="text" name="name" id="name"/>
				<div class="form-text">10 글자 이내로 입력해 주세요</div>
			</div>
			<div class="mb-2">
				<label class="form-label" for="addr">주소</label>
				<input class="form-control"  type="text" name="addr" id="addr"/>
				<div class="form-text">실제 거주하는 주소를 입력해 주세요</div>
			</div>
			<button class="btn btn-primary btn-sm" type="submit">
				저장
				<svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-floppy2-fill" viewBox="0 0 16 16">
				  <path d="M12 2h-2v3h2z"/>
				  <path d="M1.5 0A1.5 1.5 0 0 0 0 1.5v13A1.5 1.5 0 0 0 1.5 16h13a1.5 1.5 0 0 0 1.5-1.5V2.914a1.5 1.5 0 0 0-.44-1.06L14.147.439A1.5 1.5 0 0 0 13.086 0zM4 6a1 1 0 0 1-1-1V1h10v4a1 1 0 0 1-1 1zM3 9h10a1 1 0 0 1 1 1v5H2v-5a1 1 0 0 1 1-1"/>
				</svg> 
			</button>
		</form>
	</div>
	<jsp:include page="/WEB-INF/include/footer.jsp"></jsp:include>
</body>
</html>








