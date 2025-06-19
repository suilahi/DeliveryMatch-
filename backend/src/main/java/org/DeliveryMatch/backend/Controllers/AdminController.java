package org.DeliveryMatch.backend.Controllers;

import org.DeliveryMatch.backend.Model.Annonce;
import org.DeliveryMatch.backend.Services.AdminService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
@CrossOrigin
public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping("/annonces")
    public ResponseEntity<List<Annonce>> getAllAnnonces() {
        List<Annonce> annonces = adminService.getAnnonces();
        return ResponseEntity.ok(annonces);
    }

    @DeleteMapping("/annonces/{id}")
    public ResponseEntity<?> deleteAnnonce(@PathVariable Long id) {
        adminService.deleteAnnonce(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/annonces/{id}")
    public ResponseEntity<Annonce> updateAnnonce(@PathVariable Long id, @RequestBody Annonce annonce) {
        Annonce updated = adminService.updateAnnonce(id, annonce);
        return ResponseEntity.ok(updated);
    }
}
