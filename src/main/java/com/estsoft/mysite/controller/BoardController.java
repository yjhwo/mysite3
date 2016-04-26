package com.estsoft.mysite.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.estsoft.mysite.service.BoardService;
import com.estsoft.mysite.vo.BoardVO;

@Controller
@RequestMapping("/board")
public class BoardController {
	private static final int COUNTPAGE = 5; // 페이지 당 게시물의 수
	private static final int COUNTLIST = 5; // 페이지 리스트의 수

	@Autowired
	private BoardService boardService;

	@RequestMapping("")
	public ModelAndView list(@RequestParam(value = "p", required = true, defaultValue = "1") int page,
							@RequestParam(value = "kwd", required = true, defaultValue = "") String kwd) {

		List<BoardVO> list = boardService.getList(page);
		System.out.println("board_page:" + page);

		ModelAndView mav = new ModelAndView();
		mav.addObject("list", list);
		mav.setViewName("/board/list");

		// ---------------
		int left = 1, right = 1;
		int startPage, lastPage;

		int count = boardService.getTotalCount();

		int maxPage = count / COUNTPAGE;

		if (count % COUNTPAGE != 0)
			maxPage++;

		if (page < 1 || page > maxPage)
			page = 1;

		int maxPageGroup = maxPage / COUNTLIST;
		if (maxPage % COUNTLIST != 0)
			maxPageGroup++;

		int selectedPageGroup = page / COUNTLIST;
		if (page % COUNTLIST != 0)
			selectedPageGroup++;

		if (selectedPageGroup == 1)
			left = 0;

		if (selectedPageGroup == maxPageGroup)
			right = 0;

		startPage = (selectedPageGroup - 1) * COUNTLIST + 1;
		lastPage = (selectedPageGroup) * COUNTLIST;

		if (lastPage > maxPage)
			lastPage = maxPage;

		// ---------------
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("left", left);
		map.put("right", right);
		map.put("startPage", startPage);
		map.put("lastPage", lastPage);
		map.put("page", page);
		map.put("total", count);

		//	--------------???
		mav.addObject("pageMap", map);
		
		return mav;
	}
	
	
	@RequestMapping("/view")
	public ModelAndView view(@RequestParam(value="no",required=true,defaultValue="")Long no,
						@RequestParam(value="user_no",required=true,defaultValue="")Long user_no,
						HttpSession session){
		// no null처리
		BoardVO vo = boardService.get(no);	// 글 자세히 보기 ( title, content ) 가져옴
		boardService.updateCount(no);		// viewCount+1
		
		vo.setNo(no);
		vo.setUser_no(user_no);
		
		ModelAndView mav = new ModelAndView();
		mav.addObject("vo", vo);
		mav.setViewName("/board/view");		// view.jsp로 이동
		
		// 세션에 담는 것..??
		BoardVO boardVO = new BoardVO(no, vo.getTitle(), vo.getContent());
		session.setAttribute("boardVO", boardVO);
		
		return mav;
	}
	
	@RequestMapping("/modifyForm")
	public String modifyForm(@RequestParam(value="no",required=true,defaultValue="")Long no){
		System.out.println("modifyForm_no:"+no);
		return "redirect:/board/modify";
	}
	
/*	@RequestMapping("/modify")
	public String modify(//VO로 받기){
		System.out.println("modify_no:"+no);
		
		return "";
	}*/

}
