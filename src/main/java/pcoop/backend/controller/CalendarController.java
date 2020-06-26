package pcoop.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import pcoop.backend.dto.CalendarDTO;
import pcoop.backend.service.CalendarService;

@Controller
public class CalendarController {
	
	@Autowired
	private CalendarService Cservice;
	
	@RequestMapping("calendar")
	public String Calendar(Model model) {
		return "calendar/calendar";
	}
	
	@ResponseBody
	@RequestMapping("addEvent")
	public String addEvent(CalendarDTO dto) throws Exception {
		int result = Cservice.addEvent(dto);
		return "success";
	}
	
}
