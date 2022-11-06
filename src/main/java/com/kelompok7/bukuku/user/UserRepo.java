package com.kelompok7.bukuku.user;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User, Long> {
    User findByUserId(Long userId);
    User findByUsername(String username);

    boolean existsByUserId(Long userId);
}
