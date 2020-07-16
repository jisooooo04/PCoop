package pcoop.backend.service;

import java.util.ArrayList;
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
	
	
	public int selectPresentSeq() {
		return fdao.selectPresentSeq();
	}
	
	
	public int insertFile(ChatFileDTO fdto) {
		return fdao.insertFile(fdto);
	}
	
	
	
	
	// 파일리스트에서 출력할 확장자 이미지 종류
	public List<String> extensionList(int chatting_num) {
		
		List<String> extensionImg = new ArrayList<>();
		
		// 파일리스트의 확장자만 리스트로 불러오기
		List<String> extensionList = fdao.selectExtension(chatting_num);
		// 확장자 모음
		String extensionArr[] = new String[] {"css","docx","gif","html","java","jpg","js","pdf","php","png","ppt","ps","sql","txt","xls","xml","zip"};
		
		for(int i=0; i<extensionList.size(); i++) {
			extensionImg.add(i, "resources/images/chatting/file.png");
			
			for(int j=0; j<extensionArr.length; j++) {
				if(extensionList.get(i).contentEquals(extensionArr[j])) {
					extensionImg.add(i, "resources/images/chatting/"+extensionArr[j]+".png");
				}
			}
		}
		
		return extensionImg;
	}
	
		
}
