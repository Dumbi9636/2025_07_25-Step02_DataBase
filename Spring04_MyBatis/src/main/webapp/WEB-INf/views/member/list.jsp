<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:include page="/WEB-INf/include/resource.jsp"></jsp:include>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>/views/member/list.jsp</title>
</head>
<body>
	<div class="container mt-4">
		<h1 class="mb-4">회원 목록</h1>
		<table class="table table-striped table-hover text-center align-middle">
			<thead class="table-dark">
				<tr>
					<th>번호</th>
					<th>이름</th>
					<th>주소</th>
					<th>수정</th>
					<th>삭제</th>
				</tr>
			</thead>
			<tbody>
				<%--
					"list" 라는 키값으로 담긴 데이터는 List<MemberDto> 이다
					따라서 tmp 는 MemberDto type 이다 
					tmp.getNum() 하면 번호를 얻어낼 수 있는데 EL 에서는 
					tmp.num 해도 자동으로 getter 메소드를 호출해준다.
				 --%>
				<c:forEach var="tmp" items="${list }">
					<tr>
						<td>${tmp.num }</td>
						<td>${tmp.name }</td>
						<td>${tmp.addr }</td>
						<td>
							<a href="${pageContext.request.contextPath}/member/updateform?num=${tmp.num}&name=${tmp.name}">수정</a>
						</td>
						<td><a href="${pageContext.request.contextPath}/member/delete?num=${tmp.num}&name=${tmp.name}" 
								onclick="return confirm('삭제하시겠습니까?');">삭제</a>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		<div class="mt-3 text-end">
			<a href="${pageContext.request.contextPath}/" class="btn btn-secondary">인덱스로 가기</a>
			<a href="${pageContext.request.contextPath}/member/insertform" class="btn btn-primary">회원 추가</a>
		</div>
	</div>
</body>
</html>