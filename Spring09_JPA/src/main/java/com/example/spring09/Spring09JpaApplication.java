package com.example.spring09;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.example.spring09.entity.Member;
import com.example.spring09.repository.MemberRepository;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;

@SpringBootApplication
public class Spring09JpaApplication {
	/*
	 *  spring data JPA 를 dependency 로 추가하고 application.properties 파일에 적절한 jpa 설정을 해 놓으면
	 *  EntitiManagerFactory 가 bean 으로 관리 된다.
	 */
	//의존객체 주입 받기 
	@Autowired
	EntityManagerFactory emf;
	
	@Autowired
	MemberRepository memberRepo;
	
	
	@PostConstruct
	public void helloJPA() {
		//EntityManager 객체를 얻어와서 
		EntityManager em=emf.createEntityManager();
		//트랜젝션을 시작한다 
		EntityTransaction tx=em.getTransaction();
		tx.begin();
		try {
			//Entity 객체를 생성하고
			Member m1=Member.builder().name("김구라").addr("노량진").build();
			Member m2=Member.builder().name("해골").addr("행신동").build();
			Member m3=Member.builder().name("원숭이").addr("동물원").build();
			//EntityManager 객체의 persist() 메소드를 이용해서 객체에 저장된 정보를
			// 영속화(영구저장) 할수 있다. 
			em.persist(m1);
			em.persist(m2);
			em.persist(m3);
			tx.commit(); //commit 하는 시점에 저장 
		}catch(Exception e) {
			e.printStackTrace();
			tx.rollback(); //예외가 발생한 경우 저장하지 않고 취소 할수 있다.
		}finally {
			em.close();
		}
		
		
		
		EntityManager em2=emf.createEntityManager();
		/*
		 *  MEMBER_INFO 라는 테이블의 별칭을 m 이라고 하고  
		 *  m 의 모든 칼럼의 정보를 select 해서 Member.class type 객체에 담겠다는 의미 
		 *  row 가 여러개인 경우 .getResultList() 메소드를 호출해서 결과를 얻어낸다.
		 *  Member.class type 을 전달했기 때문에 List 의 generic 이 Member type 이 된다.
		 */
		List<Member> members=em2.createQuery("SELECT m FROM MEMBER_INFO m", Member.class)
				 				.getResultList();
		for (Member mem : members) {
		    System.out.println(mem.getNum() + " : " + mem.getName() + " / " + mem.getAddr());
		}
		em2.close();
		
		
		
		
		// MemberRepository 객체를 이용해서 select 작업하기
		List<Member> list = memberRepo.findAll();
		for(Member tmp:list) {
			System.out.println(tmp.getNum() +"|" +tmp.getName() +"|"+tmp.getAddr());
			
		}
	}
	
	
	
	
	
	public static void main(String[] args) {
		SpringApplication.run(Spring09JpaApplication.class, args);
	}

}
