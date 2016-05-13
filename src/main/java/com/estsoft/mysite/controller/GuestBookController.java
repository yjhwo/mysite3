package com.estsoft.mysite.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.estsoft.mysite.domain.GuestBook;
import com.estsoft.mysite.service.GuestBookService;

@Controller
@RequestMapping("/guestbook")
public class GuestBookController {

	@Autowired
	private GuestBookService guestbookService;

	@RequestMapping("/list")
	public String list(Model model) {

		List<GuestBook> list = guestbookService.getList();
		model.addAttribute("list", list);

		return "/guestbook/list";
	}

	
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String add(@ModelAttribute GuestBook guestbook) {
		guestbookService.add(guestbook);
		return "redirect:/guestbook/list";
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
		Boolean ret = guestbookService.delete(no, password);

		if (ret) { // 성공
			mav.setViewName("redirect:/guestbook/list");
		} else { 	// 실패
			mav.addObject("no", no);
			mav.setViewName("/guestbook/deletefail");
		}

		return mav;
	}

}
