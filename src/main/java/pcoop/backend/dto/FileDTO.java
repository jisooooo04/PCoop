package pcoop.backend.dto;

import java.sql.Timestamp;

public class FileDTO {
	
	private int seq;
	private int project_seq;
	private String directory_path;
	private String extension;
	private String path;
	private Timestamp upload_date;
	private String uploader;
	
	public FileDTO() {
		super();
	}

	public FileDTO(int seq, int project_seq, String directory_path, String extension, String path,
			Timestamp upload_date, String uploader) {
		super();
		this.seq = seq;
		this.project_seq = project_seq;
		this.directory_path = directory_path;
		this.extension = extension;
		this.path = path;
		this.upload_date = upload_date;
		this.uploader = uploader;
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

	public String getDirectory_path() {
		return directory_path;
	}

	public void setDirectory_path(String directory_path) {
		this.directory_path = directory_path;
	}

	public String getExtension() {
		return extension;
	}

	public void setExtension(String extension) {
		this.extension = extension;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public Timestamp getUpload_date() {
		return upload_date;
	}

	public void setUpload_date(Timestamp upload_date) {
		this.upload_date = upload_date;
	}

	public String getUploader() {
		return uploader;
	}

	public void setUploader(String uploader) {
		this.uploader = uploader;
	}

	
	
}
