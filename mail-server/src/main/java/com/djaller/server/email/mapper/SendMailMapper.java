package com.djaller.server.email.mapper;

import com.djaller.common.mail.model.SendMail;
import com.djaller.server.email.domain.MapString;
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

    protected Map<String, Object> toModel(Map<String, MapString> entity) {
        var map = new HashMap<String, Object>();
        entity.forEach((key, value) -> map.put(key, toObj(value)));
        return map;
    }

    protected Map<String, MapString> toEntity(Map<String, Object> entity) {
        var map = new HashMap<String, MapString>();
        entity.forEach((key, value) -> map.put(key, toJson(value)));
        return map;
    }

    private MapString toJson(Object obj) {
        return new MapString(obj.getClass().getName(), gson.toJson(obj));
    }

    private Object toObj(MapString json) {
        try {
            return gson.fromJson(json.getValue(), Class.forName(json.getType()));
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
