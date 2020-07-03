package pcoop.backend.service;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import pcoop.backend.dao.MemberDAO;
import pcoop.backend.dto.MemberDTO;
import pcoop.backend.dto.ProjectDTO;

@Service
public class MemberService {
	@Autowired
	private MemberDAO mdao;
	
    private JavaMailSender mailSender;
    
    
	public int signup(Map<String, Object> map) throws Exception {

		System.out.println("MemberService의 signup : "+map.get("name"));
		int result = mdao.signup(map);
		return result;
	}
    
	public static String getSHA512(String input){
		String toReturn = null;
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-512");
			digest.reset();
			digest.update(input.getBytes("utf8"));
			toReturn = String.format("%0128x", new BigInteger(1, digest.digest()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return toReturn;
	}

	public boolean duplCheck(String email) throws Exception {
		boolean result = mdao.duplCheck(email);
		return result;
	}
	
	public MemberDTO login(Map<String,String> param) throws Exception {
		MemberDTO mdto = mdao.login(param);
		return mdto;
	}
    

	
    public void join(Map<String, Object>map,MemberDTO dto) {
    	mdao.join(map,dto);
 
    }
 

 

    public String find_idCheck(MemberDTO dto) {
        String id = mdao.find_idCheck(dto);
        
        return id;
    }
 

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
 
 
    //�̸��� �ߺ� Ȯ��
    public boolean email_check(String email) throws Exception{
        
        boolean result = mdao.email_check(email);
        
        return result;
        
    }
 
    //�̸� �ߺ� Ȯ�� join_id_check ��� join_name_check
    public boolean join_name_check(String name) throws Exception {
    
        boolean result = mdao.join_name_check(name);
        
        return result;
    }
 
 
    //�ڽ��� �������� �� �� �ְ� �ϴ� �޼ҵ�
    public List<MemberDTO> member_profile(String user_id) throws Exception{
        
        return mdao.member_profile(user_id);
    }
    
	//ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ my page
    public List<ProjectDTO> getProjectList (int seq)throws Exception{
    	return mdao.getProjectList(seq);
    }
	
    public int modify (Map<String,Object> param)throws Exception{
    	return mdao.modify(param);
    }
	
}
