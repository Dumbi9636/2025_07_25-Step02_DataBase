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
<style>
	/* 대댓글이 처음에는 보이지 않도록 하기 위해 */
	.re-re{
		display:none;
	}
</style>
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
		<%-- 대댓글은 자신의 글번호와 댓글의 그룹번호가 다르다. 그런 경우 왼쪽 마진을 부여한다 --%>
		 <div class="card border border-dark mb-3 <%=tmp.getNum() == tmp.getGroupNum() ? "" : "ms-5 re-re"%>">
			<%if(tmp.getDeleted().equals("yes")){ %>
				<div class="card-body bg-light text-muted rounded">삭제된 댓글입니다</div>
			<%}else{ %>
			<div class="card-body d-flex flex-column flex-sm-row position-relative">
			
				<%-- 댓글의 갯수가 0이 아니고, 원글의 댓글에만 부여한다 --%>
				<%if(tmp.getReplyCount() != 0 && tmp.getNum() == tmp.getGroupNum()){ %>
		        	<button class="dropdown-btn btn btn-warning btn-sm position-absolute"
		        		style="bottom:16px; right:16px;">
		            	<i class="bi bi-caret-down"></i>
		            	답글 <%=tmp.getReplyCount() %> 개
		            </button>
		        <%} %>
		        
				<%-- 대댓글 작성 시 표시되는 화살표 UI --%>
				<%if(tmp.getNum() != tmp.getGroupNum()){ %>
					<i class="bi bi-arrow-return-right position-absolute" style="top:0;left:-30px"></i>
					<%} %>
            	<%-- 댓글 작성자가 로그인된 userName 과 같을때만 삭제버튼 출력 --%>
            	<%if(tmp.getWriter().equals(userName)){ %>
            		<button data-num="<%=tmp.getNum() %>" class="btn-close position-absolute top-0 end-0 m-2" ></button>
            	<%} %>
            	
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
                        
                        <%-- 댓글 작성자가 로그인된 userName 과 같으면 수정폼, 다르면 댓글폼을 출력한다 --%>
                        <%if(tmp.getWriter().equals(userName)){ %>
                         <!-- 수정 버튼 (본인에게만 보임) -->
                           <button class="btn btn-sm btn-outline-dark mb-2 edit-btn">수정</button>  
                           
                           <!-- 댓글 입력 폼 (처음에는 숨김) -->
                           <div class="d-none form-div">
                               <form action="comment-update.jsp" method="post">
                               	   <!-- 댓글을 수정하기 위한 댓글의 번호, 이 페이지로 다시 돌아오기 위한 parentNum 도 같이 전송되도록 -->
                                   <input type="hidden" name="num" value="<%=tmp.getNum() %>" />
                                   <input type="hidden" name="parentNum" value="<%=num %>" />
                                   <textarea name="content" class="form-control mb-2" rows="2" ><%=tmp.getContent() %></textarea>
                                   <button type="submit" class="btn btn-sm btn-dark mb-2">수정 완료</button>
                                   <button type="reset" class="btn btn-sm btn-secondary cancel-edit-btn mb-2">취소</button>
                               </form>
                           </div>
                        <%}else{ %>
						  <button class="btn btn-sm btn-outline-dark mb-2 show-reply-btn">댓글</button>
                       		<!-- 대댓글 입력 폼(처음에는 숨김) -->
                        	<div class="d-none form-div">
                            	<form action="save-comment.jsp" method="post">
                            		<!-- 원글의 글번호, 댓글 대상자의 userName, 댓글의 그룹번호도 같이 전송해야한다
                            		input type = "hidden"으로 들고간다.  -->
                            	<input type="hidden" name="parentNum" value="<%=dto.getNum() %>" />
                            	<input type="hidden" name="targetWriter" value="<%=tmp.getWriter() %>" />
                            	<input type="hidden" name="groupNum" value="<%=tmp.getGroupNum() %>"/>
                                <textarea name="content" class="form-control mb-3" rows="2" 
                                        placeholder="댓글을 입력하세요"></textarea>
                                <button type="submit" class="btn btn-sm btn-dark">등록</button>
                                <button type="reset" class="btn btn-sm btn-secondary cancel-reply-btn">취소</button>
                            </form>
                        </div>
                        <%} %>
                </div>
            </div><!-- .card-body -->
			<%} %>
         </div><!-- .card -->
		 <%} %>
		</div><!-- .container -->
		
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
	
	// 대댓글 보기 버튼을 눌렀을때 실행할 함수 등록 
	document.querySelectorAll(".dropdown-btn").forEach(item => {
 		  item.addEventListener("click", (e) => {
 			
 			// click 이벤트가 발생한 그 버튼의 자손요소 중에 caret up 또는 caret down 요소를 찾는다. 
 			const caret = item.querySelector(".bi-caret-up, .bi-caret-down");
 			if (caret) {
 			  // 해당 아이콘 클래스들을 toggle 한다. 
 			  caret.classList.toggle("bi-caret-down");
 			  caret.classList.toggle("bi-caret-up");
 			}
 			
 		    // 1. 버튼의 두 단계 부모 요소로 이동
 		    const grandParent = item.parentElement.parentElement;
			// 2. 두단계 부모 요소의 바로 다음 형제 요소의 참조값을 얻어낸다 
 		 	let next = grandParent.nextElementSibling;
			// 3. 반복문돌면서 
	   		while (next) {
	   		  // 만일 re-re 클래스가 있다면 	
	   		  if (next.classList.contains("re-re")) {
	   			// d-block 클래스를 토글시켜서 보였다 숨겼다를 반복시킨다
	   		    next.classList.toggle("d-block");
	   		  }else{ //존재하지 않으면
	   			  break; // 반복문 탈출
	   		  }
	   		  // 다음 형제 요소의 참조값 얻어내기
	   		  next = next.nextElementSibling;s
	   		}
 		  });
  	});
	
	// 삭제 버튼을 눌렀을때 
    document.querySelectorAll(".btn-close").forEach(item=>{
        item.addEventListener("click", ()=>{
        // data-num 속성에 출력된 삭제할 댓글의 번호값을 변수에 담기 
        const num=item.getAttribute("data-num") //  getAttribute() : ( )의 속성값을 가져오겠다는 의미
        const isDelete=confirm(num+"번 댓글을 삭제 하시겠습니까?")
        if(isDelete){
        	// "delete.jsp?num=삭제 할 댓글번호 & parentNum=원글의 번호" 형식의 요청이 되도록 한다.
        	// 달라{ } 는 jsp 가 해석하지 않도록 \${ } 역슬래시를 붙여서 작성한다. 
        	location.href=`comment-delete.jsp?num=\${num}&parentNum=<%=num %>`
        }
        // formDiv 에 d-none 클래스 추가해서 
         formDiv.classList.add("d-none");
         // formDiv 의 이전 형제요소(댓글버튼)에 d-none 추가  
         formDiv.previousElementSibling.classList.remove("d-none");
        });
    });

    // 클래스명이 edit-btn 인 모든 버튼에 "click" 이벤트리스너 등록
    document.querySelectorAll(".edit-btn").forEach(item=>{
        item.addEventListener("click", ( )=>{
            // 클릭한 버튼의 다음 형제 요소의 class 목록에서 d-none 을 제거
            item.nextElementSibling.classList.remove("d-none");
            // 클릭한 버튼의 class 목록에 d-none 을 추가 
            item.classList.add("d-none");   
         	
            // 댓글(답글) 버튼도 숨기기
            const parentCard = item.closest(".card"); // 카드 하나 전체
            const replyBtn = parentCard.querySelector(".show-reply-btn"); // 댓글 버튼 찾아서
            if(replyBtn){
                replyBtn.classList.add("d-none");
            }
        });
     	
    });
    // 취소 버튼에 d-none 등록(querySelectorAll)
    document.querySelectorAll(".cancel-edit-btn").forEach(item=>{
        // 취소 버튼을 눌렀을때 이벤트 리스너 등록
        item.addEventListener("click", ()=>{
            // 가장 가까운(closest) 부모 요소중에 클래스 속성이 form-div 인요소
            const formDiv=item.closest(".form-div");
            // formDiv 에 d-none 클래스 추가해서 
            formDiv.classList.add("d-none");
            // formDiv 의 이전 형제요소(댓글버튼)에 d-none 추가  
            formDiv.previousElementSibling.classList.remove("d-none");
         	
            // 댓글(답글) 버튼 다시 보이게
            const parentCard = item.closest(".card");
            const replyBtn = parentCard.querySelector(".show-reply-btn");
            if(replyBtn){
                replyBtn.classList.remove("d-none");
            }
        });
    });
	
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