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
	public String Calendar(Model model)throws Exception {
		List<CalendarDTO> list = new ArrayList<>();
		list = Cservice.selectAll();
		model.addAttribute("list", list);
		return "calendar/calendar";
	}
	
	@ResponseBody
	@RequestMapping("addEvent")
	public String addEvent(CalendarDTO dto) throws Exception {
		int seq = Cservice.select_seq();
		dto.setSeq(seq);
		String Sseq = Integer.toString(seq);
		int result = Cservice.addEvent(dto);
		JsonObject respObj = new JsonObject();
		respObj.addProperty("seq", Sseq);
		/* respObj.addProperty("result", result); */
		
		return new Gson().toJson(respObj);
	}
	
	@ResponseBody
	@RequestMapping("selectEvent")
	public String selectEvent (String seq)throws Exception{
		CalendarDTO result = Cservice.selectEvent(Integer.parseInt(seq));
		String resp = new Gson().toJson(result);
		return resp;
	}
	
}
