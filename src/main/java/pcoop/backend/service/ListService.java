package pcoop.backend.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pcoop.backend.dao.ListDAO;
import pcoop.backend.dto.CardDTO;
import pcoop.backend.dto.ListDTO;

@Service
public class ListService {

	@Autowired
	private ListDAO ldao;


	public List<ListDTO> selectAll() throws Exception{
		System.out.println("ListService의  selectAll");
		List<ListDTO> ldtoList = ldao.selectAll();
		return ldtoList;
	}

	public List<CardDTO> selectCard(String listId) throws Exception{
		System.out.println("ListService의  selectCard");
		List<CardDTO> ldtoList = ldao.selectCard(listId);
		return ldtoList;
	}

	public List<ListDTO> selectList(Map<String, Object> param) throws Exception{
		System.out.println("ListService의  selectListId");
		List<ListDTO> TaskList = ldao.selectList(param);
		return TaskList;
	}

	public int insert (Map<String, Object> param) {
		return ldao.insert(param);
	}
	public int delete (Map<String, Object> param) {
		return ldao.delete(param);
	}

	public int update (Map<String, Object> param) {
		return ldao.update(param);
	}

	public int selectListId (Map<String, Object> param) {
		return ldao.selectListId(param);
	}
	public int insertlist (Map<String, Object> param) {
		return ldao.insertlist(param);
	}
	public int updatelist (Map<String, Object> param) {
		return ldao.updatelist(param);
	}

	public CardDTO selectLatestCard () {
		return ldao.selectLatestCard();
	}
	public int deleteCardByListId (Map<String, Object> param) {
		return ldao.deleteCardByListId(param);
	}
	public int deleteListById (Map<String, Object> param) {
		return ldao.deleteListById(param);
	}
	public int updatelistStyle (Map<String, Object> param) {
		return ldao.updatelistStyle(param);
	}
	
	public int checkboxChange (Map<String, Object> param) {
		return ldao.checkboxChange(param);
	}
	
	public int cardIndexUpdate (Map<String, Object> param) {
		return ldao.cardIndexUpdate(param);
	}
	
	
	public int selectCount(){
		return ldao.selectCount();
	}
	public int trueCount(){
		return ldao.trueCount();
	}
	
}
