package pcoop.backend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pcoop.backend.dao.ChatDAO;
import pcoop.backend.dto.ChatDTO;
import pcoop.backend.dto.ChattingDTO;

@Service
public class ChatService {
	
	@Autowired
	private ChatDAO cdao;
	
	
	public int insertChat(ChatDTO cdto) {
		return cdao.insertChat(cdto);
	}
	
	
	public List<ChatDTO> selectChatList(String today, int chatting_num){
		
		ChatDTO cdto = new ChatDTO(0, 0, chatting_num, "", "", today, "", "", 0);
		
		return cdao.selectChatList(cdto);
	}
	
	
	public List<ChatDTO> selectLastChat(String date, int chatting_num){
		
		ChatDTO cdto = new ChatDTO(0, 0, chatting_num, "", "", date, "", "", 0);
		
		return cdao.selectLastChat(cdto);
	}
	
	
	public int deleteChat(int seq){
		return cdao.deleteChat(seq);
	}
	
	
	public int selectChatSeq(){
		return cdao.selectChatSeq();
	}
	
	
	public List<ChatDTO> selectFileList(int chatting_num) {
		return cdao.selectFileList(chatting_num);
	}
	
}
