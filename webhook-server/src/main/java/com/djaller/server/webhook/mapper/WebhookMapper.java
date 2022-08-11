package com.djaller.server.webhook.mapper;

import com.djaller.server.webhook.domain.WebhookEntity;
import com.djaller.server.webhook.model.Webhook;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        uses = WebhookHeaderMapper.class
)
public abstract class WebhookMapper {

    @InheritInverseConfiguration
    public abstract WebhookEntity toEntity(Webhook webhook);

    public abstract Webhook toModel(WebhookEntity entity);
}
