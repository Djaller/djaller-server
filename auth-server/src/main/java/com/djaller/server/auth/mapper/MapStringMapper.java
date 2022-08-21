package com.djaller.server.auth.mapper;

import com.djaller.server.auth.domain.MapString;
import com.djaller.server.auth.mapper.impl.AbstractMapStringMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Slf4j
@Component
@RequiredArgsConstructor
public class MapStringMapper implements IMapStringMapper {
    private final Collection<? extends AbstractMapStringMapper> mappers;

    public MapString toMapString(Object obj) {
        for (var mapper : mappers) {
            try {
                var mapString = mapper.toMapString(obj);
                if (mapString != null) {
                    return mapString;
                }
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
        }

        throw new RuntimeException("Cannot serialize obj");
    }

    public Object toObject(MapString mapString) {
        for (var mapper : mappers) {
            try {
                var obj = mapper.toObject(mapString);
                if (obj != null) {
                    return obj;
                }
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
        }

        throw new RuntimeException("Cannot deserialize mapString");
    }


}
