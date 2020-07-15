package pcoop.backend.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.Message.RecipientType;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import pcoop.backend.dto.ChatDTO;
import pcoop.backend.dto.ChattingDTO;
import pcoop.backend.dto.DirectoryDTO;
import pcoop.backend.dto.MemberDTO;
import pcoop.backend.dto.ProjectDTO;
import pcoop.backend.dto.ProjectMemberDTO;
import pcoop.backend.service.ChattingService;
import pcoop.backend.service.FileService;
import pcoop.backend.service.ProjectService;

@Controller
public class ProjectController {

	@Autowired
	ProjectService service;


	@Autowired
	private ChattingService ctservice;

	@Autowired
	private FileService fservice;
	
	@Autowired
	private JavaMailSender mailSender;

	@Autowired
	HttpSession session;

	@RequestMapping("project_create")
	public String project_create(Model model) throws Exception {
		int countProject = service.countProject(((MemberDTO) session.getAttribute("loginInfo")).getSeq());
		model.addAttribute("countProject", countProject);// 현재 속한 프로젝트가 몇갠지
		return "project/project_create";
	}

	@RequestMapping("create")
	public String create(String name, int people_num, Model model) throws Exception {
		ProjectDTO dto = new ProjectDTO();
		MemberDTO mdto = (MemberDTO) session.getAttribute("loginInfo");
		int leader_seq = mdto.getSeq();// 생성자 seq

		int seq = service.getproject_seq();
		String code = service.create_code();
		dto.setSeq(seq);// 프로젝트 시퀀스
		dto.setName(name);// 프로젝트 이름
		dto.setCode(code);// 프로젝트 고유코드 랜덤,중복x
		dto.setLeader_seq(leader_seq);// 팀장 seq
		dto.setPeople_num(people_num);// 인원 수

		model.addAttribute("code", code);


		model.addAttribute("title", name);
		//project table insert
		int result = service.create_project(dto);

		// project_member table insert
		ProjectMemberDTO pmdto = new ProjectMemberDTO();
		pmdto.setProject_seq(seq);
		pmdto.setProject_name(name);
		pmdto.setMember_seq(leader_seq);
		pmdto.setMember_email(mdto.getEmail());
		pmdto.setMember_name(mdto.getName());
		pmdto.setLeader_yn("y");
		pmdto.setJoin_ynd("y");
		service.insertp_m(pmdto);

		// project back root directory insert
		result = service.create_backup(dto);

		// 프로젝트 생성시 단체 채팅방 생성(입력) - 프로젝트 시퀀스, 프로젝트 이름, 멤버 시퀀스, 멤버 이름 전달
		result = ctservice.createChatting(pmdto);

		return "redirect:project_code";
	}
	
	@RequestMapping("project_code")//forward 피하기 위해서 컨트롤러를 두개 만들었다.
	public String project_code (String code,String title,Model model)throws Exception{

		model.addAttribute("code", code);
		model.addAttribute("title", title);
		return "project/project_code";
	}

	@RequestMapping("project_join")
	public String project_join(Model model) throws Exception {// 페이지 이동
		return "project/project_search";
	}

	@ResponseBody
	@RequestMapping(value = "searchByCode", produces = "application/gson;charset=utf8")
	public String searchByCode(String code) throws Exception {
		ProjectDTO dto = service.searchByCode(code);// 코드로 프로젝트 dto 찾기

		int project_seq = dto.getSeq();
		int member_seq = ((MemberDTO) session.getAttribute("loginInfo")).getSeq();
		Map<String, Integer> param = new HashMap<>();
		param.put("project_seq", project_seq);
		param.put("member_seq", member_seq);
		String result = service.JoinYNCheck(param);// 프로젝트 참여 대기중인지 아닌지 확인
		String send_result = "null";
		if (result == null) {// 참가 신청 보낼 수 있음
			send_result = "null";
		} else if (result.equals("y")) {// 이미 참가중
			send_result = "y";
		} else if (result.equals("n")) {// 대기중
			send_result = "n";
		}

		int countNum = service.countNum(project_seq);// 현재 인원 수
		int peopleNum = service.getPeopleNum(project_seq);// 정해져 있는 인원 수

		int countProject = service.countProject(member_seq);// 내가 참여한 프로젝트 갯수

		Object arr[] = { dto, send_result, countNum, peopleNum, countProject };
		String respArr = new Gson().toJson(arr);
		return respArr;
	}


