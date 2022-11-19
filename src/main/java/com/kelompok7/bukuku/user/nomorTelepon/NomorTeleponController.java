package com.kelompok7.bukuku.user.nomorTelepon;

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
public class NomorTeleponController {
    @Autowired
    private final UserService userService;
    @Autowired
    private final NomorTeleponService nomorTeleponService;

    @GetMapping("/telepon/{id}")
    public ResponseEntity<NomorTelepon> getTelepon(@PathVariable Long teleponId){
        Optional<NomorTelepon> nomorTelepon = nomorTeleponService.getTelepon(teleponId);

        if(nomorTeleponService.isExist(teleponId)){
            return ResponseEntity.ok(nomorTelepon.get());
        }else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{userId}/telepon")
    public ResponseEntity<Set<NomorTelepon>> getUserTelepon(@PathVariable Long userId){
        if(userService.isExist(userId)){
            Optional<Set<NomorTelepon>> nomorTelepons = Optional.ofNullable(nomorTeleponService.getAllTelepon(userId));
            return ResponseEntity.ok(nomorTelepons.get());
        }else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/{userId}/telepon")
    public ResponseEntity<Set<NomorTelepon>> saveTelepon(@PathVariable Long userId, @RequestBody NomorTelepon nomorTelepon){
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("user/{userId}/telepon").toUriString());
        return ResponseEntity.created(uri).body(nomorTeleponService.saveTelepon(userId, nomorTelepon));
    }

    @DeleteMapping("/telepon/{teleponId}")
    public ResponseEntity<HttpStatus> deleteTelepon(@PathVariable Long teleponId){
        if(!nomorTeleponService.isExist(teleponId)){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        try{
            nomorTeleponService.deleteTelepon(teleponId);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
