package pcoop.backend.dto;

public class ListDTO {

	private String id;
	private String title;
	private String defaultStyle;
	private String items;
	
	
	
	public ListDTO() {}
	
	public ListDTO(String id, String title, String defaultStyle, String items) {
		super();
		this.id = id;
		this.title = title;
		this.defaultStyle = defaultStyle;
		this.items = items;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
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
