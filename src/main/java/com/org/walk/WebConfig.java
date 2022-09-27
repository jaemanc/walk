package com.org.walk;

import com.org.walk.util.Interceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@EnableWebMvc
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Bean
    public Interceptor interceptor() {
        return new Interceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(interceptor())
                .addPathPatterns("/**")
                .excludePathPatterns(
                        "**",
                        "/v2/api-docs/**",
                        "/swagger-resources/**",
                        "/swagger-ui/**",
                        "/swagger-ui.html/**",
                        "/webjars/**"
                        ,"/login/**"
                ).excludePathPatterns("http://localhost:8080/walk/v2/api-docs")
        ;

        WebMvcConfigurer.super.addInterceptors(registry);

    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
    }
}
