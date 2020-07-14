package pcoop.backend.dto;

public class ChattingDTO {
	private int seq;
	private int project_seq;
	private int chatting_num;
	private String title;
	private int member_count;
	private int member_seq;
	private String member_name;
	private String create_date;
	private String type;
	
	public ChattingDTO() {
		super();
	}

	public ChattingDTO(int seq, int project_seq, int chatting_num, String title, int member_count, int member_seq, String member_name,
			String create_date, String type) {
		super();
		this.seq = seq;
		this.project_seq = project_seq;
		this.chatting_num = chatting_num;
		this.title = title;
		this.member_count = member_count;
		this.member_seq = member_seq;
		this.member_name = member_name;
		this.create_date = create_date;
		this.type = type;
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
	
	public int getChatting_num() {
		return chatting_num;
	}

	public void setChatting_num(int chatting_num) {
		this.chatting_num = chatting_num;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getMember_count() {
		return member_count;
	}

	public void setMember_count(int member_count) {
		this.member_count = member_count;
	}

	public int getMember_seq() {
		return member_seq;
	}

	public void setMember_seq(int member_seq) {
		this.member_seq = member_seq;
	}

	public String getMember_name() {
		return member_name;
	}

	public void setMember_name(String member_name) {
		this.member_name = member_name;
	}

	public String getCreate_date() {
		return create_date;
	}

	public void setCreate_date(String create_date) {
		this.create_date = create_date;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
}
