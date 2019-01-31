package io.frame;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.server.ConfigurableWebServerFactory;
import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;

//@EnableCaching // 开启缓存
@SpringBootApplication
@MapperScan(basePackages = { "io.frame.dao" })
public class ApiApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(ApiApplication.class, args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(ApiApplication.class);
	}

	@Bean
	public WebServerFactoryCustomizer<ConfigurableWebServerFactory> webServerFactoryCustomizer() {
		return factory -> {
			ErrorPage error404Page = new ErrorPage(HttpStatus.NOT_FOUND, "/index.html");
			factory.addErrorPages(error404Page);
		};

	}
}
