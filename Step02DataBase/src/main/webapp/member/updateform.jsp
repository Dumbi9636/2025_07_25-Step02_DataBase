<%@page import="test.dao.MemberDao"%>
<%@page import="test.dto.MemberDto"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	//1. GET 방식 파라미터로 전달되는 수정할 회원의 번호를 읽어온다.
	int num=Integer.parseInt(request.getParameter("num"));
	//2. MemberDao 객체를 이용해서 수정할 회원의 정보를 얻어온다.
	MemberDto dto=new MemberDao().getByNum(num);
	//3. 수정할 회원의 정보를 수정 양식으로 응답한다 
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>/member/updateform.jsp</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.7/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-LN+7fdVzj6u52u30Kp6M/trliBMCMKTyK833zpbD+pXdCLuTusPj697FH4R/5mcr" crossorigin="anonymous">
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.13.1/font/bootstrap-icons.min.css">
</head>
<body>
	<div class="container">
		<h1>회원정보 수정 양식</h1>
		<form action="${pageContext.request.contextPath }/member/update.jsp" method="post">
			<div class="mb-2">
				<label class="form-label" for="num">번호</label>
				<input class="form-control" type="text" name="num" id="num" value="<%=dto.getNum() %>" readonly/>
			</div>
			<div class="mb-2">
				<label class="form-label" for="name">이름</label>
				<input class="form-control" type="text" name="name" id="name" value="<%=dto.getName() %>"/>
			</div>
			<div class="mb-2">
				<label class="form-label" for="addr">주소</label>
				<input class="form-control" type="text" name="addr" id="addr" value="<%=dto.getAddr() %>"/>
			</div>
			<button class="btn btn-success btn-sm" type="submit">
				수정
				<i class="bi bi-send"></i>
			</button>
			<button class="btn btn-warning btn-sm" type="reset">
				취소
				<i class="bi bi-arrow-clockwise"></i>
			</button>
		</form>
	</div>
</body>
</html>










