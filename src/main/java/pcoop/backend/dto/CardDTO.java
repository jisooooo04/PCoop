package pcoop.backend.dto;

public class CardDTO {

	private String id;
	private String title;
	private String description;
	private String dueDate;
	private String done;
	private String listId;
	
	public CardDTO() {}
	
	public CardDTO(String id, String title, String description, String dueDate) {
		super();
		this.id = id;
		this.title = title;
		this.description = description;
		this.dueDate = dueDate;
	}
	
	public CardDTO(String id, String title, String description, String dueDate, String done, String listId) {
		super();
		this.id = id;
		this.title = title;
		this.description = description;
		this.dueDate = dueDate;
		this.done = done;
		this.listId = listId;
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
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getDueDate() {
		return dueDate;
	}
	public void setDueDate(String duedate) {
		this.dueDate = duedate;
	}
	public String getDone() {
		return done;
	}

	public void setDone(String done) {
		this.done = done;
	}

	public String getListId() {
		return listId;
	}

	public void setListId(String listId) {
		this.listId = listId;
	}
	
	
}
