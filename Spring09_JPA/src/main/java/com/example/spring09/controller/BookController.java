package com.example.spring09.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.spring09.dto.BookDto;
import com.example.spring09.service.BookService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class BookController {
	
	// 필요 의존 객체 주입
	private final BookService service;
	
	// 도서 목록 조회
	@GetMapping("/book/list")
	public String list(Model model) {
		
		List<BookDto> list = service.getAll();
		model.addAttribute("list", list);
		
		return "book/list";
	}
	
	// 도서 목록 추가 폼 띄우기
	@GetMapping("/book/insertform")
	public String insertform() {
		
		return "book/insertform";
	}
	
	// 도서 목록 추가 처리 
	@PostMapping("/book/insert")
	public String insert(BookDto dto) {
		service.addBook(dto);
		
		return "redirect:/book/list";
	}
	
	// 도서 수정 폼 띄우기
	@GetMapping("/book/updateform")
	public String updateform(@RequestParam int id, Model model) {
		BookDto dto = service.getBook(id);
		model.addAttribute("dto", dto);
		
		return "book/updateform";
	}
	
	// 도서 수정 처리
	@PostMapping("/book/update")
	public String update(BookDto dto) {
		
		service.updateBook(dto);
		return "book/update";
	}
	
	// 도서 삭제
	@GetMapping("/book/delete")
	public String delete(@RequestParam int id, Model model) {
		
		service.deleteBook(id);
		model.addAttribute("id", id);
		
		return "redirect:/book/list";
	}
}
