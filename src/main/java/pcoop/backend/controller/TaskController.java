package pcoop.backend.controller;


import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import pcoop.backend.dto.CardDTO;
import pcoop.backend.dto.ListDTO;
import pcoop.backend.service.ListService;


@Controller
@RequestMapping("/Task/")
public class TaskController {

	@Autowired
	private ListService lservice;

	
	@RequestMapping("/task")
	public String Task() {
		return "Task/task";
	}

	
	

	@ResponseBody
	@RequestMapping("cardListIdUpdateAjax")
	public void cardListIdUpdate(HttpServletRequest request) {
		System.out.println("cardListIdUpdate 시작");
		
		Enumeration params = request.getParameterNames();
		System.out.println("----------------------------");
		while (params.hasMoreElements()){
			String name = (String)params.nextElement();
			System.out.println(name + " : " +request.getParameter(name));
		}
		System.out.println("----------------------------");



		Map<String, Object> param = new HashMap<>();
		param.put("id", request.getParameter("id")); 
		param.put("listId", request.getParameter("listId"));

		int result = lservice.cardListIdUpdate(param);
		
		System.out.println("cardListIdUpdate 결과 : "+result);


	}
	
	
	
	@ResponseBody
	@RequestMapping("checkboxChangeAjax")
	public void checkboxChangeAjax(HttpServletRequest request) {
		System.out.println("checkboxChangeAjax 시작");
		
		Enumeration params = request.getParameterNames();
		System.out.println("----------------------------");
		while (params.hasMoreElements()){
			String name = (String)params.nextElement();
			System.out.println(name + " : " +request.getParameter(name));
		}
		System.out.println("----------------------------");



		Map<String, Object> param = new HashMap<>();
		param.put("id", request.getParameter("id")); 
		param.put("done", request.getParameter("done"));


		int result = lservice.checkboxChange(param);
		System.out.println("checkboxChange 결과 : "+result);


	}
	
	
	
	@ResponseBody
	@RequestMapping("styleChangeAjax")
	public void styleChangeAjax(HttpServletRequest request) {
		System.out.println("styleChangeAjax 시작");
		
		Enumeration params = request.getParameterNames();
		System.out.println("----------------------------");
		while (params.hasMoreElements()){
			String name = (String)params.nextElement();
			System.out.println(name + " : " +request.getParameter(name));
		}
		System.out.println("----------------------------");



		Map<String, Object> param = new HashMap<>();
		param.put("id", request.getParameter("id")); 
		param.put("defaultStyle", request.getParameter("defaultStyle"));


		// id 존재 여부 확인
		int dupleList = lservice.selectListId(param);
		System.out.println("styleChange 존재하는 리스트 : "+dupleList);


		if(dupleList>0) {
			// id가 이미 존재하면 update
			System.out.println("리스트 스타일 수정");

			int result = lservice.updatelistStyle(param);
			System.out.println("updatelistStyle 결과 : "+result);

		}


	}
	
	
	
	
	@ResponseBody
	@RequestMapping("RemoveList_Ajax")
	public void RemoveList_Ajax(HttpServletRequest request) {
		System.out.println("RemoveList_Ajax 시작");
		Enumeration params = request.getParameterNames();
		System.out.println("----------------------------");
		while (params.hasMoreElements()){
			String name = (String)params.nextElement();
			System.out.println(name + " : " +request.getParameter(name));
		}
		System.out.println("----------------------------");


		Map<String, Object> param = new HashMap<>();
		param.put("listId", request.getParameter("id")); 

		// 1. 받아온값을 listId로 하는 카드들 모두 삭제
		int result1 = lservice.deleteCardByListId(param);
		if(result1>0) {
			System.out.println("해당 카드 모두 삭제");
		}

		// 2. 받아온 값의 id로 하는 리스트 삭제
		int result2 = lservice.deleteListById(param);
		if(result2>0) {
			System.out.println("해당 리스트 삭제");
		}

	}




	@ResponseBody
	@RequestMapping("finishTitleEditingAjax")
	public void finishTitleEditingAjax(HttpServletRequest request) {
		System.out.println("test ajaxtest");
		Enumeration params = request.getParameterNames();
		System.out.println("----------------------------");
		while (params.hasMoreElements()){
			String name = (String)params.nextElement();
			System.out.println(name + " : " +request.getParameter(name));
		}
		System.out.println("----------------------------");

		Map<String, Object> param = new HashMap<>();
		param.put("id", request.getParameter("listId")); 
		param.put("title", request.getParameter("title"));


		// id 존재 여부 확인
		int dupleList = lservice.selectListId(param);
		System.out.println("dupleList : "+dupleList);


		if(dupleList>0) {
			// id가 이미 존재하면 update
			System.out.println("리스트 이름 수정");

			int result = lservice.updatelist(param);
			System.out.println("updatelist 결과 : "+result);

		}else {
			// id가 없으면 생성
			System.out.println("리스트 생성");
			int result = lservice.insertlist(param);
			System.out.println("insertlist 결과 : "+result);

		}



	}

