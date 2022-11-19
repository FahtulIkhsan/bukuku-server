package com.kelompok7.bukuku.user.alamat;

import com.kelompok7.bukuku.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class AlamatController {
    @Autowired
    private final UserService userService;
    @Autowired
    private final AlamatService alamatService;

    @GetMapping("/alamat/{id}")
    public ResponseEntity<Alamat> getAddress(@PathVariable Long id){
        Optional<Alamat> alamat = Optional.ofNullable(alamatService.getAlamat(id));

        if(alamat.isPresent()){
            return new ResponseEntity<>(alamat.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{userId}/alamat")
    public ResponseEntity<Set<Alamat>> getUserAddress(@PathVariable Long userId){
        if(userService.isExist(userId)){
            Optional<Set<Alamat>> alamat = Optional.ofNullable(alamatService.getAllAlamat(userId));
            return new ResponseEntity<>(alamat.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/{userId}/alamat")
    public ResponseEntity<Set<Alamat>> saveAlamat(@PathVariable Long userId, @RequestBody Alamat alamat){
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("user/{userId}/alamat").toUriString());
        return ResponseEntity.created(uri).body(alamatService.saveAlamat(userId, alamat));
    }

    @DeleteMapping("/alamat/{alamatId}")
    public ResponseEntity<HttpStatus> deleteAlamat(@PathVariable Long alamatId){
        if(!alamatService.isExist(alamatId)){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        try{
            alamatService.deleteAlamat(alamatId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch(Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
