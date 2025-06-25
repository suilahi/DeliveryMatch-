import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class JwtService {

  constructor() { }

  // Décoder le token JWT (base64)
  private decodeToken(token: string): any {
    try {
      const base64Url = token.split('.')[1];
      const base64 = base64Url.replace(/-/g, '+').replace(/_/g, '/');
      const jsonPayload = decodeURIComponent(atob(base64).split('').map(function(c) {
        return '%' + ('00' + c.charCodeAt(0).toString(16)).slice(-2);
      }).join(''));
      return JSON.parse(jsonPayload);
    } catch (error) {
      console.error('Erreur lors du décodage du token:', error);
      return null;
    }
  }


  // Extraire l'ID utilisateur depuis le token
  getUserIdFromToken(): number | null {
    const token = localStorage.getItem('token');
    if (!token) return null;

    const decoded = this.decodeToken(token);
    if (decoded && decoded.id) {
      return Number(decoded.id);
    }
    return null;
  }

  // Extraire le nom utilisateur depuis le token
  getUserNomFromToken(): string | null {
    const token = localStorage.getItem('token');
    if (!token) return null;

    const decoded = this.decodeToken(token);
    if (decoded && decoded.nom) {
      return decoded.nom;
    }
    return null;
  }

  // Extraire le rôle utilisateur depuis le token
  getUserRoleFromToken(): string | null {
    const token = localStorage.getItem('token');
    if (!token) return null;

    const decoded = this.decodeToken(token);
    if (decoded && decoded.role) {
      return decoded.role;
    }
    return null;
  }

  // Extraire l'email utilisateur depuis le token
  getUserEmailFromToken(): string | null {
    const token = localStorage.getItem('token');
    if (!token) return null;

    const decoded = this.decodeToken(token);
    if (decoded && decoded.sub) {
      return decoded.sub;
    }
    return null;
  }

  // Vérifier si le token est expiré
  isTokenExpired(): boolean {
    const token = localStorage.getItem('token');
    if (!token) return true;

    const decoded = this.decodeToken(token);
    if (decoded && decoded.exp) {
      const currentTime = Date.now() / 1000;
      return decoded.exp < currentTime;
    }
    return true;
  }

  // Obtenir toutes les informations utilisateur depuis le token
  getUserInfoFromToken(): { id: number | null; nom: string | null; role: string | null; email: string | null } {
    return {
      id: this.getUserIdFromToken(),
      nom: this.getUserNomFromToken(),
      role: this.getUserRoleFromToken(),
      email: this.getUserEmailFromToken()
    };
  }
}
