package pcoop.backend.dto;

public class ChattingDTO {
	private String chat;
	private String date;
	
	public ChattingDTO() {}

	public ChattingDTO(String chat, String date) {
		super();
		this.chat = chat;
		this.date = date;
	}

	public String getChat() {
		return chat;
	}

	public void setChat(String chat) {
		this.chat = chat;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}
	
}
