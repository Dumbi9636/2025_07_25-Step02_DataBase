<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>views/book/delete.jsp</title>
</head>
<body>
<script>
	 	alert("${param.num} 번 ${param.title}의 정보를 삭제했습니다");	 
	 	location.href="${pageContext.request.contextPath}/book/list";
</script>
</body>
</html>