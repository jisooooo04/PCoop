package pcoop.backend.controller;

import java.util.Locale;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class HomeController {

	@Autowired
	private HttpSession session;
	
	@RequestMapping("/")
	public String home() {
		System.out.println(session.getServletContext().getRealPath("upload"));
		
		return "index";
	}
	
}