	@RequestMapping("goProjectHome")
	public String goProjectHome(int seq, Model model) throws Exception {

		// 프로젝트 seq로 프로젝트 dto 가져오기
		ProjectDTO pdto = service.selectBySeq(seq);
		session.setAttribute("projectInfo", pdto); // 세션에 pdto담기

		int project_seq = pdto.getSeq();
		System.out.println("projectController : 프로젝트 시퀀스는 >> " + project_seq); // seq 확인용

		// 이 프로젝트에 대해 참가요청이 있다면 가져오기
		List<ProjectMemberDTO> list = service.joinCheck(project_seq);
		int size = list.size();
		if (size == 0) {

		} else {
			model.addAttribute("list", list);
		}
		
		//프로젝트에 속한 멤버리스트 뽑기
		  List<ProjectMemberDTO> memberList = service.getMemberList(project_seq); 
		  model.addAttribute("member_list", memberList);
		  
		  

		// 세션에서 member_seq 가져오기
		MemberDTO mdto = (MemberDTO) session.getAttribute("loginInfo");
		int member_seq = mdto.getSeq();

		// 해당 프로젝트 안에서 내가 속한 채팅방 목록 가져오기
		List<ChattingDTO> chattingList = ctservice.selectChattingList(project_seq, member_seq);
		JsonArray chattingArray = new JsonArray();

		for (ChattingDTO cdto : chattingList) {
			JsonObject json = new JsonObject();
			json.addProperty("chatting_seq", cdto.getSeq());
			json.addProperty("project_seq", cdto.getProject_seq());
			json.addProperty("chatting_num", cdto.getChatting_num());
			json.addProperty("title", cdto.getTitle());
			json.addProperty("member_count", cdto.getMember_count());
			json.addProperty("member_seq", cdto.getMember_seq());
			json.addProperty("member_name", cdto.getMember_name());
			json.addProperty("create_date", cdto.getCreate_date());
			json.addProperty("type", cdto.getType());
			chattingArray.add(json);
		}
		model.addAttribute("chattingList", new Gson().toJson(chattingArray));

		// 프로젝트의 루트 디렉토리 seq 가져옴
		int root_seq = fservice.getRootDirSeq(project_seq);

		// DB에서 목록 가져올 때
		List<DirectoryDTO> dirList = fservice.getDirList(root_seq);
		JsonArray dirArr = new JsonArray();

		for (DirectoryDTO dto : dirList) {
			JsonObject json = new JsonObject();
			json.addProperty("seq", dto.getSeq());
			json.addProperty("name", dto.getName());
			json.addProperty("path", dto.getPath());
			dirArr.add(json);
		}

		model.addAttribute("root_seq", root_seq);
		model.addAttribute("dirlist", new Gson().toJson(dirArr));

		return "project/project_home";
	}


	
	// 왼쪽 사이드바에 뿌리는거 에이작스
	@ResponseBody
	@RequestMapping(value="leftsidebar", produces = "application/text; charset=utf8")
	public String leftsidebar() {

		// 세션에서 project_seq 가져오기
		ProjectDTO pdto = (ProjectDTO) session.getAttribute("projectInfo");
		int project_seq = pdto.getSeq();

		// 세션에서 member_seq 가져오기
		MemberDTO mdto = (MemberDTO) session.getAttribute("loginInfo");
		int member_seq = mdto.getSeq();
		System.out.println("projectController ajax : "+project_seq + member_seq);
		
		// 해당 프로젝트 안에서 내가 속한 채팅방 목록 가져오기
		List<ChattingDTO> chattingList = ctservice.selectChattingList(project_seq, member_seq);
		JsonArray chattingArray = new JsonArray();

		for (ChattingDTO cdto : chattingList) {
			JsonObject json = new JsonObject();
			json.addProperty("chatting_seq", cdto.getSeq());
			json.addProperty("project_seq", cdto.getProject_seq());
			json.addProperty("chatting_num", cdto.getChatting_num());
			json.addProperty("title", cdto.getTitle());
			json.addProperty("member_count", cdto.getMember_count());
			json.addProperty("member_seq", cdto.getMember_seq());
			json.addProperty("member_name", cdto.getMember_name());
			json.addProperty("create_date", cdto.getCreate_date());
			json.addProperty("type", cdto.getType());
			chattingArray.add(json);
		}
		
		JsonObject data = new JsonObject();
		data.addProperty("chattingList", new Gson().toJson(chattingArray));

		
		// 프로젝트의 루트 디렉토리 seq 가져옴
		int root_seq = fservice.getRootDirSeq(project_seq);

		// DB에서 목록 가져올 때
		List<DirectoryDTO> dirList = fservice.getDirList(root_seq);
		JsonArray dirArr = new JsonArray();

		for (DirectoryDTO dto : dirList) {
			JsonObject json = new JsonObject();
			json.addProperty("seq", dto.getSeq());
			json.addProperty("name", dto.getName());
			json.addProperty("path", dto.getPath());
			dirArr.add(json);
		}

		data.addProperty("root_seq", root_seq);
		data.addProperty("dirlist", new Gson().toJson(dirArr));

		return new Gson().toJson(data);
	}

	
	
