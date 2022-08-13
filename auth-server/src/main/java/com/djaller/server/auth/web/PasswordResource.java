package com.djaller.server.auth.web;

import com.djaller.server.auth.exception.BadRequestException;
import com.djaller.server.auth.model.ForgotPassword;
import com.djaller.server.auth.model.ResetPassword;
import com.djaller.server.auth.service.AccountPasswordService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/api/passwords")
@RequiredArgsConstructor
public class PasswordResource {
    private final AccountPasswordService accountPasswordService;

    /**
     * This will validate the code and update the password for a specific user
     *
     * @param resetPassword {@link ResetPassword}
     */
    @PostMapping("reset")
    public void resetPassword(@RequestBody @Valid ResetPassword resetPassword) {
        if (!resetPassword.getPassword().equals(resetPassword.getConfirm())) {
            throw new BadRequestException();
        }

        accountPasswordService.createPassword(resetPassword.getAccountId(), resetPassword.getPassword(), resetPassword.getCodeVerifier());
    }

    /**
     * This will send an E-Mail to reset the password
     *
     * @param forgotPassword {@link ForgotPassword}
     */
    @PostMapping("forgot")
    public void forgotPassword(@RequestBody @Valid ForgotPassword forgotPassword) {
        accountPasswordService.resetPassword(forgotPassword.getUsername());
    }
}
