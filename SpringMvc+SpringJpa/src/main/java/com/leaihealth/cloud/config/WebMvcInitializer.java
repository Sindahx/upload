/**
 * ClassName: WebMvcInitializer.java
 *
 * Version Information:
 *
 * Date: 2018/06/01
 *
 * COPYRIGHT (C) 2018 RETair.com. All Rights Reserved.
 */
package com.leaihealth.cloud.config;

import javax.servlet.Filter;

import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

/**
 * Spring Web MVC initializer
 *
 * @author Joseph S. Kuo
 * @since 0.0.1, 2018/06/01
 */
public class WebMvcInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {
    /**
     * {@inheritDoc}
     */
    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class<?>[] {
                SpringRootConfig.class
        };
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class<?>[] {
                SpringWebConfig.class
        };
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected String[] getServletMappings() {
        return new String[] {
                "/"
        };
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Filter[] getServletFilters() {
        final CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
        characterEncodingFilter.setEncoding("UTF-8");
        characterEncodingFilter.setForceEncoding(true);

        return new Filter[] {
                characterEncodingFilter
        };
    }
}
