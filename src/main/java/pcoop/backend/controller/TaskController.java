package pcoop.backend.controller;


import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import pcoop.backend.dto.CalendarDTO;
import pcoop.backend.dto.CardDTO;
import pcoop.backend.dto.ListDTO;
import pcoop.backend.dto.ProjectDTO;
import pcoop.backend.service.CalendarService;
import pcoop.backend.service.ListService;


@Controller
@RequestMapping("/Task/")
public class TaskController {

	@Autowired
	private ListService lservice;
	@Autowired
	private CalendarService Cservice;
	@Autowired
	HttpSession session;
	
	@RequestMapping("/task")
	public String Task(Model model)throws Exception {
		List<CalendarDTO> list = new ArrayList<>();
		ProjectDTO pdto = (ProjectDTO)session.getAttribute("projectInfo");
		int project_seq=pdto.getSeq();
		//int project_seq=0; // 캘린더 임시 시퀀스 : 캘린더 일정 삽입이 프로젝트 시퀀스로 변경될 경우 해당 코드도 변경할 것
		list = Cservice.selectAll(project_seq);
		model.addAttribute("list", list);
		return "Task/task";
	}

	
	@ResponseBody
	@RequestMapping("listIndexUpdateAjax")
	public void listIndexUpdate(HttpServletRequest request) {
		System.out.println("listIndexUpdate 시작");
		
		Enumeration params = request.getParameterNames();
		System.out.println("----------------------------");
		while (params.hasMoreElements()){
			String name = (String)params.nextElement();
			System.out.println(name + " : " +request.getParameter(name));
		}
		System.out.println("----------------------------");



		Map<String, Object> param = new HashMap<>();
		param.put("listId", request.getParameter("listId"));
		param.put("listIndex", request.getParameter("listIndex"));

		int result = lservice.listIndexUpdate(param);
		
		System.out.println("listIndexUpdate 결과 : "+result);


	}

