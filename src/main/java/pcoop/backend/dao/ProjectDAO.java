package pcoop.backend.dao;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import pcoop.backend.dto.ProjectDTO;
import pcoop.backend.dto.ProjectMemberDTO;

@Repository
public class ProjectDAO {

	@Autowired
	private SqlSessionTemplate mybatis;
	
	public int getproject_seq() throws Exception{//project_seq 가져오기
		return mybatis.selectOne("project.getproject_seq");
	}

	public int checkCode(String code)throws Exception{
		return mybatis.selectOne("project.checkCode",code);
	}
	
	public int create_project(ProjectDTO dto)throws Exception{
		return mybatis.insert("project.createProject",dto);
	}
	
	public int insertp_m(ProjectMemberDTO dto)throws Exception{
		return mybatis.insert("project.insertProjectMember",dto);
	}
	
	public ProjectDTO searchByCode(String code)throws Exception{
		return mybatis.selectOne("project.searchByCode",code);
	}
	
	 public String joinCheck(Map<String,Integer> param )throws Exception{
	    	return mybatis.selectOne("project.joinCheck", param);
	    }
	 
	 public ProjectDTO selectBySeq(int seq)throws Exception{
		 return mybatis.selectOne("project.selectBySeq", seq);
	 }
	 
	 public List<ProjectMemberDTO> joinYNCheck (int project_seq)throws Exception{
	 	return mybatis.selectList("project.joinYNcheck", project_seq);
	 }
	 
	 public int accept (Map<String,Integer>param)throws Exception{
		 return mybatis.update("project.accept", param);
	 }
	 
	 public int count (int project_seq)throws Exception{
		 return mybatis.selectOne("project.countPeople", project_seq);
	 }
	 
	 public int getPeople (int project_seq)throws Exception{
		 return mybatis.selectOne("project.getPeople", project_seq);
	 }
	 
	 public int refuse (Map<String,Integer>param)throws Exception{
		 return mybatis.update("project.refuse", param);
	 }
	 
}
