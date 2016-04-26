package com.estsoft.mysite.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.estsoft.mysite.service.UserService;
import com.estsoft.mysite.vo.UserVO;

@Controller
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	/*
	 * redirect가 안 붙으면 view name이다..?
	 */
	@RequestMapping("/joinform")
	public String joinform(){
		return "user/joinform";
	}
	
	@RequestMapping(value="/join", method=RequestMethod.POST)
	public String join(@ModelAttribute UserVO vo){
		userService.join(vo);
		return "redirect:/user/joinsuccess";
	}
	
	@RequestMapping("/joinsuccess")
	public String joinSuccess(){		// forwarding
		return "/user/joinsuccess";
	}
	
	@RequestMapping("/loginform")
	public String loginForm(){		
		return "/user/loginform";
	}

	/*
	 *	HttpSession기술이 코드 안에 들어와있는 부분이 별로 --> 나중에 수정할 예정
	 */
	
	@RequestMapping("/login")
	public String login(@ModelAttribute UserVO vo,HttpSession session){
		// 로그인 처리
		UserVO userVo = userService.login(vo);
		
		if(userVo == null){
			// 로그인 실패
			return "/user/loginform_fail";
		}
		// 로그인 성공
		session.setAttribute("authUser", userVo);
		return "redirect:/main";
	}
	
	@RequestMapping("/logout")
	public String logout(HttpSession session){		// 로그아웃 처리
		// session이 절대 null이 될 경우는 없다. --> dispatcher-servlet이 처리해서 보내줌
		
		// 인증유무 체크
		UserVO authUser = (UserVO)session.getAttribute("authUser");
		if(authUser != null){
			session.removeAttribute("authUser");
			session.invalidate();
		}
		
		return "redirect:/main";
	}
	
	@RequestMapping("/checkemail")
	@ResponseBody
	public Map<String,Object> checkEmail(@RequestParam(value="email",required=true,defaultValue="")String email){
		// |-> Object로 써도 상관 없음.						email이라는 parameter값을 받음
		
		// mysite의 CheckEmailAction과 비교해서 봐보기!
		UserVO vo = userService.getUser(email);
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("result", "success");
		map.put("data", vo==null);
		
		return map;
	}
	
	@RequestMapping("/hello")
	@ResponseBody
	public String hello(){
		
		return "hello:안녕";
	}
}
