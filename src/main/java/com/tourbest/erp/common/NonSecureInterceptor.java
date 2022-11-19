package com.tourbest.erp.common;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.WebUtils;

import com.tourbest.erp.util.WebUtil;

@Component
public class NonSecureInterceptor implements HandlerInterceptor {

	private static final Logger logger = LoggerFactory.getLogger(NonSecureInterceptor.class);

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		
		String param = request.getQueryString();
		String url = request.getRequestURL().toString();
		
		if(param != null && param != ""){
			url = url +"?"+param;
		}
		
		if(!"XMLHttpRequest".equals(request.getHeader("x-requested-with"))) { // ajax
			if(request.isSecure()) {
				response.sendRedirect(url.replaceFirst("https", "http"));
				return false;
			}
		}
		
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