package com.example.spring08.service;

import java.util.List;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import com.example.spring08.dto.BoardDto;
import com.example.spring08.dto.BoardListResponse;
import com.example.spring08.repository.BoardDao;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class BoardServiceImpl implements BoardService {
	
	
	// 하나의 서비스에서 여러개의 dao 에 의존하는 경우도 많다
	private final BoardDao boardDao;
	
	// pageNum 또는 keyword 에 해당하는 글목록과 추가 정보를 BoardListResponse 객체에 담아서
	// 리턴하는 메소드
	@Override
	public BoardListResponse getBoardList(int pageNum, BoardDto dto) {
		// keyword 와 pageNum 은 BoardController 단에서 처리해서 넘겨줌 
		
		
		// 현 페이지에 몇개씩 표시할 것인지
		final int PAGE_ROW_COUNT=5;
		
		// 하단 페이지를 몇개씩 표시할 것인지
		final int PAGE_DISPLAY_COUNT=5;
		
		// 보여줄 페이지의 시작 ROWNUM 
		int startRowNum=1+(pageNum-1)*PAGE_ROW_COUNT; //공차수열
		// 보여줄 페이지의 끝 ROWNUM
		int endRowNum=pageNum*PAGE_ROW_COUNT; // 등비수열 
		
		//하단 시작 페이지 번호 (정수를 정수로 나누면 소수점이 버려진 정수가 나온다)
		int startPageNum = 1 + ((pageNum-1)/PAGE_DISPLAY_COUNT)*PAGE_DISPLAY_COUNT;
		//하단 끝 페이지 번호
		int endPageNum=startPageNum+PAGE_DISPLAY_COUNT-1;
		
		
		
		// 전체글의 갯수 ( 검색조건에 맞는 글의갯수 )
		int totalRow = boardDao.getCount(dto);
		//전체 페이지의 갯수 구하기
		int totalPageCount=(int)Math.ceil(totalRow/(double)PAGE_ROW_COUNT);
		//끝 페이지 번호가 이미 전체 페이지 갯수보다 크게 계산되었다면 잘못된 값이다.
		if(endPageNum > totalPageCount){
			endPageNum=totalPageCount; //보정해 준다. 
		}
		// startRowNum 과 endRowNum 을 BoardDto 객체에 담아서
		dto.setStartRowNum(startRowNum);
		dto.setEndRowNum(endRowNum);
		
		// 글 목록얻어오기 (검색 키워드가 있다면 조건에 맞는 목록만 얻어낸다)
		List<BoardDto> list = boardDao.selectPage(dto);
		
		// query 문자열을 미리 구성해서 view page 에 전달하도록 한다.
		/*
		 *  검색 키워드가 없으면 query="" 빈문자열
		 *  검색 키워드가 있으면 query="&search=검색조건&keyword=검색어" 형식의 문자열
		 */
		String query="";
		if(dto.getKeyword() != null) {
			query = "&search="+dto.getSearch()+"&keyword="+dto.getKeyword();
		}
		
		// 한줄 coding 으로 BoardListResponse 객체를 만들어서 리턴하기 		
		return BoardListResponse.builder()
				.list(list)
				.pageNum(pageNum)
				.keyword(dto.getKeyword()) // keyword 는 dto 에 있고 dto.getKeyword() 로 가져올 수 있다. 
				.search(dto.getSearch())
				.query(query)
				.startPageNum(startPageNum)
				.endPageNum(endPageNum)
				.totalPageCount(totalPageCount)
				.totalRow(totalRow)
				.build();
	}
	
	
	// 글을 저장하는 메소드 구현체 
	@Override
	public void createContent(BoardDto dto) {
		boardDao.insert(dto);
		
		return ;
	}

	
	// 글 하나의 정보
	@Override
	public BoardDto getDetail(BoardDto dto) {
		
		return boardDao.getByDto(dto);
	}

	
	// 글 삭제
	@Override
	public void deleteContent(int num) {
		// (예외처리)글 작성자와 로그인된 userName 이 동일한지 비교해서 동일하지않으면 예외를 발생시킨다.
		String writer = boardDao.getByNum(num).getWriter();
		String userName = SecurityContextHolder.getContext().getAuthentication().getName();
		if(!writer.equals(userName)) {
			// 500 Error - runtimeException
			throw new RuntimeException("남의 글을 지울수는 없다! 돌아가라!");
		}
		// 삭제하고 삭제된 row 의 갯수를 리턴받는다. 정상 삭제시 1, 삭제실패시 0 이 리턴된다.
		int rowCount = boardDao.delete(num);
		// 삭제 실패했을때 출력할 문자열 
		if(rowCount == 0) {
			throw new RuntimeException("삭제 실패!");
		}	
	}
	
	
	// 수정할 글 정보를 보여주기 위한 서비스 메소드 구현체
	@Override
	public BoardDto getData(int num) {
		
		return boardDao.getByNum(num);
	}

	
	// 글 수정 요청 처리 메소드
	@Override
	public void updateContent(BoardDto dto) {
		// (예외처리)글 작성자와 로그인된 userName 이 동일한지 비교해서 동일하지않으면 예외를 발생시킨다.
		String writer = boardDao.getByNum(dto.getNum()).getWriter();
		String userName = SecurityContextHolder.getContext().getAuthentication().getName();
		if(!writer.equals(userName)) {
			// 500 Error - runtimeException
			throw new RuntimeException("남의 글을 지울수는 없다! 돌아가라!");
		}
		int rowCount = boardDao.update(dto);
		if(rowCount == 0) {
			throw new RuntimeException("글 수정 실패!");
		}
	}
}
