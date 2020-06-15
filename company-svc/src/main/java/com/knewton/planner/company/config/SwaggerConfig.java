package com.knewton.planner.company.config;

import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Bean
    public Docket api() {
    	Parameter authHeader = new ParameterBuilder()
    			  .parameterType("header")
    			  .name("Authorization")
    			  .modelRef(new ModelRef("string"))
    			  .build();
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.knewton.planner.company.controller"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiEndPointsInfo())
                .useDefaultResponseMessages(false)
                .globalOperationParameters(Collections.singletonList(authHeader));
    }

    private ApiInfo apiEndPointsInfo() {
        return new ApiInfoBuilder().title("Company REST API")
                .description("Knewton Planner Company REST API")
                .contact(new Contact("Aamir Raza", "https://github.com/xxx", "aamir.raza@gmail.com"))
                .license("The MIT License")
                .licenseUrl("https://opensource.org/licenses/MIT")
                .version("V2")
                .build();
    }

}
