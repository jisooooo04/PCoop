package pcoop.backend.chat;

import java.io.IOException;
import java.text.SimpleDateFormat;
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

import pcoop.backend.statics.HttpSessionConfigurator;

@ServerEndpoint(value="/chat", configurator=HttpSessionConfigurator.class)
public class WebChat {
	
	//set : 중복을 방지하고 key가 존재
	//static을 해놓지 않으면 새로 접속할때마다 set이 매번 새로 만들어지는 것이므로 static으로 설정
	private static Set<Session> clients = Collections.synchronizedSet(new HashSet<>());  //싱크로나이즈드 해줌(동시성을 해결하기 위해 업그레이드?)
	private HttpSession session;  //이후에 담기 위해 지역변수로 만들어놓음
	
	
	
	@OnOpen  //접속했을때 실행하고자 하는 메서드
	public void onConnect(Session client, EndpointConfig config) {  //http세션이 아니라 websocket session임 & 엔드포인트컨피그를 여기에 담은것?
		
		//System.out.println(client.getId() + "클라이언트가 접속했습니다.");  //지금은 client id 없으므로 주석처리
		//페이지가 다시 로딩될 때마다 새로운 웹소켓이 새로 생김
		
		clients.add(client);
		
		//HttpSession에서 가져온 세션정보를 가져올 수 있는 것
		this.session = (HttpSession)config.getUserProperties().get("session");
	}

	
	@OnMessage  //메세지가 오면 이 메서드를 실행해줌
	public void onMessage(Session session, String message) {
		//Session 세션객체 안에는 다른 클래스?에 메세지를 보낼 수 있는 기능이 들어있음
		//누가보냈는지 = 세션, 내용=메세지
		//System.out.println(session.getId() + " : " + message);  //지금은 client id 없으므로 주석처리
		
		
//		Basic client = session.getBasicRemote();  //상대에게 메세지를 보낼 수 있는 객체를 꺼낸다음 아래에서 보냄
//		try {
//			client.sendText(message);  //메세지를 보냄 (보낸사람한테=session) = 에코(보낸그대로 나에게 돌아옴)
//		}catch(IOException e) {
//			e.printStackTrace();
//		}
		
		
		//String id = (String)this.session.getAttribute("loginInfo");
		
		synchronized(clients) {
			//06/12일 04:10
			for(Session client : clients) {
				//if(!client.getId().contentEquals(session.getId())) {
					Basic basic = client.getBasicRemote();
			         try {
			            //basic.sendText(id + " : " + message);  //session.getId() 대신 이젠 세션정보로 id 출력
			        	 basic.sendText(message);
			        	 
			        	 String date = this.CurrentTime();
			        	 //basic.sendText(date);
			        	 
			         } catch (IOException e) {
			        	 //상대방이 연결끊어서 메세지가 전송이 안되는 상황밖에 에러 날 이유가 없음. 따라서 print 굳이 안해줘도 됨.
			            e.printStackTrace();
			         }
				//}
			}
		}
		
		
	}
	
	
	@OnClose
	public void onClose(Session session) {
		clients.remove(session);
	}
	
	
	@OnError  //에러가 발생했을때 처리 (주로 접속이 끊겼을때?)
	public void onError(Session session, Throwable t) {
		clients.remove(session);
	}
	
	
	public String CurrentTime(){
		
		Date today = new Date();
	    System.out.println(today);
	        
	    SimpleDateFormat date = new SimpleDateFormat("yyyy년 MM월 dd일");
	    SimpleDateFormat time = new SimpleDateFormat("hh:mm:ss a");
	        
	    System.out.println("Date: "+date.format(today));
	    System.out.println("Time: "+time.format(today));
	    
	    String currentDate = date.format(today).toString();
		
		return currentDate;
	}
	
}
