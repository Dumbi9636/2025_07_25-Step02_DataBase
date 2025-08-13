<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isErrorPage="true" %>
<%--
	errorPage="true" 속성을 추가하면 exception 객체를 사용할 수 있다.
	exception 객체에는 예외의 정보가 들어있다. 
 --%>
 <%
 	// exceptiom 객체에 .getMessage를 하면 에러 메세지를 얻어낼 수 있다.
 	// String errMsg=exception.getMessage();
 	
 %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>/error/500.jsp</title>
<jsp:include page="/WEB-INF/include/resource.jsp"></jsp:include>
</head>
<body>
    <div class="container text-center py-5">
        <h1 class="display-3 text-danger">500</h1>
        <h2 class="mb-3">서버 내부 오류가 발생했습니다</h2>
        <p class="text-muted mb-4">잠시 후 다시 시도해 주세요.</p>
        <% if (exception != null) { %>
            <div class="alert alert-warning text-start mx-auto" style="max-width: 600px;">
                <strong>오류 메시지:</strong> <%= exception.getMessage() %>
            </div>
        <% } %>
        <a href="<%= request.getContextPath() %>/" class="btn btn-primary btn-lg">메인 페이지로 이동</a>
    </div>
</body>
</html>

