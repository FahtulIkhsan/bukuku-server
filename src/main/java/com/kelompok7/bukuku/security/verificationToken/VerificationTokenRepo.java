package com.kelompok7.bukuku.security.verificationToken;

import com.kelompok7.bukuku.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VerificationTokenRepo extends JpaRepository<VerificationToken, Long> {
    VerificationToken findByToken(String token);

    VerificationToken findByUser(User user);
}
