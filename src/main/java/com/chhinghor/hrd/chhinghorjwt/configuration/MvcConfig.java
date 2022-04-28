package com.chhinghor.hrd.chhinghorjwt.configuration;

import com.chhinghor.hrd.chhinghorjwt.component.LogRequestInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

    @Autowired
    LogRequestInterceptor logRequestInterceptor;

    /**
     * Method for register device
     */

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        registry.addInterceptor(logRequestInterceptor)
                .addPathPatterns("/api/**");

    }
}
