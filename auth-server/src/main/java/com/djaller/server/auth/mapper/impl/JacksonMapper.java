package com.djaller.server.auth.mapper.impl;

import com.djaller.server.auth.domain.MapString;
import com.djaller.server.auth.mapper.IMapStringMapper;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.security.jackson2.CoreJackson2Module;
import org.springframework.security.jackson2.SecurityJackson2Modules;
import org.springframework.security.oauth2.server.authorization.jackson2.OAuth2AuthorizationServerJackson2Module;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@Order(1)
public class JacksonMapper extends AbstractMapStringMapper implements IMapStringMapper {
    private final ObjectMapper objectMapper;

    public JacksonMapper(List<Module> modules) {
        this.objectMapper = JsonMapper.builder().build();
        this.objectMapper.registerModules(modules);

        var classLoader = JacksonMapper.class.getClassLoader();
        var securityModules = SecurityJackson2Modules.getModules(classLoader);
        this.objectMapper.registerModules(securityModules);

        this.objectMapper.registerModule(new OAuth2AuthorizationServerJackson2Module());
        this.objectMapper.registerModule(new CoreJackson2Module());

        this.objectMapper.enableDefaultTyping();
    }

    public MapString toMapString(Object obj) {
        try {
            final var mapString = new MapString();
            mapString.setType(obj.getClass().getName());
            mapString.setData(objectMapper.writeValueAsString(obj));
            return mapString;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Object toObject(MapString mapString) {
        try {
            return objectMapper.readValue(mapString.getData(), Class.forName(mapString.getType()));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }
}
