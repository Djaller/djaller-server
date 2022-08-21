package com.djaller.server.auth.mapper;

import com.djaller.server.auth.domain.MapString;

import java.util.HashMap;
import java.util.Map;

public interface IMapStringMapper {
    MapString toMapString(Object obj);

    Object toObject(MapString mapString);

    default Map<String, Object> toMapObj(Map<String, MapString> maps) {
        if (maps == null) {
            return null;
        }

        var result = new HashMap<String, Object>();
        maps.forEach((key, mapString) -> result.put(key, toObject(mapString)));

        return result;
    }

    default Map<String, MapString> toMapString(Map<String, Object> maps) {
        if (maps == null) {
            return null;
        }

        var result = new HashMap<String, MapString>();
        maps.forEach((key, obj) -> result.put(key, toMapString(obj)));

        return result;
    }
}
