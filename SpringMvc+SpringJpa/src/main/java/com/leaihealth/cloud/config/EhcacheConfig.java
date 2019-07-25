package com.leaihealth.cloud.config;

import net.sf.ehcache.config.CacheConfiguration;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.cache.interceptor.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * ClassName: com.leaihealth.cloud.config.EhcacheConfig
 * <p>
 * Auth: huangxing
 * <p>
 * Date: 2019/5/20
 * <p>
 * COPYRIGHT (C) 2016 RETair.com. All Rights Reserved.
 */
@Configuration
@EnableCaching
public class EhcacheConfig implements CachingConfigurer {

    public static final String USER_CACHE_INSTANCE = "myCacheName";
    private CacheManager cacheManager;
    private net.sf.ehcache.CacheManager ehCacheManager;


    public EhcacheConfig() {
        CacheConfiguration cacheConfiguration = new CacheConfiguration();
        cacheConfiguration.setName(USER_CACHE_INSTANCE);
        cacheConfiguration.setMemoryStoreEvictionPolicy("LRU");
        cacheConfiguration.setMaxEntriesLocalHeap(100);
        cacheConfiguration.setEternal(false);
        cacheConfiguration.setTimeToIdleSeconds(0);
        cacheConfiguration.setTimeToLiveSeconds(0);

        net.sf.ehcache.config.Configuration config = new net.sf.ehcache.config.Configuration();
        config.addCache(cacheConfiguration);
        this.ehCacheManager = net.sf.ehcache.CacheManager.newInstance(config);
        this.cacheManager = new EhCacheCacheManager(ehCacheManager);
    }

    @Bean(destroyMethod="shutdown")
    public net.sf.ehcache.CacheManager ehCacheManager() {
        return ehCacheManager;
    }

    @Bean
    @Override
    public CacheManager cacheManager() {
        return cacheManager;
    }

    @Bean
    @Override
    public KeyGenerator keyGenerator() {
        return new SimpleKeyGenerator();
    }

    @Bean
    @Override
    public CacheResolver cacheResolver() {
        return new SimpleCacheResolver(cacheManager);
    }

    @Bean
    @Override
    public CacheErrorHandler errorHandler() {
        return new SimpleCacheErrorHandler();
    }

}
