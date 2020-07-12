package pcoop.backend.dto;

public class ProjectDTO {
	private int seq;
	private String name;
	private String code;
	private int leader_seq;
	private int people_num;
	
	
	
	public ProjectDTO() {
		super();
	}
	public ProjectDTO(int seq, String name, String code, int leader_seq, int people_num) {
		super();
		this.seq = seq;
		this.name = name;
		this.code = code;
		this.leader_seq = leader_seq;
		this.people_num = people_num;
	}
	public int getSeq() {
		return seq;
	}
	public void setSeq(int seq) {
		this.seq = seq;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public int getLeader_seq() {
		return leader_seq;
	}
	public void setLeader_seq(int leader_seq) {
		this.leader_seq = leader_seq;
	}
	public int getPeople_num() {
		return people_num;
	}
	public void setPeople_num(int people_num) {
		this.people_num = people_num;
	}
	
	
	
	
	
}
