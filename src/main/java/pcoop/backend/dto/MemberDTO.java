package pcoop.backend.dto;

public class MemberDTO {
	private int seq;
	private String email;
	private String pw;
	private String name;
	
	
	public MemberDTO() {}
	
	
	
	public MemberDTO(String email, String pw) {
		super();
		this.email = email;
		this.pw = pw;
	}



	public MemberDTO(String email, String pw, String name) {
		super();
		this.email = email;
		this.pw = pw;
		this.name = name;
	}



	public MemberDTO(int seq, String email, String pw, String name) {
		super();
		this.seq = seq;
		this.email = email;
		this.pw = pw;
		this.name = name;
	}
	public int getSeq() {
		return seq;
	}
	public void setSeq(int seq) {
		this.seq = seq;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPw() {
		return pw;
	}
	public void setPw(String pw) {
		this.pw = pw;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
	
}
