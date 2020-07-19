package pcoop.backend.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import pcoop.backend.dto.MemberDTO;
import pcoop.backend.dto.ProjectDTO;
import pcoop.backend.dto.ProjectMemberDTO;

@Repository
public class MemberDAO {
	@Autowired
	private JdbcTemplate jdbc;
	@Autowired
	private SqlSessionTemplate mybatis;
	@Inject
	SqlSession sqlSession;
	

	
	// 회원가입 관련 메소드
	public int signup(Map<String, Object> map) throws Exception{
		System.out.println("MemberDAO의 signup : "+map.get("name")+map.get("pw")+map.get("email"));

		return mybatis.insert("Member.insert",map);
	}
	public MemberDTO login(Map<String,String> param) throws Exception{
		return mybatis.selectOne("Member.login",param);
	}
	
	
	public boolean duplCheck(String email){
		int result = mybatis.selectOne("Member.duplCheck", email);
		System.out.println("DAO duplCheck : "+result);
		if(result > 0) { return true;}
		else { return false;}
	}
	
	
	
	
	   // 회원가입 관련 메소드 (일단 유지)
		public void join(Map<String, Object>map, MemberDTO dto) {
			map.get("user_id");
			map.get("member_pass");
			map.get("e_mail");
			sqlSession.insert("member.insertUser",map);        
		}

		
		
		
	    //로그인관련 메소드 (미구현)
	    public boolean loginCheck(MemberDTO dto) {
	        String name
	            =sqlSession.selectOne("member.login_check", dto);
	        
	        //조건식 ? true일때의 값 : false일때의 값
	        return (name==null) ? false : true;
	    }


	    //아이디 찾기 관련 메소드 (미구현)
	    public String find_idCheck(MemberDTO dto) {
	        String id = sqlSession.selectOne("member.find_id_check", dto);
	        return id;
	        
	    }
	 
	    //비밀번호 찾기 관련 메소드 (미구현)
	    public String find_passCheck(MemberDTO dto) {
	        String pass = sqlSession.selectOne("member.find_pass_check", dto);
	        return pass;
	    }


	    //회원 인증 관련 메소드 (미구현)
	    //버튼을 클릭한 회원의 정보를 회원 테이블에 저장해서 사용할 수 있게 함
	    public void authentication(MemberDTO dto) {
	        
	        sqlSession.insert("member.authentication", dto);
	        
	    }
	    
	    
	    public void pass_change(Map<String, Object> map, MemberDTO dto)throws Exception{
	        
	        map.get("member_pass");
	        map.get("e_mail");
	 
	        sqlSession.update("member.pass_change", map);
	    }
	    
	    
	    public boolean email_check(String e_mail) throws Exception {
	        String email
	        =sqlSession.selectOne("member.email_check", e_mail);
	    
	        //조건식 ? true일때의 값 : false일때의 값
	        return (email==null) ? true : false;
	        
	    }
	 
	 
	    public boolean join_name_check(String name) throws Exception {
	        String user_name1
	        =sqlSession.selectOne("member.join_name_check", name);
	    
	        //조건식 ? true일때의 값 : false일때의 값
	        return (user_name1==null) ? true : false;
	    }
	    
	    
	    
	  //ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ my page
	    public List<ProjectDTO> getProjectList(int seq)throws Exception{
	    	List<Integer> project_seq = mybatis.selectList("project.get_project_seq",seq);
	    	System.out.println(project_seq.size());
	    	
	    	if(project_seq.size()==0) {//속한 프로젝트가 없을 때 
	    		List<Integer> templist = new ArrayList<>();
	    		templist.add(0);
	    		return mybatis.selectList("project.get_projectList",templist);
	    	}else {
	    		return mybatis.selectList("project.get_projectList",project_seq);
	    	}
	    	
	    }
	    
	    public int modify(Map<String,Object>param)throws Exception{
	    	return mybatis.update("Member.modify",param);
	    }
	    
	    public int checkmem(Map<String,Object> param)throws Exception{
	    	return mybatis.selectOne("Member.checkmem",param);
	    }
	    
	    public int delmem(int seq)throws Exception{
	    	return mybatis.delete("Member.delmem", seq);
	    }
	    
	    public List<Integer> SelectMyProjectSeq(int seq)throws Exception{
	    	return mybatis.selectList("project.SelectMyProjectSeq", seq);
	    }
	    
	    
	    
	    //-------------------------채팅방 기능-------------------------------
	    
	    //member_seq로 회원 이름 조회
	    public String selectName(int member_seq) throws Exception {
	        return mybatis.selectOne("Member.selectName", member_seq);
	    }
	    
	    
}
