package pcoop.backend.dto;

public class DirectoryDTO {
	
	private int seq;
	private int project_seq;
	private String name;
	private String path;
	private String root_yn;
	
	public DirectoryDTO() {
		super();
	}

	public DirectoryDTO(int seq, int project_seq, String name, String path, String root_yn) {
		super();
		this.seq = seq;
		this.project_seq = project_seq;
		this.name = name;
		this.path = path;
		this.root_yn = root_yn;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getRoot_yn() {
		return root_yn;
	}

	public void setRoot_yn(String root_yn) {
		this.root_yn = root_yn;
	}

	

}
