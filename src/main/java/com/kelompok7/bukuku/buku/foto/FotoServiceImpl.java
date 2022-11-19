package com.kelompok7.bukuku.buku.foto;

import com.kelompok7.bukuku.buku.Buku;
import com.kelompok7.bukuku.buku.BukuRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class FotoServiceImpl implements FotoService{
    @Autowired
    private BukuRepo bukuRepo;
    @Autowired
    private FotoRepo fotoRepo;

    private final String PATH = new File("./images").getAbsolutePath();

    @Override
    public Foto saveFoto(MultipartFile photo, Buku buku) throws IOException {
        log.info("{}", photo.getContentType());
        Foto foto = new Foto();
        foto.setBuku(buku);
        fotoRepo.save(foto);
        String folderPath = String.format("%s/%s", PATH, buku.getBukuId());
        folderPath = folderPath.replace("./", "");
        String fullPath = String.format("%s/%s", folderPath, foto.getFotoId());
        foto.setDirectory(fullPath);
        fotoRepo.save(foto);
        log.info("Saving photo to {}", foto.getDirectory());
        try{
            Files.createDirectories(Paths.get(folderPath));
            photo.transferTo(new File(fullPath));
            return foto;
        }catch (Exception e){
            throw e;
        }
    }

    @Override
    public byte[] getFoto(Long fotoId) throws IOException {
        Optional<Foto> photo = fotoRepo.findByFotoId(fotoId);
        if(photo.isEmpty()){
            return null;
        }
        String fotoPath = photo.get().getDirectory();
        try{
            return Files.readAllBytes(new File(fotoPath).toPath());
        }catch (Exception e){
            throw e;
        }
    }

    @Override
    public void deleteFoto(Long fotoId) throws IOException {
        Foto foto = fotoRepo.findByFotoId(fotoId).get();
        try{
            File file = new File(foto.getDirectory());
            Files.deleteIfExists(file.toPath());
            fotoRepo.deleteById(fotoId);
        }catch (Exception e){
            throw e;
        }
    }

    @Override
    public List<Map<String, Long>> getFotoBuku(Long bukuId) {
        List<Long> id = fotoRepo.findByBukuBukuId(bukuId);
        List<Map<String, Long>> map = new ArrayList<Map<String, Long>>();
        for(int i = 0; i < id.size(); i++){
            Map<String, Long> temp = new HashMap<String, Long>();
            temp.put("fotoId", id.get(i));
            map.add(temp);
        }
        return map;
    }

    @Override
    public boolean isExist(Long fotoId) {
        return fotoRepo.existsById(fotoId);
    }
}
