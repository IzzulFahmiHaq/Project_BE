package com.example.Project_BE.Project_BE.controller;

import com.example.Project_BE.Project_BE.DTO.TokoDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.Project_BE.Project_BE.model.Toko;
import com.example.Project_BE.Project_BE.service.TokoService;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/admin")
public class TokoController {

    private final TokoService tokoService;

    // Menambahkan direktori tempat menyimpan gambar
    private static final String IMAGE_DIRECTORY = "/path/to/your/images"; // Ganti dengan path yang sesuai

    public TokoController(TokoService tokoService, TokoService tokoService1) {
        this.tokoService = tokoService1;

    }

    @GetMapping("/toko/all")
    public ResponseEntity<List<Toko>> getAllToko() {
        List<Toko> tokoList = tokoService.getAllToko();
        return ResponseEntity.ok(tokoList);
    }

    @GetMapping("/toko/getAllByAdmin/{idAdmin}")
    public ResponseEntity<List<Toko>> getAllByAdmin(@PathVariable Long idAdmin) {
        List<Toko> tokoList = tokoService.getAllByAdmin(idAdmin);
        return ResponseEntity.ok(tokoList); // Pastikan data ini memiliki namaMakanan dan harga
    }


    @GetMapping("/toko/getById/{id}")
    public ResponseEntity<Toko> getTokoById(@PathVariable Long id) {
        Optional<Toko> toko = tokoService.getTokoById(id);
        return toko.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/toko/tambah/{idAdmin}")
    public ResponseEntity<TokoDTO> tambahToko(
            @PathVariable Long idAdmin,
            @RequestBody TokoDTO tokoDTO) {
        TokoDTO savedTokoDTO = tokoService.tambahTokoDTO(idAdmin, tokoDTO);
        return ResponseEntity.ok(savedTokoDTO);
    }

    @PutMapping(value = "/toko/editById/{id}")
    public ResponseEntity<TokoDTO> editToko(
            @PathVariable Long id,
            @RequestParam Long idAdmin,
            @RequestPart(value = "toko") TokoDTO tokoDTO) throws IOException {

        TokoDTO updatedToko = tokoService.editTokoDTO(id, idAdmin, tokoDTO);
        return ResponseEntity.ok(updatedToko);
    }

    @DeleteMapping("/toko/delete/{id}")
    public ResponseEntity<Void> deleteToko(@PathVariable Long id) throws IOException {
        tokoService.deleteToko(id);
        return ResponseEntity.noContent().build();
    }

    // Endpoint untuk menampilkan foto berdasarkan URL
    @GetMapping(value = "/toko/foto/{fotoUrl}", produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<byte[]> getFoto(@PathVariable String fotoUrl) {
        try {
            // Tentukan lokasi file berdasarkan URL foto yang disimpan di database
            Path imagePath = Paths.get(IMAGE_DIRECTORY, fotoUrl);
            byte[] imageBytes = Files.readAllBytes(imagePath);

            // Mengembalikan gambar dengan status OK dan header Content-Type untuk gambar
            return ResponseEntity.ok(imageBytes);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
}