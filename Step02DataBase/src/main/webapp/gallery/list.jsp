<%@page import="test.dto.GalleryDto"%>
<%@page import="test.dao.GalleryDao"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	List<GalleryDto> list = GalleryDao.getInstance().getListWithImages();
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>/gallery/list.jsp</title>
<jsp:include page="/WEB-INF/include/resource.jsp"></jsp:include>
<script src="https://unpkg.com/masonry-layout@4/dist/masonry.pkgd.min.js"></script>
<script src="https://unpkg.com/imagesloaded@4/imagesloaded.pkgd.min.js"></script>
</head>
<body>
	<jsp:include page="/WEB-INF/include/navbar.jsp">
		<jsp:param value="index" name="thisPage"/>
	</jsp:include>
	
	<div class="container mt-3">
		<a class="btn btn-warning btn-sm mb-2 border border-1 border-dark" href="new-form.jsp">
	       <i class="bi bi-image"></i> 
	       <i class="bi bi-plus"></i>
	       <!-- 포커스가 왔을때 갤러리 새글 작성이라는 텍스트를 보여줄 수 있도록 visually hidden -->
	       <span class="visually-hidden">겔러리 새글 작성</span>
	    </a>
		<h1>Gallery 목록</h1>
		<img src="${pageContext.request.contextPath}/images/banner2.jpg" class="img-fluid w-100 mb-5" alt="풀폭 배너"  style="max-height:300px; object-fit:cover; border: 3px solid black !important;" />
		<div class="row" id="masonry-grid">
			<%for(GalleryDto tmp:list){ 
				// 0번째 대표 이미지에 저장된 파일명 
				String name=tmp.getImageList().get(0).getSaveFileName();
				// 전체 이미지의 갯수
				int count=tmp.getImageList().size();
			%>
				<div class="col-sm-6 col-md-4 col-lg-3 mb-4 " >
					<div class="card">
						<img class="card-img-top" src="${pageContext.request.contextPath}/upload/<%=name %>" />
						<div class="card-body">
							<h4><%=tmp.getTitle() %></h4>
							<a class="btn btn-sm btn-warning border border-1 border-dark" href="view.jsp?num=<%=tmp.getNum()%>">
								<%=count %> 개의 이미지
							</a>
						</div>
					</div>
				</div>
			<%} %>
		</div>	
	</div>
	<script>
		const grid = document.querySelector("#masonry-grid");
		imagesLoaded(grid, ()=>{
			new Masonry(grid, { percentPosition:true});
		});
	</script>
	
	<jsp:include page="/WEB-INF/include/footer.jsp"></jsp:include>
</body>
</html>
