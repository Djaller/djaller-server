package com.djaller.server.account.model.interfaces;

import com.djaller.server.account.model.Account;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@FunctionalInterface
public interface UpdateAccount {

    @RequestMapping(
            method = RequestMethod.PUT,
            value = "/api/accounts/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseBody
    Account updateAccount(@PathVariable("id") UUID accountId, @RequestBody Account account);

}
