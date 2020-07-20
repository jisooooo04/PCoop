import org.aspectj.lang.annotation.Before;
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
import pcoop.backend.dto.ProjectDTO;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations= {
		"file:src/main/webapp/WEB-INF/spring/root-context.xml",
		"file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml"
})
public class TestBackup {

	private static final Logger logger = LoggerFactory.getLogger("TestBackup");
	
	// 웹 환경 구성 역할의 인스턴스
	protected MockHttpSession session;
    protected MockHttpServletRequest request;
    @Autowired
	private WebApplicationContext wac;
	private MockMvc mockMvc;

	MemberDTO loginInfo = new MemberDTO();
	ProjectDTO projectInfo = new ProjectDTO();

	// 테스트 코드가 실행되기 전에 먼저 실행되는 코드
	@Before(value = "")
	public void setup() {
		
		loginInfo.setSeq(6);
		loginInfo.setPw("bef56dceaf00d8e084479f6a4b975907d051dc978ded62f5d3bb4a3cce0b2ec735033b4b0fd83dd68fee470240b67c8631ddc629f9143070bdf5a91d24b98a37");
		loginInfo.setEmail("4v_v4@naver.com");
		loginInfo.setName("김지수");
		
		projectInfo.setSeq(2);
		projectInfo.setName("PCOOP");
		projectInfo.setLeader_seq(2);
		projectInfo.setCode("5G4XKq");
		projectInfo.setPeople_num(10);
		
		session = new MockHttpSession();
		session.setAttribute("loginInfo", loginInfo);
		session.setAttribute("projectInfo", projectInfo);

		request = new MockHttpServletRequest();
        request.setSession(session);
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();

	}
//	
//	@Test
//	public void getParentDirSeq() {
//		// url로 request를 build해서 post함
//		try {
//			
//			mockMvc.perform(MockMvcRequestBuilders.post("/getParentDirSeq")
//					.session(session)
//					.param("dir_seq", "144")).andDo(MockMvcResultHandlers.print())
//					.andExpect(MockMvcResultMatchers.status().isOk());
//			logger.info("테스트 성공!");
//			
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			logger.error("테스트 실패!");
//			e.printStackTrace();
//		}
//	}
//	
//	@Test
//	public void getDirAndFileList() {
//		// url로 request를 build해서 post함
//		try {
//			
//			mockMvc.perform(MockMvcRequestBuilders.post("/getDirAndFileList")
//					.session(session)
//					.param("dir_seq", "222")).andDo(MockMvcResultHandlers.print())
//					.andExpect(MockMvcResultMatchers.status().isOk());
//			logger.info("테스트 성공!");
//			
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			logger.error("테스트 실패!");
//			e.printStackTrace();
//		}
//	}
//
//	@Test
//	public void checkDirNameDupl() {
//		// url로 request를 build해서 post함
//		try {
//			
//			mockMvc.perform(MockMvcRequestBuilders.post("/checkDirNameDupl")
//					.session(session)
//					.param("dir_seq", "2")
//					.param("zip_dir", "ddd")).andDo(MockMvcResultHandlers.print())
//					.andExpect(MockMvcResultMatchers.status().isOk());
//			logger.info("테스트 성공!");
//			
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			logger.error("테스트 실패!");
//			e.printStackTrace();
//		}
//	}
//	
	@Test
	public void deleteDirectory() {
		// url로 request를 build해서 post함
		try {
			
			mockMvc.perform(MockMvcRequestBuilders.post("/deleteDirectory")
					.session(session)
					.param("seq", "221")
					.param("root_seq", "2")).andDo(MockMvcResultHandlers.print())
					.andExpect(MockMvcResultMatchers.status().isOk());
			logger.info("테스트 성공!");
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("테스트 실패!");
			e.printStackTrace();
		}
	}
	
	@Test
	public void renameDirectory() {
		// url로 request를 build해서 post함
		try {
			
			mockMvc.perform(MockMvcRequestBuilders.post("/renameDirectory")
					.session(session)
					.param("seq", "225")
					.param("rename", "rename")).andDo(MockMvcResultHandlers.print())
					.andExpect(MockMvcResultMatchers.status().isOk());
			logger.info("테스트 성공!");
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("테스트 실패!");
			e.printStackTrace();
		}
	}
	
	@Test
	public void addDirectory() {
		// url로 request를 build해서 post함
		try {
			
			mockMvc.perform(MockMvcRequestBuilders.post("/addDirectory")
					.session(session)
					.param("parent_seq", "2")
					.param("name", "New Folder")).andDo(MockMvcResultHandlers.print())
					.andExpect(MockMvcResultMatchers.status().isOk());
			logger.info("테스트 성공!");
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("테스트 실패!");
			e.printStackTrace();
		}
	}
	
	@Test
	public void readFile() {
		// url로 request를 build해서 post함
		try {
			
			mockMvc.perform(MockMvcRequestBuilders.post("/readFile")
					.session(session)
					.param("seq", "305")).andDo(MockMvcResultHandlers.print())
					.andExpect(MockMvcResultMatchers.status().isOk());
			logger.info("테스트 성공!");
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("테스트 실패!");
			e.printStackTrace();
		}
	}
	
}
