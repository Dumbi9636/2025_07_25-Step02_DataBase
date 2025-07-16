package test.dto;

public class BookDto {
	//필드 
	private int num;
	private String title;
	private String author;
	private String publisher;
	
	//디폴트 생성자
	public BookDto() {}

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

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}
	
	//setter, getter
	
	
}
