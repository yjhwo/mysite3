package com.estsoft.mysite.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class AuthLogoutInterceptor extends HandlerInterceptorAdapter {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		HttpSession httpSession = request.getSession();
		
		if(httpSession != null){
			httpSession.removeAttribute("authUser");
			httpSession.invalidate();
		}
		
		response.sendRedirect(request.getContextPath()+"/main");
		
		// return true로 하면 처리를 하다가 defaultServlet까지 처리가 되버릴 수 있다.
		// main이 나와야 하는데 다른 곳까지 ... 또는 error가 날 수도 있는데,
		// response 응답이 나왔는데 또 응답을 하니까 error 날 수도 있다.
		return false;
		//return false의 의미: 여기서 끝낸다. 다시 controller로 안 돌린다!
	}

}
