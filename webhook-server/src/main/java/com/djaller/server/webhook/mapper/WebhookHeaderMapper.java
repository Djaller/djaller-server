package com.djaller.server.webhook.mapper;

import com.djaller.server.webhook.domain.WebhookHeaderEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.util.*;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public abstract class WebhookHeaderMapper {

    public Map<String, Collection<String>> toMultiValueMap(Set<WebhookHeaderEntity> multiValueMap) {
        var result = new HashMap<String, Collection<String>>();
        for (WebhookHeaderEntity entity : multiValueMap) {
            result.put(entity.getKey(), new HashSet<>(entity.getValuesSet()));
        }
        return result;
    }

    public Set<WebhookHeaderEntity> toEntrySet(Map<String, Collection<String>> map) {
        final var result = new HashSet<WebhookHeaderEntity>();
        if (map != null)
            map.forEach((key, values) -> {
                var entity = new WebhookHeaderEntity();
                entity.setKey(key);
                entity.setValuesSet(new HashSet<>(values));
                result.add(entity);
            });

        return result;
    }

}
