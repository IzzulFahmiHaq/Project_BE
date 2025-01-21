package com.example.Project_BE.Project_BE.impl;

import com.example.Project_BE.Project_BE.DTO.TokoDTO;
import com.example.Project_BE.Project_BE.model.Admin;
import com.example.Project_BE.Project_BE.model.Toko;
import com.example.Project_BE.Project_BE.service.TokoService;
import com.example.Project_BE.Project_BE.repository.TokoRepository;
import com.example.Project_BE.Project_BE.exception.NotFoundException;
import com.example.Project_BE.Project_BE.repository.AdminRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TokoImpl implements TokoService {

    private static final String BASE_URL = "https://s3.lynk2.co/api/s3"; // Base URL untuk upload foto

    private final TokoRepository tokoRepository;
    private final AdminRepository adminRepository;

    public TokoImpl(TokoRepository tokoRepository, AdminRepository adminRepository) {
        this.tokoRepository = tokoRepository;
        this.adminRepository = adminRepository;
    }

    @Override
    public List<Toko> getAllToko() {
        return tokoRepository.findAll();
    }

    @Override
    public List<Toko> getAllByAdmin(Long idAdmin) {
        return tokoRepository.findByAdminId(idAdmin); // Query toko berdasarkan ID admin
    }

    @Override
    public Optional<Toko> getTokoById(Long id) {
        return tokoRepository.findById(id); // Mengambil toko berdasarkan ID
    }

    @Override
    public TokoDTO tambahTokoDTO(Long idAdmin, TokoDTO tokoDTO) {
        // Menemukan admin berdasarkan ID
        Admin admin = adminRepository.findById(idAdmin)
                .orElseThrow(() -> new NotFoundException("Admin with ID " + idAdmin + " not found"));

        // Membuat dan mengisi data Toko berdasarkan data dari TokoDTO
        Toko toko = new Toko();
        toko.setAdmin(admin);  // Menetapkan admin untuk toko
        toko.setNamaMakanan(tokoDTO.getNamaMakanan());
        toko.setHarga(tokoDTO.getHarga()); // Menambahkan harga

        // Menyimpan Toko ke dalam database
        Toko savedData = tokoRepository.save(toko);

        // Menyiapkan DTO untuk hasil
        TokoDTO result = new TokoDTO();
        result.setId(savedData.getId());
        result.setIdAdmin(admin.getId());
        result.setNamaMakanan(savedData.getNamaMakanan());
        result.setHarga(savedData.getHarga());  // Menambahkan harga pada DTO

        return result;
    }

    @Override
    public TokoDTO editTokoDTO(Long id, Long idAdmin, TokoDTO tokoDTO) {
        // Mengambil data toko yang ada berdasarkan ID
        Toko existingData = tokoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Toko with ID " + id + " not found"));

        // Menemukan admin berdasarkan ID
        Admin admin = adminRepository.findById(idAdmin)
                .orElseThrow(() -> new NotFoundException("Admin with ID " + idAdmin + " not found"));

        // Memperbarui data toko dengan data baru dari DTO
        existingData.setAdmin(admin);
        existingData.setNamaMakanan(tokoDTO.getNamaMakanan());
        existingData.setHarga(tokoDTO.getHarga());

        // Menyimpan perubahan pada toko
        Toko updatedData = tokoRepository.save(existingData);

        // Menyiapkan DTO untuk hasil
        TokoDTO result = new TokoDTO();
        result.setId(updatedData.getId());
        result.setIdAdmin(admin.getId());
        result.setNamaMakanan(updatedData.getNamaMakanan());
        result.setHarga(updatedData.getHarga());

        return result;
    }

    @Override
    public void deleteToko(Long id) {
        tokoRepository.deleteById(id);  // Menghapus toko berdasarkan ID
    }

    @Override
    public List<TokoDTO> getAllTokoDTO() {
        // Mengambil semua toko dan mengonversinya menjadi DTO
        List<Toko> tokoList = tokoRepository.findAll();
        return tokoList.stream()
                .map(toko -> new TokoDTO(toko))  // Mengonversi Toko ke TokoDTO
                .collect(Collectors.toList());
    }

    @Override
    public String uploadFoto(MultipartFile file) throws IOException {
        // Menggunakan RestTemplate untuk mengirim file ke layanan penyimpanan
        RestTemplate restTemplate = new RestTemplate();

        // Menyiapkan header dan body untuk request multipart
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("file", file.getResource());

        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

        // Mengirim request ke server untuk upload foto
        ResponseEntity<String> response = restTemplate.exchange(BASE_URL, HttpMethod.POST, requestEntity, String.class);

        // Ekstraksi URL file dari response
        return extractFileUrlFromResponse(response.getBody());
    }

    // Metode untuk mengekstrak URL file dari response JSON (asumsi response dalam format JSON)
    private String extractFileUrlFromResponse(String responseBody) {
        // Misalnya, jika response adalah JSON: { "url": "http://example.com/path/to/file" }
        // Anda bisa menggunakan pustaka seperti Jackson untuk parsing JSON, berikut adalah contoh ekstraksi
        // Logika ekstraksi (gunakan pustaka JSON untuk parsing jika diperlukan)

        // Sebagai contoh, kita menganggap responseBody berformat: {"url": "http://example.com/file.jpg"}
        String fileUrl = responseBody;  // Gantilah ini dengan parsing JSON sebenarnya jika perlu
        return fileUrl;  // Gantilah dengan logika ekstraksi yang benar
    }
}
