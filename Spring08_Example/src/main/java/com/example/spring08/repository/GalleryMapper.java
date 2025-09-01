package com.example.spring08.repository;

import java.util.List;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.example.spring08.dto.GalleryDto;
import com.example.spring08.dto.GalleryImageDto;

@Mapper // 흐름: mapper 어노테이션 -> @repository class 가 자동 생성 -> bean 으로 관리 -> service 에서 의존객체 주입해서 사용 
		// 매개변수에 전달 된 값이 sql 문의 #{} 에 자동 바인딩 됨 
public interface GalleryMapper {
	
	// mapper xml 에 작성한 내용을 사용해야 하기 때문에 어노테이션 없이 메소드를 만든다.
	public List<GalleryDto> getListWithImages(); //  <select id ="getListWithImages">
	
	
	// 게시글 추가 
	@Insert("""
		INSERT INTO gallery(num, title, writer, content) 
		VALUES(#{num},#{title},#{writer},#{content})
	""")
	public void insert(GalleryDto dto); // GalleryDto dto 에 전달된 매개변수의 값이 #{} 에 자동으로 바인딩 됨 
	
	
	// 이미지 저장
	@Insert("""
			INSERT INTO gallery_image 
			(num, galleryNum, saveFileName) 
			VALUES
			(gallery_image_seq.NEXTVAL, #{galleryNum}, #{saveFileName})
			""")
	public void insertImage(GalleryImageDto dto);
	
	
	
	/*
	 * 메소드의 리턴 type 이 GalleryDto 이기 때문에 
	 * SELECT 된 ROW 1개의 정보가 자동으로 GalleryDto 객체에 담겨서 리턴된다.
	 * 단) SELECT 문의 칼럼명과 GalleryDto 의 필드명이 일치해야 자동으로 담긴다. 
	 * 메소드의 매개변수의 type 이 해당 SELECT 문의 parameterType 으로 설정된다. 
	 */
	// 글 상세보기를 조회하는 메소드
	@Select("""
			SELECT g.num, title, writer, content, 
        		TO_CHAR(g.createdAt, 'YYYY-MM-DD HH24:MI:SS') AS createdAt,
        		profileImage
            FROM gallery g
            JOIN users u ON writer = userName
            WHERE g.num = #{num}
			""")
	public GalleryDto getData(int num);
	
	
	
	/*
	 * parameterType 은 int 
	 * SELECT 된 ROW 가 여러개니까 return type 이 List 이고 
	 * List 의 generic type 이 GalleryImageDto 이니까 resultType 은 GalleryImageDto 가 된다.
	 */
	// 이미지 목록 
	@Select("""
		SELECT num, saveFileName, TO_CHAR(createdAt, 'YYYY-MM-DD HH24:MI:SS') AS createdAt
        FROM gallery_image
        WHERE galleryNum = #{num}
        ORDER BY num ASC
	""")
	public List<GalleryImageDto> getImageList(int num);
	
	
	
	
	/*
	 * 메소드의 리턴 type 이 select 문의 resultType 으로 설정되기때문에
	 * selelct 된 row 의 num 이 return 된다
	 */
	// 저장할 글번호를 리턴해주는 메소드 
	@Select("SELECT board_seq.NEXTVAL AS num FROM DUAL")
	public int getSequence();
	
}
