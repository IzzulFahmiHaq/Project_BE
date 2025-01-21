package com.example.Project_BE.Project_BE.service;

import com.example.Project_BE.Project_BE.DTO.TokoDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface TokoService {
    List<com.example.Project_BE.Project_BE.model.Toko> getAllToko();

    List<com.example.Project_BE.Project_BE.model.Toko> getAllByAdmin(Long idAdmin);

    Optional<com.example.Project_BE.Project_BE.model.Toko> getTokoById(Long id);

    TokoDTO tambahTokoDTO(Long idAdmin, TokoDTO dataDTO);

    TokoDTO editTokoDTO(Long id, Long idAdmin, TokoDTO tokoDTO) throws IOException;

    void deleteToko(Long id) throws IOException;

    // Tambahkan metode baru untuk mendapatkan semua toko dalam bentuk TokoDTO
    List<TokoDTO> getAllTokoDTO();

    // Tambahkan deklarasi metode uploadFoto
    String uploadFoto(MultipartFile file) throws IOException;
}