package test.dto;

import java.util.List;

public class GalleryDto {
	private int num;
	private String title;
	private String writer;
	private String content;
	private String createdAt;
	// 하나의 갤러리 글에 업로드된 사진이 여러개 일 수 있다.
	// 사진 하나당 GalleryImageDto 객체 하나 
	// 사진이 여러개 이니까 List<GalleryImageDto> type 의 필드가 필요하다 
	private List<GalleryImageDto> imageList;
	
	// 프로필 이미지를 출력하기 위한 필드
	private String profileImage;
	
	public void setProfileImage(String profileImage) {
		this.profileImage = profileImage;
	}
	
	public String getProfileImage() {
		return profileImage;
	}
	
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getWriter() {
		return writer;
	}
	public void setWriter(String writer) {
		this.writer = writer;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}
	public List<GalleryImageDto> getImageList() {
		return imageList;
	}
	public void setImageList(List<GalleryImageDto> imageList) {
		this.imageList = imageList;
	}
	
	
	
}
