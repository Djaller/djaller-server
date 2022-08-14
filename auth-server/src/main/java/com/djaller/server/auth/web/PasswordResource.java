package com.djaller.server.auth.web;

import com.djaller.server.auth.exception.BadRequestException;
import com.djaller.server.auth.model.ForgotPassword;
import com.djaller.server.auth.model.ResetPassword;
import com.djaller.server.auth.service.AccountPasswordService;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@Tag(name = "password")
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

    @Hidden
    @GetMapping("code")
    public ResponseEntity<?> validateCode(@RequestParam(required = false) String code) {
        if (code == null) {
            return redirectToSignIn();
        }

        var accountId = accountPasswordService.accountIdForCode(code);
        if (accountId == null) {
            return redirectToSignIn();
        }

        var locationUri = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/change-password")
                .queryParam("code", code)
                .queryParam("accountId", accountId)
                .build()
                .toUri();
        return redirectTo(locationUri);
    }

    private ResponseEntity<?> redirectToSignIn() {
        var locationUri = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/sign-in")
                .build().toUri();
        return redirectTo(locationUri);
    }

    private ResponseEntity<?> redirectTo(URI locationUri) {
        return ResponseEntity
                .status(HttpStatus.FOUND)
                .location(locationUri)
                .build();
    }
}
