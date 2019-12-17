package com.wendu.config.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

@Configuration
public class WebConfig extends WebMvcConfigurationSupport {

    @Value("${wendufile.images}")
    private String imagesfile;

    @Value("${wendufile.videos}")
    private String videosfile;


    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//将所有/static/** 访问都映射到classpath:/static/ 目录下
//swagger2
        registry.addResourceHandler("/swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
        registry.addResourceHandler("/images/**").addResourceLocations("file:" + imagesfile);
        registry.addResourceHandler("/videos/**").addResourceLocations("file:" + videosfile);
        super.addResourceHandlers(registry);
    }
}
