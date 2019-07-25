/**
 * ClassName: SpringRootConfig.java
 * <p>
 * Version Information:
 * <p>
 * Date: 2018/06/01
 * <p>
 * COPYRIGHT (C) 2018 RETair.com. All Rights Reserved.
 */
package com.leaihealth.cloud.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * The Spring root configuration.
 *
 * @author Joseph S. Kuo
 * @since 0.0.1, 2018/06/01
 */
@Configuration
@Import({
        SystemConfig.class,
        IdemDatabaseConfig.class,
        AsyncConfig.class,
        ScheduleConfig.class,
        EhcacheConfig.class
})
@ComponentScan({
        "com.leaihealth.cloud.service", "com.leaihealth.cloud.security"
})
public class SpringRootConfig {
    // do nothing

  /*  @Bean
    public EhCacheCacheManager  cacheManager() {
        return new EhCacheCacheManager();
    }

    @Bean
    public EhCacheManagerFactoryBean ehCacheCacheManager() {
        EhCacheManagerFactoryBean cmfb = new EhCacheManagerFactoryBean();
        cmfb.setConfigLocation(new ClassPathResource("ehcache.xml"));
        cmfb.setShared(true);
        return cmfb;
    }*/
}
