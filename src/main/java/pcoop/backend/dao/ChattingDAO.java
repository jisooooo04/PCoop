package pcoop.backend.dao;

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
	
	
}
