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
import org.springframework.web.multipart.MultipartFile;

import pcoop.backend.dto.ChatDTO;
import pcoop.backend.dto.MemberDTO;
import pcoop.backend.service.ChatService;
import pcoop.backend.statics.HttpSessionConfigurator;

@ServerEndpoint(value="/chat", configurator=HttpSessionConfigurator.class)
public class WebChat {
	
//	@Autowired
//	private ChatService cservice;
	
	//의존성을 검색해서 집어넣어줌
	private ChatService cservice = MyApplicationContextAware.getApplicationContext().getBean(ChatService.class);
	
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
		
		System.out.println("test");
		clients.add(client);
		
		//HttpSession에서 가져온 세션정보를 가져올 수 있는 것
		this.session = (HttpSession)config.getUserProperties().get("session");
						
	}

	
	@OnMessage  //메세지가 오면 이 메서드를 실행해줌
	public void onMessage(String message) {
		//Session 세션객체 안에는 다른 클래스?에 메세지를 보낼 수 있는 기능이 들어있음
		//누가보냈는지 = 세션 / 내용=메세지
		//System.out.println(session.getId() + " : " + message);  //지금은 client id 없으므로 주석처리
		
		System.out.println(message);
		MemberDTO mdto = (MemberDTO)this.session.getAttribute("loginInfo");
		
		synchronized(clients) {
			for(Session client : clients) {
					Basic basic = client.getBasicRemote();
			         try {			        	 
			        	 System.out.println(message);  //jsp에서 잘 넘어왔는지 출력
			        	 
			        	 JSONObject jsonObj;
			        	 jsonObj = (JSONObject)jsonParser.parse(message); //클라이언트쪽에서 문자열로 넘어온 json오브젝트를 jsonObject로 만들어준다
			        	 
			        	 
			        	 String id = mdto.getName();
			        	 jsonObj.put("id", id);
			        	 String text = (String)jsonObj.get("text");
			        	 String fullDate = (String)jsonObj.get("fulldate");
			        	 String date = (String)jsonObj.get("date");  //날짜
			        	 String time = (String)jsonObj.get("time");  //시간
			        	 
			        	 
			        	 //DB에 채팅내용 저장
			        	 ChatDTO cdto = new ChatDTO(0,0,0,id,text,fullDate,date,time,"");
			        	 
			        	 int result = cservice.insertChat(cdto);
			        	 
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
