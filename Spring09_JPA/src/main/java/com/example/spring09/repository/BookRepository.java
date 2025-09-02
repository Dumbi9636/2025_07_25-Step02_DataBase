package com.example.spring09.repository;

import java.util.List;
import com.example.spring09.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface BookRepository extends JpaRepository<Book, Long>{
	public List<Book> findAllByOrderByIdDesc();
	public List<Book> findAllByOrderByTitleAsc();
	
	@Query("SELECT m FROM BOOK_INFO m ORDER BY m.id DESC")
	public List<Book> findAllQuery(); // 메소드명은 마음대로 지을 수 있음.
	
	@Query(value="SELECT id, title, author, publisher FROM BOOK_INFO ORDER BY id DESC", nativeQuery = true)
	public List<Book> findAllNativeQuery();
}
