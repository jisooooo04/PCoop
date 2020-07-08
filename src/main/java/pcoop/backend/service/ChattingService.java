package pcoop.backend.service;

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
		
		ChattingDTO cdto = new ChattingDTO(0,pj_seq, pj_name,1,mb_seq, mb_name,"sysdate","default");
				
		return cdao.createChatting(cdto);
	}
	
	
	//기본채팅방에 멤버 추가
	public int insertMember(int pj_seq, int mb_seq) throws Exception {
		
		// seq, pj_seq, title, num, mb_seq, mb_name, date, type
		
		//pj_seq로 ChattingDTO에서 채팅방 정보 받아오기
		ChattingDTO cdto = cdao.selectMainChatting(pj_seq);
		int member_count = cdto.getMember_count()+1;
		
		//mb_seq로 멤버 조회(이름받아오기)
		String mb_name = mdao.selectName(mb_seq);
				
		cdto.setMember_count(member_count);
		cdto.setMember_seq(mb_seq);
		cdto.setMember_name(mb_name);
		
		return cdao.insertMainMember(cdto);
	}
	
	
}
