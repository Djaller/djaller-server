package com.djaller.server.email.mapper;

import com.djaller.server.email.domain.MailTemplateEntity;
import com.djaller.server.email.model.MailTemplate;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface MailTemplateMapper {

    MailTemplateEntity toEntity(MailTemplate model);

    @InheritInverseConfiguration
    MailTemplate toModel(MailTemplateEntity entity);
}
