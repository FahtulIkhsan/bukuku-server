package com.kelompok7.bukuku.user.alamat;

import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.Set;

public interface AlamatRepo extends JpaRepository<Alamat, Long> {
    Set<Alamat> findByUserUserId(Long userId);
    boolean existsByAlamatId(Long alamatId);
    @Transactional
    void deleteByUserUserId(Long userId);


}
