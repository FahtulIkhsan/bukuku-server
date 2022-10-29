package com.kelompok7.bukuku.transaksi;

import com.kelompok7.bukuku.transaksi.item.Item;
import com.kelompok7.bukuku.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

import static javax.persistence.GenerationType.AUTO;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RiwayatTransaksi {
    @Id @GeneratedValue(strategy = AUTO)
    private Long transaksiId;
    @ManyToOne
    @JoinColumn(name = "buyerId", referencedColumnName = "userId")
    private User buyer;
    @ManyToOne
    @JoinColumn(name = "sellerId", referencedColumnName = "userId")
    private User seller;
    @NotNull
    private String tipeTransaksi;
    @NotNull
    private LocalDate tanggalTransaksi;
    @OneToMany(mappedBy = "riwayat")
    private List<Item> item;
}
