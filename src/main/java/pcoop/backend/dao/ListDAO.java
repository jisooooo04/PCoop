package pcoop.backend.dao;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import pcoop.backend.dto.CardDTO;
import pcoop.backend.dto.ListDTO;



@Repository
public class ListDAO {

	@Autowired
	private JdbcTemplate jdbc;

	@Autowired
	private SqlSessionTemplate mybatis;
	
	
	public List<ListDTO> selectAll() throws Exception{
		System.out.println("ListDAO의  selectAll");
		return mybatis.selectList("List.selectAll");
	}
	
	public List<CardDTO> selectCard(String listId) throws Exception{
		System.out.println("ListDAO의  selectCard");
		return mybatis.selectList("List.selectCard",listId);
	}
	
	
	public List<ListDTO> selectList(Map<String, Object> param) throws Exception{
		System.out.println("ListDAO의  selectList : "+param);
		return mybatis.selectList("List.selectList",param);
	}
	
	   public int insert(Map<String, Object> param) {
		      return mybatis.insert("List.insert", param);
	}
	   
	   public int delete(Map<String, Object> param) {
		      return mybatis.delete("List.delete", param);
	}
	   
	   public int update(Map<String, Object> param) {
		      return mybatis.update("List.update", param);
	}
	   
	   public int selectListId(Map<String, Object> param) {
		      return mybatis.selectOne("List.selectListId", param);
	}
	   public int insertlist(Map<String, Object> param) {
		      return mybatis.insert("List.insertlist", param);
	}
	   public int updatelist(Map<String, Object> param) {
		      return mybatis.update("List.updatelist", param);
	}
	   public CardDTO selectLatestCard() {
		      return mybatis.selectOne("List.selectLatestCard");
	}
	   public int deleteCardByListId(Map<String, Object> param) {
		      return mybatis.delete("List.deleteCardByListId", param);
	}
	   public int deleteListById(Map<String, Object> param) {
		      return mybatis.delete("List.deleteListById", param);
	}
	   public int updatelistStyle(Map<String, Object> param) {
		      return mybatis.update("List.updatelistStyle", param);
	}
	   public int checkboxChange(Map<String, Object> param) {
		      return mybatis.update("List.checkboxChange", param);
	}
	   public int cardListIdUpdate(Map<String, Object> param) {
		      return mybatis.update("List.cardListIdUpdate", param);
	}
	   
	   
	   public int selectCount() {
		   System.out.println("갯수확인");
			return mybatis.selectOne("List.selectCount");
			
		}
	   public int trueCount() {
		   System.out.println("투루갯수확인");
			return mybatis.selectOne("List.trueCount");
			
		}
	
	   
}

