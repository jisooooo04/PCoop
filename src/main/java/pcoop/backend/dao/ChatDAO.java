package pcoop.backend.dao;

import java.util.List;

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
	
	
	public List<ChatDTO> selectChatList(String today){
		return mybatis.selectList("Chat.selectChatList", today);
	}
	
	
	public List<ChatDTO> selectLastChat(String date){
		
		return mybatis.selectList("Chat.selectChatChat", date);
	}
	
	
	public int deleteChat(int seq){
		
		return mybatis.delete("Chat.delete", seq);
	}
}
