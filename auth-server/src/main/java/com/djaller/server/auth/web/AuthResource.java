package com.djaller.server.auth.web;

import com.djaller.server.auth.model.LoginData;
import com.djaller.server.auth.model.LoginError;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Tag(name = "auth")
@Slf4j
@RestController
@RequestMapping
@RequiredArgsConstructor
public class AuthResource {

    @PostMapping("/api/login")
    public ResponseEntity<?> login(@RequestBody @Valid LoginData loginData, HttpServletRequest request) {
        log.info("Login with {}", loginData);
        try {
            request.login(loginData.getUsername(), loginData.getPassword());
            return ResponseEntity.ok().build();
        } catch (ServletException e) {
            log.error("Login error", e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(LoginError.builder().status(400).message(e.getMessage()).build());
        }
    }

    @GetMapping("/api/logout")
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
}
