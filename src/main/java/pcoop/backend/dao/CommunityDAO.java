package pcoop.backend.dao;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import pcoop.backend.dto.CommunityDTO;

@Repository
public class CommunityDAO {
	
	@Autowired
	private SqlSessionTemplate mybatis;
	
	public List<CommunityDTO> selectList() {
		return mybatis.selectList("Community.selectList");
	}
}
