package pcoop.backend.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import pcoop.backend.dto.BoardDTO;


@Repository
public class BoardDAO {


	@Autowired
	private SqlSessionTemplate mybatis;

	public int insert(BoardDTO dto) throws Exception{
//		String sql = "insert into board values(board_seq.nextval,?,default,default,?,?,?,?,?)";
//		return jdbc.update(sql, dto.getWriter() , dto.getTitle() , dto.getCategory() , dto.getId() , dto.getContents(), dto.getIp_address() );
		return mybatis.insert("Board.insert",dto);
	}

	public int delete(int seq) throws Exception{
//		String sql = "delete from board where seq=?";
//		return jdbc.update(sql, seq );
		return mybatis.delete("Board.delete", seq);
	}

	
	public int update(Map<String,String> param) {
		System.out.println("dao성공");
		System.out.println("뭐지?"+param);
		return mybatis.update("Board.update", param);

	}
	

	
	public BoardDTO select(int seq) throws Exception{
		return mybatis.selectOne("Board.select", seq);
	}
	


	public List<BoardDTO> selectAllDesc() throws Exception{

		return mybatis.selectList("Board.selectAllDesc");

	}	

	public List<BoardDTO> selectByPageNo(Map<String,Integer> param) throws Exception{

		return mybatis.selectList("Board.selectByPageNo",param);

	}

	public List<BoardDTO> selectByView(Map<String,Integer> param) throws Exception{

		return mybatis.selectList("Board.selectByView",param);

	}
	
	public int getArticleCount() throws Exception{

		return mybatis.selectOne("Board.selectCount");
	}
	public List<BoardDTO> search(Map<String,String> param) throws Exception{
		return mybatis.selectList("Board.search", param);
	}
	

}

