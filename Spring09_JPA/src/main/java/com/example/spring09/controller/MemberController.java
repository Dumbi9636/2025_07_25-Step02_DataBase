package com.example.spring09.controller;

import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.spring09.dto.MemberDto;
/*
 *	<물건을 적절한 타이밍에 꺼내서 쓰는 사람> 
 *	여기서 물건은 DAO나 Service 같은 비즈니스 로직 컴포넌트(부품)
 *	클라이언트 요청을 받고, 필요한 작업을 실행한 후 View(jsp) 나 다른 URL 로 응답을 돌려줌 
 *	회원 추가같은경우 : DAO 주입 -> 회원 목록 조회 -> 회원 추가 폼 -> 회원 추가 처리 
 *	회원 수정같은경우 : DAO 주입 -> 회원 수정 폼 -> 회원 수정 처리
 */
import com.example.spring09.service.MemberService;

import lombok.RequiredArgsConstructor;


@Controller
@RequiredArgsConstructor
public class MemberController {
	
	// 필요한 의존 객체를 주입받는다. 
	private final MemberService service;
	
	// 회원 목록
	@GetMapping("/member/list")
	public String list(Model model) {
		// 회원목록
		List<MemberDto> list=service.getAll();
		// 응답에 필요한 객체를 Model 객체에 담는다.
		model.addAttribute("list", list);
	
		// "/WEB-INF/views/member/list.jsp" 에서 응답하기 
		return "member/list";
	}
	
	// 회원 추가 폼 띄우기
	@GetMapping("/member/insertform")
	public String insertForm(Model model) {
		model.addAttribute("dto", new MemberDto()); // 비어있는 dto라도 항상 넘겨줌
		// 현재 여기서 수행할 로직은 없고 view page 의 위치만 리턴해준다. 
		return "member/insertform";
	}
	
	// 회원 추가 처리
	@PostMapping("/member/insert")
	public String insert(MemberDto dto) { 
		/*	매개변수에 dto 를 선언하면 폼 전송되는 파라미터가 자동 추출되어서  
		 * 	MemberDto 객체에 담긴 채로 전달된다
		 * 	MemberDto 클래스의 필드명과 폼 전송되는 파라미터명이 같아야한다. 
		 * 	private String name <=> <input type ="text" name="name" > 
		 * 	private String addr <=> <input type ="text" name="addr" >
		 */
	    service.addMember(dto); 
	    // 회원 목록보기로 "/member/list" 요청을 다시 하라는 redirect 응답 하기
	    // "redirect: 리다일렉트 경로" 처럼 redirect: 으로 시작하는 문자열을 리턴하면 된다. 
	    return "redirect:/member/list";
	}
	
	// 회원 수정 폼 띄우기 
	// 여기서는 num 만 받는다 DB 에서 dto룰 꺼내서 수정 폼에 채워넣기 위해서. 
	@GetMapping("/member/updateform")
	/* Model model 은 Spring MVC 컨트롤러에서 view(JSP) 에 데이터를 전달하기 위해 쓰는 바구니 
	* 컨트롤러에서 데이터를 조회하거나 가공 후, JSP 같은 view page 에서 그 데이터를 보여줘야함 
	* 그냥 return 값만으로는 데이터 전달이 안되니, Model 이라는 객체를 제공해서 담아주는 역할임. 
	* Spring 이 컨트롤러 메서드를 호출할 때, Model 타입의 파라미터를 자동으로 생성함. 
	* model.addAttribute("키", 값) 형태로 데이터를 담음. 리턴하는 뷰 이름에 해당 JSP 가 랜더링 될때, 
	* request 객체에 자동으로 옮겨 담겨서 jsp 에서 EL ${키}로 꺼내 쓸 수 있음.
	* 
	* 이 경우에는 회원 목록을 JSP에 보여줘야해서 Model 을 사용한 것. 
	* /member/list 요청이 들어옴 → DB에서 회원 목록 가져옴 (dao.selectAll())
	* 그런데 JSP는 컨트롤러 내부 변수에는 접근 못함 → 데이터를 뷰에 넘기는 통로 필요
	* 그 통로가 **Model**임 여기서는 dto에 담긴 num 을 jsp 로 넘기는 것임 ! 그 바구니 !!!
	*/
	public String updateForm(int num, Model model) { // @ReqeustParam 은 생략이 가능함(매개변수와 파라미터명이 일치하기 때문)
		// 수정할 회원의 정보를 얻어와서 
		MemberDto dto = service.getMember(num); // num으로 해당 회원 조회
		// Model 객체에 담고 
	    model.addAttribute("dto", dto);    // JSP로 전달
	    // view page 로 forward 이동해서 응답 
	    return "member/updateform";
	}
	
	// 수정 처리
    @PostMapping("/member/update")
    public String updateForm(MemberDto dto) {
    	
    	// 수정할 회원의 정보가 MemberDto 객체에 담겨서 전달된다. 
    	 service.updateMember(dto);
        
        return "member/update";
        /*
         *	update() 메서드에서 그냥 "member/list"를 리턴하면,Spring이 JSP 파일만 바로 보여준다.
			이때 list() 메서드는 전혀 실행되지 않으므로 model이 비어 있어서 목록이 안 나오게된다.
			근데 새로고침을 하게되면 수정된 데이터가 보임. 결과적으로 redirect 를 쓰면 
			"update → 목록 새로 조회" 흐름을 자동으로 실행하게 되고, 컨트롤러 메서드(list())가 실행
			실행 중 model.addAttribute("list", ...)를 담고, JSP가 그걸 보여주게되어 출력이되는 것임.
			
			또는 member 에 update.jsp 페이지를 만들면 된다. 
         */
    }
    
    // 삭제
    @GetMapping("/member/delete")
    public String delete(@RequestParam int num, Model model) { 
    	// 매개변수에 int num 을 선언하면 요청 파라미터 중에서 num 이라는 파라미터 명으로 전달되는
    	// 문자열을 자동추출해서 Integer.parseInt() 를 수행해서 실제 int 값으로 바꾼다음 
    	// 해당 값을 매개 변수에 전달해준다. 
    	// int 값으로 바꿀 수 없는 문자열이 넘어오면 에러가 발생한다. 
    	
    	/* @RequestParam 은 HTTP 요청 파라미터를 메소드 매개변수로 매핑해주는 어노테이션 
    	*  즉, GET 이나 POST 요청으로 넘어온 값을 이름으로 꺼내서 변수에 넣어줌 
    	*  Spring MVC는 @RequestParam 을 안 붙여도, 
    	*  요청 파라미터 이름과 메서드 매개변수 이름이 동일하면 자동 바인딩을 해주기에 생략가능 
    	*  이름이 다르거나 기본값이 필요하면 어노테이션을 붙인다.  
    	*/
    	 service.deleteMember(num);
        model.addAttribute("num", num);
        return "redirect:/member/list";
    }
	
}
