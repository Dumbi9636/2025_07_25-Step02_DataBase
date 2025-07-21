<%@page import="test.dto.BoardDto"%>
<%@page import="java.util.List"%>
<%@page import="test.dao.BoardDao"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	// 전체 글 목록 얻어오기
	// List<BoardDto> list=BoardDao.getInstance().selectAll();
	
	// 페이징 처리를 위해 모든 내용을 select 해오는게 아니라 페이지에 몇개씩 보여줄지 코딩
	
	// index 에서 list로 이동할때 페이지 번호가 null인 경우 에러가 발생하기 때문에 이를 해결하기 위한 코드 
	// 기본 페이지 번호는 1로 설정
	int pageNum=1;
	// 페이지 번호를 읽어와서
	String strPageNum=request.getParameter("pageNum");
	// 전달되는 페이지 번호가 있다면 (null 이 아니라면)
	if(strPageNum != null){
		// 해당 페이지 번호를 숫자로 변경해서 사용한다.
		pageNum=Integer.parseInt(strPageNum);
	}
	
	// 현 페이지에 몇개씩 표시할 것인지
	final int PAGE_ROW_COUNT=5;
	
	// 하단 페에지를 몇개씩 표시할 것인지
	final int PAGE_DISPLAY_COUNT=5;
	
	// 보여줄 페이지의 시작 ROWNUM 
	int startRowNum=1+(pageNum-1)*PAGE_ROW_COUNT; //공차수열
	// 보여줄 페이지의 끝 ROWNUM
	int endRowNum=pageNum*PAGE_ROW_COUNT; // 등비수열 
	
	
	//하단 시작 페이지 번호 (정수를 정수로 나누면 소수점이 버려진 정수가 나온다)
	int startPageNum = 1 + ((pageNum-1)/PAGE_DISPLAY_COUNT)*PAGE_DISPLAY_COUNT;
	//하단 끝 페이지 번호
	int endPageNum=startPageNum+PAGE_DISPLAY_COUNT-1;
	//전체 글의 갯수
	int totalRow=BoardDao.getInstance().getCount();
	//전체 페이지의 갯수 구하기
	int totalPageCount=(int)Math.ceil(totalRow/(double)PAGE_ROW_COUNT);
	//끝 페이지 번호가 이미 전체 페이지 갯수보다 크게 계산되었다면 잘못된 값이다.
	if(endPageNum > totalPageCount){
		endPageNum=totalPageCount; //보정해 준다. 
	}	
	
	
	// dto 에 select 할 row 의 정보를 담고
	BoardDto dto=new BoardDto();
	dto.setStartRowNum(startRowNum);
	dto.setEndRowNum(endRowNum);
	
	// 해당 row 만 select 한다 selectAll 이 아닌 selectPage
	List<BoardDto> list = BoardDao.getInstance().selectPage(dto);
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>/board/list2.jsp</title>
<style>
	ul a{
		text-decoration: none;
	}
	/* ul 요소이면서 클래스 속성의 값이 pagination 인 요소에 적용할 css*/
	ul.my-pagination{
		list-style-type: none; /* ul 의 disc 없애기 */
		padding-left: 0; /* 왼쪽 padding 제거 */
		display: flex; /* 자식 요소(li)를 flex 레이아웃으로 배치 하기 위해(가로배치) */
		gap: 10px; /* 자식요소 끼리 공간 부여하기 */
		justify-content: center; /* 가로로 배치된 상태에서 가운데 정렬 */
	}
	.active{
		font-weight: bold;
		color: red;
		text-decoration: underline;
	}
</style>
<jsp:include page="/WEB-INF/include/resource.jsp"></jsp:include>
</head>
<body>
	<jsp:include page="/WEB-INF/include/navbar.jsp">
		<jsp:param value="index" name="thisPage"/>
	</jsp:include>
	
	<div class="container pt-3 pb-3">
		<a class="btn btn-outline-dark" href="new-form.jsp">새글 작성
		<i class="bi bi-pencil-square"></i></a>
		
		<h1 class= "container pt-3 pb-3 ">게시글 목록</h1>
		<table class="table table-bordered">
			<thead class="table-dark">
			<tr>
				<th>글 번호</th>
				<th>작성자</th>
				<th>제목</th>
				<th>조회수</th>
				<th>작성일</th>
			</tr>
			</thead>
			<tbody>
			<%for(BoardDto tmp:list){ %>
				<tr>
					<td><%=tmp.getNum() %></td>
					<td><%=tmp.getWriter() %></td>
					<td>
						<a href="view.jsp?num=<%= tmp.getNum() %>"><%= tmp.getTitle() %></a>
					</td>
					<td><%=tmp.getViewCount() %></td>
					<td><%=tmp.getCreatedAt() %></td>
				</tr>
			<%} %>
			</tbody>
		</table>
		<ul class="my-pagination">
			<%-- startPageNum 이 1이 아닐때 이전 page 가 존재하기 때문에... --%>
			<%if(startPageNum != 1){ %>
				<li>
					<a href="list.jsp?pageNum=<%=startPageNum-1 %>">&lsaquo;</a>
				</li>
			<%} %>			
			<%for(int i=startPageNum; i<=endPageNum ; i++){ %>
				<li>
					<a class="<%= i==pageNum ? "active":"" %>" href="list.jsp?pageNum=<%=i %>"><%=i %></a>
				</li>
			<%} %>
			<%-- endPageNum 이 totalPageCount 보다 작을때 다음 page 가 있다 --%>		
			<%if(endPageNum < totalPageCount){ %>
				<li>
					<a href="list.jsp?pageNum=<%=endPageNum+1 %>">&rsaquo;</a>
				</li>
			<%} %>	
		</ul>
	</div>
	<jsp:include page="/WEB-INF/include/footer.jsp"></jsp:include>
</body>
</html>