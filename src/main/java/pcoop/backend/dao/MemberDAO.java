package pcoop.backend.dao;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import pcoop.backend.dto.MemberDTO;

@Repository
public class MemberDAO {
	@Autowired
	private JdbcTemplate jdbc;
	@Autowired
	private SqlSessionTemplate mybatis;
	@Inject
	SqlSession sqlSession;
	
	   // 회원가입 관련 메소드
		public void join(Map<String, Object>map, MemberDTO dto) {

			map.get("user_id");
			map.get("member_pass");
			map.get("e_mail");
			sqlSession.insert("member.insertUser",map);        
		}

	    //로그인관련 메소드
	    public boolean loginCheck(MemberDTO dto) {
	        String name
	            =sqlSession.selectOne("member.login_check", dto);
	        
	        //조건식 ? true일때의 값 : false일때의 값
	        return (name==null) ? false : true;
	    }


	    //아이디 찾기 관련 메소드
	    public String find_idCheck(MemberDTO dto) {
	        String id = sqlSession.selectOne("member.find_id_check", dto);
	        return id;
	        
	    }
	 
	    //비밀번호 찾기 관련 메소드
	    public String find_passCheck(MemberDTO dto) {
	        String pass = sqlSession.selectOne("member.find_pass_check", dto);
	        return pass;
	    }


	    //회원 인증 관련 메소드
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
	 
	    
	    //회원의 프로필 정보를 리턴한다.
	    public List<MemberDTO> member_profile(String user_id) throws Exception {
	        
	        return sqlSession.selectList("member.member_profile", user_id);
	    }
	    
	    

}
