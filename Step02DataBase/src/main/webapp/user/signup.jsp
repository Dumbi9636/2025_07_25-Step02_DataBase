<%@page import="test.dao.UserDao"%>
<%@page import="test.dto.UserDto"%>
<%@page import="org.mindrot.jbcrypt.BCrypt"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	//폼 전송되는 정보를 추출한다.
	String userName=request.getParameter("userName");
	String password=request.getParameter("password");
	String email=request.getParameter("email");
	
	//사용자가 입력한 비밀 번호를 암호화한 비밀번호를 얻어낸다.
	String hashed=BCrypt.hashpw(password, BCrypt.gensalt());
	System.out.println("암호화된 비밀번호:"+hashed);
	
	//dto 에 담아서 
	UserDto dto=new UserDto();
	dto.setUserName(userName);
	dto.setPassword(hashed);
	dto.setEmail(email);
	//dao 객체를 이용해서 DB 에 저장하고 
	boolean isSuccess=UserDao.getInstance().insert(dto);
	//응답한다
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>/user/signup.jsp</title>
</head>
<body>
	<div class="container">
		<%if(isSuccess){ %>
			<p>
				 <strong><%=userName %></strong> 님 회원가입 되었습니다.
				 <a href="loginform.jsp">로그인 하러가기</a>
			</p>
		<%}else{ %>
			<p>
				가입 실패!
				<a href="signup-form.jsp">다시 가입</a>
			</p>
		<%} %>
	</div>
</body>
</html>