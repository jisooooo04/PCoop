package pcoop.backend.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import pcoop.backend.dao.MemberDAO;
import pcoop.backend.dto.MemberDTO;

@Service
public class MemberService {
	@Autowired
	private MemberDAO mdao;
	
    private JavaMailSender mailSender;
    
    
    //회원가입 메소드, Map과 dto를 같이 넘김
    public void join(Map<String, Object>map,MemberDTO dto) {
    	mdao.join(map,dto);
 
    }
 
    //로그인 관련 메소드 (세션에 아이디와 비밀번호를 저장)
    public boolean loginCheck(MemberDTO dto, HttpSession session) {
        
        boolean result = mdao.loginCheck(dto);
        if(result) {    //로그인 성공
            session.setAttribute("email", dto.getEmail());
            session.setAttribute("pw", dto.getPw());
            System.out.println(session.getAttribute("email"));
            System.out.println(session.getAttribute("pw"));
        }
        
        return result;
    }
 
    //아이디 찾기
    public String find_idCheck(MemberDTO dto) {
        String id = mdao.find_idCheck(dto);
        
        return id;
    }
 
    //비밀번호 찾기
    public String find_passCheck(MemberDTO dto) {
        String pass = mdao.find_passCheck(dto);
        return pass;
    }
 
 
    public void authentication(MemberDTO dto) {
        
    	mdao.authentication(dto);
    }
 
 
    public void pass_change(Map<String, Object> map, MemberDTO dto) throws Exception {
        
        
    	mdao.pass_change(map,dto);
    }
 
 
    //이메일 중복 확인
    public boolean email_check(String email) throws Exception{
        
        boolean result = mdao.email_check(email);
        
        return result;
        
    }
 
    //이름 중복 확인 join_id_check 대신 join_name_check
    public boolean join_name_check(String name) throws Exception {
    
        boolean result = mdao.join_name_check(name);
        
        return result;
    }
 
 
    //자신의 프로필을 볼 수 있게 하는 메소드
    public List<MemberDTO> member_profile(String user_id) throws Exception{
        
        return mdao.member_profile(user_id);
    }
	
	
}
