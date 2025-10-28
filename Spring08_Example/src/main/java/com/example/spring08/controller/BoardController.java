package com.example.spring08.controller;

import java.util.List;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.example.spring08.config.SecurityConfig;
import com.example.spring08.dto.BoardDto;
import com.example.spring08.dto.BoardListResponse;
import com.example.spring08.dto.CommentDto;
import com.example.spring08.service.BoardService;
import com.example.spring08.service.CommentService;
import com.example.spring08.service.CommentServiceImpl;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class BoardController {

    private final CommentServiceImpl commentServiceImpl;

    private final SecurityConfig securityConfig;
	
	// 서비스 의존성 주입 
	private final BoardService service;
	private final CommentService commentService;
	

	
	// 글 삭제 요청 처리
	@GetMapping("/board/delete")
	public String boardDelete(int num) {
		
		service.deleteContent(num);
		return "board/delete";
	}
	
	
	// 글 수정 폼 띄우기 
	@GetMapping("/board/edit")
	public String boardEdit(int num, Model model) {
		
		model.addAttribute("dto", service.getData(num));
		
		return "board/edit";
	}
	
	
	// 글 수정 요청 처리
	@PostMapping("/board/update")
	public String boardUpdate(BoardDto dto, RedirectAttributes ra) {
		// 글 수정 반영하고 
		service.updateContent(dto);
		// 리다일렉트 이동해서 출력할 메세지도 담는다
		ra.addFlashAttribute("message", "게시글을 성공적으로 수정했습니다");
		// 리다일렉트된 페이지는 모델에 담으면 전송이 안되기때문에 RedirectAttributes 에다가 담는다(키값 ra). 
		// foward 이동의 경우만 model 객체에 담음
		// 글 자세히 보기로 리다일렉트 
		return "redirect:/board/view?num="+dto.getNum();
	}
	
	
	// 글 상세보기 폼 요청 
	@GetMapping("/board/view")
	public String boardView(BoardDto requestDto, Model model) {
		/*
		 *  requestDto 에는 자세히 보여줄 글의 num 과 
		 *  search(검색조건), keyword(검색어) 가 들어있을수도 있다.
		 *  검색어가 없는 경우는 search 와 keyword 에는 null 이 들어있다. 
		 */
		// 서비스를 이용해서 응답에 필요한 데이터를 얻어내서
		BoardDto dto = service.getDetail(requestDto);
		
		String query="";
		if(dto.getKeyword() != null) {
			query = "&search="+requestDto.getSearch()+"&keyword="+requestDto.getKeyword();
		}
		// 검색 query 정보도 view page 에 전달한다
		model.addAttribute("query", query);
		
		// 댓글 목록은 원글의 글번호를 전달해서 얻어낸다. 
		List<CommentDto> comments = commentService.getComments(requestDto.getNum());
		// 모델 객체에 담고
		model.addAttribute("dto", dto);
		model.addAttribute("commentList", comments); // 기존 예제 활용 편의성을 위해 key 값은 commentList 로 
		
		// 타임리프 페이지에서 응답한다
		return "board/view";
	}
	
	
	/* 새글 작성 요청 처리
	 * 		@ModelAttribute 는 view page 에서 필요한 값을 대신 Model 객체에 담아준다 
	 * 		dto 라는 이름으로 view page 로 가져갈 수 있도록 
	 */
	@PostMapping("/board/save")
	public String boardSave(@ModelAttribute("dto") BoardDto dto) { 
		// 글작성자 불러오기 
		String userName = SecurityContextHolder.getContext().getAuthentication().getName();
		// 글작성자 userName 담기
		dto.setWriter(userName);
		// 서비스를 이용해서 글 저장하기 
		service.createContent(dto);
		// 타임리프 페이지에서 응답
		return "board/save";
	}
	
	
	// 새글 작성 폼 요청(띄우기) 
	@GetMapping("/board/new-form")
	public String newForm() {
		
	    return "board/new-form"; 
	}
	
	
	@GetMapping("/board/list")
	// /board/list?pageNum=1&keyword=xxx
	/*
	 *  @RequestParam 어노테이션을 이용하면 요청 파라미터를 추출하면서 해당 값이 없으면
	 *  defaultValue 를 설정할 수 있다. 
	 */
	public String list(Model model, 
		   @RequestParam(defaultValue = "1") int pageNum, BoardDto dto) { // 기존에는 keyword 만 담았는데, 검색 조건을 dto 에 필드로 추가해서 넣어놨으니, dto를 통해 keyword 와 search 를 동시에 가져올 예정임
		
		// BoardDto 객체에는 keyword 와 search 가 있을 수도 있다. ( 없으면 null 이다 )
		
		// 응답에 필요한 데이터를 얻어내서
		BoardListResponse listResponse = service.getBoardList(pageNum, dto);
		// 모델 객체에 담고
		model.addAttribute("dto", listResponse);
		// 타임리프 페이지에서 응답
		return "board/list";
	}
}
