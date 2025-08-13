<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%-- /WEB-INF/include/navbar.jsp --%>
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
						<a class="nav-link fw-bolder ${param.thisPage eq 'member' ? 'active':''}" href="${pageContext.request.contextPath }/member/list.jsp">Member</a>
					</li>
					<li class="nav-item">
						<a class="nav-link fw-bolder ${param.thisPage eq 'book' ? 'active':''}" href="${pageContext.request.contextPath }/book/list.jsp">Book</a>
					</li>
					<li class="nav-item">
						<a class="nav-link fw-bolder ${param.thisPage eq 'board' ? 'active':''}" href="${pageContext.request.contextPath }/board/list.jsp">Board</a>
					</li>
					<li class="nav-item">
						<a class="nav-link fw-bolder ${param.thisPage eq 'gallery' ? 'active':''}" href="${pageContext.request.contextPath }/gallery/list.jsp">gallery</a>
					</li>
				</ul>
				 <!-- 오른쪽 사용자 메뉴 -->
	            <ul class="navbar-nav">
	            <c:choose> 		<!-- sessionScope. 은 생략 가능 --> 
	            	<c:when test="${sessionScope.userName eq null }">
	            	<li class="nav-item">
	                	<a class="btn btn-secondary btn-sm me-2 fw-bolder "
	                       href="${pageContext.request.contextPath }/user/loginform.jsp">로그인</a>
	              	</li>
	                <li class="nav-item">
	                    <a class="btn btn-dark btn-sm"
	                       href="${pageContext.request.contextPath }/user/signup-form.jsp">회원가입</a>
	                </li>
	            	</c:when>
	            	<c:otherwise>
	            		<li class="nav-item  me-2">
						    <a class="nav-link  p-0"
						       href="${pageContext.request.contextPath}/user/info.jsp">
						        <strong>${userName }</strong>
						    </a>
						</li>
		                <li class="nav-item me-2">
		                    <span class="navbar-text fw-bolder">Signed in</span>
		                </li>
		                <li class="nav-item">
		                    <a class="btn btn-secondary btn-sm"
		                       href="${pageContext.request.contextPath }/user/logout.jsp">로그아웃</a>
		                </li>
	            	</c:otherwise>
	            </c:choose>  
                </ul>
			</div>
		</div>
	</nav>
	
	
	
	
	