package pcoop.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pcoop.backend.dao.CalendarDAO;
import pcoop.backend.dto.CalendarDTO;

@Service
public class CalendarService {
	
	@Autowired
	private CalendarDAO Cdao;
	
	public int addEvent (CalendarDTO dto)throws Exception{
		int result = Cdao.insert(dto);
		return result;
	}
	
}
