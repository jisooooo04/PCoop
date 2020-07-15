package pcoop.backend.controller;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import pcoop.backend.dto.ChatDTO;
import pcoop.backend.dto.ChatFileDTO;
import pcoop.backend.dto.ChattingDTO;
import pcoop.backend.service.ChatFileService;
import pcoop.backend.service.ChatService;
import pcoop.backend.service.ChattingService;

@Controller
public class ChattingController {
	
	@Autowired
	private HttpSession session;
	
	
	@Autowired
	private ChatService cservice;
	
	@Autowired
	private ChattingService ctservice;
	
	@Autowired
	private ChatFileService fservice;
	
	
	@RequestMapping("chatting")
	public String Chatting(String c_num, Model model) {
		
		int chatting_num = Integer.parseInt(c_num.substring(5));  //c_num00
		
		//chatting_seq로 채팅방 정보 불러오기
		List<ChattingDTO> chattingInfo = ctservice.selectChatting(chatting_num);
		System.out.println("chattingController : 해당 채팅방 인원수는 = " + chattingInfo.size());
		
		model.addAttribute("chattingInfo", chattingInfo);
		
		//div에 id로 부여할 수 있도록 추가로 보내줌
		String p_seq = "p_seq"+chattingInfo.get(0).getProject_seq();
		model.addAttribute("c_num", c_num);
		model.addAttribute("p_seq", p_seq);
		
		System.out.println("c_num : " + c_num + ", chatting_num : " + chatting_num);
		System.out.println("p_seq : " + p_seq);
		
		
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
		
		
		//오늘날짜랑, 채팅방 시퀀스로 대화내용 불러와야 함!!  -- ok!
		
		//해당 채팅방의 오늘 날짜 대화목록 불러오기
		String sysdate = "sysdate";
		List<ChatDTO> todayChat = cservice.selectChatList(sysdate, chatting_num);
		model.addAttribute("todayChat", todayChat);
		
		//해당 채팅방의 어제 날짜 대화목록 불러오기
		String sysdateminus = "sysdate-1";
		List<ChatDTO> yesterdayChat = cservice.selectChatList(sysdateminus, chatting_num);
		model.addAttribute("yesterdayChat", yesterdayChat);
				
		//파일리스트 불러오기
		List<ChatDTO> fileList = cservice.selectFileList(chatting_num);
		System.out.println(fileList);
		model.addAttribute("fileList", fileList);
		
		
		return "chatting/chatting";
	}
	
	
	@ResponseBody
	@RequestMapping("lastChat")
	public List<ChatDTO> lastChat(int beforenum, String c_num) {
		
		String date = "sysdate-" + beforenum;
		int chatting_num = Integer.parseInt(c_num.substring(5));
		List<ChatDTO> lastList = cservice.selectLastChat(date, chatting_num);
		
		return lastList;
	}
	
	
	@ResponseBody
	@RequestMapping("deleteChat")
	public int deleteChat(int seq) {
		
		int result = cservice.deleteChat(seq);
		
		return result;
	}
	
	
	@ResponseBody
	@RequestMapping(value="fileUpload", produces="application/json; charset=utf8")
	public ChatFileDTO fileUpload(MultipartFile file) throws Exception{
		
		String oriname = file.getOriginalFilename();
		System.out.println("오리지널 파일이름 : " + oriname);  //오리지널 파일이름
		
		//파일 확장자 설정 (jpg, png 등)
		String extension = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".")+1);
		extension = extension.toLowerCase();  //확장자를 모두 소문자로 바꾸기
		
		//DB에서 확장자 검사해서 거기에 있는거 아니면 잘못된 접근으로 보내기
		//if(!(extension.contentEquals("jpg")||extension.contentEquals("png"))) {
		//	return "warning";  //전송할 수 없는 파일입니다. 라는 안내메세지 띄우기
		//}
		
		//경로 설정
		String filepath = session.getServletContext().getRealPath("upload/chat");
		System.out.println("filePath : " + filepath);
		
		//저장 이름 설정
		String sysname = System.currentTimeMillis() + "_" + oriname;
		System.out.println("systemFileName : " + sysname);
		
		//위에서 지정한 경로가 없을 때 경로를 실제로 새로 만듬
		File tempFilePath = new File(filepath);  //아래 코드를 사용하려면 File 객체를 생성해야됨
		if(!tempFilePath.exists()) {
			System.out.println("해당 경로가 존재하지 않음");
			tempFilePath.mkdir();  //경로가 없다면 새로 만들어라
		}else {
			System.out.println("해당경로 존재함");
		}
		
		//이후 내가 원하는 위치로 바꿈
		File target = new File(filepath + "/" + sysname);  //저장하고자 하는 경로
		file.transferTo(target);  //파일 복사기능을 이용해 바로 내가 원하는 위치에 저장함
		
		String targetLocation = target.toString();
		System.out.println("target : " + target);
		
		ChatFileDTO fdto = new ChatFileDTO(0, oriname, sysname, filepath, targetLocation, extension, 0, 0);
		
		return fdto;
		
	}
	
	
	
	@RequestMapping("fileDownload")
	public void download(int presentFileSeq, HttpServletResponse response) throws Exception{
		
		ChatFileDTO fdto = fservice.selectFile(presentFileSeq);  //file 테이블에서 file 정보 가져옴
		String filepath = session.getServletContext().getRealPath("upload/chat");  //세션에 값이 있어? / request에서 받아옴
		
		File target = new File(filepath + "/" + fdto.getSysname());
		
		//해당 경로에 파일 없으면 처리하는 코드 추가 하기!
		DataInputStream dis = new DataInputStream(new FileInputStream(target));
		ServletOutputStream sos = response.getOutputStream();
		
		String oriname = new String(fdto.getOriname().getBytes("utf8"),"iso-8859-1");
		
		response.reset();
		response.setContentType("application/octet-stream");
		response.setHeader("Content-disposition", "attachment;filename="+oriname+";");
		
		byte[] fileContents = new byte[(int)target.length()];
		dis.readFully(fileContents);
		
		sos.write(fileContents);
		sos.flush();
		
	}
	
	
	
}



