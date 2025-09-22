package com.example.spring09;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.spring09.entity.Client;
import com.example.spring09.entity.Dept;
import com.example.spring09.entity.Emp;
import com.example.spring09.entity.Member;
import com.example.spring09.repository.DeptRepository;
import com.example.spring09.repository.EmpRepository;
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
	
	@Autowired
	EmpRepository empRepo;
	
	@Autowired
	DeptRepository deptRepo;
	
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
			
			// Client sample 데이터
			Client c1 = Client.builder().userName("박준일").build();
			Client c2 = Client.builder().userName("박종복").build();
			Client c3 = Client.builder().userName("서종훈").build();
			
			
			//EntityManager 객체의 persist() 메소드를 이용해서 객체에 저장된 정보를
			// 영속화(영구저장) 할수 있다. 
			em.persist(m1);
			em.persist(m2);
			em.persist(m3);
			em.persist(c1);
			em.persist(c2);
			em.persist(c3);
			// 반복문 이용해서 sample 데이터를 많이 저장하기 
			for(int i=0; i<100; i++) {
				Member tmp=Member.builder().name("이름"+i).addr("주소"+i).build();
				em.persist(tmp);
			}
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
		
		
		// 부서 정보(Dept) 를 먼저 저장하기
		Dept d10 = new Dept(10, "ACCOUNTING", "NEW YORK");
		Dept d20 = Dept.builder().deptno(20).dname("RESEARCH").loc("DALLAS").build();
		Dept d30 = Dept.builder().deptno(30).dname("SALES").loc("CHICAGO").build();
		Dept d40 = new Dept(40, "OPERATION", "BOSTON");
		/*
		deptRepo.save(d10);
		deptRepo.save(d20);
		deptRepo.save(d30);
		deptRepo.save(d40);
		*/
		// saveAll 로 List<dept>를 전달해서 한번에 저장할 수도 있다. 
		deptRepo.saveAll(List.of(d10,d20,d30,d40));
		
		DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd-MM-yyyy");

		// 2) 사원 저장 (Builder 사용)
		empRepo.saveAll(List.of(
		    Emp.builder().empno(7369).ename("SMITH").job("CLERK").mgr(7902)
		        .hiredate(LocalDate.parse("17-12-1980", fmt)).sal(800.0).comm(null).dept(d20).build(),
		    Emp.builder().empno(7499).ename("ALLEN").job("SALESMAN").mgr(7698)
		        .hiredate(LocalDate.parse("20-02-1981", fmt)).sal(1600.0).comm(300.0).dept(d30).build(),
		    Emp.builder().empno(7521).ename("WARD").job("SALESMAN").mgr(7698)
		        .hiredate(LocalDate.parse("22-02-1981", fmt)).sal(1250.0).comm(500.0).dept(d30).build(),
		    Emp.builder().empno(7566).ename("JONES").job("MANAGER").mgr(7839)
		        .hiredate(LocalDate.parse("02-04-1981", fmt)).sal(2975.0).comm(null).dept(d20).build(),
		    Emp.builder().empno(7654).ename("MARTIN").job("SALESMAN").mgr(7698)
		        .hiredate(LocalDate.parse("28-09-1981", fmt)).sal(1250.0).comm(1400.0).dept(d30).build(),
		    Emp.builder().empno(7698).ename("BLAKE").job("MANAGER").mgr(7839)
		        .hiredate(LocalDate.parse("01-05-1981", fmt)).sal(2850.0).comm(null).dept(d30).build(),
		    Emp.builder().empno(7782).ename("CLARK").job("MANAGER").mgr(7839)
		        .hiredate(LocalDate.parse("09-06-1981", fmt)).sal(2450.0).comm(null).dept(d10).build(),
		    Emp.builder().empno(7839).ename("KING").job("PRESIDENT").mgr(null)
		        .hiredate(LocalDate.parse("17-11-1981", fmt)).sal(5000.0).comm(null).dept(d10).build(),
		    Emp.builder().empno(7844).ename("TURNER").job("SALESMAN").mgr(7698)
		        .hiredate(LocalDate.parse("08-09-1981", fmt)).sal(1500.0).comm(0.0).dept(d30).build(),
		    Emp.builder().empno(7900).ename("JAMES").job("CLERK").mgr(7698)
		        .hiredate(LocalDate.parse("03-12-1981", fmt)).sal(950.0).comm(null).dept(d30).build(),
		    Emp.builder().empno(7902).ename("FORD").job("ANALYST").mgr(7566)
		        .hiredate(LocalDate.parse("03-12-1981", fmt)).sal(3000.0).comm(null).dept(d20).build(),
		    Emp.builder().empno(7934).ename("MILLER").job("CLERK").mgr(7782)
		        .hiredate(LocalDate.parse("23-01-1982", fmt)).sal(1300.0).comm(null).dept(d10).build()
		));
		
		
		// EmpRepository 객체를 이용해서 select 작업하기 
		List<Emp> empList = empRepo.findAllByOrderByEnameAsc();
		for(Emp tmp:empList) {
			System.out.println(tmp.getEname()+"|"+tmp.getDept().getDeptno()+"|"+tmp.getDept().getDname());
		}
		
	}
	
	
	
	
	public static void main(String[] args) {
		SpringApplication.run(Spring09JpaApplication.class, args);
	}
}
