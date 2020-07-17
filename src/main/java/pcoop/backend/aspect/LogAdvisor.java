package pcoop.backend.aspect;

import javax.servlet.http.HttpSession;

import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.beans.factory.annotation.Autowired;

import pcoop.backend.dto.MemberDTO;
import pcoop.backend.dto.ProjectDTO;

public class LogAdvisor {

	@Autowired
	private HttpSession session;

	
	//	<!-- aop:around로 로그인체크 : 프로젝트 컨트롤러 검사 - 멤버DTO가 세션에 있어야함--> 
	public Object loginCheck(ProceedingJoinPoint pjp) throws Throwable {
		MemberDTO mdto = (MemberDTO) session.getAttribute("loginInfo");
		if(mdto == null) {
			System.out.println("LogAdvisor > loginCheck 가 로그인 되지않은 사용자를 로그인화면으로 리다이렉트 합니다!");
			return "redirect:/member/toLoginView";
		}		
		String returnValue = (String)pjp.proceed();
		return returnValue;
	}	
	
	//	<!-- aop:around로 프로젝트체크 : 할일/파일/캘린더/채팅 검사 - 프로젝트DTO가 세션에 있어야함 -->
	public Object projectCheck(ProceedingJoinPoint pjp) throws Throwable {
		  ProjectDTO pdto = (ProjectDTO)session.getAttribute("projectInfo");

		  if(pdto == null) {
			System.out.println("LogAdvisor > projectCheck 가 프로젝트를 선택하지 않은 사용자를 마이페이지로 리다이렉트 합니다!");
			return "redirect:/member/gomypage";
		}		
		  Object returnValue = pjp.proceed(); //검사하는 컨트롤러에서 리턴값이 있을 경우 캐스팅 주의
		return returnValue;
	}
	
}
