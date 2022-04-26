package com.meta.support.autoconfig;

import com.meta.support.filter.RequestFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author Xiong Mao
 * @date 2022/04/26 01:47
 **/
@Configuration
@ComponentScan(basePackages = {
        "com.meta.support.web"
})
public class WebAutoConfiguration {
    private static final Logger LOGGER = LoggerFactory.getLogger(WebAutoConfiguration.class);

    public WebAutoConfiguration() {
        LOGGER.info("####>>>>>>>>>>>>WebAutoConfiguration init success####");
    }

    @Bean
    public FilterRegistrationBean sessionRequestFilter(){
        FilterRegistrationBean<RequestFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new RequestFilter());
        registrationBean.setOrder(0);
        return registrationBean;
    }
}
