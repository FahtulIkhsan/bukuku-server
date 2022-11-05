package com.kelompok7.bukuku.security.verificationToken;

import com.kelompok7.bukuku.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.bytebuddy.utility.RandomString;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VerificationToken {
    @Bean
    @Transient
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    private static final int EXPIRATION = 60 * 24;

    @Id
    private String token;

    @OneToOne(targetEntity = User.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "userId")
    private User user;

    private Date expiryDate;

    private Date calculateExpiryDate(int expiryTimeInMinutes) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Timestamp(cal.getTime().getTime()));
        cal.add(Calendar.MINUTE, expiryTimeInMinutes);
        return new Date(cal.getTime().getTime());
    }

    private String createToken(User user){
        String token;
        token = String.format("%s%s", encoder().encode(user.getUsername()), RandomString.make(16));
        return token;
    }

    public VerificationToken(User user){
        this.user = user;
        this.token = createToken(user);
        this.expiryDate = calculateExpiryDate(120);
    }

    // standard constructors, getters and setters
}
