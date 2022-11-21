package com.kelompok7.bukuku.user.nomorTelepon;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.kelompok7.bukuku.user.User;
import com.kelompok7.bukuku.user.alamat.Alamat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.GenerationType.AUTO;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NomorTelepon {
    @Id @GeneratedValue(strategy = AUTO)
    private Long teleponId;
    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;
    private String nomorTelepon;

    @Override
    public boolean equals(Object other){
        if(!(other instanceof Alamat)){
            return false;
        }
        return nomorTelepon.equals(((NomorTelepon)other).nomorTelepon);
    }

    @Override
    public int hashCode(){
        return nomorTelepon.hashCode();
    }
}
