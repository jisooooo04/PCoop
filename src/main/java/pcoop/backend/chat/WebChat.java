package pcoop.backend.chat;

import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpSession;
import javax.websocket.EndpointConfig;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.RemoteEndpoint.Basic;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import pcoop.backend.dto.ChatDTO;
import pcoop.backend.dto.ChatFileDTO;
import pcoop.backend.dto.MemberDTO;
import pcoop.backend.service.ChatFileService;
import pcoop.backend.service.ChatService;
import pcoop.backend.statics.HttpSessionConfigurator;

@ServerEndpoint(value="/chat", configurator=HttpSessionConfigurator.class)
public class WebChat {
	
//	@Autowired
//	private ChatService cservice;
	
	//의존성을 검색해서 집어넣어줌
	private ChatService cservice = MyApplicationContextAware.getApplicationContext().getBean(ChatService.class);
	private ChatFileService fservice = MyApplicationContextAware.getApplicationContext().getBean(ChatFileService.class);
	
	//set : 중복을 방지하고 key가 존재
	//static을 해놓지 않으면 새로 접속할때마다 set이 매번 새로 만들어지는 것이므로 static으로 설정
	private static Set<Session> clients = Collections.synchronizedSet(new HashSet<>());  //싱크로나이즈드 해줌(동시성을 해결하기 위해 업그레이드?)
	private HttpSession session;  //이후에 담기 위해 지역변수로 만들어놓음
	
