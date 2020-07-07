package pcoop.backend.controller;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import pcoop.backend.dto.ChatDTO;
import pcoop.backend.service.ChatService;

@Controller
public class ChattingController {
	
	@Autowired
	private HttpSession session;
	
	
	@Autowired
	private ChatService cservice;
	
	
	@RequestMapping("chatting")
	public String Chatting(Model model) {
		//채팅방 이름, 인원수, 이전 대화목록, 현재 날짜 보내기
		
		//사용자가 클릭한 채팅방(디폴트-속한단체채팅방) 정보 받아오기
		//채팅방seq 혹은 이름
		
		
		//현재 날짜 보내기
		Date dateobj = new Date();
		SimpleDateFormat form = new SimpleDateFormat("yyyy년 MM월 dd일 ");
		String yymmdd = form.format(dateobj); 
		
		//요일 구하기
		Calendar cal = Calendar.getInstance();
		int num = cal.get(Calendar.DAY_OF_WEEK)-1;
		int numminus = cal.get(Calendar.DAY_OF_WEEK)-2;
			if(numminus == -1) {numminus = 6;}
		
		String[] weekDay = {"일요일","월요일","화요일","수요일","목요일","금요일","토요일"};
		String day = weekDay[num];
		String dayminus = weekDay[numminus];
		
		//어제 날짜 구하기
		cal.add(Calendar.DATE, -1);
		String yymmddminus = form.format(cal.getTime());
		
		
		//오늘 날짜 + 요일
		String today = yymmdd+day;
		model.addAttribute("today", today);
		//어제 날짜 + 요일
		String yesterday = yymmddminus+dayminus;
		model.addAttribute("yesterday", yesterday);
		
		
		
		//오늘 날짜 대화목록 불러오기
		String sysdate = "sysdate";
		List<ChatDTO> todayChat = cservice.selectChatList(sysdate);
		model.addAttribute("todayChat", todayChat);
		
		//어제 날짜 대화목록 불러오기
		String sysdateminus = "sysdate-1";
		List<ChatDTO> yesterdayChat = cservice.selectChatList(sysdateminus);
		model.addAttribute("yesterdayChat", yesterdayChat);
		
		
		return "chatting/chatting";
	}
	
	
	@ResponseBody
	@RequestMapping("lastChat")
	public List<ChatDTO> lastChat(int num) {
		
		String date = "sysdate-" + num;
		List<ChatDTO> lastList = cservice.selectLastChat(date);
		
		return lastList;
	}
	
	
	@ResponseBody
	@RequestMapping("deleteChat")
	public int deleteChat(int seq) {
		
		int result = cservice.deleteChat(seq);
		
		return result;
	}
	
	
	@ResponseBody
	@RequestMapping("fileUpload")
	public String fileUpload(MultipartFile file) throws Exception{
		
		System.out.println("오리지널 파일이름 : " + file.getOriginalFilename());  //오리지널 파일이름
		
		//경로 설정
		String filePath = session.getServletContext().getRealPath("upload");
		System.out.println("filePath : " + filePath);
		
		//저장 이름 설정
		String systemFileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
		System.out.println("systemFileName : " + systemFileName);
		
		//위에서 지정한 경로가 없을 때 경로를 실제로 새로 만듬
		File tempFilePath = new File(filePath);  //아래 코드를 사용하려면 File 객체를 생성해야됨
		if(!tempFilePath.exists()) {
			tempFilePath.mkdir();  //경로가 없다면 새로 만들어라
		}
		
		//이후 내가 원하는 위치로 바꿈
		File targetLocation = new File(filePath + "/" + systemFileName);  //저장하고자 하는 경로
		file.transferTo(targetLocation);  //파일 복사기능을 이용해 바로 내가 원하는 위치에 저장함
		
		System.out.println("targetLocation : " + targetLocation);
		
		String result = targetLocation.toString();
		
		return result;
		
	}
	
	
}



