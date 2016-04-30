package com.estsoft.mysite.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.estsoft.mysite.annotation.Auth;
import com.estsoft.mysite.annotation.AuthUser;
import com.estsoft.mysite.service.BoardService;
import com.estsoft.mysite.vo.BoardVO;
import com.estsoft.mysite.vo.UserVO;
@Controller
@RequestMapping("/board")
public class BoardController {
	private static final int COUNTPAGE = 5; // 페이지 당 게시물의 수
	private static final int COUNTLIST = 5; // 페이지 리스트의 수

	@Autowired
	private BoardService boardService;

	@RequestMapping("")
	public ModelAndView list(@RequestParam(value = "page", required = true, defaultValue = "1") int page,
							@RequestParam(value = "kwd", required = true, defaultValue = "") String kwd) {

		List<BoardVO> list = boardService.getList(page);
		System.out.println("board_page:" + page);
		
		ModelAndView mav = new ModelAndView();
		mav.addObject("list", list);

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
		mav.setViewName("/board/list");
		
		return mav;
	}
	
	// ---- keyword 검색 (list와 중복된 부분이 있는데, 귀찮아서 그냥 따로 만들었음..)
	@RequestMapping("/search")
	public ModelAndView search(@RequestParam(value="kwd",required=true,defaultValue="")String kwd,
						@RequestParam(value = "page", required = true, defaultValue = "1")int page){
		
		System.out.println("search_kwd:"+kwd+" page:"+page);
		List<BoardVO> list = boardService.search(kwd, page);		// 검색된 결과 리턴
		
		ModelAndView mav = new ModelAndView();
		mav.addObject("list", list);
		
		// ------
		int left = 1, right = 1;
		int startPage, lastPage;
		int count = boardService.getTotalCount(kwd);
		int maxPage = count/COUNTPAGE;
		
		if(count % COUNTPAGE != 0)
			maxPage++;
		
		if(page <1 || page > maxPage)
			page = 1;
		
		int maxPageGroup = maxPage/COUNTLIST;
		if(maxPage % COUNTLIST != 0)
			maxPageGroup++;
		
		int selectedPageGroup = page/COUNTLIST;
		if(page % COUNTLIST != 0)
			selectedPageGroup++;
		
		if(selectedPageGroup == 1)
			left = 0;
		
		if(selectedPageGroup == maxPageGroup)
			right = 0;
		
		startPage = (selectedPageGroup-1)*COUNTLIST+1;
		lastPage = (selectedPageGroup)*COUNTLIST;
		
		if(lastPage > maxPage)
			lastPage = maxPage;
		
		// ----------
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("left", left);
		map.put("right", right);
		map.put("startPage", startPage);
		map.put("lastPage", lastPage);
		map.put("page", page);
		map.put("total", count);
		
		//		--------------???
		mav.addObject("pageMap", map);
		mav.setViewName("/board/list");
				
		return mav;
	}
	
	// ---- 게시물 보여주기
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
	
	//---- 글쓰기
	@Auth
	@RequestMapping("/addForm")
	public ModelAndView addForm(HttpSession session, @RequestParam(value="user_no",required=true,defaultValue="")Long user_no){	// no값 받고 write.jsp로 값 넘겨줌		
		
		ModelAndView mav = new ModelAndView();		
		mav.addObject("user_no", user_no);
		mav.setViewName("/board/write");
																			
		return mav;
	}
	
	@Auth
	@RequestMapping("/write")
	public String write(@AuthUser UserVO authUser,@ModelAttribute BoardVO vo){
	//public String write(HttpSession session,@ModelAttribute BoardVO vo){												// user_no, title, content 넘겨받음
																									// group_no, order_no, depth도 검토!
		// 로그인 사용자 체크
//		UserVO authUser = (UserVO)session.getAttribute("authUser");
//
//		if(authUser == null){
//			return "redirect:/user/loginform";
//		}
		System.out.println(authUser);
		
		if (vo.getGroup_no() != null) {
			vo.setGroup_no(vo.getGroup_no()); 		// 3
			vo.setOrder_no(vo.getOrder_no() + 1); 	// 2 
			vo.setDepth(vo.getDepth() + 1); 		// 1

			boardService.updateGroupOrder(vo);		// order_no 증가시키는 메소드(insert하기 전에 같은 그룹내의 order_no보다 큰 order_no는 다 +1
		}
		
		boardService.insert(vo);
		return "redirect:/board";
	}
	//----
	
	//---- 답글
	@RequestMapping("/reply")
	public ModelAndView reply(@RequestParam(value="no",required=true,defaultValue="")Long no){			// no 넘겨받음
		
		System.out.println("reply no:"+no);
		
		BoardVO vo = boardService.getReplyInform(no);		
		//  group_no, order_no, depth값 넘어옴
		ModelAndView mav = new ModelAndView();
		mav.addObject("vo", vo);
		mav.setViewName("/board/replyform");
		
		return mav;
	}	
	
	// ---- 수정
	@RequestMapping("/modifyForm")
	public ModelAndView modifyForm(@RequestParam(value="no",required=true,defaultValue="")Long no){
		
		ModelAndView mav = new ModelAndView();
		mav.addObject("no", no);
		mav.setViewName("/board/modify");
		return mav;
	}
	
	@RequestMapping("/modify")
	public String modify(@ModelAttribute BoardVO vo){	// no(게시물 번호), title, content 받음
	
		boardService.modify(vo);
		return "redirect:/board";
	}
	
	// ---- 삭제
	@RequestMapping("/delete")
	public String delete(@RequestParam(value="no",required=true,defaultValue="")int no){		// 게시글 번호
		
		System.out.println("delete_no:"+no);
		int chk = boardService.delete(no);
		
		if(chk<1){
			System.out.println("삭제에 실패하였습니다.");
		}
		
		return "redirect:/board";
	}
	
}
