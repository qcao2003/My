package com;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new Myinterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns("/login");    //order用来执行多个拦截器的执行顺序,order书写是自然数,按照自然数顺序执行
    }
}


