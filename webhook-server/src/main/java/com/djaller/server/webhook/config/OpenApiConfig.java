package com.djaller.server.webhook.config;

import org.springdoc.core.SpringDocUtils;
import org.springframework.context.annotation.Configuration;

import java.util.Locale;

@Configuration
public class OpenApiConfig {
    static {
        SpringDocUtils
                .getConfig()
                .replaceWithClass(Locale.class, String.class)
        ;
    }
}
