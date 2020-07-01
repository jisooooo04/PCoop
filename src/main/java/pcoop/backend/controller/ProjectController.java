package pcoop.backend.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("project")
public class ProjectController {
	
	@RequestMapping("project_create")
	public String project_create()throws Exception{
		return "project/project_create";
	}
}
