package pcoop.backend.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pcoop.backend.dao.ChattingDAO;
import pcoop.backend.dao.MemberDAO;
import pcoop.backend.dto.ChattingDTO;
import pcoop.backend.dto.ProjectMemberDTO;

@Service
public class ChattingService {
	
	@Autowired
	private ChattingDAO cdao;
	
	@Autowired
	private MemberDAO mdao;
	
	
	public int createChatting(ProjectMemberDTO pmdto) {
		
		int pj_seq = pmdto.getProject_seq();
		int mb_seq = pmdto.getMember_seq();
		String pj_name = pmdto.getProject_name();
		String mb_name = pmdto.getMember_name();
		
		ChattingDTO cdto = new ChattingDTO(0, pj_seq, 0, pj_name, 1, mb_seq, mb_name,"sysdate","default");
		
		return cdao.createChatting(cdto);
	}
	
	
	//내가 속한 채팅방 목록 불러오기
	public List<ChattingDTO> selectChattingList(int project_seq, int member_seq) {

		Map<String, Integer> map = new HashMap<>();
		map.put("project_seq", project_seq);
		map.put("member_seq", member_seq);
		
		return cdao.selectChattingList(map);
	}
	
	
	//chatting_seq로 채팅 정보 불러오기
	public List<ChattingDTO> selectChatting(int chatting_num) {
		return cdao.selectChatting(chatting_num);
	}
	
	
	//기본채팅방에 멤버 추가
	public int insertMainMember(int project_seq, int mb_seq, String mb_name) throws Exception {
		
		// mainChatting 정보 받아오고 > 기존 사람들 채팅방 인원수 수정 > 채팅방에 새로운 멤버 추가
		
		//'main' 채팅방 정보 받아오기 (title, mb_count, create_date)
		ChattingDTO cdto = cdao.selectMainChatting(project_seq);
		
		int chatting_num = cdto.getChatting_num();
		String title = cdto.getTitle();
		int member_count = cdto.getMember_count() + 1;
		String create_date = cdto.getCreate_date();
		
		
		//pj_seq이고 main 채팅방인 기존 사람들 인원수 변경 (update +1)
		Map<String, Integer> map = new HashMap<>();
		map.put("project_seq", project_seq);
		map.put("chatting_num", chatting_num);
		map.put("member_count", member_count);
		
		int update = cdao.updateMainChatting(map);
		
		
		//main 채팅방에 새로운 멤버 추가 (insert)
		ChattingDTO ctdto = new ChattingDTO(0, project_seq, chatting_num, title, member_count, mb_seq, mb_name, create_date, "main");
		int insert = cdao.insertMainMember(ctdto);
		
		return insert;
	}
	
	
		
	
}
