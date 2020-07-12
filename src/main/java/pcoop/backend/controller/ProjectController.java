package pcoop.backend.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import pcoop.backend.dto.MemberDTO;
import pcoop.backend.dto.ProjectDTO;
import pcoop.backend.dto.ProjectMemberDTO;
import pcoop.backend.service.ProjectService;

@Controller
@RequestMapping("project")
public class ProjectController {
	
	@Autowired
	ProjectService service;
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
		pmdto.setMember_seq(leader_seq);
		pmdto.setMember_email(mdto.getEmail());
		pmdto.setMember_name(mdto.getName());
		pmdto.setLeader_yn("y");
		service.insertp_m(pmdto);
		return "project/project_code";
	}
	
	@RequestMapping("temp")
	public String temp ()throws Exception{
		/*
		 * Random rand = new Random(); StringBuffer sb = new StringBuffer(); for(int i =
		 * 0; i<6;i++) { int index = rand.nextInt(3); switch(index) { case 0 :
		 * sb.append((char)(rand.nextInt(26)+97)); break; case 1:
		 * sb.append((char)(rand.nextInt(26)+65)); break; case 2:
		 * sb.append(rand.nextInt(10)); break; } } String ssb = sb.toString();
		 * System.out.println("실험중"+ssb);
		 */
		return "project/project_create";
	}
	
	
	
}
