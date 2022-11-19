package com.tourbest.erp.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {

	@Override
	public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
		configurer.enable();
	}
		

	/* 로그인 인증 Interceptor 설정 */
	@Autowired
	private LoginInterceptor loginInterceptor;

	@Autowired
	private MemberCheckInterceptor memberCheckInterceptor;

	@Autowired
	private SecureInterceptor secureInterceptor;

	@Autowired
	private NonSecureInterceptor nonSecureInterceptor;

	@Override
	public void addInterceptors(InterceptorRegistry registry) {

		/* 회원 세션 체크 */
		registry.addInterceptor(loginInterceptor)
				.addPathPatterns("/**")  // 모든페이지는 로그인 후 접속 가능함
				.excludePathPatterns("/login"
									,"/intranet/issue/ckeditor/image/upload.do"   // 예외 :: ckeditor  image upload
									,"/intranet/issue/write/add"   // 예외 :: 경기등록
									,"/login/**"
									, "/join/**"
									, "/etc/**"
									, "/error/**"
									, "/hayan_mobile_framework1.0/**"
									, "/hayan_mobile_framework1.0");    // 예외 페이지 주소
		

		/*// SSL 시작
		registry.addInterceptor(secureInterceptor)
				.addPathPatterns("/login/**"					 로그인 
							   , "/join/**"						 회원가입 
							   , "/booking/**"					 예약 
							   , "/mypage/**" 					 마이페이지 
							   , "/cs/partner/add"				 사업제휴  );

		// NON SSL 시작
		registry.addInterceptor(nonSecureInterceptor)
				.addPathPatterns("/welcome"						 메인 
							   , "/main/**"						 서브메인 
							   , "/area/**"						 지역메인 
							   , "/pdtList/**"					 상품리스트 
							   , "/evtList/**"					 행사리스트 
							   , "/evtDetail"					 행사상세 
							   , "/promotion/**"				 기획전 
							   , "/cs/airmeet"					 공항미팅장소안내  );*/
	}

}