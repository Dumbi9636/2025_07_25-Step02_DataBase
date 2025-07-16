<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	// 세션에 "userName" 이라는 키값으로 저장된 값이 있는지 읽어와 본다
	String userName=(String)session.getAttribute("userName");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>/index.jsp</title>
<jsp:include page="/WEB-INF/include/resource.jsp"></jsp:include>
</head>
<body>
	<jsp:include page="/WEB-INF/include/navbar.jsp">
		<jsp:param value="index" name="thisPage"/>
	</jsp:include>
	<div class="container">
		<%if(userName != null){ %>
			<strong><%=userName %></strong> 님 로그인 중...
		<%} %>
		<br />
		<br />
		<h1 class="text-center">Acorn Academy</h1>
		<ul class="nav nav-pills justify-content-center my-3">
			<li class="nav-item"><a class="nav-link" href="${pageContext.request.contextPath }/member/list.jsp">회원목록</a></li>
			<li class="nav-item"><a class="nav-link" href="${pageContext.request.contextPath }/book/list.jsp">책목록</a></li>
		</ul>
		<div id="carouselExampleIndicators" class="carousel slide">
		  <div class="carousel-indicators">
		    <button type="button" data-bs-target="#carouselExampleIndicators" data-bs-slide-to="0" class="active" aria-current="true" aria-label="Slide 1"></button>
		    <button type="button" data-bs-target="#carouselExampleIndicators" data-bs-slide-to="1" aria-label="Slide 2"></button>
		    <button type="button" data-bs-target="#carouselExampleIndicators" data-bs-slide-to="2" aria-label="Slide 3"></button>
		  </div>
		  <div class="carousel-inner">
		    <div class="carousel-item active">
		      <img src="images/top01.jpg" class="d-block w-100 border border-3 border-warning rounded" alt="...">
		    </div>
		    <div class="carousel-item">
		      <img src="images/top02.jpg" class="d-block w-100 border border-3 border-warning rounded" alt="...">
		    </div>
		    <div class="carousel-item">
		      <img src="images/top03.jpg" class="d-block w-100 border border-3 border-warning rounded" alt="...">
		    </div>
		  </div>
		  <button class="carousel-control-prev" type="button" data-bs-target="#carouselExampleIndicators" data-bs-slide="prev">
		    <span class="carousel-control-prev-icon" aria-hidden="true"></span>
		    <span class="visually-hidden">Previous</span>
		  </button>
		  <button class="carousel-control-next" type="button" data-bs-target="#carouselExampleIndicators" data-bs-slide="next">
		    <span class="carousel-control-next-icon" aria-hidden="true"></span>
		    <span class="visually-hidden">Next</span>
		  </button>
		</div>		
	</div>
	<jsp:include page="/WEB-INF/include/footer.jsp"></jsp:include>
</body>
</html>





