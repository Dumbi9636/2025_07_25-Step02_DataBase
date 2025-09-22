package com.example.spring10.service;

import java.util.List;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import com.example.spring10.dto.CommentDto;
import com.example.spring10.repository.CommentDao;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CommentServiceImpl implements CommentService{
	private final CommentDao commentDao;	
	// 댓글 목록 
		@Override
		public List<CommentDto> getComments(int parentNum) {
			
			return commentDao.selectList(parentNum);
		}

		
		// 댓글 저장 구현 메소드
		@Override
		public void createComment(CommentDto dto) {
			// 댓글의 그룹번호가 넘어오지 않으면 dto.getGroupNum() 은 0 을 리턴한다
			// 저장할 댓글의 pk 미리 얻어낸다. 
			int num = commentDao.getSequence();
			// 댓글의 글번호로 사용하고
			dto.setNum(num);
			
			// 만일 원댓글이면 groupNum = 자기번호, 대댓글이면 전달받은 groupNum 사용
			if(dto.getGroupNum() == 0) {
				dto.setGroupNum(num); // 원글의 댓글은 자신의 글번호가 댓글의 그룹번호이고,
			}
			// 대댓글이면 이미  dto 에 댓글의 그룹번호가 들어있다. 
			// 댓글 작성자를 얻어내서 dto 에 담는다. 
			String userName = SecurityContextHolder.getContext().getAuthentication().getName();
			dto.setWriter(userName);
			commentDao.insert(dto);
		}
			
			
		// 댓글 수정 구현 메소드
		@Override
		public void updateComment(CommentDto dto) {
			// (예외처리)글 작성자와 로그인된 userName 이 동일한지 비교해서 동일하지않으면 예외를 발생시킨다.
			String writer = commentDao.getByNum(dto.getNum()).getWriter();
			String userName = SecurityContextHolder.getContext().getAuthentication().getName();
			if(!writer.equals(userName)) {
				// 500 Error - runtimeException
				throw new RuntimeException("남의 글을 지울수는 없다! 돌아가라!");
			}
			commentDao.update(dto);
		}
			
			
		// 댓글 삭제 구현 메소드
		@Override
		public void deleteComment(int num) {
			// (예외처리)글 작성자와 로그인된 userName 이 동일한지 비교해서 동일하지않으면 예외를 발생시킨다.
			String writer = commentDao.getByNum(num).getWriter();
			String userName = SecurityContextHolder.getContext().getAuthentication().getName();
			if(!writer.equals(userName)) {
				// 500 Error - runtimeException
				throw new RuntimeException("남의 글을 지울수는 없다! 돌아가라!");
			}
			// 글 삭제하기
			commentDao.delete(num);
		}
	
}