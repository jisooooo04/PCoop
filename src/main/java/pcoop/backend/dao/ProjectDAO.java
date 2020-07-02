package pcoop.backend.dao;

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
		return mybatis.selectOne("getproject_seq");
	}

	public int checkCode(String code)throws Exception{
		return mybatis.selectOne("checkCode",code);
	}
	
	public int create_project(ProjectDTO dto)throws Exception{
		return mybatis.insert("createProject",dto);
	}
	
	public int insertp_m(ProjectMemberDTO dto)throws Exception{
		return mybatis.insert("insertProjectMember",dto);
	}
	
}
