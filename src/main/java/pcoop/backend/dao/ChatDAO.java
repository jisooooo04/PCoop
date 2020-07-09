package pcoop.backend.dao;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import pcoop.backend.dto.ChatDTO;

@Repository
public class ChatDAO {
	
	@Autowired
	private SqlSessionTemplate mybatis;
	
	public int insertChat(ChatDTO cdto) {
		return mybatis.insert("Chat.insert", cdto);
	}
	
	
	public List<ChatDTO> selectChatList(ChatDTO cdto){
		
		return mybatis.selectList("Chat.selectChatList", cdto);
	}
	
	
	public List<ChatDTO> selectLastChat(ChatDTO cdto){
		
		return mybatis.selectList("Chat.selectLastChat", cdto);
	}
	
	
	public int deleteChat(int seq){
		
		return mybatis.delete("Chat.delete", seq);
	}
	
	
	public int selectChatSeq(){
		
		return mybatis.selectOne("Chat.selectChatSeq");
	}
}
