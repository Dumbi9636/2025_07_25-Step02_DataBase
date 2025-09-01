package com.example.spring04.repository;

import java.util.List;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;
import com.example.spring04.dto.MemberDto;
/*	<설명서대로 동작하는 물건>
 *	MemberDao 의 메소드를 구체적으로 구현하는 곳
 *	여기서 SqlSession 을 통해 MyBatis 의 SQL 매퍼(member.selectAll) 를 호출 
 *	즉, 이 클래스가 DB 와 직접 대화하는 실제 코드를 가진다 이전의 DAO 라고 보면 됨
 *	JSP 의 DAO 에서 SQL 까지 바로 실행했던 걸, spring boot 에서는 이 구현체에서 함. 
 */


// Dao 에는 보통 @Repository 어노테이션을 붙여서 bean 을 만든다 (내부적으로 추가기능을 제공해준다)
// bean 이된다는 의미는 Spring 이 관리하는 객체가 된다. 
@Repository
public class MemberDaoImpl implements MemberDao {
	
	// MyBatis 를 사용할때 필요한 핵심 객체 (session 객체를 사용한다)
	private final SqlSession session;
	
	// 생성자를 이용해서 의존 객체를 주입받는 것이 더 일반적이다 (lombok 의 기능을 이용하면 생략 가능하다)
	//@Autowired // 생성자가 오직 1개인 경우에는 생략 가능하다. 
	public MemberDaoImpl(SqlSession session) {
		this.session=session;
	}
	
	@Override
	public List<MemberDto> selectAll() {
		/*	.selectList() 를 호출하면 리턴 type 은 무조건 List<T> 이다
		 *  List 의 generic type T 는 그때 그때 다르다
		 *  (뭐로 받겠다~ 라고 설정하는것이기에 여기서는 MemberDto) 
		 *  resultType 이 바로 List 의 generic type 으로 설정된다. 
		 */
		List<MemberDto> list = session.selectList("member.selectAll");
		return list;
	}

	@Override
	public void insert(MemberDto dto) {
		// session 을 통해서 insert, delete, update, select 를 한다. 
		session.insert("member.insert", dto);
	}

	@Override
	public int update(MemberDto dto) {
		
		// return 0; 여기서 0 정수값은 update 된 row 의 갯수를 의미 
		
		// update 를 실행하고 update 된 row 의 갯수를 바로 리턴하기
		return session.update("member.update", dto);
		// 여기서 리턴된 정수 값이 1보다 크면, 0 이상이면 리턴
	}

	@Override
	public int deleteByNum(int num) {
		// delete 를 실행하고 delete 된 row 의 갯수를 바로 리턴하기 
		return session.delete("member.delete", num);
	}

	/* select 되는 row 가 1개면 session.selectOne() 메소드를 사용하고
	 * select 되는 row 가 여러개일 가능성이 있으면 session.selectList() 메소드를 사용해서 select 한다. 
	 */
	@Override
	public MemberDto getByNum(int num) {
		
		MemberDto dto=session.selectOne("member.getByNum", num);
		return dto;
	}
	
	
	
}
