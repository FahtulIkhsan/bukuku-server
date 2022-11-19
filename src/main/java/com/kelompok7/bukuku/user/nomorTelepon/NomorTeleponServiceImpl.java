package com.kelompok7.bukuku.user.nomorTelepon;

import com.kelompok7.bukuku.user.User;
import com.kelompok7.bukuku.user.UserRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class NomorTeleponServiceImpl implements NomorTeleponService{
    @Autowired
    private final UserRepo userRepo;
    @Autowired
    private final NomorTeleponRepo nomorTeleponRepo;

    @Override
    public Set<NomorTelepon> saveTelepon(Long userId, NomorTelepon nomorTelepon) {
        User user = userRepo.findByUserId(userId);
        nomorTelepon.setUser(user);
        user.getNomorTelepon().add(nomorTelepon);
        userRepo.save(user);
        return nomorTeleponRepo.findByUserUserId(userId);
    }

    @Override
    public Optional<NomorTelepon> getTelepon(Long teleponId) {
        log.info("Fetching address {}", teleponId);
        return nomorTeleponRepo.findById(teleponId);
    }

    @Override
    public Set<NomorTelepon> getAllTelepon(Long userId) {
        log.info("Fetching user {}'s number(s)");
        return nomorTeleponRepo.findByUserUserId(userId);
    }

    @Override
    public void deleteAllTelepon(Long userId) {
        log.info("Deleting user {}'s numbers", userId);
        nomorTeleponRepo.deleteByUserUserId(userId);
    }

    @Override
    public void deleteTelepon(Long teleponId) {
        log.info("Deleting phone number {}", teleponId);
        nomorTeleponRepo.deleteById(teleponId);
    }

    @Override
    public boolean isExist(Long teleponId) {
        return nomorTeleponRepo.existsById(teleponId);
    }
}
