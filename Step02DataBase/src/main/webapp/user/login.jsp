<%@page import="java.net.URLEncoder"%>
<%@page import="org.mindrot.jbcrypt.BCrypt"%>
<%@page import="test.dao.UserDao"%>
<%@page import="test.dto.UserDto"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	//폼 전송되는 아이디와 비밀번호 추출하기
	String userName=request.getParameter("userName");
	String password=request.getParameter("password");
	// 로그인 후에 가야할 목적지 정보
	String url=request.getParameter("url");
	// 로그인 실패를 대비해서 목적지 정보를 인코딩한 결과도 준비한다
	String encodedUrl=URLEncoder.encode(url, "UTF-8");
	
	//아이디 비밀번호가 유효한 정보인지 여부 
	boolean isValid=false;
	//DB 에서 userName 을 이용해서 select 되는 정보가 있는지 select 해 본다.
	UserDto dto=UserDao.getInstance().getByUserName(userName);
	//만일 select 된 정보가 있다면(최소한 userName 은 존재한다는 것)
	if(dto != null){
		//raw 비밀번호와 DB 에 저장된 암호화된 비밀번호를 비교해서 일치 하는지 확인한다.
		// Bcrpt.checkpw(입력한 비밀번호, 암호화된 비밀번호)
		isValid = BCrypt.checkpw(password, dto.getPassword());
	}
	/*
		만일 입력한 아이디와 비밀번호가 유효한 정보라면 로그인 처리를 한다
		jsp 에서 기본 제공해주는 HttpSession 객체에 userName 을 저장한다
		HttpSession 객체에 저장하면 응답을 한 이후에 다음번 요청에서 HttpSession 객체에 저장된 정보를 읽어올수 있다.
		세션 객체에 담긴 정보는 어떤 요청도 하지 않고 일정 시간이 흐르면 자동 삭제된다.
		필요하다면(로그아웃이 요청되면) 강제로 세션 객체에 담긴 정보를 삭제할수도 있다.
		세션객체에 담긴 로그인 정보를 삭제하는것이 "로그아웃" 이고
		세션객체에 로그인 정보(userName)을 저장하는것이 "로그인" 이다.
	*/
	if(isValid){
		//HttpSession 객체에 "userName" 이라는 키값으로 userName 을 저장한다.
		session.setAttribute("userName", userName);
		//세션 유지시간 설정(초단위)
		
		// role 정보도 저장한다
		session.setAttribute("role", dto.getRole());
		
		session.setMaxInactiveInterval(60*60); //설정하지 않으면 기본30분
		// 세션 객체는 로그인할때마다 1대1로 관리해준다. 1000명이 로그인을 시도하면 세션이 1000개 만들어지는것 
	}
	
	// 체크박스를 체크한 상태로 로그인 버튼을 누르면 null 이 아니다 (체크하지 않으면 null)
	String isSave=request.getParameter("isSave");
	if(isSave != null){
		// 입력한 아이디 비밀번호를 쿠키로 응답하고 1주일 동안 유지되도록 한다 
		// 아이디 비밀번호를 쿠키로 응답하고 1주일 동안 유지되도록한다
		Cookie cook1=new Cookie("savedUserName", userName);
		Cookie cook2=new Cookie("savedPassword", password);
		//쿠키 유지시간 초단위로 설정
		cook1.setMaxAge(60*60*24*7);
		cook2.setMaxAge(60*60*24*7);
		
		// 쿠키 경로
		//cook1.setPath("/"); // 모든 경로에서 사용할 수 있는 쿠키
		//cook1.setPath("/user/"); // user 경로에서만 사용할 수 있는 쿠키 
		// 설정하지 않으면 현재 경로에서만 사용할 수 있음. 
		
		// HttpServletResponse 객체에 Cookie 객체를 담으면 응답할때 알아서 쿠키가 응답된다
		response.addCookie(cook1);
		response.addCookie(cook2);
	}else{// 체크박스를 체크하지 않았으면 쿠키를 삭제한다.
		// 특정 키값으로 저장된 쿠키값 삭제하기(value 에는 아무 값이나 넣어도 상관없다)
		Cookie cook1=new Cookie("savedUserName", "");
		Cookie cook2=new Cookie("savedPassword", null);
		//쿠키 유지시간을 0 로 설정해서 응답하면 쿠키가 삭제되는 효과를 낸다 
		cook1.setMaxAge(0);
		cook2.setMaxAge(0);
		response.addCookie(cook1);
		response.addCookie(cook2);
	}
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>/user/login.jsp</title>
</head>
<body>  
	<div class="container">
		<%if(isValid){ %>
			<p>
				<strong><%=userName %></strong> 회원님 로그인 되었습니다.
				<a href="<%=url %>">확인</a>
			</p>
		<%}else{ %>
			<p>
				아이디 혹은 비밀번호가 틀려요!
				<a href="loginform.jsp?url=<%=encodedUrl%>">다시 로그인</a>
			</p>
		<%} %>
	</div>
</body>
</html>