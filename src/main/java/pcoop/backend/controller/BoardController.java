package pcoop.backend.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class BoardController {
	
	@Autowired
	private HttpSession session;
	
	// 협업 게시판으로 이동
	@RequestMapping("goCoopBoard")
	public String goCoopBoard() {
		return "board/coop/cooperation";
	}

}
