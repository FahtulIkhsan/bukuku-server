package com.kelompok7.bukuku.user.alamat;

import com.kelompok7.bukuku.user.User;
import com.kelompok7.bukuku.user.UserRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Set;

@Service @RequiredArgsConstructor @Transactional @Slf4j
public class AlamatServiceImpl implements AlamatService{
    @Autowired
    private final UserRepo userRepo;
    @Autowired
    private final AlamatRepo alamatRepo;

    @Override
    public Set<Alamat> saveAlamat(Long userId, Alamat alamatRequest) {
        log.info("Saving new address {} to the database", alamatRequest.getAlamat());
        User user = userRepo.findByUserId(userId);
        alamatRequest.setUser(user);
        user.getAlamat().add(alamatRequest);
        userRepo.saveAndFlush(user);
        return alamatRepo.findByUserUserId(userId);
    }

    @Override
    public Alamat getAlamat(Long id) {
        log.info("Fetching address {}", id);
        return alamatRepo.findById(id).get();
    }

    @Override
    public Set<Alamat> getAllAlamat(Long userId) {
        log.info("Fetching user {}", userId);
        return alamatRepo.findByUserUserId(userId);
    }

    @Override
    public void deleteAllAlamat(Long userId) {
        log.info("Deleting address {}", userId);
        alamatRepo.deleteByUserUserId(userId);
    }

    @Override
    public void deleteAlamat(Long alamatId) {
        log.info("Deleting address {}", alamatId);
        alamatRepo.deleteById(alamatId);
    }

    @Override
    public boolean isExist(Long alamatId) {
        return alamatRepo.existsByAlamatId(alamatId);
    }
}
