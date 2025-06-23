package org.DeliveryMatch.backend.Security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String SECRET_KEY;

    @Value("${jwt.expiration-ms:86400000}") // 24h par défaut
    private long jwtExpirationMs;

    // Génération du token avec id et rôle dans les claims
    public String generateToken(org.DeliveryMatch.backend.Model.Utilisateur utilisateur) {
        Map<String, Object> extraClaims = new HashMap<>();

        // Ajouter le rôle (sans préfixe ROLE_)
        String role = utilisateur.getRole();
        if (role.startsWith("ROLE_")) {
            role = role.substring(5);
        }
        extraClaims.put("role", role);

        // Ajouter l'id utilisateur dans les claims
        extraClaims.put("id", utilisateur.getId());

        return Jwts.builder()
                .setClaims(extraClaims)
                .setSubject(utilisateur.getEmail()) // sujet = email
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationMs))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String extractUserEmail(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(String token, java.util.function.Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claims != null ? claimsResolver.apply(claims) : null;
    }

    private Claims extractAllClaims(String token) {
        try {
            return Jwts.parser()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            // Loger erreur si besoin
            return null;
        }
    }

    private SecretKey getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public boolean isTokenValid(String token, org.springframework.security.core.userdetails.UserDetails userDetails) {
        final String username = extractUserEmail(token);
        return (username != null && username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        Date expiration = extractClaim(token, Claims::getExpiration);
        return expiration != null && expiration.before(new Date());
    }

    // Méthode utile pour extraire id ou role dans le token
    public String extractRole(String token) {
        Claims claims = extractAllClaims(token);
        if (claims == null) return null;
        return claims.get("role", String.class);
    }

    public Integer extractUserId(String token) {
        Claims claims = extractAllClaims(token);
        if (claims == null) return null;
        return claims.get("id", Integer.class);  // Assure-toi que l'id est un Integer
    }
}
