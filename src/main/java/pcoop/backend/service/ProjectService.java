package pcoop.backend.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pcoop.backend.dao.ProjectDAO;
import pcoop.backend.dto.ProjectDTO;
import pcoop.backend.dto.ProjectMemberDTO;

@Service
public class ProjectService {
	
	@Autowired
	private ProjectDAO dao;
	@Autowired
	FileService fservice;
	
	public int getproject_seq ()throws Exception{
		return dao.getproject_seq();
	}
	
	
	public String create_code ()throws Exception{//랜덤코드만들기+중복검사
		String code="";
		while(true) {
			Random rand = new Random();
			StringBuffer sb = new StringBuffer();
			for(int i = 0; i<6;i++) {
				int index = rand.nextInt(3);
				switch(index) {
				case 0 : 
					sb.append((char)(rand.nextInt(26)+97));
					break;
				case 1:
					sb.append((char)(rand.nextInt(26)+65));
					break;
				case 2:
					sb.append(rand.nextInt(10));
					break;
				}
			}
			code = sb.toString();
			int check = dao.checkCode(code);//코드 중복검사
			if(check==1) {//이미 존재
				continue;
			}else if (check==0) {//존재 하지 않음
				break;
			}
		}
		return code;
	}
	
	public int create_project (ProjectDTO dto)throws Exception{
		return dao.create_project(dto);
	}
	
	public int insertp_m(ProjectMemberDTO dto)throws Exception{
		return dao.insertp_m(dto);
	}
	

	public ProjectDTO searchByCode(String code)throws Exception{
		return dao.searchByCode(code);
	}
	
	public String JoinYNCheck(Map<String,Integer> param )throws Exception{
		return dao.joinYNCheck(param);
	}
	
	public ProjectDTO selectBySeq(int seq)throws Exception{
		return dao.selectBySeq(seq);
	}
	
	public List<ProjectMemberDTO> joinCheck(int project_seq)throws Exception{
	   return dao.joinCheck(project_seq);
	  }
	 
	 public int accept (Map<String,Integer> param)throws Exception{
		 return dao.accept(param);
	 }
	
	 public int refuse (Map<String,Integer> param)throws Exception{
		 return dao.refuse(param);
	 }
	 
	 public int countNum(int project_seq)throws Exception{
		 return dao.countNum(project_seq);
	 }
	 
	 public int getPeopleNum(int project_seq)throws Exception{
		 return dao.getPeopleNum(project_seq);
	 }
	 

	public int create_backup(ProjectDTO dto) throws Exception {
		
		int seq = dto.getSeq();
		String name = dto.getName();
		fservice.createProjectBackup(seq, name);
		return 0;
	}
	
	public int countProject(int mem_seq)throws Exception{
		return dao.countProject(mem_seq);
	}
	
	public int exitProject(Map<String,Integer>param)throws Exception{
		return dao.exitProject(param);
	}
	
	public int deleteProject(Map<String,Integer>param)throws Exception{
		return dao.deleteProject(param);
	}
	
	public String checkLeaderYN(Map<String,Integer>param)throws Exception{
		return dao.checkLeaderYN(param);
	}
	
	public int updateLeader(int project_seq)throws Exception{
		Map<String,Object> map= dao.nextLeaderSeq(project_seq);
		if(map==null) {
			System.out.println("넘겨줄 팀원 없음.");
			return 0;
		}else {
			
			int member_project_seq = Integer.parseInt((map.get("SEQ").toString()));//프로젝트 멤버 시퀀스 
			int member_seq = Integer.parseInt((map.get("MEMBER_SEQ").toString()));//멤버 시퀀스
				dao.updateLeader(member_project_seq);//멤버 프로젝트 테이블 업데이트
				
				Map<String,Integer>param = new HashMap<>();
				param.put("nextProjectLeader", member_seq);
				param.put("project_seq", project_seq);
				dao.updateProjectLeader(param);//프로젝트 테이블 업데이트
				
			return 1;
		}		
	}
}
