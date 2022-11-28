package com.kelompok7.bukuku.buku.foto;

import com.kelompok7.bukuku.buku.Buku;
import com.kelompok7.bukuku.buku.BukuService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/buku")
@RequiredArgsConstructor
public class FotoController {
    @Autowired
    private final BukuService bukuService;
    @Autowired
    private final FotoService fotoService;

    @PostMapping("/{bukuId}/foto")
    public ResponseEntity<Foto> saveFoto(@PathVariable Long bukuId, @RequestParam("foto")MultipartFile foto)
            throws IOException{
        if(!Objects.equals(foto.getContentType(), "image/png")){
            return new ResponseEntity<>(HttpStatus.UNSUPPORTED_MEDIA_TYPE);
        }
        Optional<Buku> buku = Optional.ofNullable(bukuService.getBuku(bukuId));
        if(buku.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        try{
            Foto photo = fotoService.saveFoto(foto, buku.get());
            return new ResponseEntity<>(photo, HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/foto/{fotoId}")
    public ResponseEntity<byte[]> getFoto(@PathVariable Long fotoId){
        try {
            Optional<byte []> foto = Optional.ofNullable(fotoService.getFoto(fotoId));
            if(foto.isEmpty()){
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.IMAGE_PNG).body(foto.get());
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/foto/{fotoId}")
    public ResponseEntity<HttpStatus> deleteFoto(@PathVariable Long fotoId){
        if(!fotoService.isExist(fotoId)){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        try{
            fotoService.deleteFoto(fotoId);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{bukuId}/foto")
    public ResponseEntity<List<Map<String, Long>>> getFotoBuku(@PathVariable Long bukuId){
        if(!bukuService.isExist(bukuId)){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        List<Map<String, Long>> foto = fotoService.getFotoBuku(bukuId);
        return ResponseEntity.ok(foto);
    }
}
