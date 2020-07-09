package pcoop.backend.dto;

public class ListDTO {

	private int id;
	private String title;
	private String defaultStyle;
	private String items;
	private int project_seq;
	
	
	public ListDTO(int id, String title, String defaultStyle, String items, int project_seq) {
		super();
		this.id = id;
		this.title = title;
		this.defaultStyle = defaultStyle;
		this.items = items;
		this.project_seq = project_seq;
	}

	public int getProject_seq() {
		return project_seq;
	}

	public void setProject_seq(int project_seq) {
		this.project_seq = project_seq;
	}

	public ListDTO() {}
	
	public ListDTO(int id, String title, String defaultStyle, String items) {
		super();
		this.id = id;
		this.title = title;
		this.defaultStyle = defaultStyle;
		this.items = items;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDefaultStyle() {
		return defaultStyle;
	}
	public void setDefaultStyle(String defaultStyle) {
		this.defaultStyle = defaultStyle;
	}
	public String getItems() {
		return items;
	}
	public void setItems(String items) {
		this.items = items;
	}
	
	
	

}
