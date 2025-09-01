package com.example.spring04.repository;

import java.util.List;

import com.example.spring04.dto.MemberDto;
/*	<사용설명서>
 *	여기서는 무엇을 할지 만 정의 
 *	어떻게 할지는 전혀 없음 
 *	즉, 이 기능을 제공해야한다 라는 약속서 역할임 
 *	Jsp 프로젝트에서 DAO 클래스가 곧바로 기능구현까지 했다면, 여기서는 구현을 MemberDaoImpl 쪽에 위임하는 구조  
 */
public interface MemberDao {
	public List<MemberDto> selectAll();
	public void insert(MemberDto dto);
	// update, delete 는 수정, 삭제된 row 의 갯수를 리턴하는 모양으로 Dao 메소드를 정의한다
	public int update(MemberDto dto);
	public int deleteByNum(int num);
	public MemberDto getByNum(int num);
	
}
