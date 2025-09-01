<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>views/book/updateform.jsp</title>
</head>
<body>
<h1>도서정보 수정폼</h1>
	<form action="${pageContext.request.contextPath}/book/update" method="post">
		<input type="hidden" name="num" value="${dto.num }" />
		<div>
			<label for="title">제목</label>
			<input type="text" name="title" id="title" value="${dto.title }" />
		</div>
		<div>
			<label for="author">작가</label>
			<input type="text" name="author" id="author" value="${dto.author }" />
		</div>
		<div>
			<label for="publisher">출판사</label>
			<input type="text" name="publisher" id="publisher" value="${dto.publisher }" />
		</div>
		<button type="submit" class="submit">수정 확인</button>
		<button type="reset" class="reset">취소</button>
	</form>
</body>
</html>