<%@page import="test.dao.BoardDao"%>
<%@page import="test.dto.BoardDto"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	//폼 전송되는 title 과 content
	String title=request.getParameter("title");
	String content=request.getParameter("content");
	//글 작성자는 세션 객체로부터 얻어낸다.
	String writer=(String)session.getAttribute("userName");
	//DB 에 저장하기
	BoardDto dto=new BoardDto();
	dto.setWriter(writer);
	dto.setTitle(title);
	dto.setContent(content);
	// 글번호를 미리 얻어낸다
	int num=BoardDao.getInstance().getSequence();
	// 글번호도 dto 에 담는다
	dto.setNum(num);
	
	boolean isSuccess=BoardDao.getInstance().insert(dto);
	//응답하기
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>/board/save.jsp</title>
</head>
<body>
<div class="container">
	<%if(isSuccess){%>
		<script>
			alert("저장했습니다");
			location.href="view.jsp?num=<%= dto.getNum() %>";
		</script>
	<%}else{%>
		<p>
			글 저장실패!
			<a href="new-form.jsp">다시작성</a>
		</p>
	<%}%>
</div>
</body>
</html>