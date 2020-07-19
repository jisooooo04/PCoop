package pcoop.backend.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.mail.internet.MimeMessage;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import pcoop.backend.dto.MemberDTO;
import pcoop.backend.dto.ProjectDTO;
import pcoop.backend.service.MemberService;
import pcoop.backend.service.ProjectService;

@Controller  //컨트롤러 빈 선언
@RequestMapping("/member/")
public class MemberController {

	@Autowired    //서비스를 호출하기 위해서 의존성을 주입
	JavaMailSender mailSender;     //메일 서비스를 사용하기 위해 의존성을 주입함.

	@Autowired
	private MemberService mservice; //서비스를 호출하기 위해 의존성을 주입
	@Autowired 
	private ProjectService pservice;
	
	@Autowired
	private HttpSession session; // 입력한 이메일주소 저장용 

	//로깅을 위한 변수
	private static final Logger logger=
			LoggerFactory.getLogger(MemberController.class);
	private static final String String = null;


	// mailSending 코드
	@RequestMapping( value = "auth" , method=RequestMethod.POST )
	public ModelAndView mailSending(HttpServletRequest request, String e_mail, HttpServletResponse response_email) throws Exception {

		//입력 이메일 중복 체크
		String tomail = request.getParameter("e_mail"); // 받는 사람 이메일
		session.setAttribute("toemail", tomail); // 회원가입 페이지까지 갈 경우를 대비해 세션 저장
		
		if(mservice.duplCheck(tomail)) {
			System.out.println("이메일 중복입력");
			response_email.setContentType("text/html; charset=UTF-8");
			PrintWriter duplCheck_msg = response_email.getWriter();
			duplCheck_msg.println("<script>alert('이메일이 중복되었습니다.');</script>");
			duplCheck_msg.flush();
			ModelAndView mv2 = new ModelAndView();    //ModelAndView로 보낼 페이지를 지정하고, 보낼 값을 지정한다.
			mv2.setViewName("member/email");     //뷰의이름
			
			return mv2;
 
		}else{
		
		Random r = new Random();
		int dice = r.nextInt(4589362) + 49311; //이메일로 받는 인증코드 부분 (난수)

		String setfrom = "okeydoke2@naver.com";
		String title = "회원가입 인증 이메일 입니다."; // 제목
		String content =
				System.getProperty("line.separator")+ //한줄씩 줄간격을 두기위해 작성
				System.getProperty("line.separator")+
				"안녕하세요 회원님 저희 P-Coop 홈페이지를 찾아주셔서 감사합니다"
        +System.getProperty("line.separator")+
        System.getProperty("line.separator")+
        " 인증번호는 " +dice+ " 입니다. "
        +System.getProperty("line.separator")+
        System.getProperty("line.separator")+
        "받으신 인증번호를 홈페이지에 입력해 주시면 다음으로 넘어갑니다."; // 내용


		try {
			MimeMessage message = mailSender.createMimeMessage();
			MimeMessageHelper messageHelper = new MimeMessageHelper(message,
					true, "UTF-8");

			messageHelper.setFrom(setfrom); // 보내는사람 생략하면 정상작동을 안함
			messageHelper.setTo(tomail); // 받는사람 이메일
			messageHelper.setSubject(title); // 메일제목은 생략이 가능하다
			messageHelper.setText(content); // 메일 내용

			mailSender.send(message);
		} catch (Exception e) {
			System.out.println(e);
		}

		ModelAndView mv = new ModelAndView();    //ModelAndView로 보낼 페이지를 지정하고, 보낼 값을 지정한다.
		mv.setViewName("/member/email_injeung");     //뷰의이름
		mv.addObject("dice", dice);

		System.out.println("mv : "+mv);

		response_email.setContentType("text/html; charset=UTF-8");
		PrintWriter out_email = response_email.getWriter();
		out_email.println("<script>alert('이메일이 발송되었습니다. 인증번호를 입력해주세요.');</script>");
		out_email.flush();

		return mv;
		}
	}


