package com.djaller.common.mail.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.Map;
import java.util.Set;

@Data
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
