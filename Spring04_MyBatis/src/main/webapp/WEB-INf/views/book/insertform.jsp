<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>/views/book/insertform.jsp</title>
</head>
<body>
<h1>도서 추가 양식</h1>
	<div class="container">
		<form action="${pageContext.request.contextPath}/book/insert" method="post">
			<input type="hidden" name="num" value="${dto.num }" />
			<div>
				<label for="title">제목</label>
				<input type="text" name="title" id="title" />
			</div>
			<div>
				<label for="author">작가</label>
				<input type="text" name="author" id="author" />
			</div>
			<div>
				<label for="publisher">출판사</label>
				<input type="text" name="publisher" id="publisher" />
			</div>
			<button type="submit">추가</button>
			<button type="button" onclick="location.href='${pageContext.request.contextPath}/book/list'">취소</button>
		</form>
	</div>
</body>
</html>