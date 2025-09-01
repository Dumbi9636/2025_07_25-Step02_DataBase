<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:include page="/WEB-INf/include/resource.jsp"></jsp:include>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>views/book/list.jsp</title>
</head>
<body>
	<div class="container mt-4">
		<h1 class="mb-4">도서 목록</h1>
		<table class="table table-striped table-hover text-center align-middle">
			<thead class="table-dark">
				<tr>
					<th>번호</th>
					<th>제목</th>
					<th>작가</th>
					<th>출판사</th>
					<th>수정</th>
					<th>삭제</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="tmp" items="${list }">
					<tr>
						<td>${tmp.num }</td>
						<td>${tmp.title }</td>
						<td>${tmp.author }</td>
						<td>${tmp.publisher }</td>
						<td>
							<a href="${pageContext.request.contextPath}/book/updateform?num=${tmp.num}&name=${tmp.title}">수정</a>
						</td>
						<td><a href="${pageContext.request.contextPath}/book/delete?num=${tmp.num}&name=${tmp.title}" 
								onclick="return confirm('삭제하시겠습니까?');">삭제</a>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		<div class="mt-3 text-end">
			<a href="${pageContext.request.contextPath}/" class="btn btn-secondary">인덱스로 가기</a>
			<a href="${pageContext.request.contextPath}/book/insertform" class="btn btn-primary">도서 추가</a>
		</div>
	</div>
</body>
</html>