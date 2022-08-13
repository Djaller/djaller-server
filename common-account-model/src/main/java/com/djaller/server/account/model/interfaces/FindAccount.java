package com.djaller.server.account.model.interfaces;

import com.djaller.server.account.model.Account;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Collection;

@FunctionalInterface
public interface FindAccount {

    @RequestMapping(
            method = RequestMethod.GET,
            value = "/api/accounts",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseBody
    Collection<Account> find(
            @RequestParam(required = false) Collection<String> sort,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "20") Integer size
    );
}
