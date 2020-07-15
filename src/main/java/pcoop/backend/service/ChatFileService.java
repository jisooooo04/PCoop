package pcoop.backend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pcoop.backend.dao.ChatFileDAO;
import pcoop.backend.dto.ChatFileDTO;

@Service
public class ChatFileService {
	
	@Autowired
	private ChatFileDAO fdao;
	
	
	public ChatFileDTO selectFile(int seq) {
		return fdao.selectFile(seq);
	}
	
	
	public ChatFileDTO selectPresentSeq() {
		return fdao.selectPresentSeq();
	}
	
	
	public int insertFile(ChatFileDTO fdto) {
		return fdao.insertFile(fdto);
	}
	
		
}
