package com.djaller.server.auth.mapper;

import com.djaller.server.account.model.Account;
import com.djaller.server.auth.model.RegisterData;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public abstract class RegisterDataMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "pictureUrl", ignore = true)
    @Mapping(target = "locale", ignore = true)
    @Mapping(target = "gender", ignore = true)
    @Mapping(target = "birthDate", ignore = true)
    @Mapping(target = "emailVerified", ignore = true)
    @Mapping(target = "phoneNumberVerified", ignore = true)
    @Mapping(target = "status", ignore = true)
    public abstract Account map(RegisterData registerData);
}
