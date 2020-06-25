package pcoop.backend.dto;

public class CalendarDTO {
	private int project_seq;
	private String title;
	private String contents;
	private String writer;
	private String start_date;
	private String end_date;
	private String start_time;
	private String end_time;
	private String color;

	public CalendarDTO() {
		super();
	}
	
	public CalendarDTO(int project_seq, String title, String contents, String writer, String start_date,
			String end_date, String start_time, String end_time, String color) {
		super();
		this.project_seq = project_seq;
		this.title = title;
		this.contents = contents;
		this.writer = writer;
		this.start_date = start_date;
		this.end_date = end_date;
		this.start_time = start_time;
		this.end_time = end_time;
		this.color = color;
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
	public String getStart_time() {
		return start_time;
	}
	public void setStart_time(String start_time) {
		this.start_time = start_time;
	}
	public String getEnd_time() {
		return end_time;
	}
	public void setEnd_time(String end_time) {
		this.end_time = end_time;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
}
