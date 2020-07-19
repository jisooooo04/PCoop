package pcoop.backend.dto;

public class CommunityDTO {
	private int seq;
	private int member_seq;
	private String member_name;
	private String title;
	private String category;
	private String contents;
	private String write_date;
	private int view_count;
	private String link;
	private int file_seq;
	
	public CommunityDTO() {
		super();
	}

	public CommunityDTO(int seq, int member_seq, String member_name, String title, String category, String contents,
			String write_date, int view_count, String link, int file_seq) {
		super();
		this.seq = seq;
		this.member_seq = member_seq;
		this.member_name = member_name;
		this.title = title;
		this.category = category;
		this.contents = contents;
		this.write_date = write_date;
		this.view_count = view_count;
		this.link = link;
		this.file_seq = file_seq;
	}

	public int getSeq() {
		return seq;
	}

	public void setSeq(int seq) {
		this.seq = seq;
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

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getContents() {
		return contents;
	}

	public void setContents(String contents) {
		this.contents = contents;
	}

	public String getWrite_date() {
		return write_date;
	}

	public void setWrite_date(String write_date) {
		this.write_date = write_date;
	}

	public int getView_count() {
		return view_count;
	}

	public void setView_count(int view_count) {
		this.view_count = view_count;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public int getFile_seq() {
		return file_seq;
	}

	public void setFile_seq(int file_seq) {
		this.file_seq = file_seq;
	}
	
	
}
