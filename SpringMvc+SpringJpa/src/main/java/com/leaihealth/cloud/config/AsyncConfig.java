package com.leaihealth.cloud.config;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;


/**
 * ClassName: com.leaihealth.cloud.config
 * Version Information :
 * Date: 2018/10/8
 * COPYRIGHT (C) 2016 RETair.com. All Rights Reserved.
 */
@Configuration
@EnableAsync
public class AsyncConfig implements AsyncConfigurer {


    /**
     *  Set the ThreadPoolExecutor's core pool size. 
     */

    private int corePoolSize = 10;

    /**
     *  Set the ThreadPoolExecutor's maximum pool size. 
     */

    private int maxPoolSize = 200;

    /**
     *  Set the capacity for the ThreadPoolExecutor's BlockingQueue. 
     */

    private int queueCapacity = 10;
    private String ThreadNamePrefix = "MyLogExecutor-";

    @Override
    public Executor getAsyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(corePoolSize);
        executor.setMaxPoolSize(maxPoolSize);
        executor.setQueueCapacity(queueCapacity);
        executor.setThreadNamePrefix(ThreadNamePrefix);
        // rejection-policy：当pool已经达到max size的时候，如何处理新任务
        // CALLER_RUNS：不在新线程中执行任务，而是有调用者所在的线程来执行
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        executor.initialize();
        return executor;
    }
}
