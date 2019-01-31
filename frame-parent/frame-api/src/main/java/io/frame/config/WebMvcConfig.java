package io.frame.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import io.frame.interceptor.AuthorizationInterceptor;
import io.frame.resolver.LoginUserHandlerMethodArgumentResolver;

/**
 * MVC配置
 * 
 * @author fury
 *
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
	@Autowired
	private AuthorizationInterceptor authorizationInterceptor;
	@Autowired
	private LoginUserHandlerMethodArgumentResolver loginUserHandlerMethodArgumentResolver;

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/statics/**").addResourceLocations("classpath:/statics/");
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(authorizationInterceptor).addPathPatterns("/api/**");
	}

	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
		argumentResolvers.add(loginUserHandlerMethodArgumentResolver);
	}

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**").allowedOrigins("*").allowedMethods("POST", "GET", "PUT", "OPTIONS", "DELETE")
				.maxAge(5000).allowCredentials(true);
	}
}