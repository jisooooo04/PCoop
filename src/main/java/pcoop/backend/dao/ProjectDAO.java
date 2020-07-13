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
	
	 public String joinYNCheck(Map<String,Integer> param )throws Exception{
	    	return mybatis.selectOne("project.joinYNCheck", param);
	    }
	 
	 public ProjectDTO selectBySeq(int seq)throws Exception{
		 return mybatis.selectOne("project.selectBySeq", seq);
	 }
	 
	 public List<ProjectMemberDTO> joinCheck (int project_seq)throws Exception{
	 	return mybatis.selectList("project.joincheck", project_seq);
	 }
	 
	 public int accept (Map<String,Integer>param)throws Exception{
		 return mybatis.update("project.accept", param);
	 }
	 
	 public int countNum (int project_seq)throws Exception{
		 return mybatis.selectOne("project.countPeople", project_seq);
	 }
	 
	 public int getPeopleNum (int project_seq)throws Exception{
		 return mybatis.selectOne("project.getPeopleNum", project_seq);
	 }
	 
	 public int refuse (Map<String,Integer>param)throws Exception{
		 return mybatis.update("project.refuse", param);
	 }
	 
	 public int countProject(int mem_seq)throws Exception{
		 return mybatis.selectOne("project.countProject", mem_seq);
	 }
	 
	 public int exitProject(Map<String,Integer>param)throws Exception{
		 return mybatis.delete("project.exitProject", param);
	 }
	 
	 public int deleteProject(Map<String,Integer>param)throws Exception{
		 return mybatis.delete("project.deleteProject", param);
	 }
	 
	 public String checkLeaderYN(Map<String,Integer>param)throws Exception{
		 return mybatis.selectOne("project.checkLeaderYN", param);
	 }
	 
	 public Map<String,Object> nextLeaderSeq(int project_seq)throws Exception{
		 return mybatis.selectOne("project.nextLeaderSeq", project_seq);
	 }
	 
	 public int updateLeader(int nextLeaderSeq)throws Exception{
		 return mybatis.update("project.updateLeader", nextLeaderSeq);
	 }
	 
	 public int updateProjectLeader(Map<String,Integer>param)throws Exception{
		 return mybatis.update("project.updateProjectLeader", param);
	 }
	 
	 public List<ProjectMemberDTO> getMemberList(int project_seq)throws Exception{
		 return mybatis.selectList("project.getMemberList", project_seq);
	 }
}