	@ResponseBody
	@RequestMapping("cardIndexUpdateAjax")
	public void cardListIdUpdate(HttpServletRequest request) {
		System.out.println("cardIndexUpdate 시작");
		
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
		param.put("cardIndex", request.getParameter("cardIndex"));

		int result = lservice.cardIndexUpdate(param);
		
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

		}else {
			//리스트 추가하자마자 새로 불러오기
			System.out.println("리스트 스타일 수정 실패!");

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
if(request.getParameter("id").contentEquals("NaN")) {
System.out.println("도움말 리스트 화면에서 삭제");
}else{
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
	}


	
	@ResponseBody
	@RequestMapping("titleChangeAjax")
	public void titleChangeAjax(HttpServletRequest request) {
		System.out.println("titleChangeAjax");
		Enumeration params = request.getParameterNames();
		System.out.println("----------------------------");
		while (params.hasMoreElements()){
			String name = (String)params.nextElement();
			System.out.println(name + " : " +request.getParameter(name));
		}
		System.out.println("----------------------------");

	
		
		Map<String, Object> param = new HashMap<>();
		param.put("id", request.getParameter("listId")); 
		param.put("title", request.getParameter("title") );



		// id 존재 여부 확인 특히 프로젝트가 여럿일때 getId() 값 중복 체크 할것!!!
		int dupleList = lservice.selectListId(param);
		System.out.println("dupleList : "+dupleList);

		if(dupleList == 0) {
		System.out.println("해당 리스트아이디 존재 안함. 리스트 생성??");
//		int result = lservice.insertlist(param);
//		System.out.println("insertlist 결과 : "+result);
		}else {
			// id가 이미 존재하면 update
			System.out.println("리스트 이름 수정 !!");
			int result = lservice.updatelist(param);
			System.out.println("updatelist 결과 : "+result);
		}

		



	}
	
	
	
	@ResponseBody
	@RequestMapping("listAddAjax")
	public Object listAddAjax(HttpServletRequest request) {
		System.out.println("listAddAjax");
		Enumeration params = request.getParameterNames();
		System.out.println("----------------------------");
		while (params.hasMoreElements()){
			String name = (String)params.nextElement();
			System.out.println(name + " : " +request.getParameter(name));
		}
		System.out.println("----------------------------");

		ProjectDTO pdto = (ProjectDTO) session.getAttribute("projectInfo");
	
		
		ListDTO ldto = new ListDTO();
		ldto.setTitle(request.getParameter("title"));
		ldto.setProject_seq(pdto.getSeq());
//		Map<String, Object> param = new HashMap<>();
//		param.put("title", request.getParameter("title") );
//		param.put("project_seq", pdto.getSeq() ); //세션에서 project_seq 가져오기



		System.out.println("리스트 생성 !");
		int result = lservice.insertlist(ldto);
		System.out.println("insertlist 결과 : "+result);
		System.out.println("추가된 리스트아이디 결과 : "+ldto.getId());
		
		//int listSeqCurrval = lservice.listSeqCurrval();
		//System.out.println("listSeqCurrval 결과 : "+listSeqCurrval);

		JsonObject json = new JsonObject();	
		json.addProperty("listSeqCurrval", ldto.getId() );
		return json;

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


		CardDTO cdto = new CardDTO();

		//Map<String, Object> param = new HashMap<>();

		//param.put("listId", request.getParameter("listId")); 
		if(request.getParameter("listId").contentEquals("NaN")) {
			System.out.println("도움말에 카드를 추가해도 저장되지 않음");
			Map<String, Object> failCardMap = new HashMap<>();
			failCardMap.put("success", false);
			return failCardMap;
		}
		cdto.setListId(Integer.parseInt(request.getParameter("listId")));
		cdto.setTitle(request.getParameter("title"));
		cdto.setDescription(request.getParameter("description"));
		cdto.setDueDate(request.getParameter("dueDate"));

		ProjectDTO pdto = (ProjectDTO) session.getAttribute("projectInfo");
		cdto.setProject_seq(pdto.getSeq());
		
		int result = lservice.insert(cdto);
		System.out.println("insert 결과 : "+result);
		Boolean success = false;
		if(result>0) {
			success = true;
		}else {
			success = false;
		}

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
	public JsonObject TaskAjax(HttpServletRequest request)  throws Exception {
		System.out.println("TaskAjax 도착 load");
		//파라미터 이름 보기
		Enumeration params = request.getParameterNames();
		System.out.println("----------------------------");
		while (params.hasMoreElements()){
			String name = (String)params.nextElement();
			System.out.println(name + " : " +request.getParameter(name));
		}
		System.out.println("----------------------------");

		ProjectDTO pdto = (ProjectDTO) session.getAttribute("projectInfo");
		Map<String, Object> param = new HashMap<>();
		param.put("project_seq", pdto.getSeq()); // project_seq 받아오기
		
		JsonObject returnjson = lservice.load(param);
		

		return returnjson;
	}

	
	@Transactional("txManager")
	@ResponseBody
	@RequestMapping("selectCount")
	public JsonObject countdone(Model model) throws Exception {
		
		ProjectDTO pdto = (ProjectDTO) session.getAttribute("projectInfo");
		Map<String, Object> param = new HashMap<>();
		param.put("project_seq", pdto.getSeq() ); //세션에서 project_seq 가져오기
		
		JsonObject json = new JsonObject();	
		
		int allcount =  lservice.selectCount(param);
		int truecount =  lservice.trueCount(param);
		
		System.out.println("프로젝트의 총 Task_card 수: "+allcount);
		System.out.println("완료된 Task_card 수: "+truecount);
		System.out.println("진행률 : "+Math.round(((double) truecount / (double) allcount) * 100)+ "%");
		int count = (int) Math.round(((double) truecount / (double) allcount) * 100);

		String to = Integer.toString(count);

		
		model.addAttribute("to",to);
		json.addProperty("to", to );
		
		return json;
		
		
	
	}
	
}
