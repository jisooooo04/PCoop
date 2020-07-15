package pcoop.backend.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import pcoop.backend.dao.ListDAO;
import pcoop.backend.dto.CardDTO;
import pcoop.backend.dto.ListDTO;

@Service
public class ListService {

	@Autowired
	private ListDAO ldao;


	public List<ListDTO> selectAll() throws Exception{
		//System.out.println("ListService의  selectAll");
		List<ListDTO> ldtoList = ldao.selectAll();
		return ldtoList;
	}

	public List<CardDTO> selectCard(String listId) throws Exception{
		//System.out.println("ListService의  selectCard");
		List<CardDTO> ldtoList = ldao.selectCard(listId);
		return ldtoList;
	}

	
	@Transactional("txManager")
	public JsonObject load(Map<String, Object> param) throws Exception{
		//System.out.println("ListService의  selectListId");
		List<ListDTO> TaskList = ldao.selectList(param);
		
		//파싱 작업 후 컨트롤러로 보내기
		
		String TaskListArr = new Gson().toJson(TaskList);
		JsonParser jParser = new JsonParser();
		JsonArray jsonListArray = (JsonArray)jParser.parse(TaskListArr);
		JsonObject returnjson = new JsonObject();
		returnjson.addProperty("onSingleLine", true); //설정값 넣기
		JsonArray lists = new JsonArray(); // 리스트들이 들어갈 곳

		for(int j = 0;j<jsonListArray.size();j++) {
			JsonObject ListObject = (JsonObject) jsonListArray.get(j); //jsonArray의 첫번째 값 꺼내기
			String list_Id = ListObject.get("id").getAsString();
			String listTitle = String.valueOf(ListObject.get("title"));// null 오브젝트에 getAsString() 을 사용하면 에러 발생, String.valueOf()를 사용할것
			String defaultStyle = ListObject.get("defaultStyle").getAsString();
			
			List<CardDTO> selectlist = ldao.selectCard(list_Id);
			String CardArr = new Gson().toJson(selectlist);

			// 문자열 쪼개기! 파싱!
			JsonArray jsonCardArray = (JsonArray)jParser.parse(CardArr);
			JsonObject list = new JsonObject();	
			JsonArray items = new JsonArray(); //리스트안의 작업들(아이템들)이 들어갈돗
			for(int i = 0;i<jsonCardArray.size();i++) {
				JsonObject item = new JsonObject(); //개별 작업(아이템): 배열로 넣어야 하기 때문에 반복문 안에서 생성
				JsonObject CardObject = (JsonObject) jsonCardArray.get(i); //jsonArray의 첫번째 값 꺼내기
				String id = String.valueOf(CardObject.get("id"));
				String title = String.valueOf(CardObject.get("title"));

				if(!title.contentEquals("null")) {
					String trim_title = title.substring(1, title.length()-1); //쌍따옴표 제거
					item.addProperty("title", trim_title);  
				}

				String description = String.valueOf(CardObject.get("description"));
				if(!description.contentEquals("null")) {
					String trim_description = description.substring(1, description.length()-1); //쌍따옴표 제거
					item.addProperty("description", trim_description);  
				}

				String dueDate = String.valueOf(CardObject.get("dueDate")); // null 오브젝트에 getAsString() 을 사용하면 에러 발생, String.valueOf()를 사용할것
				if(!dueDate.contentEquals("null")) {
					String trim_dueDate = dueDate.substring(1, dueDate.length()-10); //쌍따옴표 제거	
					item.addProperty("dueDate", trim_dueDate );  //시간 없이 날짜만 출력?
				}
				
				String card_id = CardObject.get("id").getAsString();
				item.addProperty("id", card_id ); 

				String listId = CardObject.get("listId").getAsString();
				item.addProperty("listId", listId ); 

				boolean done = false;
				if(CardObject.get("done").getAsString().equals("true")){
					done = true;
				}
				item.addProperty("done", done ); 
				items.add(item); // 각 아이템들을 items배열에 담기

			}

			list.addProperty("id", list_Id);

			if(!listTitle.contentEquals("null")) {
				String trim_listTitle = listTitle.substring(1, listTitle.length()-1); //쌍따옴표 제거
				list.addProperty("title", trim_listTitle);  
			}

			list.addProperty("defaultStyle", defaultStyle); //리스트 색상 생성시엔 회색으로 고정
			list.add("items", items);
			lists.add(list);

		}
		returnjson.add("lists", lists);
		System.out.println("returnjson : "+returnjson);
		
		return returnjson;
	}

	public int insert (CardDTO cdto) {
		return ldao.insert(cdto);
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
	public int insertlist (ListDTO ldto) {
		return ldao.insertlist(ldto);
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

	public int listIndexUpdate (Map<String, Object> param) {
		return ldao.listIndexUpdate(param);
	}
	
	public int selectCount(Map<String, Object> param){
		return ldao.selectCount(param);
	}
	public int trueCount(Map<String, Object> param){
		return ldao.trueCount(param);
	}
	
	
}
