package org.DeliveryMatch.backend.Security;

import org.DeliveryMatch.backend.Model.Utilisateur;
import org.DeliveryMatch.backend.Repository.UtilisateurRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class AdminInitializer {
    @Bean
    CommandLineRunner createAdmin(UtilisateurRepository userRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            if (userRepository.findByEmail("suilahizakaria@gmail.com").isEmpty()) {
                Utilisateur admin = new Utilisateur();
                admin.setNom("Admin");
                admin.setEmail("suilahizakaria@gmail.com");
                admin.setPassword(passwordEncoder.encode("azerty123"));
                admin.setRole("ROLE_ADMIN");

                userRepository.save(admin);
                System.out.println("✅ Admin user created: suilahizakaria@gmail.com / azerty123");
            } else {
                System.out.println("ℹ️ Admin user already exists");
            }
        };
    }
}
