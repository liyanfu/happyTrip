
package io.frame.config;

import static com.google.common.collect.Lists.newArrayList;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.annotations.ApiOperation;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
	@Bean
	public Docket createRestApi() {
		return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo()).select()
				// 加了ApiOperation注解的类，才生成接口文档
				.apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
				// 包下的类，才生成接口文档
				.apis(RequestHandlerSelectors.basePackage("io.frame.controller"))//
				.paths(PathSelectors.any()).build().securitySchemes(security());
	}

	private ApiInfo apiInfo() {
		String str = "返回参数说明:msg=提示消息,code=返回状态码[0=成功,530=未登录,其余状态都是错误提示信息],Object=返回的数据对象.登录后,提交传递参数中需要在heard中传递token参数,也可以在参数中传递token";
		return new ApiInfoBuilder().title("frame-api").description("frame-api模块接口文档," + str)
				.termsOfServiceUrl("www.250977428@qq.com").version("1.0").build();
	}

	private List<ApiKey> security() {
		return newArrayList(new ApiKey("token", "token", "header"));
	}

}