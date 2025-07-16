<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	//여기는 java coding 영역 입니다.
	//서블릿의 service() 메소드 안쪽 영역이라고 이해하면 됩니다.
	String uri=request.getRemoteHost();
	//입력한 이름 추출 
	String name=request.getParameter("name");
	//입력한 메세지 추출
	String msg=request.getParameter("msg");
	//콘솔창에 출력해보기
	System.out.println(uri+":"+name+" : "+msg);
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>/send.jsp</title>
</head>
<body>
	<h1>jsp 페이지에서 응답합니다</h1>
	<p>보낸 사람의 이름 : <%=name %></p>
	<p>보낸 내용: <%=msg %></p>
</body>
</html>





