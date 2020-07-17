package kh.pcoop.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
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

import pcoop.backend.dto.MemberDTO;
import pcoop.backend.dto.ProjectDTO;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations= {
		"file:src/main/webapp/WEB-INF/spring/root-context.xml",
		"file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml"
})
public class TestClient {
	@Autowired
	private WebApplicationContext wac;
	private MockMvc mockMvc;

	@Autowired
	private MockHttpSession session;

	
	@Before
	public void setup() {
		
		MemberDTO mdto = new MemberDTO();
		mdto.setSeq(1);
		mdto.setEmail("sskfro@naver.com");
		mdto.setName("세히");
		mdto.setPw("1111");
		
		/*
		 * ProjectDTO pdto = new ProjectDTO(); pdto.setSeq(5); pdto.setCode(code);
		 */
		session.setAttribute("loginInfo", mdto);
		//session.setAttribute("projectInfo",pdto);
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
	}

	@Test
	public void testProject() {
		try {
			mockMvc.perform(MockMvcRequestBuilders.post("/addEvent")
					.session(session).param("dto", "new calendarDTO(10,5,title,msg,writer,2020-07-16T00:00,2020-07-16T00:00,red)")
					
					)
			.andDo(MockMvcResultHandlers.print())
			.andExpect(MockMvcResultMatchers.status().isOk());
		} catch (Exception e) {

			// TODO: handle exception
		}
	}
}
