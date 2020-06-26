package pcoop.backend.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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
				dto.setProject_name(rs.getString("project_name"));
				dto.setName(rs.getString("name"));
				dto.setRoot_yn(rs.getString("root_yn"));
				dto.setPath(rs.getString("path"));
				return dto;
				
			}
		});

	}
	
	// 파일 리스트
	public List<FileDTO> getFileList(){
		return new ArrayList<FileDTO>();
	}

}
