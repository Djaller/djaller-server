package com.djaller.server.email.web;

import com.djaller.server.email.domain.SendMailEntity;
import com.djaller.server.email.repo.SendMailRepo;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.converters.models.PageableAsQueryParam;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;


@Tag(name = "send-mail")
@RestController
@RequestMapping("api/send-mails")
@RequiredArgsConstructor
public class SendMailResource {
    private final SendMailRepo mailRepo;

    @GetMapping
    @PageableAsQueryParam
    public Collection<SendMailEntity> getMailSend(@Parameter(hidden = true) @PageableDefault Pageable pageable) {
        return mailRepo.findAll(pageable).getContent();
    }

}
