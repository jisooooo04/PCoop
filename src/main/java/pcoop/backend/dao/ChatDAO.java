package pcoop.backend.dao;

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
}
