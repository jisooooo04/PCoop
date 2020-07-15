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
	
	
	public List<ChattingDTO> selectChattingList(Map<String, Integer> map) {
		return mybatis.selectList("Chatting.selectChattingList", map);
	}
	
	
	public List<ChattingDTO> selectChatting(int chatting_num) {
		return mybatis.selectList("Chatting.selectChatting", chatting_num);
	}
	
	
	public ChattingDTO selectMainChatting(int project_seq) {
		return mybatis.selectOne("Chatting.selectMainChatting", project_seq);
	}
	
	
	public int updateMainChatting(Map<String, Integer> map) {
		return mybatis.update("Chatting.updateMainChatting", map);
	}
	
	
	public int insertMainMember(ChattingDTO cdto) {
		return mybatis.insert("Chatting.insertMainMember", cdto);
	}
	
	
}
