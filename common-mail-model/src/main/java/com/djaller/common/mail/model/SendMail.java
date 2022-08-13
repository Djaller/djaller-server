package com.djaller.common.mail.model;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.Map;
import java.util.Set;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class SendMail implements Serializable {

    @NonNull
    @NotEmpty
    private Set<SendTo> to;

    @NonNull
    @NotEmpty
    private String subject;

    @NonNull
    @NotEmpty
    private EmailTemplate template;

    private Map<String, Object> config;

    private Set<EmailAttachment> attachments;
}
