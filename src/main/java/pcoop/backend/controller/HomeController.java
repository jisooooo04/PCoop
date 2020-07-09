package pcoop.backend.controller;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import pcoop.backend.dto.ChatDTO;
import pcoop.backend.dto.ChattingDTO;
import pcoop.backend.dto.DirectoryDTO;
import pcoop.backend.dto.MemberDTO;
import pcoop.backend.dto.ProjectDTO;
import pcoop.backend.service.ChatService;
import pcoop.backend.service.ChattingService;
import pcoop.backend.service.FileService;
import pcoop.backend.service.ProjectService;


@Controller
public class HomeController {

	@Autowired
	HttpSession session;
	
	@Autowired
	private ProjectService pservice;

	@Autowired
	private ChatService cservice;
	
	@Autowired
	private ChattingService ctservice;
	
	@Autowired
	private FileService fservice;

	@RequestMapping("/")
	public String home() {
		System.out.println(session.getServletContext().getRealPath("upload"));

		return "index";
	}

	@RequestMapping("backup")
	public String backup() {
		return "backup/backup";
	}

	  
	  @RequestMapping("goMain")
	  public String goMain()throws Exception{
		  session.removeAttribute("projectInfo"); //프로젝트 세션만 삭제하기
		  return "redirect:/";
	  }
	  
	  
	  @RequestMapping("project-main")
	  public String projectMain(int seq, Model model) throws Exception {
		  
		  //프로젝트 seq로 프로젝트 dto 가져오기
		  ProjectDTO pdto = pservice.selectBySeq(seq);
		  session.setAttribute("projectInfo", pdto);  //세션에 pdto담기 
		  		  
		  int project_seq = pdto.getSeq();
		  System.out.println("HomeController : 프로젝트 시퀀스는 >> " + project_seq);
		  
		  
		  //세션에서 member_seq 가져오기
		  MemberDTO mdto = (MemberDTO)session.getAttribute("loginInfo");
		  int member_seq = mdto.getSeq();
		  
		  
		  //내가 속한 채팅방 목록 가져오기
		  List<ChattingDTO> chattingList = ctservice.selectChattingList(project_seq, member_seq);
		  model.addAttribute("chattingList", chattingList);
		  
		  // 프로젝트의 루트 디렉토리 seq 가져옴
		  int root_seq = fservice.getRootDirSeq(project_seq);
		  
		  // DB에서 목록 가져올 때
		  List<DirectoryDTO> dirList = fservice.getDirList(root_seq);
		  JsonArray dirArr = new JsonArray();
		  
		  for(DirectoryDTO dto : dirList) {
			  JsonObject json = new JsonObject();
			  json.addProperty("seq", dto.getSeq());
			  json.addProperty("name", dto.getName());
			  json.addProperty("path", dto.getPath());
			  dirArr.add(json);
			  }
		  
		  model.addAttribute("root_seq", root_seq);
		  model.addAttribute("dirlist", new Gson().toJson(dirArr));
		  
		  
		  return "project-main";
	}
	  
	  

}
