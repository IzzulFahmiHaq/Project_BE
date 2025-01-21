package com.example.Project_BE.Project_BE.model;

import javax.persistence.*;

@Entity
@Table(name = "toko")
public class Toko {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nama_makanan")
    private String namaMakanan;

    @Column(name = "harga")
    private Double harga;

    @ManyToOne
    @JoinColumn(name = "id_admin", nullable = false)
    private Admin admin;

    @Column(name = "image")
    private String image;  // Menyimpan path atau URL gambar

    // Konstruktor default
    public Toko() {
    }

    // Konstruktor dengan parameter
    public Toko(Long id, String namaMakanan, Double harga, Admin admin, String image) {
        this.id = id;
        this.namaMakanan = namaMakanan;
        this.harga = harga;
        this.admin = admin;
        this.image = image;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNamaMakanan() {
        return namaMakanan;
    }

    public void setNamaMakanan(String namaMakanan) {
        this.namaMakanan = namaMakanan;
    }

    public Double getHarga() {
        return harga;
    }

    public void setHarga(Double harga) {
        this.harga = harga;
    }

    public Admin getAdmin() {
        return admin;
    }

    public void setAdmin(Admin admin) {
        this.admin = admin;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
