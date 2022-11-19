package com.kelompok7.bukuku.user.nomorTelepon;

import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.Set;

public interface NomorTeleponRepo extends JpaRepository<NomorTelepon, Long> {
    Set<NomorTelepon> findByUserUserId(Long userId);
    @Transactional
    void deleteByUserUserId(Long userId);
}
