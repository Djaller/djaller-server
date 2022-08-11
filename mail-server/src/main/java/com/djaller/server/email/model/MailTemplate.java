package com.djaller.server.email.model;

import com.djaller.common.mail.model.EmailTemplate;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Data
public class MailTemplate {
    private UUID id;

    @NotNull
    private EmailTemplate template;

    @NotEmpty
    @Length(max = 256)
    private String planText;

    @NotEmpty
    private String htmlText;
}
