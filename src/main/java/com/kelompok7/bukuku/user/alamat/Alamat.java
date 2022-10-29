package com.kelompok7.bukuku.user.alamat;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.kelompok7.bukuku.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.GenerationType.AUTO;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Alamat {
    @Id @GeneratedValue(strategy = AUTO)
    private Long id;
    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;
    private String alamat;

    @Override
    public boolean equals(Object other){
        if(!(other instanceof Alamat)){
            return false;
        }
        return alamat.equals(((Alamat)other).alamat);
    }

    @Override
    public int hashCode(){
        return alamat.hashCode();
    }
}