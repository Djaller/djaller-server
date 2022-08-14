package com.djaller.server.auth.mapper;

import com.djaller.server.auth.domain.AppClientEntity;
import com.djaller.server.auth.model.AppClient;
import lombok.AllArgsConstructor;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
@AllArgsConstructor
public abstract class AppClientMapper {

    @InheritInverseConfiguration
    public abstract AppClient toModel(AppClientEntity entity);

    public abstract AppClientEntity toEntity(AppClient model);
}
