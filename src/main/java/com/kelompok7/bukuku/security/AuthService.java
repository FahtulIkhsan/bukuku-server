package com.kelompok7.bukuku.security;

import com.kelompok7.bukuku.user.User;
import com.kelompok7.bukuku.user.UserRepo;
import com.kelompok7.bukuku.user.role.ERole;
import com.kelompok7.bukuku.user.role.Role;
import com.kelompok7.bukuku.security.verificationToken.VerificationToken;
import com.kelompok7.bukuku.security.verificationToken.VerificationTokenRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {
    @Autowired
    private final UserRepo userRepo;
    @Autowired
    private final VerificationTokenRepo verificationTokenRepo;
    @Autowired
    private final JavaMailSender mailSender;
    @Autowired
    private final JwtEncoder encoder;
    @Autowired
    private final ApplicationProperties applicationProperties;
    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    public String generateToken(Authentication authentication){
        String userId = userRepo.findByUsername(authentication.getName()).getUserId().toString();
        Instant now = Instant.now();
        String scope = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(" "));
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("self")
                .issuedAt(now)
                .expiresAt(now.plus(2, ChronoUnit.HOURS))
                .subject(userId)
                .claim("scope", scope)
                .build();
        return this.encoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }

    public User register(User user) throws MessagingException, UnsupportedEncodingException {
        log.info("Saving new user {} to the database", user.getName());
        user.setPassword(encoder().encode(user.getPassword()));
//        user.setPassword(String.format("{bcrypt}%s",user.getPassword()));
        Set<Role> role = new HashSet<>();
        role.add(new Role(ERole.ROLE_USER));
        user.setRoles(role);
        user.setEnabled(false);
        userRepo.save(user);
        sendVerificationEmail(user);
        return user;
    }

    public void sendVerificationEmail(User user) throws MessagingException, UnsupportedEncodingException{
        String siteURL = String.format("localhost:%s", applicationProperties.getPort());
        String toAddress = user.getEmail();
        String fromAddress = "test.test.777555333@gmail.com";
        String senderName = "Bukuku";
        String subject = "Please verify your registration";
        String content = "Dear [[name]],<br>"
                + "Please open the link below to verify your registration:<br>"
                + "<br>"
                + "[[URL]]<br>"
                + "<br>"
                + "Thank you,<br>"
                + "Bukuku team";

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom(fromAddress, senderName);
        helper.setTo(toAddress);
        helper.setSubject(subject);

        content = content.replace("[[name]]", user.getUsername());
        VerificationToken token = new VerificationToken(user);
        verificationTokenRepo.save(token);
        String verifyURL = siteURL + "/auth/verify?code=" + token.getToken();

        content = content.replace("[[URL]]", verifyURL);

        helper.setText(content, true);

        mailSender.send(message);
    }

    public boolean verify(String verificationCode) {
        log.info("verificationCode: {}", verificationCode);
        VerificationToken token = verificationTokenRepo.findByToken(verificationCode);
        User user = token.getUser();
        if (user == null || user.isEnabled()) {
            return false;
        } else {
            user.setEnabled(true);
            userRepo.save(user);
            verificationTokenRepo.delete(token);
            return true;
        }
    }
}
