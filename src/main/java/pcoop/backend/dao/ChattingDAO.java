package pcoop.backend.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import pcoop.backend.dto.ChattingDTO;

@Repository
public class ChattingDAO {
	
	@Autowired
	private SqlSessionTemplate mybatis;
	
	public int createChatting(ChattingDTO cdto) {
		return mybatis.insert("Chatting.createChatting", cdto);
	}
	
	
	public ChattingDTO selectMainChatting(int project_seq) {
		return mybatis.selectOne("Chatting.selectMainChatting", project_seq);
	}
	
	
	public int insertMainMember(ChattingDTO cdto) {
		return mybatis.insert("Chatting.insertMainMember", cdto);
	}
	
	
	public List<ChattingDTO> selectChattingList(int project_seq, int member_seq) {
		
		Map<String, Integer> map = new HashMap<>();
		map.put("project_seq", project_seq);
		map.put("member_seq", member_seq);
		
		return mybatis.selectList("Chatting.selectChattingList", map);
	}
	
	
	public List<ChattingDTO> selectChatting(int seq) {
		return mybatis.selectList("Chatting.selectChatting", seq);
	}
	
}
