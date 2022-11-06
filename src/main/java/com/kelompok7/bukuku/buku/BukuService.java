package com.kelompok7.bukuku.buku;

import java.util.List;

public interface BukuService {
    Buku saveBuku(Buku buku);
    Buku getBuku(Long bukuId);
    void deleteBuku(Long bukuId);
    List<Buku> getBuku();
    List<Buku> getBukuUser(Long userId);
}
