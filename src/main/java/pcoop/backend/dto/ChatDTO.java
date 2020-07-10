package pcoop.backend.dto;

public class ChatDTO {
	private int seq;
	private int project_seq;
	private int chatting_num;
	private String writer;
	private String chat;
	private String full_date;
	private String form_date;
	private String time;
	private int file_seq; //file_seq로 바꾸기 (만약 file 아니면 null값 들어가게)
	
	public ChatDTO() {
		super();
	}

	public ChatDTO(int seq, int project_seq, int chatting_num, String writer, String chat, String full_date,
			String form_date, String time, int file_seq) {
		super();
		this.seq = seq;
		this.project_seq = project_seq;
		this.chatting_num = chatting_num;
		this.writer = writer;
		this.chat = chat;
		this.full_date = full_date;
		this.form_date = form_date;
		this.time = time;
		this.file_seq = file_seq;
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

	public String getWriter() {
		return writer;
	}

	public void setWriter(String writer) {
		this.writer = writer;
	}

	public String getChat() {
		return chat;
	}

	public void setChat(String chat) {
		this.chat = chat;
	}

	public String getFull_date() {
		return full_date;
	}

	public void setFull_date(String full_date) {
		this.full_date = full_date;
	}

	public String getForm_date() {
		return form_date;
	}

	public void setForm_date(String form_date) {
		this.form_date = form_date;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public int getFile_seq() {
		return file_seq;
	}

	public void setFile_seq(int file_seq) {
		this.file_seq = file_seq;
	}
	
}
