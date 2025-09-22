package com.example.spring09.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.OpenAPI;
/*
 * 	1. pom.xml 에 아래의 dependency 추가 
 * 		<!-- swagger ui 를 사용하기 위한 dependency (spring boot 3.0 이상 버전에서)-->
		<dependency>
		    <groupId>org.springdoc</groupId>
		    <artifactId>springdoc-openapi-starter-webmvc-ui</artifactId>
		    <version>2.6.0</version>
		</dependency>
		
	2. SwaggerConfing 설정 파일 추가 
	
	3. application.properties 파일에 아래의 설정 추가
	
		springdoc.api-docs.path=/api-docs
		springdoc.swagger-ui.path=/swagger-ui.html
		
	4. 웹브라우저의 주소창에 위에서 설정한 경로를 요청해서 swagger ui 를 사용할 수 있다.
		/swagger-ui/index.html
 */
@Configuration
public class SwaggerConfig {
	
	@Bean
	public OpenAPI customOpenAPI() {
		Info info = new Info()
				.title("API 문서입니다")
				.version("v1")
				.description("어쩌구... 저쩌구...");
		
		return new OpenAPI().info(info);
	}
}
