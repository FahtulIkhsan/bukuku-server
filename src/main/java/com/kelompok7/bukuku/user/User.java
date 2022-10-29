package com.kelompok7.bukuku.user;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.kelompok7.bukuku.buku.Buku;
import com.kelompok7.bukuku.transaksi.RiwayatTransaksi;
import com.kelompok7.bukuku.user.alamat.Alamat;
import com.kelompok7.bukuku.user.nomorTelepon.NomorTelepon;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static javax.persistence.GenerationType.AUTO;

@Entity @Data @NoArgsConstructor @AllArgsConstructor
public class User {
    @Id @GeneratedValue(strategy = AUTO)
    private Long userId;
    @NotNull
    @Column(unique = true)
    private String username;
    @NotNull
    @Column(unique = true)
    private String email;
    @NotNull
    private String password;
    private String role;
    private String NIK;
    private String name;
    private String tempatLahir;
    private LocalDate tanggalLahir;
    @JsonManagedReference
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @ToString.Exclude
    private Set<Alamat> alamat = new HashSet<>();
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private Set<NomorTelepon> nomorTelepon;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Buku> buku;
}
