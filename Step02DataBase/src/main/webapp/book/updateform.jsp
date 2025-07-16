<%@page import="test.dao.BookDao"%>
<%@page import="test.dto.BookDto"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	//수정할 책의 번호
	int num=Integer.parseInt(request.getParameter("num"));
	//수정할 책의 정보를 DB 에서 불러온다.
	BookDto dto=new BookDao().getByNum(num);
	//책 수정 양식을 응답한다.
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>/book/updateform.jsp</title>
</head>
<body>
	<div class="container">
		<h1>책정보 수정 양식</h1>
		<form action="update.jsp" method="post">
			<div>
				<label for="num">책번호</label>
				<input type="text" name="num" id="num" value="<%=dto.getNum() %>" readonly/>
			</div>
			<div>
				<label for="title">제목</label>
				<input type="text" name="title" id="title" value="<%=dto.getTitle() %>"/>
			</div>
			<div>
				<label for="author">저자</label>
				<input type="text" name="author" id="author" value="<%=dto.getAuthor() %>"/>
			</div>
			<div>
				<label for="publisher">출판사</label>
				<input type="text" name="publisher" id="publisher" value="<%=dto.getPublisher() %>"/>
			</div>
			<button type="submit">수정 확인</button>
			<button type="reset">취소</button>
		</form>
	</div>
</body>
</html>









