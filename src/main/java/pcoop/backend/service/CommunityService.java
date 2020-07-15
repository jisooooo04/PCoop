package pcoop.backend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pcoop.backend.dao.CommunityDAO;
import pcoop.backend.dto.CommunityDTO;

@Service
public class CommunityService {
	
	@Autowired
	private CommunityDAO cmdao;
	
	
	public List<CommunityDTO> selectList() {
		return cmdao.selectList();
	}
	
}
