<%@page import="test.dto.UserDto"%>
<%@page import="test.dao.UserDao"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	// form 의 value 값에 넣을 정보를 DB 에서 가져온다. 
	String userName=(String)session.getAttribute("userName");
	UserDto dto=new UserDao().getByUserName(userName);
	
	
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>/user/edit.jsp</title>
</head>
<body>
	<div class="container">
		<h1>가입정보 수정 양식</h1>
		<!-- 파일 업로드 처리는 서블릿에서 수행해야함. 그래서 update.jsp가 아닌 update -->
		<!-- 
			input type = "file"이 있는 form 전송 방식은 다르다
			따라서 enctype="multipart/form-data" 속성을 form 에 추가해준다.
			서버에서 해당 요청을 처리하는 방법도 다르기 때문에 jsp 가 아닌 서블릿에서 처리를 해야한다.
		 -->
		<form action="${pageContext.request.contextPath}/user/update" method ="post" 
			enctype="multipart/form-data">
			<div>
				<label for="userName">아이디</label>
				<input type="text" name="userName" value="<%=dto.getUserName() %>" readonly />
			</div>
			<br />
			<div>
				<label for="email">이메일</label>
				<input type="email" name="email" value="<%=dto.getEmail() %>" />
			</div>
			<br />
			<div>
				<label>프로필사진</label>
				<div>
				<%if(dto.getProfileImage() == null){ %>
				
				<%}else{ %>
				
				<%} %>
				</div>
				<br />
				<br />
				<input type="file" name="profileImage" accept="image/*"/>
			</div>
			<br />
			<button type="submit">수정확인</button>
			<button type="reset">취소</button>
		</form>
	</div>
</body>
</html>