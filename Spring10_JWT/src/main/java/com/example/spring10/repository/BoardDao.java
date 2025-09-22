package com.example.spring10.repository;

import java.util.List;
import com.example.spring10.dto.BoardDto;

public interface BoardDao {
	// startrow num 과 end row num 을 list 에 담아서 리턴 해주는 select 메소드
	public List<BoardDto> selectPage(BoardDto dto); // 검색키워드가 있을때랑 없을때의 경우 모두다 같은 메소드를 사용하도록 수정할 예정임
	public int getCount(BoardDto dto);
	
	public void insert(BoardDto dto);
	public BoardDto getByNum(int num);
	public BoardDto getByDto(BoardDto dto);
	public int delete(int num); // 글 삭제
	public int update(BoardDto dto); // 수정된 row 의 수를 반환해야해서 int 로 
	
	
}
