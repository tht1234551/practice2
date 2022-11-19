package com.tourbest.erp.common;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Component
public class LoginInterceptor implements HandlerInterceptor {

	private static final Logger logger = LoggerFactory.getLogger(LoginInterceptor.class);

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

		/*String url = request.getRequestURL().toString();
		String uri = request.getRequestURI();
		logger.info("URL:"+url);
		//logger.info("URI:"+uri);
		//logger.info("isAjax:" + request.getHeader("isAJAX"));  // W2UI Ajax 요청인지 확인함

		LoginVO loginVO = (LoginVO) UserDetailsHelper.getAuthenticatedUser();
		if (loginVO.getUserId() == 0 || "N".equals(loginVO.getMberYn())) {
			if("true".equals(request.getHeader("isAJAX"))) {
				response.sendRedirect("/login?returnUrl=AJAXLOGIN");   // W2UI 인경우
			}else
				response.sendRedirect("/login?returnUrl=" + request.getRequestURI());
			return false;
		}
*/		return true;
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