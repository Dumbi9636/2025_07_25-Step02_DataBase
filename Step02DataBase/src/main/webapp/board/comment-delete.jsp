<%@page import="test.dao.CommentDao"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	// 삭제할 댓글번호
	int num=Integer.parseInt(request.getParameter("num"));
	
	// 리다일렉트 이동할때 필요한 원글의 글번호
	String parentNum=request.getParameter("parentNum");
	
	// dao 객체를 이용해서 삭제하고
	CommentDao.getInstance().delete(num);
	
	// 리다일렉트 이동 (절대경로로 이동하기에 cPath)
	String cPath=request.getContextPath(); // getContextPath( ): context 경로를 얻어냄 
	response.sendRedirect(cPath+"/board/view.jsp?num="+parentNum);
%> 
// delete를 위한 요청만 하기 때문에 html 은 필요없다 -> 새로고침하는 효과를 나타낼 수 있음. 삭제버튼을 누르면 순간적으로 view.jsp로 이동하는 듯한 
