package com.estsoft.mysite.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.estsoft.mysite.domain.User;
import com.estsoft.mysite.service.UserService;

public class AuthLoginInterceptor extends HandlerInterceptorAdapter {

	@Autowired
	UserService userService;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		
		User user = new User(email, password);
		
		// login 서비스 호출(로그인 작업)
		User authUser = userService.login(user);
		System.out.println("AuthLoginInterceptor_authUser:"+authUser);
		
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
