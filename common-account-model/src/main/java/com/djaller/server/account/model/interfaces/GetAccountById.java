package com.djaller.server.account.model.interfaces;

import com.djaller.server.account.model.Account;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.UUID;

@FunctionalInterface
public interface GetAccountById {

    @RequestMapping(
            method = RequestMethod.GET,
            value = "/api/accounts/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseBody
    Account getAccountById(@PathVariable("id") UUID accountId);

}
