package com.kelompok7.bukuku.user.nomorTelepon;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.kelompok7.bukuku.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NomorTelepon {
    @Id
    private Long id;
    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;
    private String nomorTelepon;
}
