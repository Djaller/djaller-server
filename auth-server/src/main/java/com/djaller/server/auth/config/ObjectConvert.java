package com.djaller.server.auth.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ObjectConvert {

    @Bean
    public Gson gson() {
        var builder = new GsonBuilder();
        return builder.create();
    }
}