	  @ResponseBody 
	  @RequestMapping("sendJoin") 
	  public String sendJoin (int project_seq,String project_name)throws Exception{
		  MemberDTO mdto = (MemberDTO)session.getAttribute("loginInfo");
		  ProjectMemberDTO pdto = new ProjectMemberDTO(0,project_seq,project_name,mdto.getSeq(),mdto.getEmail(),mdto.getName(),"n","n");
		  int result = service.insertp_m(pdto);
		  return result+""; //참여 신청 보내기 
	  }
	 
	  
	  @RequestMapping("accept")
	  public String accept(int mem_seq,int project_seq,String member_name,Model model)throws Exception{//참가 수락
		 Map<String,Integer>param = new HashMap<>();
		 param.put("mem_seq", mem_seq);
		 param.put("project_seq", project_seq);
		 int result = service.accept(param);
		 model.addAttribute("seq", project_seq);
		 
		 //멤버 추가시, 단체 채팅방에도 멤버 추가(+기존 인원수 변경)
		result = ctservice.insertMainMember(project_seq, mem_seq, member_name);
		 
		 return "redirect:goProjectHome";
	  }
	  
	  @RequestMapping("refuse")
	  public String refuse(int mem_seq,int project_seq,Model model)throws Exception{//참가 거절
		  Map<String,Integer>param = new HashMap<>();
		  param.put("mem_seq", mem_seq);
		  param.put("project_seq", project_seq);
		  model.addAttribute("seq", project_seq);
		  service.refuse(param);
		  return "redirect:goProjectHome";
	  }
	  
	  @RequestMapping("exitProject")
	  public String exitProject (int project_seq,int mem_seq)throws Exception{//프로젝트 나가기
		  
		  Map<String,Integer>param = new HashMap<>();
		  param.put("mem_seq", mem_seq);
		  param.put("project_seq", project_seq);

		  String leader = service.checkLeaderYN(param);
		  //리더인지 아닌지부터 검사
		  if(leader.contentEquals("y")) {
			 int result = service.updateLeader(project_seq);
			//다른 팀원에게 리더 넘겨주기.
			 service.exitProject(param);
			 //멤버 프로젝트 테이블에서 삭제
			if(result==0) {//넘겨줄 팀원 없음 . 
				service.deleteProject(param);//프로젝트 삭제
			}
			
		  	}else if(leader.contentEquals("n")) {//리더가 아니다.
			  service.exitProject(param);
			 // 멤버 프로젝트에서 삭제하기.
			  
		  }		  
		  return "redirect:member/gomypage";
	  }
	  
	  @RequestMapping("ProjectInvite")
	  public String ProjectInvite (String code,Model model)throws Exception{
		  model.addAttribute("code", code);
		  return "project/project_invite";
	  }
	  
	  @ResponseBody
	  @RequestMapping("sendEmail")
	  public String sendEmail(String email,String code,String title)throws Exception{
		  System.out.println(email+code);
			  MimeMessage msg = mailSender.createMimeMessage();
			  msg.setSubject("PCOOP에서 프로젝트 초대가 도착했습니다.");
			  msg.setText(title+"프로젝트에서 회원님을 초대하셨습니다. :)"); 
			  msg.setText("프로젝트의 초대코드는 "+code+" 이며 프로젝트 코드로 검색 하신 뒤 참가 요청을 보내주세요."); 
			  msg.setRecipient(RecipientType.TO, new InternetAddress(email));
			  mailSender.send(msg);
		 
		  
		  return "";		  
	  }
	  
	  
	  @RequestMapping("ProjectMemberDelete")//강퇴 기능 
	  public String ProjectMemberDelete (int project_mem_seq,Model model)throws Exception{
		  service.ProjectMemberDelete(project_mem_seq);
		  model.addAttribute("seq", ((ProjectDTO)session.getAttribute("projectInfo")).getSeq());
		  return "redirect:goProjectHome";
	  }
}
