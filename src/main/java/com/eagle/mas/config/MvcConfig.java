package com.eagle.mas.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        // Redirect empty or root ("/") requests to the login page
        registry.addViewController("/").setViewName("redirect:/");
        registry.addViewController("/index.html").setViewName("redirect:/");
    }

    @Override
    public void addResourceHandlers(final ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/*.js/**").addResourceLocations("/static/css/js/");
        registry.addResourceHandler("/*.css/**").addResourceLocations("/ui/static/");
    }

}