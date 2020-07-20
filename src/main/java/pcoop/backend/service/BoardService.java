package pcoop.backend.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import pcoop.backend.dao.BoardDAO;
import pcoop.backend.dao.MemberDAO;
import pcoop.backend.dto.BoardDTO;
import pcoop.backend.statics.Configuration;



@Service
public class BoardService {
	@Autowired
	private MemberDAO mdao;
	@Autowired
	private BoardDAO bdao;
	

	
//	@Transactional("txManager")
//	public void testWrite() throws Exception{
//		bdao.insert(new BoardDTO(0,"A테스트 글쓰기",Timestamp.valueOf(LocalDateTime.now()),0,"C테스트 제목","카테고리","CCC","CCC"));
//		bdao.insert(new BoardDTO(0,"B테스트 글쓰기",Timestamp.valueOf(LocalDateTime.now()),0,"D테스트 제목","카테고리",null,"DDD"));
//	}
	
	
	public int boardWrite(BoardDTO bdto) throws Exception {
		return bdao.insert(bdto);
	}
	public BoardDTO select(int seq) throws Exception{
		
		return bdao.select(seq);
		
		
	}public int delete(int seq) throws Exception{
		return bdao.delete(seq);
	}
	public List<BoardDTO> search(Map<String,String> param) throws Exception{
		System.out.println("검색성공");
		return bdao.search(param);
	}
	public int update(Map<String,String> param){
		System.out.println("서비스성공");
		return bdao.update(param);
	}
	
	
	public List<BoardDTO> selectByPageNo(int cpage) throws Exception{
		
		int start = cpage * Configuration.RECOURD_COUNT_PER_PAGE - (Configuration.RECOURD_COUNT_PER_PAGE-1);
		int end = start + (Configuration.RECOURD_COUNT_PER_PAGE-1);
		
		
		System.out.println(start+"에서"+end+"까지 출력하기");

		Map<String, Integer> param = new HashMap<>();
		param.put("start", start);
		param.put("end", end);
		
		List<BoardDTO> list = bdao.selectByPageNo(param);
		return list;
	}
	
	public List<BoardDTO> selectByView(int cpage) throws Exception{
		
		int start = cpage * Configuration.RECOURD_COUNT_PER_PAGE - (Configuration.RECOURD_COUNT_PER_PAGE-1);
		int end = start + (Configuration.RECOURD_COUNT_PER_PAGE-1);
		System.out.println(start+"에서"+end+"까지 출력하기");

		Map<String, Integer> param = new HashMap<>();
		param.put("start", start);
		param.put("end", end);
		
		List<BoardDTO> list = bdao.selectByPageNo(param);
		return list;
	}
	
	

	
	
	
	public String getPageNavi(int currentPage) throws Exception{
		int recordTotalCount = bdao.getArticleCount(); 
		int pageTotalCount = 0;
		if(recordTotalCount % Configuration.RECOURD_COUNT_PER_PAGE > 0) {
			pageTotalCount = recordTotalCount / Configuration.RECOURD_COUNT_PER_PAGE + 1;	
		}else {
			pageTotalCount = recordTotalCount / Configuration.RECOURD_COUNT_PER_PAGE;
		}
		if(currentPage < 1) {
			currentPage = 1;
		}else if(currentPage > pageTotalCount) {
			currentPage = pageTotalCount;
		}
		int startNavi = (currentPage - 1) / Configuration.NAVI_COUNT_PER_PAGE * Configuration.NAVI_COUNT_PER_PAGE + 1;
		int endNavi = startNavi + Configuration.NAVI_COUNT_PER_PAGE - 1;
		if(endNavi > pageTotalCount) {
			endNavi = pageTotalCount;
		}
		boolean needPrev = true;
		boolean needNext = true;
		if(startNavi == 1) {needPrev = false;}
		if(endNavi == pageTotalCount) {needNext = false;}

		StringBuilder sb = new StringBuilder();
		if(needPrev) {sb.append("<li class='page-item'><a class='page-link' href='list?cpage="+(startNavi-1)+"'><i class='fas fa-chevron-left'></i></a></li>");}
		for(int i = startNavi;i <= endNavi;i++) {
			sb.append("<li class='page-item'><a class='page-link' href='list?cpage="+i+"'>" + i + "</a></li>");
		}
		if(needNext) {sb.append("<li class='page-item'><a class='page-link' href='list?cpage="+(endNavi+1)+"'><i class='fas fa-chevron-right'></i></a></li>");}
		return sb.toString();
	}
}
