package com.example.spring08.repository;


import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.example.spring08.dto.MemberDto;

// MyBatis 의 Mapper 를 인터페이스 설정으로 사용할 수 있도록 어노테이션을 추가
// 이 인터페이스를 구현한 클래스가 자동으로 만들어지고 해당 클래스로 만들어진 객체가 bean 으로 등록된다
// 해당 bean 을 Service 혹은 Dao 에 주입을 받아서 사용할 수 있다. 
// Dao 에 주입해서 사용하면 번거로우니 특별한 이유가 없다면  Service 에 주입해서 사용하면 된다. 
@Mapper  
public interface MemberMapper {
	@Select("SELECT num, name, addr FROM member ORDER BY num ASC")
	public List<MemberDto> selectAll();
	
	
	@Select("SELECT num, name, addr FROM member WHERE num=#{num}")
	public MemberDto getByNum(int num);
	
	
	@Delete("DELETE FROM member WHERE num=${num}")
	public int deleteByNum(int num); // delete 된 rowCount 가 리턴된다. 
	
	
	@Update("UPDATE member SET name=#{name}, addr=#{addr} WHERE num=#{num}")
	public int update(MemberDto dto); // update 된 rowCount 가 리턴된다.
	
	/*
	@Insert("""
			INSERT INTO member
			(num, name, addr)
			VALUES(#{num},#{name},#{addr
			})
			""")
	@SelectKey(statement = "SELECT member_seq.NEXTVAL FROM DUAL", 
			   keyProperty = "num", resultType = int.class, before = true)
	public void insert(MemberDto dto);
	*/
	
	// 위의 기능은 Mapper.xml 문서를 활용해서 구현하기
	
	/*
	 * 	어노테이션 없이 메소드만 정의하기
	 * 	Mapper.xml 문서의 namespace="패키지명포함해서 @Mapper 클래스 정의하기"
	 * 	
	 *	메소드명이 xml 문서안에 들어있는 sql 의 id 가 된다. 
	 *	namespace="com.example.spring08.repository.MemberMapper" 
	 *	<insert id = "insert" parameterType="memberDto">
	 */
	public void insert(MemberDto dto);
}
