package com.djaller.server.auth.config;

import com.djaller.server.auth.converter.GrantedAuthorityGsonTypeAdapter;
import com.djaller.server.auth.converter.InstantGsonTypeAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.GrantedAuthority;

import java.time.Instant;

@Configuration
@RequiredArgsConstructor
public class ObjectConvert {

    @Bean
    public Gson gson() {
        var builder = new GsonBuilder();
        builder.registerTypeAdapter(Instant.class, new InstantGsonTypeAdapter());
        builder.registerTypeAdapter(GrantedAuthority.class, new GrantedAuthorityGsonTypeAdapter());
        return builder.create();
    }
}
