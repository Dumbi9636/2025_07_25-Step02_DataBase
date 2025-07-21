<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%-- /WEB-INF/include/navbar.jsp --%>
<%
	//navbar.jsp 페이지가 어떤 페이지에 include 되었는지 파라미터 읽어오기
	String thisPage=request.getParameter("thisPage");// "index" or "member" or "book"
	
	// 로그인된 userName 이 있는지 읽어와본다
	String userName=(String)session.getAttribute("userName");
%>
	<nav class="navbar navbar-expand-md bg-warning">
		<div class="container">
			<a class="navbar-brand fw-bolder" href="${pageContext.request.contextPath }/">Acorn</a>
			<button class="navbar-toggler" type="button"
				data-bs-toggle="collapse" data-bs-target="#navbarNav">
				<span class="navbar-toggler-icon"></span>
			</button>
			<div class="collapse navbar-collapse" id="navbarNav">
				<ul class="navbar-nav me-auto"> <!-- me-auto 는 ul 요소의 마진을 필요한 만큼 가지겠다는 의미 -->
					<li class="nav-item">
						<a class="nav-link fw-bolder" <%=thisPage.equals("member") ? "active":""%> href="${pageContext.request.contextPath }/member/list.jsp">Member</a>
					</li>
					<li class="nav-item">
						<a class="nav-link fw-bolder" <%=thisPage.equals("book") ? "active":""%> href="${pageContext.request.contextPath }/book/list.jsp">Book</a>
					</li>
					<li class="nav-item">
						<a class="nav-link fw-bolder" <%=thisPage.equals("board") ? "active":""%> href="${pageContext.request.contextPath }/board/list.jsp">Board</a>
					</li>
					<li class="nav-item">
						<a class="nav-link fw-bolder" <%=thisPage.equals("goods") ? "active":""%> href="${pageContext.request.contextPath }/goods/list.jsp">goods</a>
					</li>
				</ul>
				 <!-- 오른쪽 사용자 메뉴 -->
	            <ul class="navbar-nav">
                <%if (userName == null) {%>
	                <li class="nav-item">
	                    <a class="btn btn-secondary btn-sm me-2 fw-bolder "
	                       href="${pageContext.request.contextPath }/user/loginform.jsp">로그인</a>
	                </li>
	                <li class="nav-item">
	                    <a class="btn btn-dark btn-sm"
	                       href="${pageContext.request.contextPath }/user/signup-form.jsp">회원가입</a>
	                </li>
                <%}else {%>
	                <li class="nav-item  me-2">
					    <a class="nav-link  p-0"
					       href="${pageContext.request.contextPath}/user/info.jsp">
					        <strong><%= userName %></strong>
					    </a>
					</li>
	                <li class="nav-item me-2">
	                    <span class="navbar-text fw-bolder">Signed in</span>
	                </li>
	                <li class="nav-item">
	                    <a class="btn btn-secondary btn-sm"
	                       href="${pageContext.request.contextPath }/user/logout.jsp">로그아웃</a>
	                </li>
                <%}%>
                </ul>
			</div>
		</div>
	</nav>
	
	
	
	
	