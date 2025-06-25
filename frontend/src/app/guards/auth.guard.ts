import { Injectable } from '@angular/core';
import { CanActivate, Router, ActivatedRouteSnapshot, RouterStateSnapshot } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class AuthGuard implements CanActivate {
  
  constructor(private router: Router) {}

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean {
    const token = localStorage.getItem('token');
    const userRole = localStorage.getItem('userRole');

    // Si pas de token, rediriger vers login
    if (!token) {
      this.router.navigate(['/login']);
      return false;
    }

    // Vérifier si la route nécessite un rôle spécifique
    const requiredRole = route.data['role'];
    if (requiredRole) {
      if (this.hasRequiredRole(userRole, requiredRole)) {
        return true;
      } else {
        // Rediriger vers le dashboard approprié selon le rôle actuel
        this.redirectToAppropriateDashboard(userRole);
        return false;
      }
    }

    return true;
  }

  private hasRequiredRole(userRole: string | null, requiredRole: string | string[]): boolean {
    if (!userRole) return false;
    
    if (Array.isArray(requiredRole)) {
      return requiredRole.some(role => 
        userRole.toUpperCase() === role.toUpperCase() || 
        userRole.toUpperCase() === `ROLE_${role.toUpperCase()}`
      );
    }
    
    return userRole.toUpperCase() === requiredRole.toUpperCase() || 
           userRole.toUpperCase() === `ROLE_${requiredRole.toUpperCase()}`;
  }

  private redirectToAppropriateDashboard(userRole: string | null): void {
    switch (userRole?.toUpperCase()) {
      case 'ROLE_CONDUCTEUR':
      case 'CONDUCTEUR':
        this.router.navigate(['/conducteur/dashboard']);
        break;
      case 'ROLE_EXPEDITEUR':
      case 'EXPEDITEUR':
        this.router.navigate(['/expediteur/dashboard']);
        break;
      case 'ROLE_ADMIN':
      case 'ADMIN':
        this.router.navigate(['/admin/dashboard']);
        break;
      default:
        this.router.navigate(['/']);
        break;
    }
  }
} 