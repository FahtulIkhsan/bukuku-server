package com.kelompok7.bukuku.buku;

import com.kelompok7.bukuku.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/buku")
@RequiredArgsConstructor
public class BukuController {
    @Autowired
    private final BukuService bukuService;
    @Autowired
    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<Buku>> getBuku() {
        return ResponseEntity.ok().body(bukuService.getBuku());
    }

    @GetMapping("/{bookId}")
    public ResponseEntity<Buku> getBuku(@PathVariable Long bukuId) {
        Optional<Buku> buku = Optional.ofNullable(bukuService.getBuku(bukuId));

        if(buku.isPresent()){
            return new ResponseEntity<>(buku.get(), HttpStatus.OK);
        } else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("user/{userId}")
    public ResponseEntity<List<Buku>> getBukuUser(@PathVariable Long userId) {
        boolean exist = userService.isExist(userId);
        if(!exist){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        List<Buku> listBuku = bukuService.getBukuUser(userId);
        return new ResponseEntity<>(listBuku, HttpStatus.OK);
    }

    @PostMapping("/save")
    public ResponseEntity<Buku> saveBuku(@RequestBody Buku buku) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("buku/save").toUriString());
        return ResponseEntity.created(uri).body(bukuService.saveBuku(buku));
    }

    public ResponseEntity<HttpStatus> deleteBuku(@PathVariable Long bukuId) {
        try{
            bukuService.deleteBuku(bukuId);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
