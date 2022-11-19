package com.kelompok7.bukuku.user.nomorTelepon;

import java.util.Optional;
import java.util.Set;

public interface NomorTeleponService {
    Set<NomorTelepon> saveTelepon(Long userId, NomorTelepon nomorTelepon);
    Optional<NomorTelepon> getTelepon(Long teleponId);
    Set<NomorTelepon> getAllTelepon(Long userId);
    void deleteAllTelepon(Long userId);
    void deleteTelepon(Long teleponId);
    boolean isExist(Long teleponId);
}
