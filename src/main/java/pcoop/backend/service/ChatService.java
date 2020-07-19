package pcoop.backend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pcoop.backend.dao.ChatDAO;
import pcoop.backend.dao.ChatFileDAO;
import pcoop.backend.dto.ChatDTO;

@Service
public class ChatService {
	
	@Autowired
	private ChatDAO cdao;
	
	@Autowired
	private ChatFileDAO fdao;
	
	
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
	
	
	public int deleteChat(int chat_seq){
		
		//chat_file에서도 파일 삭제!!
		//chat에서 seq가 chat_seq인 file_seq를 조회후, 해당 file_seq인 chat_file을 삭제
		//delete from chat_file where seq = (select file_seq from chat where seq = 00)
		int result1 = fdao.deleteFile(chat_seq);
		
		//chat에서도 삭제
		int result2 = cdao.deleteChat(chat_seq);
		
		return result2;
	}
	
	
	public int selectChatSeq(){
		return cdao.selectChatSeq();
	}
	
	
	public List<ChatDTO> selectFileList(int chatting_num) {
		return cdao.selectFileList(chatting_num);
	}
	
}
