<%@page import="test.dto.BoardDto"%>
<%@page import="test.dao.BoardDao"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	// 폼 전송되는 내용을 읽어와서 
	int num=Integer.parseInt(request.getParameter("num"));
	String title=request.getParameter("title");
	String content=request.getParameter("content");
	
	// 글 작성자와 로그인된 userName 이 동일한지 비교해서 동일하지 않으면 에러를 응답한다
	String writer=BoardDao.getInstance().getByNum(num).getWriter(); // 수정할 글 작성자
	String user_name=(String)session.getAttribute("user_name");
	if(!writer.equals(user_name)){
		// 에러 페이지 응답  SC_FORBIDDEN = 403 Error
		response.sendError(HttpServletResponse.SC_FORBIDDEN, "다른 사용자의 글은 수정할 수 없습니다.");
		return; // 메소드 종료
	}
	
	// DB 에 수정 반영하고
	BoardDto dto=new BoardDto();
	dto.setNum(num);
	dto.setTitle(title);
	dto.setContent(content);
	BoardDao.getInstance().update(dto);
	// 응답한다
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>/board/update.jsp</title>
</head>
<body>
	<script>
	    alert("수정했습니다!!");
	    location.href = "${pageContext.request.contextPath}/board/view.jsp?num=<%= num %>";
</script>
</body>
</html>