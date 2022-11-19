package com.tourbest.erp.common;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Component
public class MemberCheckInterceptor implements HandlerInterceptor {

	private static final Logger logger = LoggerFactory.getLogger(MemberCheckInterceptor.class);

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		
		/*String url = request.getRequestURL().toString();
		
		LoginVO loginVO = (LoginVO) UserDetailsHelper.getAuthenticatedUser();
		if ("N".equals(loginVO.getMberYn())) {
			if ("INCLUDE".equals(request.getDispatcherType().toString()) ||
				"XMLHttpRequest".equals(request.getHeader("x-requested-with"))) {
				return true;
			}
			
			if(!url.contains("/mypage") && !url.contains("/pay")) {  
				HttpSession session = request.getSession();
				session.invalidate();
			}
		}*/
		
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub }
	}
}