package com.djaller.server.auth.mapper;

import com.djaller.server.auth.domain.MapString;
import com.google.gson.Gson;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class MapStringMapper {

    private final Gson gson;

    public MapString toMapString(Object obj) {
        return new MapString(obj.getClass().toString(), gson.toJson(obj));
    }

    public Object toObject(MapString mapString) {
        try {
            return gson.fromJson(mapString.getData(), Class.forName(mapString.getType()));
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
