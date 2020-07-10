package pcoop.backend.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;

import pcoop.backend.dto.MemberDTO;
import pcoop.backend.dto.ProjectDTO;
import pcoop.backend.dto.ProjectMemberDTO;
import pcoop.backend.service.ChattingService;
import pcoop.backend.service.ProjectService;

@Controller
public class ProjectController {
	
	@Autowired
	ProjectService service;
	
	@Autowired
	ChattingService cservice;
	

	@Autowired
	HttpSession session;
	
	@RequestMapping("project_create")
	public String project_create()throws Exception{
		return "project/project_create";
	}
	
	@RequestMapping("create")
	public String create(String name , int people_num,Model model)throws Exception{
		ProjectDTO dto = new ProjectDTO();
		
		MemberDTO mdto = (MemberDTO)session.getAttribute("loginInfo");
		int leader_seq = mdto.getSeq();//생성자 seq
		
		int seq = service.getproject_seq();
		String code = service.create_code();
		dto.setSeq(seq);//프로젝트 시퀀스
		dto.setName(name);//프로젝트 이름
		dto.setCode(code);//프로젝트 고유코드 랜덤,중복x
		dto.setLeader_seq(leader_seq);//팀장 seq
		dto.setPeople_num(people_num);// 인원 수 	
		
		model.addAttribute("code", code);
		
		//project table insert
		int result = service.create_project(dto);
		
		//project_member table insert
		ProjectMemberDTO pmdto =new ProjectMemberDTO();
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
		
		//프로젝트 생성시 단체 채팅방 생성(입력) - 프로젝트 시퀀스, 프로젝트 이름, 멤버 시퀀스, 멤버 이름 전달
		result = cservice.createChatting(pmdto);

		return "redirect:project_code";
	}
	
	@RequestMapping("project_code")//forward 피하기 위해서 컨트롤러를 두개 만들었다.
	public String project_code (String code,Model model)throws Exception{
		model.addAttribute("code", code);
		return "project/project_code";
	}
	
	@RequestMapping("project_join")
	public String project_join (Model model)throws Exception{//페이지 이동
		return "project/project_search";
	}
	
	@ResponseBody
	@RequestMapping(value ="searchByCode",produces="application/gson;charset=utf8")
	public String searchByCode (String code)throws Exception{
		ProjectDTO dto = service.searchByCode(code);//코드로 프로젝트 dto 찾기
		
		int project_seq = dto.getSeq();
		int member_seq= ((MemberDTO)session.getAttribute("loginInfo")).getSeq();
		Map<String,Integer> param = new HashMap<>();
		param.put("project_seq", project_seq);
		param.put("member_seq", member_seq);
		String result = service.JoinCheck(param);//프로젝트 참여 대기중인지 아닌지 확인
		String send_result = "null";
		if(result==null) {//참가 신청 보낼 수 있음
			send_result= "null";
		}else if(result.equals("y")) {//이미 참가중
			send_result="y";
		}else if(result.equals("n")) {//대기중
			send_result="n";
		}
		
		 int countNum = service.countNum(project_seq);//현재 인원 수 
		 int peopleNum = service.getPeopleNum(project_seq);//정해져 있는 인원 수 
		
		 int countProject = service.countProject(member_seq);//내가 참여한 프로젝트 갯수
		 
		Object arr [] = {dto,send_result,countNum,peopleNum,countProject};
		String respArr = new Gson().toJson(arr);
		return respArr;
	}
	
	
	  @ResponseBody 
	  @RequestMapping("sendJoin") 
	  public String sendJoin (int project_seq,String project_name)throws Exception{
		  MemberDTO mdto = (MemberDTO)session.getAttribute("loginInfo");
		  ProjectMemberDTO pdto = new ProjectMemberDTO(0,project_seq,project_name,mdto.getSeq(),mdto.getEmail(),mdto.getName(),"null","n");
		  int result = service.insertp_m(pdto);
		  return result+""; //참여 신청 보내기 
	  }
	 
	  @RequestMapping("goProjectHome")
	  public String goProjectHome(Model model)throws Exception{
		  
		  //ProjectDTO pdto = service.selectBySeq(seq);//프로젝트 seq로 프로젝트 dto 가져오기
		  //session.setAttribute("projectInfo", pdto);//세션에 dto담기 
		  
		  ProjectDTO pdto = (ProjectDTO)session.getAttribute("projectInfo");
		  int project_seq = pdto.getSeq();
		  System.out.println("projectController : 프로젝트 시퀀스는 >> " + project_seq); //seq 확인용
		  
		  //이 프로젝트에 대해 참가요청이 있다면 가져오기
		  List<ProjectMemberDTO> list = service.joinYNCheck(project_seq);
		  int size = list.size(); 
		  if(size==0) {
			  
			  }else {
				  model.addAttribute("list", list); 
				  }
		 
		  return "project/project_home";
	  }
	  
	  
	  @RequestMapping("accept")
	  public String accept(int mem_seq,int project_seq,String member_name,Model model)throws Exception{//참가 수락
		 Map<String,Integer>param = new HashMap<>();
		 param.put("mem_seq", mem_seq);
		 param.put("project_seq", project_seq);
		 int result = service.accept(param);
		 model.addAttribute("seq", project_seq);
		 
		 //멤버 추가시, 단체 채팅방에도 멤버 추가(+기존 인원수 변경)
		 result = cservice.insertMainMember(project_seq, mem_seq, member_name);
		 
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
	  
	  @ResponseBody
	  @RequestMapping("countProject")
	  public String countProject(int mem_seq)throws Exception{//프로젝트는 최대 10개까지만 참여가능
		  String str = null;
		  int count = service.countProject(mem_seq);
		  System.out.println("count는"+count);
		  if(count>=10) {
			  str="fail";
		  }else {
			  str="success";
		  }
		  return str;
	  }
}
