package pcoop.backend.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import pcoop.backend.dto.CalendarDTO;
import pcoop.backend.service.CalendarService;

@Controller
public class CalendarController {
	
	@Autowired
	private CalendarService Cservice;
	
	@RequestMapping("calendar")
	public String Calendar(Model model,int project_seq)throws Exception {//list출력
		List<CalendarDTO> list = new ArrayList<>();
		list = Cservice.selectAll(project_seq);
		model.addAttribute("list", list);
		return "calendar/calendar";
	}
	
	@ResponseBody
	@RequestMapping("addEvent")
	public String addEvent(CalendarDTO dto) throws Exception {//캘린더 insert
		int seq = Cservice.select_seq();//fullcalendar와 내 db에 동시에 저장하기 위해
		dto.setSeq(seq);
		String Sseq = Integer.toString(seq);
		int result = Cservice.addEvent(dto);
		JsonObject respObj = new JsonObject();
		respObj.addProperty("seq", Sseq);
		/* respObj.addProperty("result", result); */
		
		return new Gson().toJson(respObj);
	}
	
	@ResponseBody
	@RequestMapping(value="selectEvent",produces="application/gson;charset=utf8")
	public String selectEvent (String seq)throws Exception{//일정 조회
		CalendarDTO result = Cservice.selectEvent(Integer.parseInt(seq));
		String resp = new Gson().toJson(result);
		return resp;
	}
	
	@ResponseBody
	@RequestMapping(value="EditEvent",produces="application/gson;charset-utf8")
	public String editEvent(CalendarDTO dto)throws Exception{//일정 수정
		System.out.println(dto.getSeq()+":"+dto.getTitle()+":"+dto.getContents()+":"+dto.getWriter()+":"+dto.getStart_date()+":"+dto.getEnd_date()+":"+dto.getColor());
		int result = Cservice.editEvent(dto);
		return "";
	}
	
	@ResponseBody
	@RequestMapping(value="DeleteEvent",produces="application/gson;charset-utf8")
	public String deleteEvent(int seq)throws Exception{//일정 삭제 
		System.out.println(seq);
		int result = Cservice.deleteEvent(seq);
		return "";
	}
}
