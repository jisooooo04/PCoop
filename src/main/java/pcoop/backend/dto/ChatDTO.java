package pcoop.backend.dto;

public class ChatDTO {
	private int seq;
	private int project_seq;
	private int chatting_seq;
	private String writer;
	private String chat;
	private String full_date;
	private String form_date;
	private String time;
	private String file_path;
	
	public ChatDTO() {
		super();
	}

	public ChatDTO(int seq, int project_seq, int chatting_seq, String writer, String chat, String full_date,
			String form_date, String time, String file_path) {
		super();
		this.seq = seq;
		this.project_seq = project_seq;
		this.chatting_seq = chatting_seq;
		this.writer = writer;
		this.chat = chat;
		this.full_date = full_date;
		this.form_date = form_date;
		this.time = time;
		this.file_path = file_path;
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

	public int getChatting_seq() {
		return chatting_seq;
	}

	public void setChatting_seq(int chatting_seq) {
		this.chatting_seq = chatting_seq;
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

	public String getFile_path() {
		return file_path;
	}

	public void setFile_path(String file_path) {
		this.file_path = file_path;
	}
	
}
