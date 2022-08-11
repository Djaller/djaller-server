package com.djaller.server.email.web;

import com.djaller.common.mail.model.SendMail;
import com.djaller.server.email.mapper.SendMailMapper;
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
    private final SendMailMapper mapper;

    @GetMapping
    @PageableAsQueryParam
    public Collection<SendMail> getMailSend(@Parameter(hidden = true) @PageableDefault Pageable pageable) {
        return mailRepo.findAll(pageable).map(mapper::toModel).getContent();
    }

}
