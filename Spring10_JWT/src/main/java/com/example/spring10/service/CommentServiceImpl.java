package com.example.spring10.service;

import java.util.List;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.example.spring10.dto.CommentDto;
import com.example.spring10.dto.CommentListResponse;
import com.example.spring10.repository.CommentDao;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CommentServiceImpl implements CommentService{

	private final CommentDao commentDao;
	
	//페이징 처리를 위해 pageNum 도 전달 받는다.
	@Override
	public CommentListResponse getComments(int parentNum, int pageNum) {
		//한 페이지에 몇개씩 표시할 것인지
		final int PAGE_ROW_COUNT=3;
		//하단 페이지를 몇개씩 표시할 것인지
		final int PAGE_DISPLAY_COUNT=3;
		//보여줄 페이지의 시작 ROWNUM
		int startRowNum=1+(pageNum-1)*PAGE_ROW_COUNT; //공차수열
		//보여줄 페이지의 끝 ROWNUM
		int endRowNum=pageNum*PAGE_ROW_COUNT; //등비수열 
		
		//하단 시작 페이지 번호 (정수를 정수로 나누면 소수점이 버려진 정수가 나온다)
		int startPageNum = 1 + ((pageNum-1)/PAGE_DISPLAY_COUNT)*PAGE_DISPLAY_COUNT;
		//하단 끝 페이지 번호
		int endPageNum=startPageNum+PAGE_DISPLAY_COUNT-1;
		
		//원글에 달린 전체 댓글의 갯수
		int totalRow = commentDao.getCount(parentNum);
		
		//전체 페이지의 갯수 구하기
		int totalPageCount=(int)Math.ceil(totalRow/(double)PAGE_ROW_COUNT);
		//끝 페이지 번호가 이미 전체 페이지 갯수보다 크게 계산되었다면 잘못된 값이다.
		if(endPageNum > totalPageCount){
			endPageNum=totalPageCount; //보정해 준다. 
		}	
		//dao 에 전달할 CommentDto 를 구성한다
		CommentDto dto=CommentDto.builder()
				.parentNum(parentNum)
				.startRowNum(startRowNum)
				.endRowNum(endRowNum)
				.build();
		// parentNum, startRowNum, endRowNum 이 담겨진 CommentDto 를 전달해서 댓글 목록을 얻어낸다.
		List<CommentDto> list=commentDao.selectList(dto);
		
		// CommentListResponse 객체를 잘 구성해서 리턴해준다. 
		return CommentListResponse.builder()
				.list(list)
				.pageNum(pageNum)
				.startPageNum(startPageNum)
				.endPageNum(endPageNum)
				.totalPageCount(totalPageCount)
				.build();
	}

	@Override
	public void createComment(CommentDto dto) {
		//댓글의 그룹번호가 넘어오지 않으면 dto.getGroupNum() 은 0 을 리턴한다. 
		
		//저장할 댓글의 pk 를 미리 얻어낸다.
		int num=commentDao.getSequence();
		//댓글의 글번호로 사용하고 
		dto.setNum(num);
		//만일 원글의 댓글이면
		if(dto.getGroupNum() == 0) {
			dto.setGroupNum(num); //원글의 댓글은 자신의 글번호가 댓글의 그룹번호이고 
		}
		
		//댓글 작성자를 얻어내서 dto 에 담는다 
		String userName = SecurityContextHolder.getContext().getAuthentication().getName();
		dto.setWriter(userName);
		
		//대댓글이면 이미 dto 에 댓글의 그룹번호가 들어 있다.
		commentDao.insert(dto);
	}

	@Override
	public void updateComment(CommentDto dto) {
		//글 작성자와 로그인된 userName 이 동일한지 비교해서 동일하지 않으면 예외를 발생시킨다.
		String writer = commentDao.getByNum(dto.getNum()).getWriter();
		String userName = SecurityContextHolder.getContext().getAuthentication().getName();
		if(!writer.equals(userName)) {
			throw new RuntimeException("남의 글을 수정할수 없습니다!");
		}
		commentDao.update(dto);
		
	}

	@Override
	public void deleteComment(int num) {
		//글 작성자와 로그인된 userName 이 동일한지 비교해서 동일하지 않으면 예외를 발생시킨다.
		String writer = commentDao.getByNum(num).getWriter();
		String userName = SecurityContextHolder.getContext().getAuthentication().getName();
		if(!writer.equals(userName)) {
			throw new RuntimeException("남의 글을 지울수는 없습니다!");
		}
		//글 삭제하기 
		commentDao.delete(num);
	}

}