<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>/goods/list.jsp</title>
<style>
	ul.pagination{
		list-style: none;
		display: flex;
		gap: 10px;
		justify-content: center;
	}
</style>
<jsp:include page="/WEB-INF/include/resource.jsp"></jsp:include>
</head>
<body>
	<jsp:include page="/WEB-INF/include/navbar.jsp">
		<jsp:param value="index" name="thisPage"/>
	</jsp:include>
	
	<div class="container">
	<h1> 굿즈 상품 목록</h1>
		<ul class="pagination">
			<li><img style="max-height:300px;"src="${pageContext.request.contextPath}/images/bottle.png" alt="" />상품1</li>
			<li><img style="max-height:300px;"src="${pageContext.request.contextPath}/images/rabbit_1.png" alt="" />상품2</li>
			<li><img style="max-height:300px;"src="${pageContext.request.contextPath}/images/yellowbird.png" alt="" />상품3</li>
			<li><img style="max-height:300px;"src="${pageContext.request.contextPath}/images/t9dog2_trans.png" alt="" />상품4</li>
			<li><img style="max-height:300px;" src="${pageContext.request.contextPath}/images/dumbi.png" alt="" />상품5</li>
		</ul>
	</div>
	<jsp:include page="/WEB-INF/include/footer.jsp"></jsp:include>
</body>
</html>