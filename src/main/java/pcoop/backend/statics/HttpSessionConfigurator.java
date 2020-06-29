package pcoop.backend.statics;

import javax.servlet.http.HttpSession;
import javax.websocket.HandshakeResponse;
import javax.websocket.server.HandshakeRequest;
import javax.websocket.server.ServerEndpointConfig;
import javax.websocket.server.ServerEndpointConfig.Configurator;

public class HttpSessionConfigurator extends Configurator{
	//세션을 가져와서 통신하는 class & 서버껄로 임포트
	
	@Override
	public void modifyHandshake(ServerEndpointConfig sec, HandshakeRequest request, HandshakeResponse response) {
		//sec : ServerEndPoint까지 전달되는 변수
		//세션을 주고받기 위해 주고받는 과정을 handshake라고 함
		//원래는 수정할 수 없지만 http 세션을 가져와서? 수정함?
		HttpSession session = (HttpSession)request.getHttpSession();
		sec.getUserProperties().put("session", session);  //map으로 구성되어 있음
		
		System.out.println("HttpSessionConfigurator 클래스 입니다.");
	}
}
