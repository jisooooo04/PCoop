package pcoop.backend.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import pcoop.backend.dto.MemberDTO;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration  //RunWith가 시작되기 전에 톰캣 서버가 켜지도록 설정 (web 환경을 구성)
@ContextConfiguration(locations = {
		"file:src/main/webapp/WEB-INF/spring/root-context.xml",
		"file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml"
})
public class TestChatting {
	
	private static final Logger logger = LoggerFactory.getLogger(TestChatting.class);  //org.slf4j.Logger 임포트
	
	protected MockHttpSession session;
	protected MockHttpServletRequest request;

	    
	@Autowired
	private WebApplicationContext wac;  //웹 환경 구성 역할의 인스턴스 - 실제 웹 환경을 설정하기 위한 애(위에 어노테이션에서는 준비)
	private MockMvc mockMvc;  //테스트 기능을 가지고있는 애
	
	
	MemberDTO loginInfo = new MemberDTO();
	
	@Before  //테스트 코드보다 먼저 실행되는 코드
	public void setup() {
		
		loginInfo.setSeq(1);
		loginInfo.setPw("33275a8aa48ea918bd53a9181aa975f15ab0d0645398f5918a006d08675c1cb27d5c645dbd"
				+ "084eee56e675e25ba4019f2ecea37ca9e2995b49fcb12c096a032e");
	    loginInfo.setEmail("jungblyy@naver.com");
	    loginInfo.setName("kh");
	    session = new MockHttpSession();
	    session.setAttribute("loginInfo", loginInfo);
	    request = new MockHttpServletRequest();
	    request.setSession(session);
	    RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
	    
	    this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();

	}

	
	@Test
	public void lastChat() {
		try {
			mockMvc.perform(MockMvcRequestBuilders.post("/lastChat").  //insert 라는 url에 post로 request를 perform 하겠다.
					param("beforenum", "1").
					param("c_num", "c_num4")).
			andDo(MockMvcResultHandlers.print()).  //어떤 요청이 어떤 메서드로가서 어떤 기능을 했는지 리포팅해줌
			andExpect(MockMvcResultMatchers.status().isOk());
			//chaining 기법 (.찍고 불러서 또 .찍고)
			
			logger.info("테스트 성공!");  //기록 전용 프레임워크 (sysout 대신 사용!)
			
			System.out.println("Test 성공!");
			
		}catch (Exception e) {
			logger.error("테스트 실패" + e.getMessage());
			System.out.println("Test 실패!");
			e.printStackTrace();
		}
	}
	
	
	@Test
	public void deleteChat() {
		try {
			mockMvc.perform(MockMvcRequestBuilders.post("/deleteChat").  //insert 라는 url에 post로 request를 perform 하겠다.
					param("int", "556")).
			andDo(MockMvcResultHandlers.print()).  //어떤 요청이 어떤 메서드로가서 어떤 기능을 했는지 리포팅해줌
			andExpect(MockMvcResultMatchers.status().isOk());
			//chaining 기법 (.찍고 불러서 또 .찍고)
			
			logger.info("테스트 성공!");  //기록 전용 프레임워크 (sysout 대신 사용!)
			
			System.out.println("Test 성공!");
			
		}catch (Exception e) {
			logger.error("테스트 실패" + e.getMessage());
			System.out.println("Test 실패!");
			e.printStackTrace();
		}
	}
	
	
	@Test
	public void chatting() {
		try {
			mockMvc.perform(MockMvcRequestBuilders.post("/chatting").  //insert 라는 url에 post로 request를 perform 하겠다.
					param("c_num", "c_num4").
					param("Model", "model")).
			andDo(MockMvcResultHandlers.print()).  //어떤 요청이 어떤 메서드로가서 어떤 기능을 했는지 리포팅해줌
			andExpect(MockMvcResultMatchers.status().isOk());
			//chaining 기법 (.찍고 불러서 또 .찍고)
			
			logger.info("테스트 성공!");  //기록 전용 프레임워크 (sysout 대신 사용!)
			
			System.out.println("Test 성공!");
			
		}catch (Exception e) {
			logger.error("테스트 실패" + e.getMessage());
			System.out.println("Test 실패!");
			e.printStackTrace();
		}
	}
	
	
}
