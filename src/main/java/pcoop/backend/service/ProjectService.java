package pcoop.backend.service;

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
	
	public int create_backup(ProjectDTO dto) throws Exception {
		return 0;
	}
	
}
