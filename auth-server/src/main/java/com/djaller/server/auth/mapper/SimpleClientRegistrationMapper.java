package com.djaller.server.auth.mapper;

import com.djaller.server.auth.domain.ProviderClientEntity;
import com.djaller.server.auth.model.SimpleProviderClientModel;
import lombok.AllArgsConstructor;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
@AllArgsConstructor
public abstract class SimpleClientRegistrationMapper {

    @InheritInverseConfiguration
    public abstract SimpleProviderClientModel toModel(ProviderClientEntity entity);

    @Mapping(target = "scopes", ignore = true)
    @Mapping(target = "redirectUri", ignore = true)
    @Mapping(target = "detailUserInfoUri", ignore = true)
    @Mapping(target = "detailUserInfoAuthenticationMethod", ignore = true)
    @Mapping(target = "detailUserInfoAttributeName", ignore = true)
    @Mapping(target = "detailTokenUri", ignore = true)
    @Mapping(target = "detailJwkSetUri", ignore = true)
    @Mapping(target = "detailIssuerUri", ignore = true)
    @Mapping(target = "detailConfigurationMetadata", ignore = true)
    @Mapping(target = "detailAuthorizationUri", ignore = true)
    @Mapping(target = "clientSecret", ignore = true)
    @Mapping(target = "clientAuthenticationMethod", ignore = true)
    @Mapping(target = "authorizationGrantType", ignore = true)
    @Mapping(target = "systemClient", expression = "java( false )")
    public abstract ProviderClientEntity toEntity(SimpleProviderClientModel model);
}
