package pcoop.backend.dto;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class BoardDTO {
	private Integer seq;
	private String writer;
	private Timestamp write_date;
	private Integer view_count;
	private String title;
	private String category;
	private String id;
	private String contents;
	
	
	
	
	public BoardDTO() {}



	
	

	public BoardDTO(Integer seq, String writer, Timestamp write_date, Integer view_count, String title, String category,
			String id, String contents) {
		super();
		this.seq = seq;
		this.writer = writer;
		this.write_date = write_date;
		this.view_count = view_count;
		this.title = title;
		this.category = category;
		this.id = id;
		this.contents = contents;
	}






	public Integer getSeq() {
		return seq;
	}




	public void setSeq(Integer seq) {
		this.seq = seq;
	}




	public String getWriter() {
		return writer;
	}




	public void setWriter(String writer) {
		this.writer = writer;
	}




	public Timestamp getWrite_date() {
		return write_date;
	}




	public void setWrite_date(Timestamp write_date) {
		this.write_date = write_date;
	}




	public Integer getView_count() {
		return view_count;
	}




	public void setView_count(Integer view_count) {
		this.view_count = view_count;
	}




	public String getTitle() {
		return title;
	}




	public void setTitle(String title) {
		this.title = title;
	}




	public String getCategory() {
		return category;
	}




	public void setCategory(String category) {
		this.category = category;
	}




	public String getId() {
		return id;
	}




	public void setId(String id) {
		this.id = id;
	}




	public String getContents() {
		return contents;
	}




	public void setContents(String contents) {
		this.contents = contents;
	}
}
	