	private JSONParser jsonParser = new JSONParser();
	private Date today = new Date();
	
	
	@OnOpen  //접속했을때 실행하고자 하는 메서드
	public void onConnect(Session client, EndpointConfig config) {  //http세션이 아니라 websocket session임 & 엔드포인트컨피그를 여기에 담은것?
		
		//System.out.println(client.getId() + "클라이언트가 접속했습니다.");  //지금은 client id 없으므로 주석처리
		//페이지가 다시 로딩될 때마다 새로운 웹소켓이 새로 생김
		
		clients.add(client);
		
		//HttpSession에서 가져온 세션정보를 가져올 수 있는 것
		this.session = (HttpSession)config.getUserProperties().get("session");
						
	}

	
	@OnMessage  //메세지가 오면 이 메서드를 실행해줌
	public void onMessage(String message) {
		
		System.out.println(message);  //잘 왔는지 json 출력
		MemberDTO mdto = (MemberDTO)this.session.getAttribute("loginInfo");  //사용자 정보
		
		synchronized(clients) {
			for(Session client : clients) {
					Basic basic = client.getBasicRemote();
			         try {
			        	 
			        	 JSONObject jsonObj;
			        	 jsonObj = (JSONObject)jsonParser.parse(message); //클라이언트쪽에서 문자열로 넘어온 json오브젝트를 jsonObject로 만들어준다
			        	 
			        	 //json으로 넘어온 정보들을 cdto에 저장하기 위해 String 변수로 변환해 임시 저장
			        	 String nickname = mdto.getName();
			        	 String fullDate = (String)jsonObj.get("fulldate");
			        	 String date = (String)jsonObj.get("date");  //날짜
			        	 String time = (String)jsonObj.get("time");  //시간
			        	 
			        	 String p_seq = (String)jsonObj.get("p_seq");  //p_seq+project_seq 문자열
			        	 String c_num = (String)jsonObj.get("c_num");  //c_seq+chatting_seq 문자열
			        	 int project_seq = Integer.parseInt(p_seq.substring(5));  //실제 seq
			        	 int chatting_num = Integer.parseInt(c_num.substring(5));  //실제 seq
			        	 
			        	 jsonObj.put("nickname", nickname);
			        	 
			        	 
			        	 //파일인지 텍스트인지 구분
			        	 String text = "";
			        	 String file_date_form = "";
			        	 int nextFileSeq = 0;
			        	 
			        	 if(jsonObj.containsKey("file")) {
			        		 System.out.println("WebChat으로 넘어온 json은 파일을 가지고 있습니다.");
			        		 
			        		 int presentFileSeq = 0;  //chat 테이블에 넣을 file_seq 불러옴
			        		 if(fservice.selectPresentSeq()!=null) {
			        			 presentFileSeq=fservice.selectPresentSeq().getSeq();
			        		 }
			        		
			        		
			        		 nextFileSeq = presentFileSeq + 1;
			        		 
			        		 String oriname = (String)jsonObj.get("file");
			        		 text = "<a href='fileDownload?presentFileSeq="+nextFileSeq+"'>" + oriname + "</a>";
			        		 
			        		 String sysname = (String)jsonObj.get("sysname");
			        		 String filepath = (String)jsonObj.get("filepath");
			        		 String target = (String)jsonObj.get("target");
			        		 String extension = (String)jsonObj.get("extension");
			        		 file_date_form = (String)jsonObj.get("file_date_form");
			        		 
			        		 
			        		 //이미지 일때 이미지로 보여주기
			        		 //if(extension.contentEquals("jpg") || extension.contentEquals("png")) {
			        		 //	 text = "<img src='"+target+"' style='width: 100px'>";
			        		 //}
			        		 
			        		 
			        		 //chatFile 테이블에 file 정보 저장
			        		 System.out.println(oriname+":"+sysname+":"+filepath+":"+target+":"+extension+":"+project_seq+":"+chatting_num);
			        		 ChatFileDTO fdto = new ChatFileDTO(0, oriname, sysname, filepath, target, extension, project_seq, chatting_num);
			        		 int result = fservice.insertFile(fdto);

			        		 
			        		 jsonObj.put("text", text);  //json에 file 키만 넘어왔으므로 text키에 file명을 넣어줌
			        		 
			        		 
			        	 }else {
			        		 System.out.println("WebChat으로 넘어온 json은 텍스트 메세지 입니다.");
			        		 text = (String)jsonObj.get("text");
			        	 }
			        	 
			        	 
			        	 // file / text 공통 수행
			        	 ChatDTO cdto = new ChatDTO(0,project_seq,chatting_num,nickname,text,fullDate,date,time,nextFileSeq);
			        	 
			        	 //chat DB에 채팅내용 저장  (seq, pj_seq, chatting_num, ... , file_seq)
			        	 int result = cservice.insertChat(cdto);
			        	 System.out.println("chat insert 여부 : " + result);
			        	 
			        	 
			        	 //DB에 가장 최근에 입력된 챗의 seq값 불러오기 (chat div에 id 부여하는 용도)
			        	 int seq = cservice.selectChatSeq();
			        	 jsonObj.put("seq", seq);
			        	 System.out.println("chat_seq : "+seq);
			        	 
			        	 
			        	 basic.sendText(jsonObj.toJSONString());  //jsp로 보내기
			        	 
			         } catch (Exception e) {
			            e.printStackTrace();
			         }
			}
		}
		
	}
	
	
	@OnClose  //닫을때 한번에 저장할까? map같은데에 저장해놓고!
	public void onClose(Session session) {
		clients.remove(session);
	}
	
	
	@OnError  //에러가 발생했을때 처리 (주로 접속이 끊겼을때?)
	public void onError(Session session, Throwable t) {
		clients.remove(session);
	}
	
	
	
//	//테스트용!
//	@OnMessage  //메세지가 오면 이 메서드를 실행해줌
//	public void onMessage(MultipartFile file) {
//		
//		System.out.println(file.getName());
//		
//		
//		MemberDTO mdto = (MemberDTO)this.session.getAttribute("loginInfo");
//		
//		synchronized(clients) {
//			for(Session client : clients) {
//					Basic basic = client.getBasicRemote();
//			         try {
//			        	 
//			        	 
//			         } catch (Exception e) {
//			            e.printStackTrace();
//			         }
//			}
//		}
//		
//	}
	
	
}
