package org.DeliveryMatch.backend.Controllers;

import org.DeliveryMatch.backend.Dto.AuthRequest;
import org.DeliveryMatch.backend.Dto.AuthResponse;
import org.DeliveryMatch.backend.Dto.RegisterRequest;
import org.DeliveryMatch.backend.Services.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("auth")
public class AuthController {

    private final AuthService service;

    public AuthController(AuthService authService) {
        this.service = authService;
    }


    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(
            @RequestBody RegisterRequest request
    ) {
        return ResponseEntity.ok(service.register(request));

    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthResponse> register(
            @RequestBody AuthRequest request
    ) {
        return ResponseEntity.ok(service.authenticate(request));
    }
}