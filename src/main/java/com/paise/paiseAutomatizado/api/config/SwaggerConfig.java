package com.paise.paiseAutomatizado.api.config;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import com.paise.paiseAutomatizado.api.security.utils.JwtTokenUtil;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@Profile("dev")
@EnableSwagger2
public class SwaggerConfig {

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private UserDetailsService userDetailsService;

	@Bean
	public Docket api() {
		
		String swaggerToken;
		try {
			UserDetails userDetails = this.userDetailsService.loadUserByUsername("galotinhodelta@gmail.com");
			swaggerToken = this.jwtTokenUtil.obterToken(userDetails);
		} catch (Exception e) {
			swaggerToken = "";
		}
		
		return new Docket(DocumentationType.SWAGGER_2).select()
				.apis(RequestHandlerSelectors.basePackage("com.paise.paiseAutomatizado.api.controllers"))
				.paths(PathSelectors.any()).build()
				.apiInfo(apiInfo())
				.globalOperationParameters(
		            		Collections.singletonList(
		                    new ParameterBuilder()
		                            .name("Authorization")
		                            .modelRef(new ModelRef("string"))
		                            .parameterType("header")
		                            .required(true)
		                            .hidden(true)
		                            .defaultValue("Bearer " + swaggerToken)
		                            .build()
		                    )
		            );
		            
	}

	private ApiInfo apiInfo() {
		return new ApiInfoBuilder().title("PAISE API")
				.description("Documentação da API de acesso aos endpoints do PAISE.").version("1.0")
				.build();
	}

}