	//이메일 인증 페이지 맵핑 메소드
	@RequestMapping("toEmailView")
	public String email() {
		System.out.println("이메일 인증 페이지로 이동");
		return "member/email";
	}
	//회원가입 페이지 맵핑 메소드
	@RequestMapping("toSignup")
	public String toSignup() {
		System.out.println("회원가입 페이지로 이동");

		return "member/signupView";
	}
	
	@RequestMapping("toLoginView")
	public String toLogin() {
		System.out.println("로그인 페이지로 이동");
		return "member/login";
	}
	
	
	
	//이메일로 받은 인증번호를 입력하고 전송 버튼을 누르면 맵핑되는 메소드.
	//내가 입력한 인증번호와 메일로 입력한 인증번호가 맞는지 확인해서 맞으면 회원가입 페이지로 넘어가고,
	//틀리면 다시 원래 페이지로 돌아오는 메소드
	@RequestMapping(value = "join_injeung.do{dice}", method = RequestMethod.POST)
	public ModelAndView join_injeung(String email_injeung, @PathVariable String dice, HttpServletResponse response_equals) throws IOException {

		System.out.println("마지막 : email_injeung : "+email_injeung);      
		System.out.println("마지막 : dice : "+dice);     

		//페이지이동과 자료를 동시에 하기위해 ModelAndView를 사용해서 이동할 페이지와 자료를 담음

		ModelAndView mv = new ModelAndView();     
		mv.setViewName("/member/join.do");  
		mv.addObject("e_mail",email_injeung);

		if (email_injeung.equals(dice)) {
			//인증번호가 일치할 경우 인증번호가 맞다는 창을 출력하고 회원가입창으로 이동함

			mv.setViewName("member/join");
			mv.addObject("e_mail",email_injeung);
			//만약 인증번호가 같다면 이메일을 회원가입 페이지로 같이 넘겨서 이메일을
			//한번더 입력할 필요가 없게 한다.

			mv.addObject("tomail",session.getAttribute("tomail"));//세션에서 인증용으로 입력한 메일주소 불러오기
			System.out.println(session.getAttribute("tomail"));

			response_equals.setContentType("text/html; charset=UTF-8");
			PrintWriter out_equals = response_equals.getWriter();
			out_equals.println("<script>alert('인증번호가 일치하였습니다. 회원가입창으로 이동합니다.');</script>");
			out_equals.flush();

			return mv;

		}else if (email_injeung != dice) {


			ModelAndView mv2 = new ModelAndView(); 

			mv2.setViewName("member/email_injeung");

			response_equals.setContentType("text/html; charset=UTF-8");
			PrintWriter out_equals = response_equals.getWriter();
			out_equals.println("<script>alert('인증번호가 일치하지않습니다. 인증번호를 다시 입력해주세요.'); history.go(-1);</script>");
			out_equals.flush();


			return mv2;

		}    

		return mv;
	}

	@RequestMapping("signup") // 회원가입 기능
	public String signup(HttpServletRequest request) throws Exception{


		String pw = mservice.getSHA512(request.getParameter("pw"));
		String name = request.getParameter("name");
		String email = (java.lang.String) session.getAttribute("toemail");// 세션에서 이메일 가져오기?
		System.out.println("session의 이메일 값 : "+email);
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("email", email);
		map.put("pw", pw);
		map.put("name", name);
		
		int result = mservice.signup(map);

		System.out.println("signup 결과 : "+result);
		
		// 회원가입후 로그인 되어 있도록 하기
		Map<String,String> param =new HashMap<>();
		param.put("loginId", email);
		param.put("loginPw", pw);
		MemberDTO mdto = mservice.login(param);
		String ip_address = request.getRemoteAddr();
		if(mdto != null) {
			this.session.setAttribute("loginInfo", mdto); // 로그인시 세션에 회원정보 저장
			this.session.setAttribute("ip_address", ip_address);
		}
		

		return "redirect:/";

	}

