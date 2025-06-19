package org.DeliveryMatch.backend.Dto;

import org.DeliveryMatch.backend.Model.Utilisateur;
import org.DeliveryMatch.backend.Repository.UtilisateurRepository;
import org.DeliveryMatch.backend.Security.JwtService;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.server.ResponseStatusException;

public class LoginRequest {
    private final UtilisateurRepository repository;

    public LoginRequest(UtilisateurRepository repository, PasswordEncoder passwordEncoder, JwtService jwtService, AuthenticationManager authenticationManager) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;


    public AuthResponse register(RegisterRequest request) {
        if (repository.findByEmail(request.getEmail()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cet email existe déjà");
        }

        Utilisateur utilisateur = new Utilisateur();


        utilisateur.setNom(request.getNom());
        utilisateur.setEmail(request.getEmail());
        utilisateur.setPassword(passwordEncoder.encode(request.getPassword()));
        utilisateur.setRole(request.getRole().toString());

        utilisateur.setEstVerifie(false);

        repository.save(utilisateur);

        var jwtToken = jwtService.generateToken(utilisateur);

        AuthResponse response = new AuthResponse();
        response.setToken(jwtToken);
        response.setRole(utilisateur.getRole());
        return response;
    }


    public AuthResponse authenticate(AuthRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        var utilisateur = repository.findByEmail(request.getEmail())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Utilisateur non trouvé"));

        var jwtToken = jwtService.generateToken(utilisateur);

        AuthResponse response = new AuthResponse();
        response.setToken(jwtToken);
        response.setRole(utilisateur.getRole());
        return response;
    }
}
