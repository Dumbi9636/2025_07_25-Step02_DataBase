<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>/error/404.jsp</title>
<jsp:include page="/WEB-INF/include/resource.jsp"></jsp:include>
</head>
<body>
	 <div class="container text-center py-5">
        <h1 class="display-3 text-danger">404</h1>
        <h2 class="mb-3">페이지를 찾을 수 없습니다</h2>
        <p class="text-muted mb-4">요청하신 페이지가 존재하지 않거나 이동되었을 수 있습니다.</p>
        <a href="<%= request.getContextPath() %>/" class="btn btn-primary btn-lg">메인 페이지로 이동</a>
    </div>
</body>
</html>