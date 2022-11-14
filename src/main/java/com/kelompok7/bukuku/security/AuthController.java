package com.kelompok7.bukuku.security;

import com.kelompok7.bukuku.user.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.mail.MessagingException;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URI;

@RestController
@RequestMapping("/auth")
@Slf4j
@RequiredArgsConstructor
public class AuthController {
    @Autowired
    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody User user) throws MessagingException, UnsupportedEncodingException {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("auth/register").toUriString());
        return ResponseEntity.created(uri).body(authService.register(user));
    }

    @PostMapping("/login")
    public String token(Authentication authentication){
        log.info("Token requested for user: {}", authentication.getName());
        String token = authService.generateToken(authentication);
        log.info("Token granted: {}", token);
        return token;
    }
    @GetMapping("/verify")
    public String verifyUser(@Param("code") String code) {
        if (authService.verify(code)) {
            return "verify_success";
        } else {
            return "verify_fail";
        }
    }

    @GetMapping("/test")
    public ResponseEntity<String> test(){
        return ResponseEntity.ok("Server is running");
    }
}
