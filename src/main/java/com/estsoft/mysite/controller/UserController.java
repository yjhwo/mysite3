package com.estsoft.mysite.controller;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.estsoft.mysite.domain.User;
import com.estsoft.mysite.service.UserService;
@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;


	@RequestMapping("/joinform")
	public String joinform() {
		return "/user/joinform";
	}

	@RequestMapping(value = "/join", method = RequestMethod.POST)
	public String join(@ModelAttribute @Valid User user, BindingResult result, Model model) {

		if (result.hasErrors()) {
			model.addAllAttributes(result.getModel());		// Map return
			return "/user/joinform";						// forwarding
		}
		userService.join(user);
		return "redirect:/user/joinsuccess";
	}

	@RequestMapping("/joinsuccess")
	public String joinSuccess() { 							// forwarding
		return "/user/joinsuccess";
	}

	@RequestMapping("/loginform")
	public String loginForm() {
		return "/user/loginform";
	}

	@RequestMapping("/checkemail")
	@ResponseBody
	public Map<String, Object> checkEmail(@RequestParam(value = "email", required = true, defaultValue = "") String email) {
		// |-> Object로 써도 상관 없음. email이라는 parameter값을 받음

		// mysite의 CheckEmailAction과 비교해서 봐보기!
		User user = userService.getUser(email);

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("result", "success");
		map.put("data", user == null);

		return map;
	}

}
