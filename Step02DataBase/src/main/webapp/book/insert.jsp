<%@page import="test.dao.BookDao"%>
<%@page import="test.dto.BookDto"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	//1. 폼전송되는 책 정보를 추출
	String title=request.getParameter("title");
	String author=request.getParameter("author");
	String publisher=request.getParameter("publisher");
	//2. 책 정보를 dto 에 담고
	BookDto dto=new BookDto();
	dto.setTitle(title);
	dto.setAuthor(author);
	dto.setPublisher(publisher);
	//3. dao 객체를 이용해서 DB 에 저장
	boolean isSuccess=new BookDao().insert(dto);
	//4. 응답
%>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>/book/insert.jsp</title>
</head>
<body>
	<div class="container">
		<%if(isSuccess){ %>
			<p> 
				<strong><%=title %></strong> 책을 새로 등록했습니다.
				<a href="list.jsp">목록보기</a>
			</p>
		<%}else{ %>
			<p>
				책 등록 실패!
				<a href="insertform.jsp">다시 시도</a>
			</p>
		<%} %>
	</div>
</body>
</html>



