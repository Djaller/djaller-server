package com.djaller.server.email.mapper;

import com.djaller.common.mail.model.SendMail;
import com.djaller.server.email.domain.SendMailEntity;
import com.google.gson.Gson;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        uses = {SendToMapper.class, EmailAttachmentMapper.class}
)
public abstract class SendMailMapper {

    @Autowired
    private Gson gson;

    @InheritInverseConfiguration
    public abstract SendMail toModel(SendMailEntity entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "status", ignore = true)
    public abstract SendMailEntity toEntity(SendMail model);

    protected Map<String, Object> toModel(Map<String, String> entity) {
        var map = new HashMap<String, Object>();
        entity.forEach((key, value) -> map.put(key, toObj(value)));
        return map;
    }

    protected Map<String, String> toEntity(Map<String, Object> entity) {
        var map = new HashMap<String, String>();
        entity.forEach((key, value) -> map.put(key, toJson(value)));
        return map;
    }

    private String toJson(Object obj) {
        return gson.toJson(obj);
    }

    private Object toObj(String json) {
        return gson.fromJson(json, Object.class);
    }
}
