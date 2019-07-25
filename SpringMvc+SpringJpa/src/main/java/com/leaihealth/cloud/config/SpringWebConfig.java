/**
 * ClassName: SpringWebConfig.java
 * <p>
 * Version Information:
 * <p>
 * Date: 2018/06/01
 * <p>
 * COPYRIGHT (C) 2018 RETair.com. All Rights Reserved.
 */
package com.leaihealth.cloud.config;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;
import com.leaihealth.cloud.security.LoginStatusFilter;
import com.leaihealth.cloud.util.RestControllerExceptionHandler;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

/**
 * Spring Web MVC related configuration
 *
 * @author Joseph S. Kuo
 * @since 0.0.1, 2018/06/01
 */
@Configuration
@EnableWebMvc
@Import({SecurityConfig.class, SwaggerConfig.class})
@ComponentScan(value = {"com.leaihealth.cloud.controller"}, basePackageClasses = {RestControllerExceptionHandler.class})
public class SpringWebConfig implements WebMvcConfigurer {
    @Autowired
    private LoginStatusFilter loginStatusFilter;

    /**
     * {@inheritDoc}
     */
    @Override
    public void addInterceptors(final InterceptorRegistry registry) {

        List<String> urlWhiteList = new ArrayList<>();
        urlWhiteList.add("/console/login");
        urlWhiteList.add("/v2/api-docs");
        urlWhiteList.add("/upload/downUpload");
        registry.addInterceptor(this.loginStatusFilter).excludePathPatterns(urlWhiteList);
    }

    /**
     * jackson will not serialize 1. object attributes marked with @Transient 2.
     * lazily loaded objects
     */
    @Override
    public void configureMessageConverters(final List<HttpMessageConverter<?>> converters) {
        converters.add(SpringWebConfig.jacksonMessageConverter());
    }

    /**
     * enable SpringMVC to resolve "Pageable" method argument
     */
    @Override
    public void addArgumentResolvers(final List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(new PageableHandlerMethodArgumentResolver());
    }

    private static MappingJackson2HttpMessageConverter jacksonMessageConverter() {
        final MappingJackson2HttpMessageConverter messageConverter = new MappingJackson2HttpMessageConverter();
        final ObjectMapper mapper = new ObjectMapper();
        mapper.getSerializerProvider().setNullValueSerializer(new JsonSerializer<Object>() {
            @Override
            public void serialize(Object arg0, JsonGenerator arg1, SerializerProvider arg2) throws IOException {
                arg1.writeString("");
            }
        });
        //设置日期格式
        SimpleDateFormat smt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        mapper.setDateFormat(smt);
        // Registering Hibernate5Module to support lazy objects
        mapper.registerModule(new Hibernate5Module().disable(Hibernate5Module.Feature.USE_TRANSIENT_ANNOTATION));
        messageConverter.setObjectMapper(mapper);

        return messageConverter;
    }

    /**
     * Returns a new multipart resolver.
     *
     * @return a new multipart resolver
     */
    @SuppressWarnings("static-method")
    @Bean
    public CommonsMultipartResolver multipartResolver() {
        final CommonsMultipartResolver resolver = new CommonsMultipartResolver();
        resolver.setDefaultEncoding("UTF-8");
        // set as 30 MB
        resolver.setMaxInMemorySize((int) (30 * FileUtils.ONE_MB));
        return resolver;
    }

    /**
     * Disable Spring MVC determines the requested media types from the client
     * for request mapping. {@inheritDoc}
     */
    @Override
    public void configureContentNegotiation(final ContentNegotiationConfigurer configurer) {
        configurer.favorPathExtension(false);
    }

}
