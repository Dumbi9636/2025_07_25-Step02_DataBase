<%@page import="test.dto.CommentDto"%>
<%@page import="java.util.List"%>
<%@page import="test.dao.CommentDao"%>
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
	// 로그인된 userName(null 일 가능성이 있음), session 영역에 userName 이 있는지 읽어와서
	String userName=(String)session.getAttribute("userName");
	// 만일 본인 글 자세히 보기가 아니면 조회수 1을 증가시킨다
	if(!dto.getWriter().equals(userName)){
		BoardDao.getInstance().addViewCount(num);
	}
	// 댓글 목록을 DB 에서 읽어오기
	List<CommentDto> commentList=CommentDao.getInstance().selectList(num);
	
	// 클라이언트가 로그인 했는지 여부 알아내기
	boolean isLogin = userName == null ? false: true;
	
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
		    <li class="breadcrumb-item">
		    <a href="${pageContext.request.contextPath}/">Home</a>
		    </li>
		    <li class="breadcrumb-item">
		    <a href="${pageContext.request.contextPath}/board/list.jsp">Board</a>
		    </li>
		    <li class="breadcrumb-item active">Detail</li>
		  </ol>
		</nav>
		<h1>게시글 상세보기</h1>
		
		<table class="table table-bordered  border-dark">
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
		
		<!-- <div> >% dto.getContent().replaceAll("\n", "<br>") %< </div> -->
		<!-- replaceAll 은 String type 메소드 \n을 <br>로 바꿔달라는 의미 그래서 개행기호도 그대로 출력이 됨. -->
		<!-- pre 요소 안에 넣어도 같은 의미이다. <div><pre> >%=dto.getContent() %< </pre></div> -->
		<div class="card mt-4">
			<div class="card-header bg-warning">
				<strong>본문 내용</strong>
			</div>
			<div class="card-body p-1">
		    <%=dto.getContent() %>
		  </div>
		</div>
		<%if(dto.getWriter().equals(userName)){ %>
		<div class="text-end pt-3">
			<a class="btn btn-warning" href="edit.jsp?num=<%=dto.getNum() %>">수정</a>
			<a class="btn btn-secondary" href="delete.jsp?num=<%=dto.getNum() %>">삭제</a>
		</div>
		<%} %>
		
		<div class="card my-3">
		  <div class="card-header bg-warning text-dark">댓글을 입력해 주세요</div>
			  <div class="card-body">
			    <!-- 원글의 댓글을 작성할 폼 -->
			    <form action="save-comment.jsp" method="post">
			      <!-- 숨겨진 입력값 parentNum:원글의 글번호, targetWriter: 글 작성자 -->
			      <input type="hidden" name="parentNum" value="<%=dto.getNum() %>"/>
			      <input type="hidden" name="targetWriter" value="<%=dto.getWriter() %>" />
			      
			      <div class="mb-3">
			        <label for="commentContent" class="form-label">댓글 내용</label>
			        <textarea id="commentContent" name="content" rows="5" class="form-control" placeholder="댓글을 입력하세요"></textarea>
			      </div>
			      
			      <button type="submit" class="btn btn-secondary">등록</button>
			    </form>
			  </div>
		</div>
		<!-- 댓글 목록을 출력하기 -->
		<div class="comments">
		<%for(CommentDto tmp:commentList){ %>
		<div class="card border border-dark mb-3 ">
            <div class="card-body d-flex">
            	<%if(tmp.getProfileImage() == null){ %>
					<i style="font-size:50px;" class="bi bi-person-circle me-3 align-self-center"></i>
            	<%}else{ %>
            		<img class="rounded-circle me-3 align-self-center " 
                	 src="${pageContext.request.contextPath}/upload/<%=tmp.getProfileImage() %>" 
                	 alt="프로필이미지"
                	 style="width:50px; height:50px;">
            	<%} %>
                <div class="flex-grow-1">
                        <div class="d-flex justify-content-between">
                                <div >
                                        <strong><%=tmp.getWriter() %></strong>
                                        <small><span>@<%=tmp.getTargetWriter() %></span></small>
                                </div>
                                <small><%=tmp.getCreatedAt() %></small>
                        </div>
                        <pre><%=tmp.getContent() %></pre>
                        <%if(tmp.getWriter().equals(userName)){ %>
                        
                        <%}else{ %>
                        <!-- 댓글 입력 폼(처음에는 숨김) -->
                        <div class="d-none form-div">
                            <form action="comment-save.jsp" method="post">
                                <textarea class="form-control mb-3" rows="2" 
                                        placeholder="댓글을 입력하세요"></textarea>
                                <button type="submit" class="btn btn-sm btn-dark">등록</button>
                                <button type="reset" class="btn btn-sm btn-secondary cancel-reply-btn">취소</button>
                            </form>
                        </div>
                        <%} %>
                         <button class="btn btn-sm btn-outline-dark mb-3  show-reply-btn">댓글</button>
                </div>
            </div>
        </div>
		<%} %>
		</div>
		
		<!-- 다음글, 이전글 -->
		<div class="text-center mt-2">
		<a class="btn btn-warning btn-sm <%= dto.getPrevNum() == 0 ? "disabled":"" %>" href="view.jsp?num=<%=dto.getPrevNum()%>">
			<i class="bi bi-arrow-left"></i>
			다음 글
		</a>
		<a class="btn btn-warning btn-sm <%= dto.getNextNum() == 0 ? "disabled":"" %>" href="view.jsp?num=<%=dto.getNextNum()%>">
			이전 글
			<i class="bi bi-arrow-right"></i>
		</a>
		</div>
		
	</div>

	<script>
	
	//클라이언트가 로그인 했는지 여부
	const isLogin = <%=isLogin %>;
	
	document.querySelector("#commentContent").addEventListener("input", ()=>{
		//원글의 댓글 입력란에 입력했을때 만일 로그인 하지 않았다면
		if(!isLogin){
			alert("댓글 작성을 위해 로그인이 필요합니다!");
			location.href=
				"${pageContext.request.contextPath }/user/loginform.jsp?url=${pageContext.request.contextPath }/board/view.jsp?num=<%=num %>";
		}
	});
	
	// 모든 댓글 버튼에 이벤트 등록(querySelectorAll)
    document.querySelectorAll(".show-reply-btn").forEach(item=>{
        // 매개변수에 전달된 item 은 댓글 button 의 참조값이다. 
        item.addEventListener("click", ()=>{
        	
        	//원글의 댓글 입력란에 입력했을때 만일 로그인 하지 않았다면
    		if(!isLogin){
    			alert("댓글 작성을 위해 로그인이 필요합니다!");
    			location.href=
    				"${pageContext.request.contextPath }/user/loginform.jsp?url=${pageContext.request.contextPath }/board/view.jsp?num=<%=num %>";
    			return;
    		}
        	
            // 클릭한 버튼의 다음 형제요소의 class 목록에서 d-none 을 제거
            item.nextElementSibling.classList.remove("d-none");
            // 클릭한 버튼의 class 목록에 d-none 을 추가 
            item.classList.add("d-none");
        })
    });
    // 모든 취소 버튼에 d-none 등록(querySelectorAll)
    document.querySelectorAll(".cancel-reply-btn").forEach(item=>{
        item.addEventListener("click", ()=>{
            // 가장 가까운(closest) 부모 요소중에 클래스 속성이 form-div 인요소
            const formDiv=item.closest(".form-div");
            // formDiv 에 d-none 클래스 추가해서 
            formDiv.classList.add("d-none");
            // formDiv 의 이전 형제요소(댓글버튼)에 d-none 추가  
            formDiv.previousElementSibling.classList.remove("d-none");
        })
    });        
    </script>

	<!-- footer -->
	<jsp:include page="/WEB-INF/include/footer.jsp"></jsp:include>
</body>
</html>