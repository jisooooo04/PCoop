package pcoop.backend.dto;

public class ProjectMemberDTO {
	private int seq;
	private int project_seq;
	private String project_name;
	private int member_seq;
	private String member_email;
	private String member_name;
	private String leader_yn;
	private String join_ynd;
	
	
	
	public ProjectMemberDTO() {
		super();
	}
	public ProjectMemberDTO(int seq, int project_seq, String project_name, int member_seq, String member_email,
			String member_name, String leader_yn, String join_ynd) {
		super();
		this.seq = seq;
		this.project_seq = project_seq;
		this.project_name = project_name;
		this.member_seq = member_seq;
		this.member_email = member_email;
		this.member_name = member_name;
		this.leader_yn = leader_yn;
		this.join_ynd = join_ynd;
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
	public String getProject_name() {
		return project_name;
	}
	public void setProject_name(String project_name) {
		this.project_name = project_name;
	}
	public int getMember_seq() {
		return member_seq;
	}
	public void setMember_seq(int member_seq) {
		this.member_seq = member_seq;
	}
	public String getMember_email() {
		return member_email;
	}
	public void setMember_email(String member_email) {
		this.member_email = member_email;
	}
	public String getMember_name() {
		return member_name;
	}
	public void setMember_name(String member_name) {
		this.member_name = member_name;
	}
	public String getLeader_yn() {
		return leader_yn;
	}
	public void setLeader_yn(String leader_yn) {
		this.leader_yn = leader_yn;
	}
	public String getJoin_ynd() {
		return join_ynd;
	}
	public void setJoin_ynd(String join_ynd) {
		this.join_ynd = join_ynd;
	}
	
	
	
	
	
}
