package com.djaller.common.mail.model;

import javax.validation.constraints.NotEmpty;
import lombok.Data;
import org.hibernate.validator.constraints.URL;

import java.io.Serializable;

@Data
public class EmailAttachment implements Serializable {
    @URL
    @NotEmpty
    private String url;

    @NotEmpty
    private String name;

    private Long size;

    @NotEmpty
    private String contentType;
}
