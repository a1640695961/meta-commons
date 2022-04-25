package com.meta.support.autoconfig;

import com.meta.support.exception.ExceptionHandlerResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author xiongmao
 * <p>
 * exception auto starter
 */
@Configuration
public class ExceptionAutoConfiguration {
    private static final Logger LOGGER = LoggerFactory.getLogger(ExceptionAutoConfiguration.class);

    public ExceptionAutoConfiguration() {
        LOGGER.info("####>>>>>>>>>>ExceptionAutoConfiguration init success####");
    }

    @Bean
    public ExceptionHandlerResolver configExceptionResolver() {
        return new ExceptionHandlerResolver();
    }
}