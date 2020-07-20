package pcoop.backend.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pcoop.backend.dao.ChattingDAO;
import pcoop.backend.dao.MemberDAO;
import pcoop.backend.dto.ChattingDTO;
import pcoop.backend.dto.MemberDTO;
import pcoop.backend.dto.ProjectMemberDTO;

@Service
public class ChattingService {
	
	@Autowired
	private ChattingDAO cdao;
	
	@Autowired
	private MemberDAO mdao;
	
	@Autowired
	private HttpSession session;
	
	
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
	
	
	@Transactional("txManager")
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
	
	
	
	//프로젝트에 멤버 추가시, 개인 채팅방 생성
	public int insertPersonalMember(int project_seq, int member_seq, String member_name) {
		
		List<ChattingDTO> memberList = cdao.selectProjectMember(project_seq);
		int member_count = memberList.get(0).getMember_count();
		
		for(int i=0; i<member_count; i++) {
			
			//상대방 member_name 받아와서 채팅방 제목으로 정해서 채팅방 생성하기
			String partner_name = memberList.get(i).getMember_name();
			int partner_seq = memberList.get(i).getMember_seq();
			
			ChattingDTO ctdto = new ChattingDTO(0, project_seq, 0, member_name, 2, partner_seq, partner_name, "sysdate", "personal");
			int create = cdao.createPersonalMember(ctdto);
			
			//파트너의 chatting_num 받아오기			
			ChattingDTO ctdto2 = new ChattingDTO(0, project_seq, 0, member_name, 2, partner_seq, partner_name, "sysdate", "personal");
			int chatting_num = cdao.selectPartnerChattingnum(ctdto2);
			
			ChattingDTO ctdto3 = new ChattingDTO(0, project_seq, chatting_num, partner_name, 2, member_seq, member_name, "sysdate", "personal");
			int insert = cdao.insertPersonalMember(ctdto3);
		}
		
		return member_count;
	}
	
	
	
	//프로젝트 강퇴, 나갈 시 채팅방에서 삭제
	public int deleteProjectMember(int member_seq, int project_seq) {
		
		Map<String, Integer> map = new HashMap<>();
		map.put("member_seq", member_seq);
		map.put("project_seq", project_seq);
		
		// 인원수 변경
		// 1)기존 팀원들 채팅방 인원수 변경하기 위해 탈퇴하고자 하는 member가 참여하고있는 채팅 목록(num)을 리스트로 받아옴
		List<ChattingDTO> chatting_num = cdao.projectBelongChatting(map);
		
		// 2)위에서 불러온 채팅방 num 리스트로 해당 채팅방의 인원수를 모두 변경
		int update = cdao.minusChattingCount(chatting_num);
				
		
		//이후 chatting 테이블에서 해당 멤버 삭제
		int delete = cdao.deleteProjectMember(map);
		
		
		return delete;
	}
	
	
	//회원 탈퇴시 해당 회원이 참여하고 있는 모든 프로젝트의 채팅방에서 인원수 변경 및 회원 삭제
	public int deleteMemberout(int member_seq) {
		
		//해당 회원이 참여하고 있는 채팅방 목록 불러오기 (chatting_num)
		List<ChattingDTO> chatting_num = cdao.memberBelongChatting(member_seq);
		
		//해당 채팅방 목록의 인원수 -1 감소
		int update = cdao.minusChattingCount(chatting_num);
		
		//이후 chatting 테이블에서 해당 회원이 참가하고 있는 행 모두 삭제
		int delete = cdao.deleteMemberout(member_seq);
		
		return delete;
	}
	
	
	public ChattingDTO selectMyChatting(int chatting_num) {
		MemberDTO mdto = (MemberDTO)this.session.getAttribute("loginInfo");
		int member_seq = mdto.getSeq();
		ChattingDTO ctdto = new ChattingDTO(0, 0, chatting_num, "", 0, member_seq, "", "", "personal");
		return cdao.selectMyChatting(ctdto);
	}
	
}
