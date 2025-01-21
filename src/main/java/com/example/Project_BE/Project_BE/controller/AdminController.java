package com.example.Project_BE.Project_BE.controller;

import com.example.Project_BE.Project_BE.DTO.PasswordDTO;
import com.example.Project_BE.Project_BE.exception.CommonResponse;
import com.example.Project_BE.Project_BE.exception.ResponseHelper;
import com.example.Project_BE.Project_BE.model.Admin;
import com.example.Project_BE.Project_BE.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin(origins = "*")
public class AdminController {

    @Autowired
    private AdminService adminService;  // Menyesuaikan service untuk Admin

    // Endpoint untuk mendaftar admin
    @PostMapping("/register")
    public ResponseEntity<Admin> registerAdmin(@RequestBody Admin admin) {
        Admin registeredAdmin = adminService.registerAdmin(admin);  // Mendaftar admin baru
        return new ResponseEntity<>(registeredAdmin, HttpStatus.CREATED);
    }

    // Endpoint untuk mendapatkan admin berdasarkan ID
    @GetMapping("/admin/{id}")
    public ResponseEntity<Admin> getAdminById(@PathVariable Long id) {
        Admin admin = adminService.getById(id);  // Mengambil admin berdasarkan ID
        return new ResponseEntity<>(admin, HttpStatus.OK);
    }

    // Endpoint untuk mendapatkan semua admin
    @GetMapping("/admins")
    public ResponseEntity<List<Admin>> getAllAdmins() {
        List<Admin> admins = adminService.getAll();  // Mengambil semua admin
        return new ResponseEntity<>(admins, HttpStatus.OK);
    }

    // Endpoint untuk mengedit data admin
    @PutMapping("/admin/edit/{id}")
    public ResponseEntity<Admin> updateAdmin(@PathVariable Long id, @RequestBody Admin admin) {
        Admin updatedAdmin = adminService.edit(id, admin);  // Mengedit data admin berdasarkan ID
        return new ResponseEntity<>(updatedAdmin, HttpStatus.OK);
    }

    // Endpoint untuk mengupdate password admin
    @PutMapping("/admin/edit-password/{id}")
    public CommonResponse<Admin> updateAdminPassword(@RequestBody PasswordDTO password, @PathVariable Long id) {
        return ResponseHelper.ok(adminService.putPasswordAdmin(password, id));  // Mengupdate password admin
    }

    // Endpoint untuk menghapus admin
    @DeleteMapping("/admin/delete/{id}")
    public ResponseEntity<Map<String, Boolean>> deleteAdmin(@PathVariable Long id) {
        Map<String, Boolean> response = adminService.delete(id);  // Menghapus admin berdasarkan ID
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