	@RequestMapping("login") //로그인
	public String login(String email, String pw, ServletRequest request, HttpServletResponse response) throws Exception{
		Map<String,String> param =new HashMap<>();

		param.put("loginId", email); // 이메일이 아이디 역할
		param.put("loginPw", mservice.getSHA512(pw));
		MemberDTO mdto = mservice.login(param);
		String ip_address = request.getRemoteAddr();

		if(mdto != null) {
			System.out.println(mdto.getName() +" : "+ mdto.getEmail());

			this.session.setAttribute("loginInfo", mdto); // 로그인시 세션에 회원정보 저장
			this.session.setAttribute("ip_address", ip_address);
			return "redirect:/";
		}else {
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter login_fail = response.getWriter();
			login_fail.println("<script>alert('회원정보가 일치하지않습니다. 이메일과 비밀번호를 다시 확인해주세요.'); history.go(-1);</script>");
			login_fail.flush();
			return "member/email";
		}
	
	}
	
	@RequestMapping("logout")
	public String logout() {
		session.invalidate();
		return "redirect:/";
	}
	
	//ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ my page
	@RequestMapping("gomypage")
	public String gomypage (Model model)throws Exception{
		MemberDTO mdto = (MemberDTO)session.getAttribute("loginInfo");
		int seq = mdto.getSeq();
		List<ProjectDTO> project_list = mservice.getProjectList(seq); //내가 속한 프로젝트들 
		model.addAttribute("list", project_list);
		model.addAttribute("list_size", project_list.size());
		//----------내가 속한 모든 프로젝트 뽑기
		
		System.out.println(project_list.size());
		int peopleNum = 0;
		
		//----------내가 리더인 프로젝트들의 다음 조원들 뽑기
		for(ProjectDTO dto : project_list) {
			int count = dto.getPeople_num();
			if(count!=1) {//프로젝트 팀원이 나 혼자인 경우
				List<Integer> SelectMyProjectSeq =mservice.SelectMyPojectSeq(seq);
			}
		}

		//프로젝트에 속한 현재 인원 수 
		JsonObject respObj = new JsonObject(); 
		for(ProjectDTO dto : project_list) {
			int project_seq = dto.getSeq();
			String key = project_seq+"";
			int countPeople = pservice.countNum(project_seq);
			respObj.addProperty(key, countPeople);			
		}
		model.addAttribute("respObj",respObj);
		return "member/mypage";
	}
	
	@ResponseBody
	@RequestMapping("modify")
	public String modify (String name ,String pw)throws Exception{
		int seq = ((MemberDTO)session.getAttribute("loginInfo")).getSeq();
		Map <String,Object> param = new HashMap<>();
		pw=mservice.getSHA512(pw);
    	param.put("name", name);
    	param.put("pw", pw);
    	param.put("seq", seq);
		int result = mservice.modify(param);
		
		return result+"";
	}
	
	@ResponseBody
	@RequestMapping(value ="delmem",produces="application/gson;charset=utf8")
	public String delmem(int seq,String pw)throws Exception{
		Map<String , Object> map = new HashMap<>();
		map.put("seq", seq); // seq 값 세션에서?
		map.put("pw", mservice.getSHA512(pw));
		int check = mservice.checkmem(map);//비밀번호가 일치하는지 확인
		String result = null;
		if(check==0) {//일치하는 정보가 없음.
			result = "fail";
		}else if(check==1) {//로그인 정보 일치
			List<ProjectDTO> plist = mservice.getProjectList(seq);//내가 속한 프로젝트 불러오기
			for(ProjectDTO dto :plist) {
				Map<String,Integer>param =new HashMap<>();
				param.put("mem_seq", seq);
				param.put("project_seq",dto.getSeq());
				if(dto.getLeader_seq()==seq) {//내가 리더라면
					int res = pservice.updateLeader(dto.getSeq());//리더위임
					if(res==0) {//넘겨줄 팀원 없음	
						pservice.deleteProject(param);//프로젝트 삭제						
					}
					pservice.exitProject(param);//프로젝트 멤버 테이블에서 삭제
				}else{//리더가 아니라면
					pservice.exitProject(param);//프로젝트 멤버 테이블에서 삭제
				}
			}
			int delresult = mservice.delmem(seq); //멤버 테이블에서 삭제
			session.invalidate();//세션 무효화
				result = "success";
		}
		return result;
	}
	
}