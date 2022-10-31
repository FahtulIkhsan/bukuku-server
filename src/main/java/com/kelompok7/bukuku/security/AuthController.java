package com.kelompok7.bukuku.security;

import com.kelompok7.bukuku.user.User;
import com.kelompok7.bukuku.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/auth")
@Slf4j
@RequiredArgsConstructor
public class AuthController {
    private final TokenService tokenService;
    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody User user) {
        log.info("Line1");
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/auth/register").toUriString());
        log.info("Line2");
        return ResponseEntity.created(uri).body(userService.saveUser(user));
    }

    @PostMapping("/login")
    public String token(Authentication authentication){
        log.info("Token requested for user: {}", authentication.getName());
        String token = tokenService.generateToken(authentication);
        log.info("Token granted: {}", token);
        return token;
    }
}