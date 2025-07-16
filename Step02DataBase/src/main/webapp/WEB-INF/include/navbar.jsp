<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%-- /WEB-INF/include/navbar.jsp --%>
<%
	//navbar.jsp 페이지가 어떤 페이지에 include 되었는지 파라미터 읽어오기
	String thisPage=request.getParameter("thisPage");// "index" or "member" or "book"
%>
	<nav class="navbar navbar-expand-md bg-warning">
		<div class="container">
			<a class="navbar-brand fw-bolder" href="${pageContext.request.contextPath }/">Acorn</a>
			<button class="navbar-toggler" type="button"
				data-bs-toggle="collapse" data-bs-target="#navbarNav">
				<span class="navbar-toggler-icon"></span>
			</button>
			<div class="collapse navbar-collapse" id="navbarNav">
				<ul class="navbar-nav">
					
					<li class="nav-item">
						<a class="nav-link fw-semibold" <%=thisPage.equals("member") ? "active":""%> href="${pageContext.request.contextPath }/member/list.jsp">Member</a>
					</li>
					<li class="nav-item">
						<a class="nav-link fw-semibold" <%=thisPage.equals("book") ? "active":""%> href="${pageContext.request.contextPath }/book/list.jsp">Book</a>
					</li>
					<li class="nav-item">
						<a class="nav-link fw-semibold" href="${pageContext.request.contextPath }/user/signup-form.jsp">회원가입</a></li>
					<li class="nav-item">
						<a class="nav-link fw-semibold" href="${pageContext.request.contextPath }/user/loginform.jsp">로그인</a></li>
					<li class="nav-item">
						<a class="nav-link fw-semibold" href="${pageContext.request.contextPath }/user/logout.jsp">로그아웃</a></li>
				</ul>
			</div>
		</div>
	</nav>
	
	
	
	
	