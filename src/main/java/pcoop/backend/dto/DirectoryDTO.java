package pcoop.backend.dto;

public class DirectoryDTO {
	
	private int seq;
	private String project_name;
	private String name;
	private String root_yn;
	private String path;
	
	
	public DirectoryDTO() {
		super();
	}

	public DirectoryDTO(int seq, String project_name, String name, String root_yn, String path) {
		super();
		this.seq = seq;
		this.project_name = project_name;
		this.name = name;
		this.root_yn = root_yn;
		this.path = path;
	}

	public int getSeq() {
		return seq;
	}

	public void setSeq(int seq) {
		this.seq = seq;
	}

	public String getProject_name() {
		return project_name;
	}

	public void setProject_name(String project_name) {
		this.project_name = project_name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRoot_yn() {
		return root_yn;
	}

	public void setRoot_yn(String root_yn) {
		this.root_yn = root_yn;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
	

}
