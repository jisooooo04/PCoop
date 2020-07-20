package pcoop.backend.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CoopBoardController {

	@Autowired
	private HttpSession session;

	// 협업 게시판 - 글쓰기로 이동
	@RequestMapping("goCoopWrite")
	public String goCoopBoardWrite() {
		return "board/coop/coop-write";
	}
	
	// 글쓰기 완료
	@RequestMapping("coop-write")
	public String coopWrite() {
		return "board/coop/cooperation";
	}

}
