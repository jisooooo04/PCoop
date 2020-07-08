package pcoop.backend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pcoop.backend.dao.ChatDAO;
import pcoop.backend.dto.ChatDTO;

@Service
public class ChatService {
	
	@Autowired
	private ChatDAO cdao;
	
	
	public int insertChat(ChatDTO cdto) {
		return cdao.insertChat(cdto);
	}
	
	
	public List<ChatDTO> selectChatList(String today){
		return cdao.selectChatList(today);
	}
	
	
	public List<ChatDTO> selectLastChat(String date){
		return cdao.selectLastChat(date);
	}
	
	
	public int deleteChat(int seq){
		return cdao.deleteChat(seq);
	}
	
	
	public int selectChatSeq(){
		return cdao.selectChatSeq();
	}
	
	
}
