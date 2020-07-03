package pcoop.backend.controller;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import pcoop.backend.dto.ChatDTO;
import pcoop.backend.service.ChatService;


@Controller
public class HomeController {

	@Autowired
	private HttpSession session;
	
	@Autowired
	private ChatService cservice;

	@RequestMapping("/")
	public String home() {
		System.out.println(session.getServletContext().getRealPath("upload"));

		return "index";
	}

	@RequestMapping("backup")
	public String backup() {
		return "backup/backup";
	}

	@RequestMapping("chat")
	public String chat(Model model) {

		//채팅방 이름, 인원수, 이전 대화목록, 현재 날짜 보내기

		//사용자가 클릭한 채팅방(디폴트-속한단체채팅방) 정보 받아오기
		//채팅방seq 혹은 이름


		//현재 날짜 보내기
		Date dateobj = new Date();
		SimpleDateFormat format = new SimpleDateFormat("yyyy년 MM월 dd일 ");
		String yymmdd = format.format(dateobj); 

		Calendar cal = Calendar.getInstance();
		int num = cal.get(Calendar.DAY_OF_WEEK)-1;

		String[] weekDay = {"일요일","월요일","화요일","수요일","목요일","금요일","토요일"};
		String day = weekDay[num];

		String date = yymmdd+day;

		model.addAttribute("date", date);


		//오늘 날짜 대화목록
		String today = "sysdate";
		List<ChatDTO> chatList = cservice.selectChatList(today);
		model.addAttribute("chatList", chatList);
		return "chatting/merge-chatting";
	}

}
