package pcoop.backend.dto;

public class CalendarDTO {
	private int seq;
	private int project_seq;
	private String title;
	private String contents;
	private String writer;
	private String start_date;
	private String end_date;
	private String color;
	
	
	
	public CalendarDTO() {
		super();
	}
	public CalendarDTO(int seq, int project_seq, String title, String contents, String writer, String start_date,
			String end_date, String color) {
		super();
		this.seq = seq;
		this.project_seq = project_seq;
		this.title = title;
		this.contents = contents;
		this.writer = writer;
		this.start_date = start_date;
		this.end_date = end_date;
		this.color = color;
	}
	public int getSeq() {
		return seq;
	}
	public void setSeq(int seq) {
		this.seq = seq;
	}
	public int getProject_seq() {
		return project_seq;
	}
	public void setProject_seq(int project_seq) {
		this.project_seq = project_seq;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContents() {
		return contents;
	}
	public void setContents(String contents) {
		this.contents = contents;
	}
	public String getWriter() {
		return writer;
	}
	public void setWriter(String writer) {
		this.writer = writer;
	}
	public String getStart_date() {
		return start_date;
	}
	public void setStart_date(String start_date) {
		this.start_date = start_date;
	}
	public String getEnd_date() {
		return end_date;
	}
	public void setEnd_date(String end_date) {
		this.end_date = end_date;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	
	
	
	
	
	
	
}
