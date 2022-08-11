package com.djaller.server.webhook.web;

import com.djaller.server.webhook.exception.NotFoundException;
import com.djaller.server.webhook.mapper.WebhookMapper;
import com.djaller.server.webhook.model.Webhook;
import com.djaller.server.webhook.repo.WebhookRepository;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.converters.models.PageableAsQueryParam;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;
import java.util.UUID;


@Tag(name = "webhook")
@RestController
@RequestMapping(value = "api/webhooks")
@RequiredArgsConstructor
public class WebhookResource {
    private final WebhookRepository repository;
    private final WebhookMapper mapper;

    @GetMapping
    @ResponseBody
    @PageableAsQueryParam
    public Collection<Webhook> getAllWebhook(@Parameter(hidden = true) @PageableDefault Pageable pageable) {
        var all = repository.findAll(pageable);
        return all.map(mapper::toModel).getContent();
    }

    @ResponseBody
    @GetMapping("{id}")
    public Webhook getSingle(@PathVariable UUID id) {
        return repository
                .findById(id)
                .map(mapper::toModel)
                .orElseThrow(NotFoundException::new);
    }

    @ResponseBody
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Webhook createWebhook(@RequestBody @Valid Webhook model) {
        var mapped = mapper.toEntity(model);
        mapped.setId(null);

        for (var header : mapped.getHeaders()) {
            header.setWebhook(mapped);
        }

        var saved = repository.save(mapped);
        return mapper.toModel(saved);
    }

    @ResponseBody
    @PutMapping("{id}")
    public Webhook updateWebhook(@PathVariable UUID id, @RequestBody @Valid Webhook model) {
        var entity = repository.findById(id).orElseThrow(NotFoundException::new);
        var mapped = mapper.toEntity(model);

        entity.setUrl(mapped.getUrl());
        entity.setHeaders(mapped.getHeaders());

        for (var header : mapped.getHeaders()) {
            header.setWebhook(mapped);
        }

        var saved = repository.save(entity);
        return mapper.toModel(saved);
    }

    @DeleteMapping("{id}")
    public void deleteWebhook(@PathVariable UUID id) {
        var entity = repository.findById(id).orElseThrow(NotFoundException::new);
        repository.delete(entity);
    }

}
