package com.djaller.server.auth.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/auth/sign-in").setViewName("auth/sign-in");
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/auth/oauth2/token");
    }
}
