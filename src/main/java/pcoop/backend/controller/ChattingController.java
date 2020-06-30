package pcoop.backend.controller;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ChattingController {
	
	@RequestMapping("chatting")
	public String Chatting(Model model) {
		//채팅방 이름, 인원수, 이전 대화목록, 현재 날짜 보내기
		
		//사용자가 클릭한 채팅방(디폴트-속한단체채팅방) 정보 받아오기
		//채팅방seq 혹은 이름
		
		//이전 대화목록 불러오기
		//날짜별로 list?에 담아서 jsp에서 for문 등으로 출력
		
		
		Date dateobj = new Date();
		SimpleDateFormat format = new SimpleDateFormat("yyyy년 MM월 dd일 ");
		String yymmdd = format.format(dateobj); 
		
		Calendar cal = Calendar.getInstance();
		int num = cal.get(Calendar.DAY_OF_WEEK)-1;
		
		String[] weekDay = {"일요일","월요일","화요일","수요일","목요일","금요일","토요일"};
		String day = weekDay[num];
		
		String date = yymmdd+day;
		
		model.addAttribute("date", date);  //현재 날짜
		
		
		return "chatting/chatting";
	}
	
	@RequestMapping("insertChat")
	public String insertChat(Model model) {
		
		
		
		return "chatting/chatting";
	}
	
}



