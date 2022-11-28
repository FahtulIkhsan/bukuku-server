package com.kelompok7.bukuku.buku;

import java.util.List;

public interface BukuService {
    List<Buku> saveBuku(Long userId, Buku buku);
    Buku getBuku(Long bukuId);
    void deleteBuku(Long bukuId);
    List<Buku> getBuku();
    List<Buku> getBukuUser(Long userId);
    boolean isExist(Long bukuId);
}
