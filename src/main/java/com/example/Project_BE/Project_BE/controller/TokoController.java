package com.example.Project_BE.Project_BE.controller;

import com.example.Project_BE.Project_BE.DTO.TokoDTO;
import com.example.Project_BE.Project_BE.model.Toko;
import com.example.Project_BE.Project_BE.service.TokoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/admin")
public class TokoController {

    private final TokoService tokoService;

    public TokoController(TokoService tokoService) {
        this.tokoService = tokoService;
    }

    @GetMapping("/toko/all")
    public ResponseEntity<List<Toko>> getAllToko() {
        List<Toko> tokoList = tokoService.getAllToko();
        return ResponseEntity.ok(tokoList);
    }

    @GetMapping("/toko/getAllByAdmin/{idAdmin}")
    public ResponseEntity<List<Toko>> getAllByAdmin(@PathVariable Long idAdmin) {
        List<Toko> tokoList = tokoService.getAllByAdmin(idAdmin);
        return ResponseEntity.ok(tokoList);
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
        TokoDTO savedToko = tokoService.tambahTokoDTO(idAdmin, tokoDTO);
        return ResponseEntity.ok(savedToko);
    }

    @PutMapping("/toko/edit/{id}/{idAdmin}")
    public ResponseEntity<TokoDTO> editToko(
            @PathVariable Long id,
            @PathVariable Long idAdmin,
            @RequestParam("toko") String tokoJson,
            @RequestParam(value = "file", required = false) MultipartFile file) throws IOException {
        TokoDTO updatedToko = tokoService.editTokoDTO(id, idAdmin, tokoJson, file);
        return ResponseEntity.ok(updatedToko);
    }

    @DeleteMapping("/toko/delete/{id}")
    public ResponseEntity<Void> deleteToko(@PathVariable Long id) throws IOException {
        tokoService.deleteToko(id);
        return ResponseEntity.noContent().build();
    }
}
