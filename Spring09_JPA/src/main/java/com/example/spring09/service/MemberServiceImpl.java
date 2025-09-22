package com.example.spring09.service;

import java.awt.print.Pageable;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.spring09.dto.MemberDto;
import com.example.spring09.dto.MemberListRequest;
import com.example.spring09.dto.MemberPageResponse;
import com.example.spring09.repository.MemberRepository;
import com.example.spring09.entity.Member;
import lombok.RequiredArgsConstructor;


@Service // 서비스 클래스에 붙여줄 어노테이션
@RequiredArgsConstructor // lombok 이 생성자를 자동으로 만들어주도록 한다.
public class MemberServiceImpl implements MemberService {
	
	// 한페이지에 몇개의 row 를 출력할 것인지에 대한 값 
	final int PAGE_ROW_COUNT=10;
	// 페이징 처리 UI 에 페이지 번호를 몇개씩 출력할지에 대한 값 
	final int PAGE_DISPLAY_COUNT=5;
	
	// JPA Repository 주입 (생성자주입)
	private final MemberRepository memberRepo;
	
	
	// select 전용 메소드는 @Transactional(readOnly=true) 를 붙이면 안전하다
	// 의도치 않은 수정, 삭제를 방지하기 위해 
	@Transactional(readOnly=true)
	@Override
	public List<MemberDto> getAll() {
		// 전체 회원목록을 얻어낸다(Entity 의 목록)
		/*
		List<Member> list = memberRepo.findAll(); // 서비스에서 repository주입받은 객체를 사용해서 목록을 얻어낸다 
												  // 이후 entity 의 목록을 dto 의 목록으로 만들어야한다 
		
		List<MemberDto> dtoList = new ArrayList<>();
		// 반복문 돌면서
		for(Member tmp:list) {
			// entity 하나당 dto 하나에 넣어서 
			MemberDto dto = MemberDto.builder()
					.num(tmp.getNum())
					.name(tmp.getName())
					.addr(tmp.getAddr())
					.build();
			// dto 목록에 담는다 
			dtoList.add(dto);
		}
		*/
		/*
		 *  Entity 의 List 를 stream 으로 만들어서 map() 함수를 이용해서
		 *  stream 에 저장된 Entity 를 dto 로 변경한 다음 
		 *  List 로 변경한다 
		 *  
		 *  클래스명 :: static 메소드명 은 클래스안에 만들어진 static 메소드를 참조하는 표현식이다
		 *  즉, .map() 함수 안에서 사용될 메소드를 미리만들어진 메소드를 참조해서 전달하는 방식이다. 
		 *  
		 *  stream 은 자바스크립트스럽게 자바를 사용하기 위함 
		 *  
		 */
		
		// 1. id 칼럼에 대해서 default 로 오름차순 정렬된 List 가 반환된다
		//List<MemberDto> dtoList=memberRepo.findAll().stream().map(MemberDto :: toDto).toList();
		
		/* 
		* 2. findAll(Sort, sort) List<Member> -> findAll 하면서  어떠한 칼럼에 대해서 오름차순, 내림차순 정렬할 수 있다.
		* Sort.Direction.ASC or Sort.Directuin.DESC
		* "num" 은 num 이라는 칼럼에 대해서 정렬하라는 의미
		*/
		//List<MemberDto> dtoList=memberRepo.findAll(Sort.by(Sort.Direction.DESC,"num"))	
			//	.stream().map(MemberDto :: toDto).toList();
		
		// 3. repository 인터페이스에 정해진 형식의 메소드를 만들어서 정렬된 결과를 얻어낼 수도 있다.
		//List<MemberDto> dtoList = memberRepo.findAllByOrderByNameAsc()
			//	.stream().map(MemberDto :: toDto).toList();
		
		// 4. repository 인터페이스에 작성한 JPQL 을 이용해서 정렬된 결과 얻어내기 
		//List<MemberDto> dtoList = memberRepo.findAllQuery()	
			//	.stream().map(MemberDto :: toDto).toList();
		
		// 5. repository 인터페이스에 작성한 Native Query 문을 이용해서 정렬된 결과 얻어내기 
		List<MemberDto> dtoList=memberRepo.findAllNativeQuery()	
				.stream().map(MemberDto :: toDto).toList();
		return dtoList;
	}

	@Transactional(readOnly=true)
	@Override
	public MemberDto getMember(int num) {
		// 번호를 이용해서 Member entity 객체를 얻어낸 다음
		//Member entity = memberRepo.findById(num).get(); 이것과 같은 방법은 아래와 같다. 아래의 경우 예외 발생의 후처리를 포함한다.
		
		// num 에 해당하는 Member entity 를 얻어내는데 만일 존재하지 않는다면 예외발생시키기(후처리)
		Member entity = memberRepo.findById(num)
				.orElseThrow(()-> new IllegalArgumentException("회원이 존재하지 않습니다 num="+num));
		
		// entity 를 toDto 메소드를 이용해서 Dto 로 변경해서 리턴한다
		return MemberDto.toDto(entity);
	}

