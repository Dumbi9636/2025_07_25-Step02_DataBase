<%@page import="test.dto.BookDto"%>
<%@page import="test.dao.BookDao"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	//출력해줄 책 목록 얻어오기
	List<BookDto> list=new BookDao().selectAll();
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>/book/list.jsp</title>
<jsp:include page="/WEB-INF/include/resource.jsp"></jsp:include>
</head>
<body>
	<jsp:include page="/WEB-INF/include/navbar.jsp">
		<jsp:param value="book" name="thisPage"/>
	</jsp:include>
	
	<div class="container pt-3 pb-3">
		<a class="btn btn-outline-dark mt-2 mb-2" href="insertform.jsp">책 등록</a>
		<h1 class= "container pt-3 pb-3">책 목록</h1>
		<table class="table table-bordered border-dark">
			<thead class="table-dark">
				<tr>
					<th>번호</th>
					<th>제목</th>
					<th>저자</th>
					<th>출판사</th>
					<th>수정</th>
					<th>삭제</th>
				</tr>
			</thead>
			<tbody>
			<%for(BookDto tmp:list){ %>
				<tr>
					<td><%=tmp.getNum() %></td>
					<td><%=tmp.getTitle() %></td>
					<td><%=tmp.getAuthor() %></td>
					<td><%=tmp.getPublisher() %></td>
					<td>
						<a href="updateform.jsp?num=<%=tmp.getNum() %>">수정</a>
					</td>
					<td>
						<a href="delete.jsp?num=<%=tmp.getNum() %>">삭제</a>
					</td>
				</tr>
			<%} %>
			</tbody>
		</table>
	</div>
	<jsp:include page="/WEB-INF/include/footer.jsp"></jsp:include>
</body>
</html>






