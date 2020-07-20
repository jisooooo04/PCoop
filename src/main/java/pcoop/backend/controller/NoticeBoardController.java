package pcoop.backend.controller;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;


import com.google.gson.Gson;

import pcoop.backend.dto.BoardDTO;
import pcoop.backend.dto.MemberDTO;
import pcoop.backend.service.BoardService;



@Controller
@RequestMapping("/board/")
public class NoticeBoardController {

	@Autowired
	private BoardService bservice;

	@Autowired
	private HttpSession session;


	@RequestMapping("test")
	public String test() {
		return "board/test";
	}


	@RequestMapping("toBoard")
	public String toBoard(HttpServletRequest request) throws Exception {
		return "board/boardListView";
	}


	@RequestMapping("list")
	public String boardListAjax(HttpServletRequest request , Model model,String cpage,BoardDTO dto)  throws Exception {
		//		int cpage = Integer.parseInt(request.getParameter("cpage"));
		
		
		
		
		
		
		
		int currentPage = 1;

		System.out.println("무슨값"+cpage);

		try { currentPage = Integer.parseInt(cpage); }
		catch(Exception e) {}


		//게시판 페이지 네비
		String pageNavi = bservice.getPageNavi(currentPage);

		model.addAttribute("pageNavi", pageNavi);
		
		List<BoardDTO> list = bservice.selectByPageNo(currentPage);
		String id = "master@naver.com";
		
		
		model.addAttribute("list", list);
		model.addAttribute("id", id);
		
		
		
	
		

		

		return "board/boardListView";
	}


	@RequestMapping("toBoardContents")
	public String toBoardContents(BoardDTO dto, Model model) throws Exception {
		
		
		
		
		System.out.println("접속성공");
		
		
		model.addAttribute("read",bservice.select(dto.getSeq()));
		System.out.println(bservice.select(dto.getSeq()).getWriter());
		
		

		return "board/boardContentsView";
	}
	@RequestMapping("toBoardWrite")
	public String toBoardWrite() throws Exception {
		return "board/boardWriteView";
	}


	@RequestMapping("boardWrite")
	public String boardWrite(HttpServletRequest request) throws Exception {
		//관리자아이디
		String id = "master@naver.com";
		System.out.println("게시판에 글 쓰는 id : "+id);
		
		String writer = "master@naver.com";
		String title = request.getParameter("title");
		String contents = request.getParameter("contents");
		String category = request.getParameter("category");

		BoardDTO bdto = new BoardDTO(0,writer,Timestamp.valueOf(LocalDateTime.now()),0,title,category,id,contents);
		bservice.boardWrite(bdto);


		return "redirect:/board/list";
	}
	@RequestMapping("delete")
	public String delete(BoardDTO dto) throws Exception {


//댓글달기
//검색기능
		
		int result = bservice.delete(dto.getSeq());
		System.out.println(dto.getSeq());
		System.out.println(result);

		return "redirect:/board/list";
	}
	@RequestMapping("updatefomr")
	public String updatefomr(BoardDTO dto, Model model) throws Exception {
		
		model.addAttribute("read",bservice.select(dto.getSeq()));


		return "/board/boardModifyView";
	}

	@RequestMapping("update")
	public String update(HttpServletRequest request,BoardDTO dto) {

//엡데이트 

		Map<String, String> param = new HashMap<>();

		System.out.println(request.getParameter("title"));
		System.out.println(request.getParameter("category"));
		System.out.println(request.getParameter("contents"));
		

		System.out.println("널값?"+dto.getSeq());
		
		String seq = Integer.toString(dto.getSeq());
		

		
		param.put("category", request.getParameter("category"));
		param.put("title", request.getParameter("title"));
		param.put("contents",request.getParameter("contents"));
		param.put("seq",seq);
		System.out.println("왜안되냐"+param);



		int result = bservice.update(param);
		System.out.println(param);


		return "redirect:/board/list";
	}
	@RequestMapping("search")
	public String search(HttpServletRequest request,Model model) throws Exception {
		
		
		
		
		
		Map<String, String> param = new HashMap<>();
		
		param.put("title", request.getParameter("title"));
		
		bservice.search(param);
        List<BoardDTO> list =bservice.search(param);
		
		
		
		model.addAttribute("list", list);


		return "board/boardListView";
	}





}
