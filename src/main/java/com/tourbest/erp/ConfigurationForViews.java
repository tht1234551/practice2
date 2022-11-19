package com.tourbest.erp;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.multipart.support.MultipartFilter;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.JstlView;
import org.springframework.web.servlet.view.UrlBasedViewResolver;
import org.springframework.web.servlet.view.tiles3.TilesConfigurer;
import org.springframework.web.servlet.view.tiles3.TilesView;
import org.springframework.web.servlet.view.tiles3.TilesViewResolver;

import com.navercorp.lucy.security.xss.servletfilter.XssEscapeServletFilter;
import com.tourbest.erp.common.HttpsCookieFilter;
import com.tourbest.erp.common.XssServletFilter;

/**
 * Tiles configuration.
 *
 * References:
 * http://docs.spring.io/spring/docs/4.0.6.RELEASE/spring-framework-reference/html/view.html#view-tiles-integrate
 *
 * @author Mark Meany
 */
@Configuration
public class ConfigurationForViews {
	
    
	/*
	 * xssFilter
	 *
	*/
	@Bean
	public FilterRegistrationBean xssEscapeServletFilter() {
	    FilterRegistrationBean registrationBean = new FilterRegistrationBean();
	    registrationBean.setFilter(new XssEscapeServletFilter());
	    registrationBean.setOrder(1);
	    registrationBean.addUrlPatterns("/*");
	    return registrationBean;
	}
	
	@Bean
	public FilterRegistrationBean httpsCookieFilter() {
		FilterRegistrationBean registrationBean = new FilterRegistrationBean();
		registrationBean.setFilter(new HttpsCookieFilter());
		registrationBean.setOrder(2);
		registrationBean.addUrlPatterns("/*");
		return registrationBean;
	}

	/**
	 * Initialise Tiles on application startup and identify the location of the
	 * tiles configuration file, tiles.xml.
	 *
	 * @return tiles configurer
	 */
	@Bean
	public TilesConfigurer tilesConfigurer() {
		final TilesConfigurer configurer = new TilesConfigurer();
		configurer.setDefinitions(new String[] { "WEB-INF/tiles/tiles.xml" });
		configurer.setCheckRefresh(true);
		return configurer;
	}

	/**
	 * Introduce a Tiles view resolver, this is a convenience implementation that
	 * extends URLBasedViewResolver.
	 *
	 * @return tiles view resolver
	 */
	@Bean
	public TilesViewResolver tilesViewResolver() {
		final TilesViewResolver resolver = new TilesViewResolver();
		resolver.setViewClass(TilesView.class);
		resolver.setOrder(2);
		return resolver;
	}

	@Bean
	public ViewResolver viewResolver() {
		UrlBasedViewResolver resolver = new UrlBasedViewResolver();
		resolver.setPrefix("/WEB-INF/views/");
		resolver.setSuffix(".jsp");
		resolver.setViewClass(JstlView.class);
		resolver.setOrder(3);
		return resolver;
	}

}
