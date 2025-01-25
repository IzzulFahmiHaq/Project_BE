package com.example.Project_BE.Project_BE.service;

import com.example.Project_BE.Project_BE.DTO.TokoDTO;
import com.example.Project_BE.Project_BE.model.Toko;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface TokoService {
    List<Toko> getAllToko();

    List<Toko> getAllByAdmin(Long idAdmin);

    Optional<Toko> getTokoById(Long id);

    TokoDTO tambahTokoDTO(Long idAdmin, TokoDTO tokoDTO);

    TokoDTO editTokoDTO(Long id, Long idAdmin, String tokoJson, MultipartFile file) throws IOException;

    void deleteToko(Long id) throws IOException;

    TokoDTO editTokoDTO(Long id, Long idAdmin, TokoDTO tokoDTO);

    List<TokoDTO> getAllTokoDTO();
}