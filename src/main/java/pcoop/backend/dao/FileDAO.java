package pcoop.backend.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import pcoop.backend.dto.DirectoryDTO;
import pcoop.backend.dto.FileDTO;

@Repository
public class FileDAO {

	@Autowired
	private JdbcTemplate jdbc;

	// 이름으로 디렉토리 seq 검색
	public int getDirSeqByName(String name) {
		String sql = "select seq from directory where name = ?";
		return jdbc.queryForObject(sql, new Object[] {name}, Integer.class);
	}

	// seq로 디렉토리 경로 검색
	public String getDirPathBySeq(int seq) {

		String sql = "select path from directory where seq = ?";

		return jdbc.queryForObject(sql, new Object[] {seq}, String.class);
	}

	// 디렉토리 insert
	public int insertDirectory(String path, String name) {

		String sql = "insert into directory values(DIRECTORY_SEQ.nextval, ?, ?, ?, 'N')";

		return jdbc.update(sql, 11, name, path);
	}

	// 디렉토리 delete
	public int deleteDirectory(String path) {
		String sql = "delete from directory where path like ?";
		return jdbc.update(sql, path + "%");
	}

	// 디렉토리 리스트
	public List<DirectoryDTO> getDirList(){

		String sql = "select * from directory where root_yn = 'N' order by 1";
		return jdbc.query(sql, new RowMapper<DirectoryDTO>() {
			@Override
			public DirectoryDTO mapRow(ResultSet rs, int rowNum) throws SQLException {

				DirectoryDTO dto = new DirectoryDTO();
				dto.setSeq(rs.getInt("seq"));
				dto.setProject_seq(rs.getInt("project_seq"));
				dto.setName(rs.getString("name"));
				dto.setPath(rs.getString("path"));
				dto.setRoot_yn(rs.getString("root_yn"));

				return dto;

			}
		});

	}
	
	// 파일 이름 가져오기
	public String getFileNameBySeq(int seq) {
		String sql = "select name from files where seq = ?";
		return jdbc.queryForObject(sql, new Object[] {seq}, String.class);
	}

	// 파일 경로 가져오기
	public String getFilePathBySeq(int seq) {

		String sql = "select path from files where seq = ?";

		return jdbc.queryForObject(sql, new Object[] {seq}, String.class);
	}

	// 같은 디렉토리 내 파일명 중복 확인
	public int checkDuplFileName(int directory_seq, String name) {

		String sql = "select count(*) from files where directory_seq = ? and name = ?";
		return jdbc.queryForObject(sql, new Object[] {directory_seq, name}, Integer.class);
	}


	// 특정 디렉토리 내 파일 리스트
	public List<FileDTO> getFileListByDirSeq(int dir_seq){

		String sql = "select * from files where directory_seq = ?";
		return jdbc.query(sql, new Object[] {dir_seq}, new RowMapper<FileDTO>(){
			public FileDTO mapRow(ResultSet rs, int rowNum) throws SQLException {

				FileDTO dto = new FileDTO();

				dto.setSeq(rs.getInt("seq"));
				dto.setProject_seq(rs.getInt("project_seq"));
				dto.setDirectory_seq(rs.getInt("directory_seq"));
				dto.setDirectory_path(rs.getString("directory_path"));
				dto.setName(rs.getString("name"));
				dto.setExtension(rs.getString("extension"));
				dto.setPath(rs.getString("path"));
				dto.setUpload_date(rs.getTimestamp("upload_date"));
				dto.setUploader(rs.getString("uploader"));

				return dto;
			};
		});
	}

	// 전체 파일 리스트
	public List<FileDTO> getFileList(){

		String sql = "select * from files";

		return jdbc.query(sql, new RowMapper<FileDTO>() {
			@Override
			public FileDTO mapRow(ResultSet rs, int rowNum) throws SQLException {

				FileDTO dto = new FileDTO();

				dto.setSeq(rs.getInt("seq"));
				dto.setProject_seq(rs.getInt("project_seq"));
				dto.setDirectory_seq(rs.getInt("directory_seq"));
				dto.setDirectory_path(rs.getString("directory_path"));
				dto.setName(rs.getString("name"));
				dto.setExtension(rs.getString("extension"));
				dto.setPath(rs.getString("path"));
				dto.setUpload_date(rs.getTimestamp("upload_date"));
				dto.setUploader(rs.getString("uploader"));

				return dto;
			}
		});

	}

	// 파일 삭제
	public int insertFile(int project_seq, int directory_seq, String directory_path,
			String name, String extension, String path, String uploader){

		String sql = "insert into files values(files_seq.nextval, ?, ?, ?, ?, ?, ?, sysdate, ?)";
		return jdbc.update(sql, project_seq, directory_seq, directory_path, name, extension, path, uploader);
	}



}
