<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>/book/insertform.jsp</title>
<jsp:include page="/WEB-INF/include/resource.jsp"></jsp:include>
</head>
<body>
	<jsp:include page="/WEB-INF/include/navbar.jsp">
		<jsp:param value="index" name="thisPage"/>
	</jsp:include>
	<div class="container">
		<h1>새로운 책 등록 양식</h1>
		<form action="insert.jsp" method="post">
			<div>
				<label for="title">제목</label>
				<input type="text" name="title" id="title"/>
			</div>
			<div>
				<label for="author">저자</label>
				<input type="text" name="author" id="author"/>
			</div>
			<div>
				<label for="publisher">출판사</label>
				<input type="text" name="publisher" id="publisher"/>
			</div>
			<button type="submit">추가</button>
		</form>
	</div>
	<jsp:include page="/WEB-INF/include/footer.jsp"></jsp:include>
</body>
</html>






