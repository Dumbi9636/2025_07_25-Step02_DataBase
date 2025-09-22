package com.example.spring09.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.spring09.entity.Emp;

public interface EmpRepository extends JpaRepository<Emp, Integer> {
	
	// 사원 이름에 대해서 오름차순 정렬된 결과를 리턴하는 메소드 추가
	public List<Emp> findAllByOrderByEnameAsc(); // 정해진 규칙으로 메소드명을 만든다. 
	
	/*
	 * 	@Param("deptno" <=> :deptno
	 */
	
	// 실행할 query 문 (JPQL) 을 직접 작성한다
	/*
	 * 여기서 :deptno는 JPQL(named parameter) 문법이다 그냥 변수 자리를 이름 붙여 둔 것. 
	 * 실제 실행 시점에는 메서드의 인자로 받은 값을 여기에 바인딩한다.
	 * @Param("deptno")는 "이 메서드 인자 'deptno'를 JPQL 안의 :deptno에 매핑하겠다"는 뜻이다.
	 * 즉, Java 메서드 인자 ↔ JPQL 파라미터를 연결해 주는 역할이에요.
	 * 
	 * :deptno → JPQL에서 사용하는 파라미터 이름
	 * @Param("deptno") → 메서드 인자를 그 이름과 연결
	 * 둘 이름이 같아야 값이 제대로 들어간다.
	 * 최신 JPA에선 파라미터 이름이 일치하면 @Param 생략 가능하다.
	 */
	@Query("SELECT e FROM Emp e WHERE e.dept.deptno = :deptno ORDER BY e.ename ASC")
	public List<Emp> findEmps(@Param("deptno") Integer deptno); // 메소드명은 우리가 마음대로 만든다.   
	//지금 JPQL은 Emp를 SELECT하는 쿼리이다. Dept를 SELECT하는 게 아니라서, Dept 엔티티를 기준으로 직접 필터링할 수는 없다.
	
	// 메소드에 전달된 매개변수의 순서를 이용해서 값을 바인딩할 수도 있다.
	@Query("SELECT e FROM Emp e WHERE e.dept.deptno = ?1 ORDER BY e.ename ASC")
	public List<Emp> findEmps2(Integer deptno);  
	
	
	/*
	 * 	Emp entity 에 
	 * 	
	 * 	@ManyToOne
	 * 	Dept dept; 
	 * 
	 * 	가 있기 때문에 이걸 활용해서 메소드 만들기
	 * 
	 * 	Dept_Deptno => Emp 의 dept 필드를 타고 들어가 Dept entity 의 deptno 속성을 조건으로 사용 
	 * 	extneds JpaRepository<Emp, Integer> 에서 첫번째 generic type 이 Emp 이기 때문에
	 * 	Emp entity 에서 dept 라는 필드를 타고 들어가는 것이다.
	 * 	(EmpRepository, 반환해야하는 타입이 Emp 이기 때문에 deptno 는 dept 에도 들어있겠지만, emp 에서 찾는 것)
	 */
	// 정해진 규칙으로 메소드명을 작성해서 위와 같은 결과 얻어내기
	public List<Emp> findByDept_DeptnoOrderByEnameAsc(Integer deptno);
	
}
