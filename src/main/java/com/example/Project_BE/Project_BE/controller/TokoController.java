package com.example.Project_BE.Project_BE.controller;

import com.example.Project_BE.Project_BE.DTO.TokoDTO;
import com.example.Project_BE.Project_BE.model.Toko;
import com.example.Project_BE.Project_BE.service.TokoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/admin")  // Endpoint untuk admin
public class TokoController {

    private final TokoService tokoService;

    // Menambahkan direktori tempat menyimpan gambar
    private static final String IMAGE_DIRECTORY = "src/main/resources/static/images"; // Ganti dengan path yang sesuai

    public TokoController(TokoService tokoService) {
        this.tokoService = tokoService;
    }

    // Endpoint untuk mendapatkan semua toko dalam bentuk Toko
    @GetMapping("/toko/all")
    public ResponseEntity<List<Toko>> getAllToko() {
        List<Toko> tokoList = tokoService.getAllToko();
        return ResponseEntity.ok(tokoList);
    }

    // Endpoint untuk mendapatkan semua toko berdasarkan ID Admin
    @GetMapping("/toko/getAllByAdmin/{idAdmin}")
    public ResponseEntity<List<Toko>> getAllByAdmin(@PathVariable Long idAdmin) {
        List<Toko> tokoList = tokoService.getAllByAdmin(idAdmin);
        return ResponseEntity.ok(tokoList);
    }

    // Endpoint untuk mendapatkan toko berdasarkan ID
    @GetMapping("/toko/getById/{id}")
    public ResponseEntity<Toko> getTokoById(@PathVariable Long id) {
        Optional<Toko> toko = tokoService.getTokoById(id);
        return toko.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Endpoint untuk menambah toko
    @PostMapping("/toko/tambah/{idAdmin}")
    public ResponseEntity<TokoDTO> tambahToko(
            @PathVariable Long idAdmin,
            @RequestBody TokoDTO tokoDTO) {
        TokoDTO savedTokoDTO = tokoService.tambahTokoDTO(idAdmin, tokoDTO);
        return ResponseEntity.ok(savedTokoDTO);
    }

    // Endpoint untuk mengedit toko
    @PutMapping(value = "/toko/editById/{id}")
    public ResponseEntity<TokoDTO> editToko(
            @PathVariable Long id,
            @RequestParam Long idAdmin,
            @RequestPart(value = "toko") TokoDTO tokoDTO) throws IOException {
        TokoDTO updatedToko = tokoService.editTokoDTO(id, idAdmin, tokoDTO);
        return ResponseEntity.ok(updatedToko);
    }

    // Endpoint untuk menghapus toko
    @DeleteMapping("/toko/delete/{id}")
    public ResponseEntity<Void> deleteToko(@PathVariable Long id) throws IOException {
        tokoService.deleteToko(id);
        return ResponseEntity.noContent().build();
    }

    // Endpoint untuk mengupload gambar
    @PostMapping("/toko/uploadFoto")
    public ResponseEntity<String> uploadFoto(@RequestParam("file") MultipartFile file) {
        try {
            // Buat nama file unik menggunakan UUID
            String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
            Path path = Paths.get(IMAGE_DIRECTORY, fileName);

            // Simpan file ke direktori yang telah ditentukan
            Files.copy(file.getInputStream(), path);

            // URL untuk mengakses gambar yang telah diupload
            String imageUrl = "http://localhost:8080/api/admin/toko/foto/" + fileName;

            return ResponseEntity.ok(imageUrl);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Gagal mengunggah gambar");
        }
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
