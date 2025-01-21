package com.example.Project_BE.Project_BE.DTO;

import com.example.Project_BE.Project_BE.model.Toko;

public class TokoDTO {
    private Long id;           // Toko ID
    private Long idAdmin;      // Admin (User) ID
    private String namaMakanan; // Nama makanan
    private Double harga;      // Harga makanan

    // Konstruktor tanpa parameter
    public TokoDTO() {}

    // Konstruktor dengan parameter Toko untuk mengubah Toko ke TokoDTO
    public TokoDTO(Toko toko) {
        this.id = toko.getId();
        this.idAdmin = toko.getAdmin().getId(); // Mengambil ID Admin dari objek Toko
        this.namaMakanan = toko.getNamaMakanan();
        this.harga = toko.getHarga();
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdAdmin() {
        return idAdmin;
    }

    public void setIdAdmin(Long idAdmin) {
        this.idAdmin = idAdmin;
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
}