	@ResponseBody
	@RequestMapping("insert")
	public Object insert(HttpServletRequest request) {
		System.out.println("test insert");
		Enumeration params = request.getParameterNames();
		System.out.println("----------------------------");
		while (params.hasMoreElements()){
			String name = (String)params.nextElement();
			System.out.println(name + " : " +request.getParameter(name));
		}
		System.out.println("----------------------------");


		Map<String, Object> param = new HashMap<>();
		param.put("listId", request.getParameter("listId")); 
		param.put("title", request.getParameter("title")); 
		param.put("description", request.getParameter("description")); 
		param.put("dueDate", request.getParameter("dueDate")); 


		int result = lservice.insert(param);
		System.out.println("insert 결과 : "+result);
		Boolean success = false;
		if(result>0) {
			success = true;
		}else {
			success = false;
		}

		CardDTO cdto = lservice.selectLatestCard();
		//		System.out.println("List<BoardDTO> list의 사이즈 : "+list.size());
		//클라이언트는 JSON으로 받는게 좋음 : List<BoardDTO>를 JSON으로

		Map<String, Object> cardMap = new HashMap<>();
		cardMap.put("id", cdto.getId());
		cardMap.put("title", cdto.getTitle());
		cardMap.put("done", cdto.getDone());
		cardMap.put("listId", cdto.getListId());
		cardMap.put("success", success);
		return cardMap;

	}

	@ResponseBody
	@RequestMapping("delete")
	public Object delete(HttpServletRequest request) {
		System.out.println("test delete");
		Enumeration params = request.getParameterNames();
		System.out.println("----------------------------");
		while (params.hasMoreElements()){
			String name = (String)params.nextElement();
			System.out.println(name + " : " +request.getParameter(name));
		}
		System.out.println("----------------------------");


		Map<String, Object> param = new HashMap<>();
		param.put("id", request.getParameter("id")); 



		int result = lservice.delete(param);
		System.out.println("delete 결과 : "+result);
		Boolean success = false;
		if(result>0) {
			success = true;
		}else {
			success = false;
		}

		param.put("success", success);
		return param;
	}

	@ResponseBody
	@RequestMapping("update")
	public Object update(HttpServletRequest request) {
		System.out.println("test update");
		Enumeration params = request.getParameterNames();
		System.out.println("----------------------------");
		while (params.hasMoreElements()){
			String name = (String)params.nextElement();
			System.out.println(name + " : " +request.getParameter(name));
		}
		System.out.println("----------------------------");

		Map<String, Object> param = new HashMap<>();
		param.put("id", request.getParameter("id")); 
		param.put("title", request.getParameter("title")); 
		param.put("description", request.getParameter("description")); 
		param.put("dueDate", request.getParameter("dueDate")); 

		int result = lservice.update(param);
		System.out.println("update 결과 : "+result);
		Boolean success = false;
		if(result>0) {
			success = true;
		}else {
			success = false;
		}
		param.put("success", success);
		System.out.println("update 리턴 param : "+param);
		return param;
	}


	@RequestMapping(value="TaskAjax",produces="application/json;charset=utf8")
	@ResponseBody
	public Object TaskAjax(HttpServletRequest request)  throws Exception {
		System.out.println("TaskAjax 도착");
		//파라미터 이름 보기
		Enumeration params = request.getParameterNames();
		System.out.println("----------------------------");
		while (params.hasMoreElements()){
			String name = (String)params.nextElement();
			System.out.println(name + " : " +request.getParameter(name));
		}
		System.out.println("----------------------------");


		List<ListDTO> TaskList = lservice.selectList();
		String TaskListArr = new Gson().toJson(TaskList);
		System.out.println("TaskListArr : "+ TaskListArr);


		JsonParser jParser = new JsonParser();
		JsonArray jsonListArray = (JsonArray)jParser.parse(TaskListArr);
		System.out.println("jsonListArray : "+jsonListArray);


		JsonObject returnjson = new JsonObject();
		returnjson.addProperty("onSingleLine", true); //설정값 넣기
		JsonArray lists = new JsonArray(); // 리스트들이 들어갈 곳

		for(int j = 0;j<jsonListArray.size();j++) {
			JsonObject ListObject = (JsonObject) jsonListArray.get(j); //jsonArray의 첫번째 값 꺼내기

			//System.out.print(j+" 번째 Listobject : "+ListObject);	

			String list_Id = ListObject.get("id").getAsString();

			String listTitle = ListObject.get("title").getAsString();

			String defaultStyle = ListObject.get("defaultStyle").getAsString();

			List<CardDTO> selectlist = lservice.selectCard(list_Id);
			String CardArr = new Gson().toJson(selectlist);

			// 문자열 쪼개기! 파싱!
			JsonArray jsonCardArray = (JsonArray)jParser.parse(CardArr);



			// 1. Oracle 12c 이후 버전으로 업그레이드-----------
			// For json path 기능 사용 : join을 json 안에 json 형식으로

			// 2. 작업카드가 한 덩어리 -----------------------

			// 3. 이중for문으로 Json 안에 Json 형태로 새롭게 만들기


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
//				System.out.println("item에 listId : "+ listId);

				boolean done = false;
				if(CardObject.get("done").getAsString().equals("true")){
					done = true;
				}
				item.addProperty("done", done ); 
				//System.out.println("item에 done : "+ done);


				items.add(item); // 각 아이템들을 items배열에 담기
				//System.out.println("items에 item : "+ item);

			}

			list.addProperty("id", list_Id);
//			System.out.println("list에 id : "+ list_Id);

			list.addProperty("title", listTitle);
//			System.out.println("list에 title : "+ listTitle);

			list.addProperty("defaultStyle", defaultStyle); //리스트 색상 일단 고정
//			System.out.println("list에 defaultStyle : " + defaultStyle);

			list.add("items", items);
//			System.out.println("list에 items : "+ items);

			lists.add(list);
//			System.out.println("lists에 list : "+ list);

		}

		returnjson.add("lists", lists);
		System.out.println("returnjson : "+returnjson);

		return returnjson;
	}

}