	@Transactional
	@Override
	public MemberDto addMember(MemberDto dto) {
		/*
		 *  dto 를 Entity 로 변경해서 save() 메소드에 전달하면 된다.
		 *  
		 * 	- Entity 의 id 필드에 해당하는 정보가 없으면 insert 된다
		 *  - Entity 의 id 필드에 해당하는 정보가 DB 에 이미 존재하면 update 된다. 
		 * 	- save() 는 추가와 수정의 겸용 
		 */
		// insert or update  된 entity 를 리턴해준다. 
		Member m = memberRepo.save(dto.toEntity()); // entity 객체에 toEntity 메소드를 만들었었는데 MemberDto 로 옮김
		// memberDto 의 num 이 integer(참조데이터 타입) 이기에 null 일 수도있다.  
		// 방금 추가한 회원의 정보를 리턴해준다. 
		return MemberDto.toDto(m);
		
	}
	
	
	/*
	 *  Entity 를 수정해서 DB 에 반영되게 하려면 @Transactional 은 필수 
	 *  Entity 변경 감지 하기 위해 
	 */
	@Transactional
	@Override
	public void updateMember(MemberDto dto) {
		//memberRepo.save(Member.toEntity(dto));
		
		// 위의 경우는 잘못된 데이터가 DB 에 insert 될 위험이 있기 때문에 아래의 방식을 많이 사용한다
		// entity를 이용해서 번호를 갖고 와서 
		Member entity = memberRepo.findById(dto.getNum())
				// 번호가 없으면 exception 을 발생시키고  
				.orElseThrow(()-> new IllegalArgumentException("수정할 회원이 존재하지 않아요 num="+dto.getNum()));
		// 존재할 경우 entity 객체를 수정하면 DB 에 자동 반영된다
		entity.setName(dto.getName());
		entity.setAddr(dto.getAddr());
	}

	@Transactional
	@Override
	public MemberDto deleteMember(int num) {
		
		// 만일 삭제할 entity 가 존재하지 않으면 
		if(!memberRepo.existsById(num)) {
			throw new IllegalArgumentException("삭제할 회원이 존재하지 않아요 num="+num);
		}
		
		Member m = memberRepo.findById(num).get();
		// 번호를 이용해서 삭제(실패시 예외가 발생하지는 않는다)
		memberRepo.deleteById(num);
		//방금 삭제한 회원의 정보를 리턴해준다.(삭제할 회원의 정보를 m 에 담아서 삭제 후 리턴)
		return MemberDto.toDto(m);
	}
	
	
	@Override
	public MemberPageResponse getPage(MemberListRequest request) {
		// 페이지 번호
		int pageNum = request.getPageNum();
		
		// (1)num 에 대해서 내림차순 정렬하겠다는 Sort 객체 
		Sort sort = Sort.by(Sort.Direction.DESC, "num");
		
		// (2)pageNum 과 page row count 와 정렬 객체(sort)를 전달해서 원하는 PageRequest 를 만들어내고
		// 1을빼주는 이유는 시작할때 내부 시스템적으론 0페이지로 시작, 우리가 생각하는 1페이지는 곧 0페이지로 계산해줘야한다.
		PageRequest pageRequest  = PageRequest.of(pageNum-1, PAGE_ROW_COUNT, sort);

		// (3)org.springframework.data.domain 패키지의 Page type 을 import 해야한다 
		String keyword = request.getKeyword();
		Page<Member> page = null;
		// 만일 키워드가 비었으면 모든 회원정보중에서 원하는 페이지의 결과 얻어내기 
		if(keyword == null || keyword.isEmpty()) { // keyword 가 null 이면 isEmpty()를 호출할수 없어서, keyword==null || 처럼 null 체크를 해줘야함
			page=memberRepo.findAll(pageRequest);
		}else { // 키워드가 있으면 키워드에 해당하는 결과 얻어내기 
			// if 문 안에 if 문은 가독성이 떨어져서 switch 문으로 작성함
			/*	 
			 	 검색조건으로 분기한다 
			 	 
				 키워드: 무엇을 찾을 것인가 (검색어)
				 검색조건: 어디서 찾을 것인가 (검색 대상 필드)
				 그래서 분기문을 keyword 로 나눌 필요는 없고, 검색조건(condition) 으로 나누는 게 맞다 ✔
			 */
			switch(request.getCondition()) {
				case "name" :
					page = memberRepo.findByNameContaining(keyword, pageRequest);
					break;
				case "addr" :
					page = memberRepo.findByAddrContaining(keyword, pageRequest);
					break;
				case "name_addr" :
					page = memberRepo.findByNameContainingOrAddrContaining(keyword, keyword, pageRequest);
					break;
			}
		}

		
		// (4)Page 객체(Page<Member>)를 stream 으로 만들어서 dto 의 List 를 얻어낸다
		List<MemberDto> list = page.stream().map(MemberDto ::toDto).toList();
		
		//하단 시작 페이지 번호 
		int startPageNum = 1 + ((pageNum-1)/PAGE_DISPLAY_COUNT)*PAGE_DISPLAY_COUNT;
		//하단 끝 페이지 번호
		int endPageNum=startPageNum+PAGE_DISPLAY_COUNT-1;
		//전체 페이지의 갯수 구하기 (Page 객체에 이미 계산되어서 들어 있다)
		int totalPageCount=page.getTotalPages();
		//끝 페이지 번호가 이미 전체 페이지 갯수보다 크게 계산되었다면 잘못된 값이다.
		if(endPageNum > totalPageCount){
			endPageNum=totalPageCount; //보정해 준다. 
		}
		
		// Entity 의 Stream 을 Dto 의 Stream 으로 만드는것
		return MemberPageResponse.builder()
				.list(list)
				.PageNum(pageNum)
				.totalPageCount(totalPageCount)
				.startPageNum(startPageNum)
				.endPageNum(endPageNum)
				.build();
	}
}