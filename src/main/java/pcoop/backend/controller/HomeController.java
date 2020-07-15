package pcoop.backend.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import pcoop.backend.dto.ChattingDTO;
import pcoop.backend.dto.DirectoryDTO;
import pcoop.backend.dto.FileDTO;
import pcoop.backend.dto.MemberDTO;
import pcoop.backend.dto.ProjectDTO;
import pcoop.backend.dto.ProjectMemberDTO;
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
	public String backup(int dir_seq, Model model) {
		ProjectDTO project = (ProjectDTO) session.getAttribute("projectInfo");
		int root_seq = fservice.getRootDirSeq(project.getSeq());
		String path = fservice.getDirPathBySeq(dir_seq);
		List<DirectoryDTO> dirList = fservice.getDirList(dir_seq);
		List<FileDTO> fileList = fservice.getFileListByDirSeq(dir_seq);
		JsonArray dirArr = new JsonArray();
		JsonArray fileArr = new JsonArray();
		
		for(DirectoryDTO dto : dirList) {
			JsonObject json = new JsonObject();
			json.addProperty("seq", dto.getSeq());
			// json.addProperty("project_seq", dto.getProject_seq());
			json.addProperty("parent_seq", dto.getParent_seq());
			json.addProperty("name", dto.getName());
			json.addProperty("path", dto.getPath());
			json.addProperty("root_yn", dto.getRoot_yn());
			dirArr.add(json);
		}

		for(FileDTO dto : fileList) {
			JsonObject json = new JsonObject();
			json.addProperty("seq", dto.getSeq());
			json.addProperty("path", dto.getPath());
			json.addProperty("name", dto.getName());
			json.addProperty("text_yn", dto.getText_yn());
			fileArr.add(json);
		}

		//JsonObject data = new JsonObject();

		model.addAttribute("root_seq", root_seq);
		model.addAttribute("dir_seq", dir_seq);
		model.addAttribute("path", path);
		model.addAttribute("dirArr", new Gson().toJson(dirArr));
		model.addAttribute("fileArr", new Gson().toJson(fileArr));

//		data.addProperty("path", path);
//		data.addProperty("dirArr", new Gson().toJson(dirArr));
//		data.addProperty("fileArr", new Gson().toJson(fileArr));
		return "backup/backup";
	}

	  
	  @RequestMapping("goMain")
	  public String goMain()throws Exception{
		  session.removeAttribute("projectInfo"); //프로젝트 세션만 삭제하기
		  return "redirect:/";
	  }
	    

}
