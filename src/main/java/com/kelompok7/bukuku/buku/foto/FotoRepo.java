package com.kelompok7.bukuku.buku.foto;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface FotoRepo extends JpaRepository<Foto, Long> {
    Optional<Foto> findByFotoId(Long fotoId);
    @Query("SELECT f.fotoId FROM Foto f JOIN f.buku b WHERE b.bukuId = ?1")
    List<Long> findByBukuBukuId(Long bukuId);
    boolean existsByFotoId(Long fotoId);
}
