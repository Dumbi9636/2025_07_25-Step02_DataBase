package com.example.spring09.repository;

import com.example.spring09.entity.Member;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/*
 *  JpaRepository 인터페이스를 상속받은 인터페이스를 정의하는것 만으로 구현클래스가 만들어지고
 *  해당 클래스로 생성된 객체가 bean 으로 관리가 된다. 
 *  Dao 가 자동으로 만들어진다고 생각하면 된다. 이 객체가 필요한 곳은 service 단이다. 
 *  
 *  ex) class xxx implements MemberRepository 가 만들어지고 자연스럽게 객체가 생성되고 spring bean container 에서 관리가되며
 *  필요한 곳에서 의존객체 주입을 받아서 사용한다. 그 주입받는 곳은 service 이다. 
 *  
 *  인터페이스를 정의하는것만으로 구현체 생성, bean으로 관리 
 *  
 *  @mapper 어노테이션을 인터페이스에다가 붙이는 것만으로 구현클래스가 만들어지고 해당 클래스의 객체가 bean으로 관리되는것과 같다. 
 *  
 *  extends JpaRepository< Entity 클래스명 , 해당 Entity 에서 PK 의 data type > 
 */
public interface MemberRepository extends JpaRepository<Member, Integer> {
	/*
	 * 	미리 정해진 형식으로 메소드를 만들면 알아서 정렬된다.
	 * 	findAllByOrderByNumDesc()
	 * findAllByOrderByNumAsc()
	 * findAllByOrderByNameDesc()
	 * findAllByOrderByAddrDesc()
	 * 
	 * findAllByOrderBy칼럼명Asc()
	 * findAllByOrderBy칼럼명Desc()
	 * 칼럼명을 Camel Case 로 작성하면 된다.
	 */
	
	public List<Member> findAllByOrderByNumDesc();
	public List<Member> findAllByOrderByNameAsc();
	
	
	/*
	 * 	JPQL 문법 형식의 select 문을 직접 작성해서 실행의 결과를 얻어낼수도 있다. 
	 * 	- sql 처럼 생겼지만 entity 중심으로 작성하는 객체 지향 쿼리 언어 
	 * 	- DB 종류에 종속되지 않는다
	 */
	@Query("SELECT m FROM MEMBER_INFO m ORDER BY m.num DESC")
	public List<Member> findAllQuery(); // 메소드명은 마음대로 지을 수 있음.
	
	/*
	 * 	특정 DB 에서만 실행될 수 있는 원래의 query 문을 실행할수도 있다.
	 * 	- (value="Native 쿼리문", nativeQuery = true) 옵션을 주면된다. 
	 */
	@Query(value="SELECT num, name, addr FROM MEMBER_INFO ORDER BY num DESC", nativeQuery = true)
	public List<Member> findAllNativeQuery();
}
