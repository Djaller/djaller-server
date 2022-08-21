package com.djaller.server.auth.mapper.impl;

import com.djaller.server.auth.domain.MapString;
import com.djaller.server.auth.mapper.IMapStringMapper;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
@Order(0)
public class GsonMapper extends AbstractMapStringMapper implements IMapStringMapper {
    private final Gson gson;

    public MapString toMapString(Object obj) {
        try {
            return new MapString(obj.getClass().getName(), gson.toJson(obj));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Object toObject(MapString mapString) {
        try {
            return gson.fromJson(mapString.getData(), Class.forName(mapString.getType()));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
