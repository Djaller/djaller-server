package com.djaller.server.email.repo;

import com.djaller.common.mail.model.EmailTemplate;
import com.djaller.server.email.domain.MailTemplateEntity;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.NotNull;
import java.util.Optional;
import java.util.UUID;

@Cacheable("mail-template-repo")
@Repository
public interface MailTemplateRepo extends JpaRepository<MailTemplateEntity, UUID> {

    Optional<MailTemplateEntity> findByTemplate(@NotNull EmailTemplate template);

}
