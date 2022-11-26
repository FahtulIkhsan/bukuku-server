package com.kelompok7.bukuku.security;

import com.kelompok7.bukuku.user.User;
import com.kelompok7.bukuku.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.mail.MessagingException;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
@Slf4j
@RequiredArgsConstructor
public class AuthController {
    @Autowired
    private final AuthService authService;
    @Autowired
    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody User user) throws MessagingException, UnsupportedEncodingException {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("auth/register").toUriString());
        return ResponseEntity.created(uri).body(authService.register(user));
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> token(Authentication authentication){
        Optional<User> user = Optional.ofNullable(userService.getUser(authentication.getName()));
        if(!user.isPresent()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Map<String, String> map = new HashMap<String, String>();
        if(!user.get().isEnabled()){
            map.put("error", "Email belum diverifikasi");
            return new ResponseEntity<>(map, HttpStatus.FORBIDDEN);
        }
        log.info("Token requested for user: {}", authentication.getName());
        String token = authService.generateToken(authentication);
        map.put("token", token);
        log.info("Token granted: {}", token);
        return ResponseEntity.ok(map);
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
