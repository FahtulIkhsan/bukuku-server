package com.kelompok7.bukuku.buku.foto;

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
    @ManyToOne
    @JoinColumn(name = "bookId")
    private Buku buku;
    @NotNull
    @Column(unique = true)
    private String directory;
}
