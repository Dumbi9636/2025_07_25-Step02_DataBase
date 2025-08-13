<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>/test/fileform.jsp</title>
</head>
<body>
	<div class="container">
		<h1>파일 업로드 테스트</h1>
		<form action="${pageContext.request.contextPath}/test/fileup" method="post"
			enctype="multipart/form-data">
			<input type="text" name="caption" placeholder="설명입력..." />
			<br />
			<input type="file" name="myFile" />
			<br />
			<button class="sumbit">업로드</button>
		</form>
	</div>
</body>
</html>