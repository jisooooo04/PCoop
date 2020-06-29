package pcoop.backend.dao;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import pcoop.backend.dto.CalendarDTO;

@Repository
public class CalendarDAO {
	
	@Autowired
	private SqlSessionTemplate mybatis;
	
	public List<CalendarDTO> selectAll(int project_seq) throws Exception{
		return mybatis.selectList("calendar.selectAll",project_seq);
	}
	
	public int select_seq() {
		return mybatis.selectOne("calendar.select_seq");
	}
	
	public int insert(CalendarDTO dto) {
//		System.out.println(dto.getSeq()+":"+dto.getTitle()+":"+dto.getContents()+":"+dto.getWriter()+":"+dto.getStart_date()+":"+dto.getEnd_date()+":"+dto.getColor());
		return mybatis.insert("calendar.insert", dto);
	}
	
	public CalendarDTO selectEvent (int seq){
		return mybatis.selectOne("calendar.selectEvent",seq);
	}
	
	
}
