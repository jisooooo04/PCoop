package pcoop.backend.dao;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import pcoop.backend.dto.ChatFileDTO;

@Repository
public class ChatFileDAO {
	
	@Autowired
	private SqlSessionTemplate mybatis;
	
	public ChatFileDTO selectFile(int seq) {
		return mybatis.selectOne("ChatFile.selectFile", seq);
	}
	
	
	public int selectPresentSeq() {
		return mybatis.selectOne("ChatFile.selectPresentSeq");
	}
	
	
	public int insertFile(ChatFileDTO fdto) {
		return mybatis.insert("ChatFile.insertFile", fdto);
	}
		
	
}
