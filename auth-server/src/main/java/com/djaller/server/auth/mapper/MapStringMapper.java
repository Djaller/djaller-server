package com.djaller.server.auth.mapper;

import com.djaller.server.auth.domain.MapString;
import com.google.gson.Gson;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@AllArgsConstructor
public class MapStringMapper {

    private final Gson gson;

    public MapString toMapString(Object obj) {
        return new MapString(obj.getClass().getName(), gson.toJson(obj));
    }

    public Object toObject(MapString mapString) {
        try {
            return gson.fromJson(mapString.getData(), Class.forName(mapString.getType()));
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public Map<String, Object> toMapObj(Map<String, MapString> maps) {
        if (maps == null) {
            return null;
        }

        var result = new HashMap<String, Object>();
        maps.forEach((key, mapString) -> result.put(key, toObject(mapString)));

        return result;
    }

    public Map<String, MapString> toMapString(Map<String, Object> maps) {
        if (maps == null) {
            return null;
        }

        var result = new HashMap<String, MapString>();
        maps.forEach((key, obj) -> result.put(key, toMapString(obj)));

        return result;
    }
}
