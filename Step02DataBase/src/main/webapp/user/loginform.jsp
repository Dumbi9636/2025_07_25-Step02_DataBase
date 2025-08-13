<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	// Get 방식 파라미터 url 이라는 이름으로 전달되는 값이 있는지 읽어와본다
	String url = request.getParameter("userName");
	// 만일 넘어오는 값이 없다면
	if(url == null){
		// 로그인 후에 인덱스 페이지로 갈 수 있도록 한다
		String cPath=request.getContextPath();
		url=cPath+"/index.jsp";
	}
	
	// 쿠키에 저장된 아이디와 비밀번호를 담을 변수
	String savedUserName="";
	String savedPassword="";
	
	//HttpServletRequest 객체의 메소드를 이용해서 전달된 쿠키 목록을 얻어낼 수 있다. 
	Cookie[] cooks=request.getCookies();
		// 반복문 돌면서 쿠키 객체를 하나씩 참조해서
		for(Cookie tmp:cooks){
			// 저장된 키값을 읽어온다
			String key=tmp.getName();
			// 만일 키값이 savedId 라면
			if(key.equals("savedUserName")){
				// 쿠키 value 값을 savedUserName 이라는 지역변수에 저장
				savedUserName=tmp.getValue();
			}
			if(key.equals("savedPassword")){
				savedPassword=tmp.getValue();
			}
		}
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>/user/loginform.jsp</title>
<jsp:include page="/WEB-INF/include/resource.jsp"></jsp:include>
<style>
	html,
	body {
	  height: 100%;
	}
	
	.form-signin {
	  max-width: 330px;
	  padding: 1rem;
	}
	
	.form-signin .form-floating:focus-within {
	  z-index: 2;
	}
	
	.form-signin input[type="email"] {
	  margin-bottom: -1px;
	  border-bottom-right-radius: 0;
	  border-bottom-left-radius: 0;
	}
	
	.form-signin input[type="password"] {
	  margin-bottom: 10px;
	  border-top-left-radius: 0;
	  border-top-right-radius: 0;
	}

</style>
</head>
<body class="d-flex flex-column min-vh-100 align-items-center py-4 bg-body-tertiary">
	<jsp:include page="/WEB-INF/include/navbar.jsp">
		<jsp:param value="index" name="thisPage"/>
	</jsp:include>
	<main class="form-signin w-100 m-auto flex-grow-1">
	  	<form action="login.jsp" method="post">
	  		<input type="hidden" name="url" value="<%=url %>"/>
		    <h1 class="h3 mb-3 fw-normal">로그인 양식</h1>
		    <div class="form-floating">
		      <input value="<%=savedUserName %>" type="text" class="form-control" id="userName" name="userName" placeholder="아이디 입력..." >
		      <label for="userName">아이디</label>
		    </div>
		    <div  class="form-floating">
		      <input value="<%=savedPassword %>" type="password" class="form-control" id="password" name="password" placeholder="비밀번호 입력..." >
		      <label for="password">비밀번호</label>
		    </div>
		
		    <div class="form-check text-start my-3">
		      <input class="form-check-input" type="checkbox" name="isSave" value="yes" id="flexCheckDefault" <%=savedUserName.equals("") ? "":"checked" %>>>
		      <label class="form-check-label" for="flexCheckDefault">
		        로그인 정보 저장
		      </label>
		    </div>
		    <button class="btn btn-warning w-100 py-2" type="submit">로그인</button>
		    <p class="mt-5 mb-3 text-body-secondary">&copy; 2017–2023</p>
	  	</form>
	</main>
	<jsp:include page="/WEB-INF/include/footer.jsp"></jsp:include>
</body>
</html>