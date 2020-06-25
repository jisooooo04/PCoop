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

import pcoop.backend.service.FileService;

@Controller
public class FileController {
	
	@Autowired
	HttpSession session;
	@Autowired
	FileService fservice;
	
	@RequestMapping("fileList")
	public String fileList(Model model) {
		
		String project_name = "temp";
		
		String rootDir = session.getServletContext().getRealPath("upload/backup/" + project_name);
		List<String> dirList = fservice.getDirList(rootDir);
		List<String> fileList = fservice.getFileList(rootDir);
		
		JsonArray dirArr = new JsonArray();
		JsonArray fileArr = new JsonArray();
		
		for(String s : dirList) {
			JsonObject json = new JsonObject();
			json.addProperty("id", 1);
			json.addProperty("path", s);
			dirArr.add(json);

		}
		
		for(String s : fileList) {
			System.out.println(s);
			JsonObject json = new JsonObject();
			json.addProperty("id", 1);
			json.addProperty("path", s);
			fileArr.add(json);

		}
		
		model.addAttribute("dirlist", new Gson().toJson(dirArr));
		model.addAttribute("filelist", new Gson().toJson(fileArr));
		return "backup/fileList";
	}

}
