package pcoop.backend.dto;

public class ChatFileDTO {
	private int seq;
	private String oriname;
	private String sysname;
	private String filepath;
	private String target;
	private String extension;
	private int project_seq;
	private int chatting_seq;
	private int chat_seq;  //추가로 writer, 날짜, 시간?
	
	public ChatFileDTO() {
		super();
	}

	public ChatFileDTO(int seq, String oriname, String sysname, String filepath, String target, String extension,
			int project_seq, int chatting_seq, int chat_seq) {
		super();
		this.seq = seq;
		this.oriname = oriname;
		this.sysname = sysname;
		this.filepath = filepath;
		this.target = target;
		this.extension = extension;
		this.project_seq = project_seq;
		this.chatting_seq = chatting_seq;
		this.chat_seq = chat_seq;
	}

	public int getSeq() {
		return seq;
	}

	public void setSeq(int seq) {
		this.seq = seq;
	}

	public String getOriname() {
		return oriname;
	}

	public void setOriname(String oriname) {
		this.oriname = oriname;
	}

	public String getSysname() {
		return sysname;
	}

	public void setSysname(String sysname) {
		this.sysname = sysname;
	}

	public String getFilepath() {
		return filepath;
	}

	public void setFilepath(String filepath) {
		this.filepath = filepath;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public String getExtension() {
		return extension;
	}

	public void setExtension(String extension) {
		this.extension = extension;
	}

	public int getProject_seq() {
		return project_seq;
	}

	public void setProject_seq(int project_seq) {
		this.project_seq = project_seq;
	}

	public int getChatting_seq() {
		return chatting_seq;
	}

	public void setChatting_seq(int chatting_seq) {
		this.chatting_seq = chatting_seq;
	}

	public int getChat_seq() {
		return chat_seq;
	}

	public void setChat_seq(int chat_seq) {
		this.chat_seq = chat_seq;
	}
	
}
