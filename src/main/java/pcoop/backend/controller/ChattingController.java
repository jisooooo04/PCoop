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

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import pcoop.backend.dto.ChatDTO;
import pcoop.backend.dto.ChatFileDTO;
import pcoop.backend.dto.ChattingDTO;
import pcoop.backend.dto.MemberDTO;
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

	private JSONParser jsonParser = new JSONParser();

	
	@RequestMapping("chatting")
	public String Chatting(String c_num, Model model) {

		int chatting_num = Integer.parseInt(c_num.substring(5)); // c_num00

		// chatting_seq로 채팅방 정보 불러오기
		List<ChattingDTO> chattingInfo = ctservice.selectChatting(chatting_num);
		model.addAttribute("chattingInfo", chattingInfo);
		
		//내가 속한 현재 채팅방 정보
		ChattingDTO cdto = ctservice.selectMyChatting(chatting_num);
		model.addAttribute("myChatting", cdto);

		
		// div에 id로 부여할 수 있도록 추가로 보내줌
		String p_seq = "p_seq" + chattingInfo.get(0).getProject_seq();
		model.addAttribute("c_num", c_num);
		model.addAttribute("p_seq", p_seq);

		
		// 현재 날짜 보내기
		Date dateobj = new Date();
		SimpleDateFormat form = new SimpleDateFormat("yyyy년 MM월 dd일 ");
		String yymmdd = form.format(dateobj);

		// 요일 구하기
		Calendar cal = Calendar.getInstance();
		int num = cal.get(Calendar.DAY_OF_WEEK) - 1;
		int numminus = cal.get(Calendar.DAY_OF_WEEK) - 2;
		if (numminus == -1) {
			numminus = 6;
		}

		String[] weekDay = { "일요일", "월요일", "화요일", "수요일", "목요일", "금요일", "토요일" };
		String day = weekDay[num];
		String dayminus = weekDay[numminus];

		
		// 어제 날짜 구하기
		cal.add(Calendar.DATE, -1);
		String yymmddminus = form.format(cal.getTime());

		// 오늘 날짜 + 요일
		String today = yymmdd + day;
		model.addAttribute("today", today);
		// 어제 날짜 + 요일
		String yesterday = yymmddminus + dayminus;
		model.addAttribute("yesterday", yesterday);

		// 오늘날짜랑, 채팅방 시퀀스로 대화내용 불러와야 함!! -- ok!

		// 해당 채팅방의 오늘 날짜 대화목록 불러오기
		String sysdate = "sysdate";
		List<ChatDTO> todayChat = cservice.selectChatList(sysdate, chatting_num);
		model.addAttribute("todayChat", todayChat);

		// 해당 채팅방의 어제 날짜 대화목록 불러오기
		String sysdateminus = "sysdate-1";
		List<ChatDTO> yesterdayChat = cservice.selectChatList(sysdateminus, chatting_num);
		model.addAttribute("yesterdayChat", yesterdayChat);

		
		// 파일리스트 불러오기
		List<ChatDTO> fileList = cservice.selectFileList(chatting_num);
		model.addAttribute("fileList", fileList);

		// jsp에서 뿌려줄 확장자 이미지 이름
		List<String> extensionList = fservice.extensionList(chatting_num);
		model.addAttribute("extensionList", extensionList);

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
	@RequestMapping(value = "fileUpload", produces = "application/json; charset=utf8")
	public String fileUpload(MultipartFile file, String message) throws Exception {

		System.out.println("ChattingController 넘어온 json : "+message);
		
		MemberDTO mdto = (MemberDTO)this.session.getAttribute("loginInfo");
		
		JSONObject jsonObj = (JSONObject) jsonParser.parse(message);
		
		String nickname = mdto.getName();
		String fullDate = (String) jsonObj.get("fulldate");
		String date = (String) jsonObj.get("date"); // 날짜
		String time = (String) jsonObj.get("time"); // 시간

		String p_seq = (String) jsonObj.get("p_seq"); // p_seq+project_seq 문자열
		String c_num = (String) jsonObj.get("c_num"); // c_seq+chatting_seq 문자열
		int project_seq = Integer.parseInt(p_seq.substring(5)); // 실제 seq
		int chatting_num = Integer.parseInt(c_num.substring(5)); // 실제 seq

		jsonObj.put("nickname", nickname);
		
		
		// 파일인지 텍스트인지 구분
		String text = "";
		int nextFileSeq = 0;
		
		
		if(!jsonObj.containsKey("file")) {
			System.out.println("ChatController로 넘어온 json은 텍스트 메세지 입니다.");
			text = (String) jsonObj.get("text");

			
			// file / text 공통 수행
			ChatDTO cdto = new ChatDTO(0, project_seq, chatting_num, nickname, text, fullDate, date, time, nextFileSeq);

			
			// chat DB에 채팅내용 저장 (seq, pj_seq, chatting_num, ... , file_seq)
			int result = cservice.insertChat(cdto);
			System.out.println("chat insert 여부 : " + result);

			
			// DB에 가장 최근에 입력된 챗의 seq값 불러오기 (chat div에 id 부여하는 용도)
			int seq = cservice.selectChatSeq();
			jsonObj.put("seq", seq);
			System.out.println("chat_seq : " + seq);
			
			
		}else if(jsonObj.containsKey("file")) {
			
			System.out.println("ChattingController로 넘어온 json은 파일을 가지고 있습니다.");

			int presentFileSeq = 0; // chat 테이블에 넣을 file_seq 불러옴
			if (fservice.selectPresentSeq() != null) {
				presentFileSeq = fservice.selectPresentSeq().getSeq();
			}

			nextFileSeq = presentFileSeq + 1;
			
			String oriname = file.getOriginalFilename();
			text = "<a href='fileDownload?presentFileSeq=" + nextFileSeq + "'>" + oriname + "</a>";

			
			// 저장 이름 설정
			String sysname = System.currentTimeMillis() + "_" + oriname;
			

			// 파일 확장자 설정 (jpg, png 등)
			String extension = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1);
			extension = extension.toLowerCase(); // 확장자를 모두 소문자로 바꾸기
			jsonObj.put("extension", extension);

			// DB에서 확장자 검사해서 거기에 있는거 아니면 잘못된 접근으로 보내기
			// if(!(extension.contentEquals("jpg")||extension.contentEquals("png"))) {
			// return "warning"; //전송할 수 없는 파일입니다. 라는 안내메세지 띄우기
			// }

			
			// 경로 설정 1단계
			String filepathUpload = session.getServletContext().getRealPath("upload");

			// 위에서 지정한 경로가 없을 때 경로를 실제로 새로 만듬
			File tempFilePath1 = new File(filepathUpload); // 아래 코드를 사용하려면 File 객체를 생성해야됨
			if (!tempFilePath1.exists()) {
				System.out.println("upload 경로가 존재하지 않음");
				tempFilePath1.mkdir(); // 경로가 없다면 새로 만들어라
			}

			// 경로 설정 2단계 - 실제 최종 경로
			String filepath = session.getServletContext().getRealPath("upload/chat");

			// 위에서 지정한 경로가 없을 때 경로를 실제로 새로 만듬
			File tempFilePath2 = new File(filepath); // 아래 코드를 사용하려면 File 객체를 생성해야됨
			if (!tempFilePath2.exists()) {
				System.out.println("upload/chat 경로가 존재하지 않음");
				tempFilePath2.mkdir(); // 경로가 없다면 새로 만들어라
			}

			// 이후 내가 원하는 위치로 바꿈
			File target = new File(filepath + "/" + sysname); // 저장하고자 하는 경로
			file.transferTo(target); // 파일 복사기능을 이용해 바로 내가 원하는 위치에 저장함

			String targetLocation = target.toString();
			System.out.println("target : " + target);
			
			
			// 이미지 일때 이미지로 보여주기
			// if(extension.contentEquals("jpg") || extension.contentEquals("png")) {
			// text = "<img src='"+target+"' style='width: 100px'>";
			// }

			
			// chatFile 테이블에 file 정보 저장
			System.out.println(oriname + ":" + sysname + ":" + filepath + ":" + target + ":" + extension + ":"
					+ project_seq + ":" + chatting_num);
			ChatFileDTO fdto = new ChatFileDTO(0, oriname, sysname, filepath, targetLocation, extension, project_seq, chatting_num);
			int result = fservice.insertFile(fdto);

			jsonObj.put("text", text); // json에 file 키만 넘어왔으므로 text키에 file명을 넣어줌
			
			
			// file / text 공통 수행
			ChatDTO cdto = new ChatDTO(0, project_seq, chatting_num, nickname, text, fullDate, date, time, nextFileSeq);

			
			// chat DB에 채팅내용 저장 (seq, pj_seq, chatting_num, ... , file_seq)
			int chat_result = cservice.insertChat(cdto);

			
			// DB에 가장 최근에 입력된 챗의 seq값 불러오기 (chat div에 id 부여하는 용도)
			int seq = cservice.selectChatSeq();
			jsonObj.put("seq", seq);
			System.out.println("chat_seq : " + seq);
			
		}
		
		
		return jsonObj.toJSONString();

	}

	@RequestMapping("fileDownload")
	public void download(int presentFileSeq, HttpServletResponse response) throws Exception {

		ChatFileDTO fdto = fservice.selectFile(presentFileSeq); // file 테이블에서 file 정보 가져옴
		String filepath = session.getServletContext().getRealPath("upload/chat"); // 세션에 값이 있어? / request에서 받아옴

		File target = new File(filepath + "/" + fdto.getSysname());

		// 해당 경로에 파일 없으면 처리하는 코드 추가 하기!
		DataInputStream dis = new DataInputStream(new FileInputStream(target));
		ServletOutputStream sos = response.getOutputStream();

		String oriname = new String(fdto.getOriname().getBytes("utf8"), "iso-8859-1");

		response.reset();
		response.setContentType("application/octet-stream");
		response.setHeader("Content-disposition", "attachment;filename=" + oriname + ";");

		byte[] fileContents = new byte[(int) target.length()];
		dis.readFully(fileContents);

		sos.write(fileContents);
		sos.flush();

	}

}
