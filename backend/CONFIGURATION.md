# Configuration Guide

## Environment Variables

Pour l'environnement de production, configurez les variables d'environnement suivantes :

### Database Configuration
```bash
DB_URL=jdbc:mysql://localhost:3306/transport_app?useSSL=true&allowPublicKeyRetrieval=true&serverTimezone=UTC
DB_USERNAME=root
DB_PASSWORD=your_secure_password_here
```

### JWT Configuration
```bash
JWT_SECRET=your-super-secret-jwt-key-here-change-in-production-environment
JWT_EXPIRATION=86400000
```

### Security Configuration
```bash
ADMIN_USERNAME=admin
ADMIN_PASSWORD=your_secure_admin_password_here
```

### CORS Configuration
```bash
CORS_ALLOWED_ORIGINS=http://localhost:3000,https://yourdomain.com
```

## Profiles

### Development Profile (default)
- Utilise `application.properties`
- Mode Hibernate : `update`
- Logging détaillé activé
- CORS ouvert pour le développement

### Production Profile
- Utilise `application-prod.properties`
- Mode Hibernate : `validate`
- Logging minimal
- CORS restreint
- Variables d'environnement requises

## Activation du Profile de Production

```bash
# Via Maven
mvn spring-boot:run -Dspring-boot.run.profiles=prod

# Via JAR
java -jar backend.jar --spring.profiles.active=prod

# Via variable d'environnement
export SPRING_PROFILES_ACTIVE=prod
```

## Sécurité

⚠️ **IMPORTANT** : 
- Changez TOUTES les valeurs par défaut en production
- Utilisez des mots de passe forts
- Générez une clé JWT sécurisée (au moins 256 bits)
- Configurez SSL/TLS pour la base de données
- Restreignez les origines CORS aux domaines autorisés 