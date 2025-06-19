package org.DeliveryMatch.backend.Services;

import org.DeliveryMatch.backend.Dto.AuthRequest;
import org.DeliveryMatch.backend.Dto.AuthResponse;
import org.DeliveryMatch.backend.Dto.RegisterRequest;
import org.DeliveryMatch.backend.Model.Admin;
import org.DeliveryMatch.backend.Model.Conducteur;
import org.DeliveryMatch.backend.Model.Expediteur;
import org.DeliveryMatch.backend.Model.Utilisateur;
import org.DeliveryMatch.backend.Repository.UtilisateurRepository;
import org.DeliveryMatch.backend.Security.JwtService;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
@Service
public class AuthService {

    private final UtilisateurRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager manager;

    public AuthService(UtilisateurRepository repository, PasswordEncoder passwordEncoder, JwtService jwtService, AuthenticationManager manager) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.manager = manager;
    }

    public AuthResponse register(RegisterRequest request) {
        if (repository.findByEmail(request.getEmail()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email already exists");
        }

        Utilisateur utilisateur;

        switch (request.getRole().toLowerCase()) { // tolérance casse
            case "admin":
                utilisateur = new Admin();
                break;
            case "conducteur":
                utilisateur = new Conducteur();
                break;
            case "expediteur":
                utilisateur = new Expediteur();
                break;
            default:
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Rôle non supporté : " + request.getRole());
        }

        utilisateur.setNom(request.getNom());
        utilisateur.setEmail(request.getEmail());
        utilisateur.setPassword(passwordEncoder.encode(request.getPassword()));

        // Stocker le rôle sans "ROLE_" prefix
        utilisateur.setRole(request.getRole().toUpperCase());

        repository.save(utilisateur);

        String jwtToken = jwtService.generateToken(utilisateur);

        AuthResponse response = new AuthResponse();
        response.setToken(jwtToken);
        response.setRole(utilisateur.getRole());
        return response;
    }

    public AuthResponse authenticate(AuthRequest request) {
        manager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        Utilisateur user = repository.findByEmail(request.getEmail())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Utilisateur non trouvé"));

        String jwtToken = jwtService.generateToken(user);

        AuthResponse response = new AuthResponse();
        response.setToken(jwtToken);
        response.setRole(user.getRole());

        return response;
    }
}
