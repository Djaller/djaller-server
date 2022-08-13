package com.djaller.server.email.domain;

import com.djaller.common.mail.model.EmailTemplate;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "send_mails")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SendMailEntity implements Serializable {

    @Id
    @GeneratedValue
    @Column(columnDefinition = "uuid")
    private UUID id;

    @NonNull
    @NotEmpty
    private String subject;

    @NotEmpty
    @ElementCollection
    @CollectionTable(name = "send_mail_recipients")
    private Set<SendToEmbeddable> to = new HashSet<>();

    @NotNull
    @Enumerated(EnumType.STRING)
    private SendMailStatus status;

    @NotNull
    private EmailTemplate template;

    @ElementCollection
    @CollectionTable(name = "send_mail_configs")
    private Map<String, MapString> config;

    @ElementCollection
    @CollectionTable(name = "send_mail_attachments")
    private Set<EmailAttachmentEmbeddable> attachments = new HashSet<>();
}
