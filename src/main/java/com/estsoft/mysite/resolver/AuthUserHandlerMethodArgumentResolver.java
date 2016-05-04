package com.estsoft.mysite.resolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebArgumentResolver;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.estsoft.mysite.annotation.AuthUser;
import com.estsoft.mysite.vo.UserVO;

public class AuthUserHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {


	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		// check parameter Annotation( @AuthUser )
		AuthUser authUser = parameter.getParameterAnnotation(AuthUser.class);
		
		if(authUser == null){
			return false;
		}
		
		// check parameter type (UserVO)
		if(parameter.getParameterType().equals(UserVO.class) == false){
			// parameter.getParameterType() 타입이 class로 나옴 -> instanceOf X!
			return false;
		}
		
		return true;
	}
	
	@Override
	public Object resolveArgument( MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest,
			WebDataBinderFactory binderFactory) throws Exception {
		
		if( this.supportsParameter(parameter)==false ){
			return WebArgumentResolver.UNRESOLVED;
		}
		
		// Session에서 authUser 가져오기
		HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
		HttpSession session = request.getSession();
		
		if( session == null ){
			return WebArgumentResolver.UNRESOLVED;
		}
		
		return session.getAttribute("authUser");			// UserVO 객체가 return
	}




}
