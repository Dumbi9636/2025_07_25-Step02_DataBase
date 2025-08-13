<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>403 - 접근 거부</title>
<jsp:include page="/WEB-INF/include/resource.jsp"></jsp:include>
<style>
    .error-container {
        text-align: center;
        padding: 100px 20px;
    }

    .error-code {
        font-size: 100px;
        font-weight: bold;
        color: #d9534f;
    }

    .error-message {
        font-size: 24px;
        margin-bottom: 30px;
    }

    .btn-group {
        margin-top: 20px;
    }

    .btn-group a {
        margin: 0 10px;
    }
</style>
</head>
<body>
    <jsp:include page="/WEB-INF/include/navbar.jsp" />
    
    <div class="container error-container">
        <div class="error-code">403</div>
        <div class="error-message">접근이 거부되었습니다.<br>이 페이지를 볼 권한이 없습니다.</div>

        <div class="btn-group">
            <a href="<%=request.getContextPath()%>/" class="btn btn-primary">메인으로</a>
            <a href="javascript:history.back();" class="btn btn-secondary">이전 페이지</a>
        </div>
    </div>
</body>
</html>