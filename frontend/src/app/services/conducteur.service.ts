import { Injectable, Inject, PLATFORM_ID } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { isPlatformBrowser } from '@angular/common';

export interface DemandeTransport {
  id: number;
  statut: string;
}

export interface Annonce {
  id: number;
  lieuDepart: string;
  destination: string;
  etapes?: string;
  typeMarchandise: string;
  capaciteDisponible: number;
  dimensionsMax: number;
}

@Injectable({
  providedIn: 'root'
})
export class ConducteurService {
  private apiUrl = 'http://localhost:8080/api';

  constructor(
    private http: HttpClient,
    @Inject(PLATFORM_ID) private platformId: Object
  ) {}

  private getHeaders(): HttpHeaders {
    let token = '';
    if (isPlatformBrowser(this.platformId)) {
      token = localStorage.getItem('token') ?? '';
    }
    console.log('Token utilisé pour l\'authentification:', token ? 'Token présent' : 'Token manquant');
    return new HttpHeaders({
      'Authorization': `Bearer ${token}`,
      'Content-Type': 'application/json'
    });
  }

  publierAnnonce(conducteurId: number, annonce: Annonce): Observable<Annonce> {
    return this.http.post<Annonce>(`${this.apiUrl}/Add/${conducteurId}`, annonce, {
      headers: this.getHeaders()
    });
  }

  getAnnoncesParConducteur(conducteurId: number): Observable<Annonce[]> {
    return this.http.get<Annonce[]>(`${this.apiUrl}/conducteurs/${conducteurId}/annonces`, {
      headers: this.getHeaders()
    });
  }

  updateAnnonce(annonceId: number, annonce: Annonce): Observable<Annonce> {
    return this.http.put<Annonce>(`${this.apiUrl}/annonces/${annonceId}`, annonce, {
      headers: this.getHeaders()
    });
  }

}

