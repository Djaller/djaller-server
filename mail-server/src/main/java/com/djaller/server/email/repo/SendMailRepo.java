package com.djaller.server.email.repo;

import com.djaller.common.mail.model.EmailTemplate;
import com.djaller.server.email.domain.SendMailEntity;
import com.djaller.server.email.domain.SendMailStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

@Repository
public interface SendMailRepo extends JpaRepository<SendMailEntity, UUID> {

    List<SendMailEntity> findAllByStatusAndTemplate(@NotNull SendMailStatus status, @NotNull EmailTemplate template);

}
