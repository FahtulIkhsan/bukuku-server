package com.kelompok7.bukuku.buku.foto;

import com.kelompok7.bukuku.buku.Buku;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface FotoService {
    Foto saveFoto(MultipartFile foto, Buku buku) throws IOException;
    byte[] getFoto(Long fotoId) throws IOException;
    void deleteFoto(Long fotoId) throws IOException;
    List<Map<String, Long>> getFotoBuku(Long bukuId);
    boolean isExist(Long fotoId);
}
