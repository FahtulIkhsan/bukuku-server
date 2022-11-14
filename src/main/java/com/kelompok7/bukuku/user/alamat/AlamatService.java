package com.kelompok7.bukuku.user.alamat;

import java.util.Set;

public interface AlamatService {
    Set<Alamat> saveAlamat(Long userId, Alamat alamat);
    Alamat getAlamat(Long id);
    Set<Alamat> getAllAlamat(Long userId);
    void deleteAllAlamat(Long userId);
    void deleteAlamat(Long alamatId);
    boolean isExist(Long alamatId);
}
