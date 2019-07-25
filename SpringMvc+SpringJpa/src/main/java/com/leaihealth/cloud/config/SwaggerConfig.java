/**
 * ClassName: SwaggerConfig.java
 *
 * Version Information:
 *
 * Date: Mar 23, 2016
 *
 * COPYRIGHT (C) 2016 RETair.com. All Rights Reserved.
 */
package com.leaihealth.cloud.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Swagger configuration.
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {
    /**
     * Returns a new docket for Swagger.
     *
     * @return a new docket for Swagger
     */
    @SuppressWarnings("static-method")
    @Bean
    public Docket customImplementation() {
        return new Docket(DocumentationType.SWAGGER_2);
    }
}
