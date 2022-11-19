package com.kelompok7.bukuku.buku;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BukuRepo extends JpaRepository<Buku, Long> {
    Buku findByBukuId(Long bukuId);
    List<Buku> findByUserUserId(Long userId);
}
