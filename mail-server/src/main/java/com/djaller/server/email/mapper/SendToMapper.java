package com.djaller.server.email.mapper;

import com.djaller.common.mail.model.SendTo;
import com.djaller.server.email.domain.SendToEmbeddable;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface SendToMapper {

    SendToEmbeddable toEntity(SendTo model);

    @InheritInverseConfiguration
    SendTo toModel(SendToEmbeddable entity);
}
