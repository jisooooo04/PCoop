package pcoop.backend.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ChattingController {
	
	@RequestMapping("chatting")
	public String Chatting() {
		return "chatting/chatting";
	}
	
}
