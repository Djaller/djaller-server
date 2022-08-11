package com.djaller.server.email.web;

import com.djaller.common.mail.model.EmailTemplate;
import com.djaller.server.email.domain.MailTemplateEntity;
import com.djaller.server.email.exception.NotFoundException;
import com.djaller.server.email.mapper.MailTemplateMapper;
import com.djaller.server.email.model.MailTemplate;
import com.djaller.server.email.repo.MailTemplateRepo;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.converters.models.PageableAsQueryParam;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;


@Tag(name = "mail-template")
@RestController
@RequestMapping("api/mail-templates")
@RequiredArgsConstructor
public class MailTemplateResource {
    private final MailTemplateRepo mailTemplateRepo;
    private final MailTemplateMapper mapper;

    @GetMapping
    @PageableAsQueryParam
    public Collection<MailTemplate> getAllTemplate(@Parameter(hidden = true) @PageableDefault Pageable pageable) {
        return mailTemplateRepo
                .findAll(pageable)
                .map(mapper::toModel)
                .getContent();
    }

    @GetMapping("{template}")
    @PageableAsQueryParam
    public MailTemplate getSingle(@PathVariable EmailTemplate template) {
        return mailTemplateRepo
                .findByTemplate(template)
                .map(mapper::toModel)
                .orElseThrow(NotFoundException::new);
    }

    @PutMapping("{template}")
    public MailTemplate save(@PathVariable EmailTemplate template, @RequestBody @Valid MailTemplate model) {
        var entity = mailTemplateRepo.findByTemplate(template).orElseGet(() -> {
            var n = new MailTemplateEntity();
            n.setTemplate(template);
            return n;
        });
        entity.setHtmlText(model.getHtmlText());
        entity.setPlanText(model.getPlanText());
        var saved = mailTemplateRepo.save(entity);
        return mapper.toModel(saved);
    }

}
