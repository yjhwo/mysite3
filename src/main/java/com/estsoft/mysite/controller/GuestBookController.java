package com.estsoft.mysite.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.estsoft.mysite.service.GuestBookService;
import com.estsoft.mysite.vo.GuestBookVO;

@Controller
@RequestMapping("/guestbook")
public class GuestBookController {

	@Autowired
	private GuestBookService guestbookService;

/*	@RequestMapping("/list")
	public String list(Model model) {

		List<GuestBookVO> list = guestbookService.getList();
		model.addAttribute("list", list);

		return "/guestbook/list";
	}*/

	@RequestMapping("/list")
	public String list2() {
		return "/guestbook/ajax-main";
	}
	
	// ----------- ajax로 고치기
	
	@RequestMapping("/ajax-list")
	@ResponseBody
	public Map<String, Object> ajax_list(@RequestParam(value = "p", required = true, defaultValue = "1") int page, Model model)
			throws Exception { // default값 수정

		List<GuestBookVO> list = guestbookService.getList(page);
		model.addAttribute("list", list);
		
		Map<String, Object> map = new HashMap<String, Object>();

		map.put("result", "success");
		map.put("data", list);

		return map;
	}

	@RequestMapping("/ajax-insert")
	@ResponseBody
	public Map<String, Object> ajax_insert(@ModelAttribute GuestBookVO vo){		// name,passwd,message 받음
																				// 값을 받으면 insert해주고 list를 다시 뿌려줌
		
		Long no = guestbookService.add(vo);
		GuestBookVO guestbookvo = guestbookService.get(no);						// 이렇게 해야 reg_date이 들어있음
		
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("result", "success");
		map.put("data", guestbookvo);											// vo값을 가지고 화면에 맨 위에 추가해주기 위해서
		
		return map;
	}
	
	@RequestMapping("/ajax-delete")
	@ResponseBody
	public Map<String, Object> ajax_delete(@RequestParam(value = "no", required = true, defaultValue = "-1") int no,
								@RequestParam(value = "passwd", required = true, defaultValue = "-1") String passwd){		// no, passwd값 받음
		
		int cnt = guestbookService.delete(no, passwd);
		System.out.println("delete_no:"+no+"passwd:"+passwd);
		
		Map<String, Object> map = new HashMap<String,Object>();
		
		if(cnt < 1)	// 실패한 경우
			map.put("result", "fail");
		else
			map.put("result", "success");
		
		System.out.println("ajax-delete의 결과:"+cnt);
		return map;
	}
	
	
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String add(@ModelAttribute GuestBookVO vo) {
		guestbookService.add(vo);

		// return "/guestbook/list";
		return "/guestbook/ajax-main";
	}

	@RequestMapping("/deleteform")
	public ModelAndView deleteform(@RequestParam(value = "no", required = true, defaultValue = "") int no) {

		ModelAndView mav = new ModelAndView();
		mav.addObject("no", no);
		mav.setViewName("/guestbook/deleteform");

		return mav;
	}

	@RequestMapping("/delete")
	public ModelAndView delete(@RequestParam(value = "no", required = true, defaultValue = "") int no,
			@RequestParam(value = "password", required = true, defaultValue = "") String password) {
		ModelAndView mav = new ModelAndView();

		// 성공하면 list, 실패하면 deletefail
		int chk = guestbookService.delete(no, password);

		if (chk > 0) { // 성공
			// mav.setViewName("redirect:/guestbook/list");
			mav.setViewName("redirect:/guestbook/ajax-main");
		} else { // 실패
			mav.addObject("no", no);
			mav.setViewName("/guestbook/deletefail");
		}

		return mav;
	}

}
