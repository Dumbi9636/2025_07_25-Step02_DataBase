<%@page import="test.dao.BoardDao"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	// Get 방식 파라미터로 전달되는 글번호를 읽어와서
	int num=Integer.parseInt(request.getParameter("num"));
	
	// 글 작성자와 로그인된 userName 이 동일한지 비교해서 동일하지 않으면 에러를 응답한다
	String writer=BoardDao.getInstance().getByNum(num).getWriter(); // 삭제할 글 작성자
	String userName=(String)session.getAttribute("userName");
	// 만일 글장석자와 로그인된 userName이 일치하지 않으면 
	if(!writer.equals(userName)){
		// 에러 페이지 응답  SC_FORBIDDEN = 403 Error
		response.sendError(HttpServletResponse.SC_FORBIDDEN, "남의 글 지우려고 하면 혼난다!");
		return; // 메소드 종료
	}
	// Db 에서 해당 글을 삭제하고
	BoardDao.getInstance().deleteByNum(num);
	// 응답한다.
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>/board/delete.jsp</title>
</head>
<body>
	<script>
		alert("삭제 했습니다!")
		<a href="${pageContext.request.contextPath}/board/list.jsp"></a>
	</script>
</body>
</html>