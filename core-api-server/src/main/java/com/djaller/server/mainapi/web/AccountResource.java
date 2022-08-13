package com.djaller.server.mainapi.web;

import com.djaller.server.account.model.Account;
import com.djaller.server.account.model.interfaces.*;
import com.djaller.server.mainapi.exception.NotFoundException;
import com.djaller.server.mainapi.mapper.AccountMapper;
import com.djaller.server.mainapi.repo.AccountRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.Collections;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping
@RequiredArgsConstructor
public class AccountResource implements CreateAccount, FindAccount, GetAccountByEmail, GetAccountById, UpdateAccount {
    private final AccountMapper accountMapper;
    private final AccountRepo accountRepo;

    @Override
    public Account createAccount(Account account) {
        final var entity = accountMapper.toEntity(account);
        final var created = accountRepo.save(entity);
        return accountMapper.toModel(created);
    }

    @Override
    public Collection<Account> find(Collection<String> sort, Integer page, Integer size) {
        if (sort == null) {
            sort = Collections.emptyList();
        }

        return accountRepo
                .findAll(PageRequest.of(page, size, Sort.by(sort.toArray(new String[0]))))
                .map(accountMapper::toModel)
                .getContent();
    }

    @Override
    public Account getAccountByEmail(String email) {
        return accountRepo
                .findByEmail(email)
                .map(accountMapper::toModel)
                .orElseThrow(NotFoundException::new);
    }

    @Override
    public Account getAccountById(UUID accountId) {
        return accountRepo
                .findById(accountId)
                .map(accountMapper::toModel)
                .orElseThrow(NotFoundException::new);
    }

    @Override
    public Account updateAccount(UUID accountId, Account account) {
        var found = accountRepo.findById(accountId).orElseThrow(NotFoundException::new);
        var mapped = accountMapper.toEntity(account);
        BeanUtils.copyProperties(mapped, found);
        var saved = accountRepo.save(found);
        return accountMapper.toModel(saved);
    }
}
