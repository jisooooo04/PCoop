package pcoop.backend.dto;

public class CardDTO {

	private int id;
	private String title;
	private String description;
	private String dueDate;
	private String done;
	private int listId;
	private int project_seq;
	
	
	public CardDTO(int id, String title, String description, String dueDate, String done, int listId, int project_seq) {
		super();
		this.id = id;
		this.title = title;
		this.description = description;
		this.dueDate = dueDate;
		this.done = done;
		this.listId = listId;
		this.project_seq = project_seq;
	}

	public int getProject_seq() {
		return project_seq;
	}

	public void setProject_seq(int project_seq) {
		this.project_seq = project_seq;
	}

	public CardDTO() {}
	
	public CardDTO(int id, String title, String description, String dueDate) {
		super();
		this.id = id;
		this.title = title;
		this.description = description;
		this.dueDate = dueDate;
	}
	
	public CardDTO(int id, String title, String description, String dueDate, String done, int listId) {
		super();
		this.id = id;
		this.title = title;
		this.description = description;
		this.dueDate = dueDate;
		this.done = done;
		this.listId = listId;
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

	public int getListId() {
		return listId;
	}

	public void setListId(int listId) {
		this.listId = listId;
	}


	
	
}
