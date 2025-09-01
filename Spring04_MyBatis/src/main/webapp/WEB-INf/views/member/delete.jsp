<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>views/member/delete.jsp</title>
</head>
<body>
	<%--
	 	파라미터로 전달되었던 값을 필요하다면 EL 로 추출해서 응답할때 활용할 수도 있다 
		${param.파라미터명}		
	 --%>
	 <script>
	 	alert("${param.num} 번 ${param.name}님의 정보를 삭제했습니다");	 
	 	location.href="${pageContext.request.contextPath}/member/list";
	 </script>
</body>
</html>