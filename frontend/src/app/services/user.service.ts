import { Injectable } from '@angular/core';
import { JwtService } from './jwt.service';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private jwtService: JwtService) { }

  getUserId(): number | null {
    // Essayer d'abord depuis le localStorage, puis depuis le token
    const userId = localStorage.getItem('userId');
    if (userId) {
      return parseInt(userId, 10);
    }
    return this.jwtService.getUserIdFromToken();
  }

  getUserRole(): string | null {
    // Essayer d'abord depuis le localStorage, puis depuis le token
    const role = localStorage.getItem('userRole');
    if (role) {
      return role;
    }
    return this.jwtService.getUserRoleFromToken();
  }

  getUserNom(): string | null {
    // Essayer d'abord depuis le localStorage, puis depuis le token
    const nom = localStorage.getItem('userNom');
    if (nom) {
      return nom;
    }
    return this.jwtService.getUserNomFromToken();
  }

  getToken(): string | null {
    return localStorage.getItem('token');
  }

  isLoggedIn(): boolean {
    const token = this.getToken();
    if (!token) return false;
    
    // Vérifier si le token n'est pas expiré
    return !this.jwtService.isTokenExpired();
  }

  getUserInfo(): { id: number | null; nom: string | null; role: string | null; email: string | null } {
    // Utiliser directement le JwtService pour obtenir les informations depuis le token
    return this.jwtService.getUserInfoFromToken();
  }

  logout(): void {
    localStorage.removeItem('token');
    localStorage.removeItem('userRole');
    localStorage.removeItem('userId');
    localStorage.removeItem('userNom');
  }

  // Méthode pour forcer la mise à jour des informations depuis le token
  refreshUserInfo(): void {
    const userInfo = this.jwtService.getUserInfoFromToken();
    
    // Mettre à jour le localStorage avec les informations du token
    if (userInfo.id) {
      localStorage.setItem('userId', userInfo.id.toString());
    }
    if (userInfo.nom) {
      localStorage.setItem('userNom', userInfo.nom);
    }
    if (userInfo.role) {
      localStorage.setItem('userRole', userInfo.role);
    }
  }
} 