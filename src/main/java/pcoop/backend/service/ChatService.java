package pcoop.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pcoop.backend.dao.ChatDAO;
import pcoop.backend.dto.ChatDTO;

@Service
public class ChatService {
	
	@Autowired
	private ChatDAO cdao;
	
	public int insertChat(ChatDTO cdto) {
		System.out.println("ChatService : " + cdto.getWriter());
		return cdao.insertChat(cdto);
	}
	
}
