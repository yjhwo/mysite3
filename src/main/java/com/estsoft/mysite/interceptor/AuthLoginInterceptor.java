package com.estsoft.mysite.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.estsoft.mysite.service.UserService;
import com.estsoft.mysite.vo.UserVO;

public class AuthLoginInterceptor extends HandlerInterceptorAdapter {

	@Autowired
	UserService userService;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		
		UserVO userVo = new UserVO(email, password);
		
		// login 서비스 호출(로그인 작업)
		UserVO authUser = userService.login(userVo);
		
		if(authUser == null){
			response.sendRedirect(request.getContextPath()+"/user/loginform");
			return false;
		}
		
		// 로그인 처리
		HttpSession session = request.getSession(true);		// 없으면 새로 만들어라
		session.setAttribute("authUser", authUser);
		response.sendRedirect(request.getContextPath()+"/main");

		/*
		// ----- autowired가 안 된다고 생각해서 짠 코드 -----
		
		// Root Application Context(IoC Container 가져오기)
		ApplicationContext applicationContext = WebApplicationContextUtils.getWebApplicationContext(request.getServletContext());
		
		// Bean
		UserService userService = applicationContext.getBean(UserService.class);
		
		*/		
		
		// 처리하고 끝내기 위해 false;
		return false;
	}

}
