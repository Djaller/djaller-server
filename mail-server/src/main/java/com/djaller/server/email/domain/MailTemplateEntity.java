package com.djaller.server.email.domain;

import com.djaller.common.mail.model.EmailTemplate;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.UUID;

@Entity
@Table(name = "mail_templates", indexes = @Index(name = "idx_mail_template", columnList = "template"))
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MailTemplateEntity implements Serializable {

    @Id
    @GeneratedValue
    @Column(columnDefinition = "uuid")
    private UUID id;

    @JsonIgnore
    @Column(unique = true)
    @Enumerated(EnumType.STRING)
    private EmailTemplate template;

    @NotEmpty
    @Length(max = 256)
    @Column(length = 256)
    private String planText;

    @NotEmpty
    @Column(columnDefinition = "text")
    private String htmlText;

}
