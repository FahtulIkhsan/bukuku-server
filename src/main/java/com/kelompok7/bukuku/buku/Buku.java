package com.kelompok7.bukuku.buku;

import com.kelompok7.bukuku.buku.foto.Foto;
import com.kelompok7.bukuku.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import java.util.List;

import static javax.persistence.GenerationType.AUTO;

@Entity @Data @NoArgsConstructor @AllArgsConstructor
public class Buku {
    @Id @GeneratedValue(strategy = AUTO)
    private Long bukuId;
    @NotNull
    private String judul;
    @NotNull
    private Long harga;
    private String ISBN;
    private String pengarang;
    private String penerbit;
    private String sinopsis;
    @OneToMany(mappedBy = "buku")
    private List<Foto> foto;
    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;
}
