<%@page import="org.apache.commons.text.StringEscapeUtils"%>
<%@page import="test.dto.BoardDto"%>
<%@page import="test.dao.BoardDao"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	// get 방식 파라미터로 전달되는 글번호 얻어내기
	int num=Integer.parseInt(request.getParameter("num"));
	// DB 에서 해당글의 자세한 정보를 얻어낸다.
	BoardDto dto=BoardDao.getInstance().getByNum(num);
	// 로그인된 userName(null 일 가능성이 있음)
	String userName=(String)session.getAttribute("userName");
	// 만일 본인 글 자세히 보기가 아니면 조회수 1을 증가시킨다
	if(!dto.getWriter().equals(userName)){
		BoardDao.getInstance().addViewCount(num);
	}
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>/board/view.jsp</title>
<jsp:include page="/WEB-INF/include/resource.jsp"></jsp:include>
</head>
<body>
	<jsp:include page="/WEB-INF/include/navbar.jsp">
		<jsp:param value="index" name="thisPage"/>
	</jsp:include>
	
	<div class="container pt-3 pb-3">
		<nav aria-label="breadcrumb">
		  <ol class="breadcrumb">
		    <li class="breadcrumb-item"><a href="${pageContext.request.contextPath}/">Home</a>
		    </li>
		    <li class="breadcrumb-item"><a href="${pageContext.request.contextPath}/board/list.jsp">Board</a>
		    </li>
		    <li class="breadcrumb-item active">Detail</li>
		  </ol>
		</nav>
		<h1>게시글 상세보기</h1>
		
		<table class="talbe table-bordered">
			<colgroup>
				<col class="col-2" />
				<col class="col" />
			</colgroup>
				<tr>
					<th >글번호</th>
					<td><%=num %></td>
				</tr>
				<tr>
					<th>작성자</th>
					<td>
						<%if(dto.getProfileImage() == null){ %>
							<i style="font-size:100px;" class="bi bi-person-circle"></i>
						<%}else{ %>
							<img src="${pageContext.request.contextPath }/upload/<%=dto.getProfileImage() %>" 
								style="width:100px;height:100px;border-radius:50%;"/>
						<%} %>
						<%=dto.getWriter() %>
					</td>
				</tr>
				<tr>
					<th>제목</th>
					<td><%=StringEscapeUtils.escapeHtml4(dto.getTitle()) %></td>
				</tr>
				<tr>
					<th>조회수</th>
					<td><%=dto.getViewCount() %></td>
				</tr>
				<tr>
					<th>작성일</th>
					<td><%=dto.getCreatedAt() %></td>
				</tr>
		</table>
		<%--
			클라이언트가 작성한 글 제목이나 내용을 그대로 클라이언트에게 출력하는것은 javascript 주입 공격을 받을 수 있다
			따라서 해당 문자열은 escape 해서 출력하는것이 안전하다 
		 --%>
		 <div><pre><%=StringEscapeUtils.escapeHtml4(dto.getContent()) %></pre></div>
		<!-- <div> >% dto.getContent().replaceAll("\n", "<br>") %< </div> -->
		<!-- replaceAll 은 String type 메소드 \n을 <br>로 바꿔달라는 의미 그래서 개행기호도 그대로 출력이 됨. -->
		<!-- pre 요소 안에 넣어도 같은 의미이다. <div><pre> >%=dto.getContent() %< </pre></div> -->
		<div class="card mt-4">
			<div class="card-header bg-warning">
				<strong>본문 내용</strong>
			</div>
			<div class="card-body p-1">
				<pre class="mb-0" 
				style="background-color: #f8f9fa; 
						border-radius: 5px; 
						padding: 1rem; 
						white-space: pre-wrap; 
						font-family: '한컴산뜻돋움', 'Consolas', monospace, ;"><%=StringEscapeUtils.escapeHtml4(dto.getContent()) %></pre>
		  </div>
		</div>
		<%if(dto.getWriter().equals(userName)){ %>
		<div class="text-end pt-3">
			<a class="btn btn-warning" href="edit.jsp?num=<%=dto.getNum() %>">수정</a>
			<a class="btn btn-secondary" href="delete.jsp?num=<%=dto.getNum() %>">삭제</a>
		</div>
		<%} %>
		<div class="text-center mt-2">
		<a class="btn btn-warning btn-sm <%= dto.getPrevNum() == 0 ? "disabled":"" %>" href="view.jsp?num=<%=dto.getPrevNum()%>">
			<i class="bi bi-arrow-left"></i>
			이전 글
		</a>
		<a class="btn btn-warning btn-sm <%= dto.getNextNum() == 0 ? "disabled":"" %>" href="view.jsp?num=<%=dto.getNextNum()%>">
			다음 글
			<i class="bi bi-arrow-right"></i>
		</a>
		</div>
	</div>
	<jsp:include page="/WEB-INF/include/footer.jsp"></jsp:include>
</body>
</html>