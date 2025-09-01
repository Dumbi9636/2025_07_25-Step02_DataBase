package com.example.spring08.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.spring08.dto.CommentDto;
import com.example.spring08.repository.CommentDao;
import com.example.spring08.service.CommentService;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@Controller
public class CommentController {
	
	// 의존성 주입
	private final CommentDao commentDao;
	private final CommentService commentService;
	
	// 댓글 수정
	@PostMapping("/{category}/comment-update")
	public String commentUpdate(CommentDto dto, 
		 @PathVariable("category") String category) {
			
		commentService.updateComment(dto);
			
		return "redirect:/"+category+"/view?num="+dto.getParentNum();
	}
		
		
	// 댓글 삭제
	@GetMapping("/{category}/comment-delete")
	public String boardDelete(CommentDto dto, 
		@PathVariable("category") String category) {
		//dto 에는 삭제할 댓글의 글번호와 원글의 글번호가 들어 있다.
		commentService.deleteComment(dto.getNum());
			
		return "redirect:/"+category+"/view?num="+dto.getParentNum();
	}
		
		
	// 댓글 저장
	@PostMapping("/{category}/save-comment")
	public String boardSave(CommentDto dto, 
			@PathVariable("category") String category) {
		//서비스를 이용해서 새로운 댓글을 저장한다 
		commentService.createComment(dto);
		//댓글을 작성한 원글 자세히 보기로 다시 리다일렉트 이동시키기
		return "redirect:/"+category+"/view?num="+dto.getParentNum();
	}
}
