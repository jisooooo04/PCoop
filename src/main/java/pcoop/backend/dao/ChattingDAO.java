package pcoop.backend.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import pcoop.backend.dto.ChatFileDTO;
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
	
	
	public List<ChattingDTO> projectBelongChatting(Map<String, Integer> map) {
		return mybatis.selectList("Chatting.projectBelongChatting", map);
	}
	
	
	public int minusChattingCount(List<ChattingDTO> chatting_num) {
		
		int result = 0;
		for(int i=0; i<chatting_num.size(); i++) {
			result = mybatis.update("Chatting.minusChattingCount", chatting_num.get(i).getChatting_num());
		}
		return result;
	}
	
	
	public int deleteProjectMember(Map<String, Integer> map) {
		return mybatis.delete("Chatting.deleteProjectMember", map);
	}
	
	
	public List<ChattingDTO> memberBelongChatting(int member_seq) {
		return mybatis.selectList("Chatting.memberBelongChatting", member_seq);
	}
	
	
	public int deleteMemberout(int member_seq) {
		return mybatis.delete("Chatting.deleteMemberout", member_seq);
	}
	
		
	
}
