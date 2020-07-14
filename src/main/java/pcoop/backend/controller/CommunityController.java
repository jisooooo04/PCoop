package pcoop.backend.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import pcoop.backend.dto.CommunityDTO;
import pcoop.backend.service.CommunityService;

@Controller
public class CommunityController {
	
	@Autowired
	private HttpSession session;
	
	@Autowired
	private CommunityService cmservice;
	
	
	@RequestMapping("community")
	public String Community(Model model) {
		
		//게시판 들어오면 글 목록 뿌려주기
		//디비에서 목록 List select 
		//글번호, 제목, 작성자, 작성날짜, 카테고리, 조회수, (좋아요)
		//페이지번호, 글쓰기, 제목조회, 작성자조회, 카테고리 조회
		
		List<CommunityDTO> list = cmservice.selectList();
		model.addAttribute("list", list);
		
		return "community/community";
	}
	
	@RequestMapping("community_write")
	public String CommunityWrite(Model model) {
		
		return "community/community_write";
	}
	
	
	
}
