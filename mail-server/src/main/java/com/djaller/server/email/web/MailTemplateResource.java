package com.djaller.server.email.web;

import com.djaller.common.mail.model.EmailTemplate;
import com.djaller.server.email.domain.MailTemplateEntity;
import com.djaller.server.email.exception.NotFoundException;
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

    @GetMapping
    @PageableAsQueryParam
    public Collection<MailTemplateEntity> getAllTemplate(@Parameter(hidden = true) @PageableDefault Pageable pageable) {
        return mailTemplateRepo.findAll(pageable).getContent();
    }

    @GetMapping("{template}")
    @PageableAsQueryParam
    public MailTemplateEntity getSingle(@PathVariable EmailTemplate template) {
        return mailTemplateRepo.findByTemplate(template).orElseThrow(NotFoundException::new);
    }

    @PutMapping("{template}")
    public MailTemplateEntity save(@PathVariable EmailTemplate template, @RequestBody @Valid MailTemplateEntity model) {
        var entity = mailTemplateRepo.findByTemplate(template).orElseGet(() -> {
            var n = new MailTemplateEntity();
            n.setTemplate(template);
            return n;
        });
        entity.setHtmlText(model.getHtmlText());
        entity.setPlanText(model.getPlanText());
        return mailTemplateRepo.save(entity);
    }

}
