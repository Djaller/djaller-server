package com.djaller.server.auth.web;

import com.djaller.server.account.model.Account;
import com.djaller.server.account.model.interfaces.CreateAccount;
import com.djaller.server.auth.exception.BadRequestException;
import com.djaller.server.auth.mapper.RegisterDataMapper;
import com.djaller.server.auth.model.LoginData;
import com.djaller.server.auth.model.RedirectionData;
import com.djaller.server.auth.model.RegisterData;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@Tag(name = "auth")
@Slf4j
@RestController
@RequestMapping("auth/api")
@RequiredArgsConstructor
public class AuthResource {

    private final CreateAccount createAccount;
    private final RegisterDataMapper registerDataMapper;
    private final RememberMeServices rememberMeServices;

    @PostMapping("login")
    public RedirectionData login(@RequestBody @Valid LoginData loginData, HttpServletRequest request, HttpServletResponse response) {
        log.info("Login with {}", loginData);
        try {
            if (isAuthenticated()) {
                log.info("Account already authenticated");
            } else {
                request.login(loginData.getUsername(), loginData.getPassword());
            }

            var requestCache = new HttpSessionRequestCache();
            var savedRequest = requestCache.getRequest(request, response);
            if (savedRequest != null) {
                var redirectUrl = savedRequest.getRedirectUrl();
                log.info("Redirect to request is {}", savedRequest);
                return RedirectionData.builder().redirectUrl(redirectUrl).build();
            }

            log.info("No savedRequest found. Going to main");
            return RedirectionData.builder().redirectUrl("/").build();
        } catch (ServletException e) {
            log.error("Login error", e);
            throw new BadRequestException(e);
        }
    }

    @PostMapping("register")
    public Account register(@RequestBody @Valid RegisterData registerData) {
        if (isAuthenticated()) {
            log.info("Cannot register if already logged in");
            return null;
        }

        log.info("Register new user {}", registerData);
        var mapped = registerDataMapper.map(registerData);
        var account = createAccount.createAccount(mapped);
        log.info("Account created {}={}", account.getId(), account.getEmail());
        return account;
    }

    @GetMapping("logout")
    public void logout(HttpServletRequest request) {

        var session = request.getSession(false);
        SecurityContextHolder.clearContext();
        // will check existence of session. If session exists,
        // then it returns the reference of that session object, if not, this methods will return null.
        session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        if (request.getCookies() != null) {
            for (var cookie : request.getCookies()) {
                // Will delete the cookie right away
                cookie.setMaxAge(0);
            }
        }
    }

    private boolean isAuthenticated() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof AnonymousAuthenticationToken) {
            return false;
        }
        return authentication.isAuthenticated();
    }
}
