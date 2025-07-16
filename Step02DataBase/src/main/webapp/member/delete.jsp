<%@page import="test.dao.MemberDao"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	// GET 방식 파라미터로 전달되는 회원의 번호 얻어내기
	int num = Integer.parseInt(request.getParameter("num"));
	//삭제할 회원정보를 삭제하고
	MemberDao dao=new MemberDao();
	dao.deleteByNum(num);
	//응답한다
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>/member/delete.jsp</title>
</head>
<body>
	<%-- javascript 를 응답해서 알림창이 뜨고 "확인" 버튼을 누르면 다시 목록보기로 이동하도록 한다 --%>
	<script>
		alert("삭제 했습니다");
		location.href="${pageContext.request.contextPath }/member/list.jsp";
	</script>
</body>
</html>





