package com.djaller.server.email.mapper;

import com.djaller.common.mail.model.EmailAttachment;
import com.djaller.server.email.domain.EmailAttachmentEmbeddable;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface EmailAttachmentMapper {

    EmailAttachmentEmbeddable toEntity(EmailAttachment model);

    @InheritInverseConfiguration
    EmailAttachment toModel(EmailAttachmentEmbeddable entity);
}
