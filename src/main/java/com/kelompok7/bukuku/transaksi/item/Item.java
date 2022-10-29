package com.kelompok7.bukuku.transaksi.item;

import com.kelompok7.bukuku.transaksi.RiwayatTransaksi;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.GenerationType.AUTO;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Item {
    @Id @GeneratedValue(strategy = AUTO)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "transaksiId")
    private RiwayatTransaksi riwayat;
    private Long bookId;
    private Long harga;
    private Long jumlah;
}
