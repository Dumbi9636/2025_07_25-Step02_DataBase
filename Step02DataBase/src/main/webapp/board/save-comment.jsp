<%@page import="test.dto.CommentDto"%>
<%@page import="test.dao.CommentDao"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	// 폼 전송되는 댓글 정보를 얻어낸다.	
	int parentNum=Integer.parseInt(request.getParameter("parentNum"));
	String targetWriter=request.getParameter("targetWriter");
	String content=request.getParameter("content");
	
	
	// 댓글의 그룹번호가 넘어오는지 읽어와 본다( null 이면 원글의 댓글 저장 요청이다)
	String strGroupNum=request.getParameter("groupNum");
	// 댓글의 그룹번호를 담을 변수를 미리 만들고
	int groupNum = 0;
	// 만일 댓글의 그룹번호가 넘어온다면(댓글의 댓글 이라면)
	if(strGroupNum != null){
		// 댓글의 그룹 번호를 변수에 담는다 
		groupNum = Integer.parseInt(strGroupNum);
	}
	
	// 댓글 작성자 정보는 session 으로 부터 얻어낸다.(로그인해야지만 댓글을 입력할 수 있기 때문)
	String writer=(String)session.getAttribute("userName");
	// 저장할 댓글의 글번호를 미리 얻어낸다.
	int num=CommentDao.getInstance().getSequence();
	// 저장할 댓글 정보를 dto 에 담고
	CommentDto dto=new CommentDto();
	dto.setNum(num);
	dto.setWriter(writer);
	dto.setTargetWriter(targetWriter);
	dto.setContent(content);
	dto.setParentNum(parentNum);
	//dto.setGroupNum(num); // 원글의 댓글은 자신의 글번호가 댓글의 그룹번호이다 
	
	// 만일 원글의 댓글이면 
	if(groupNum == 0){
		dto.setGroupNum(num); // 원글의 댓글은 자신의 글번호가 댓글의 그룹번호이다
	}else{
		dto.setGroupNum(groupNum); // 대댓글은 전송된 그룹번호가 댓글의 그룹번호이다
	}
	
	// DB 에 저장하고
	boolean isSuccess=CommentDao.getInstance().insert(dto);

	// 응답한다
	String cPath=request.getContextPath();
	
	// 원래글 자세히 보기 페이지로 이동하라는 리다일렉트 응답 
	response.sendRedirect(cPath+"/board/view.jsp?num="+parentNum);
%>
<!-- html로 응답하는게 아니기 때문에 html은 삭제해도 무방하다. -->