package com.kelompok7.bukuku.buku.foto;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kelompok7.bukuku.buku.Buku;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import static javax.persistence.GenerationType.AUTO;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Foto {
    @Id @GeneratedValue(strategy = AUTO)
    private Long fotoId;
    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "bukuId")
    private Buku buku;
    @JsonIgnore
    @Column(unique = true)
    private String directory;
}
