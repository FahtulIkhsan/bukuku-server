package com.kelompok7.bukuku.buku;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.kelompok7.bukuku.buku.foto.Foto;
import com.kelompok7.bukuku.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

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
    @JsonManagedReference
    @OneToMany(mappedBy = "buku", cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<Foto> foto;
    @NotNull
    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;
}
