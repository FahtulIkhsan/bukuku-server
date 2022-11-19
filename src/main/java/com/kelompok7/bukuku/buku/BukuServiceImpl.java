package com.kelompok7.bukuku.buku;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class BukuServiceImpl implements BukuService{
    @Autowired
    private final BukuRepo bukuRepo;

    @Override
    public Buku saveBuku(Buku buku) {
        log.info("Saving new book {} to the database", buku.getJudul());
        return bukuRepo.save(buku);
    }

    @Override
    public Buku getBuku(Long bukuId) {
        log.info("Fetching book {}", bukuId);
        return bukuRepo.findByBukuId(bukuId);
    }

    @Override
    public void deleteBuku(Long bukuId) {
        log.info("Deleting book {}", bukuId);
        bukuRepo.deleteById(bukuId);
    }

    @Override
    public List<Buku> getBuku() {
        log.info("Fetching all books");
        return bukuRepo.findAll();
    }

    @Override
    public List<Buku> getBukuUser(Long userId) {
        log.info("Fetching {}'s books", userId);
        return bukuRepo.findByUserUserId(userId);
    }

    @Override
    public boolean isExist(Long bukuId) {
        return bukuRepo.existsById(bukuId);
    }
}
