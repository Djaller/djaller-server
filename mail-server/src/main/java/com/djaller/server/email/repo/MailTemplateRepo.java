package com.djaller.server.email.repo;

import com.djaller.common.mail.model.EmailTemplate;
import com.djaller.server.email.domain.MailTemplateEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.NotNull;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface MailTemplateRepo extends JpaRepository<MailTemplateEntity, UUID> {

    Optional<MailTemplateEntity> findByTemplate(@NotNull EmailTemplate template);

    @Override
    <S extends MailTemplateEntity> S save(S entity);
}
