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

	// 디렉토리 리스트
	public List<DirectoryDTO> getDirList(){

		String sql = "select * from directory where root_yn = 'N'";
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
	
	// 파일 리스트
	public List<FileDTO> getFileList(){
		
		String sql = "select * from files";
		
		return jdbc.query(sql, new RowMapper<FileDTO>() {
			@Override
			public FileDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
				
				FileDTO dto = new FileDTO();
				
				dto.setSeq(rs.getInt("seq"));
				dto.setProject_seq(rs.getInt("project_seq"));
				dto.setDirectory_path(rs.getString("directory_path"));
				dto.setExtension(rs.getString("extension"));
				dto.setPath(rs.getString("path"));
				dto.setUpload_date(rs.getTimestamp("upload_date"));
				dto.setUploader(rs.getString("uploader"));
				
				return dto;
			}
		});
		
	}

}
