package com.djaller.server.email.domain;

import lombok.Data;
import org.hibernate.validator.constraints.URL;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotEmpty;

@Data
@Embeddable
public class EmailAttachmentEmbeddable {
    @URL
    @NotEmpty
    @Column(columnDefinition = "text")
    private String url;

    @NotEmpty
    private String name;

    private Long size;

    @NotEmpty
    private String contentType;
}
