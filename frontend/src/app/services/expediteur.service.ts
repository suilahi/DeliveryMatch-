import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface Colis {
  description: string;
  poids: number;
  longueur: number;
  largeur: number;
  hauteur: number;
}

export interface DemandeTransport {
  id?: number;
  statut?: string;
  annonceId?: number;
  expediteurId?: number;
  colis: Colis;
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
export class ExpediteurService {
  private apiUrl = 'http://localhost:8080/expediteur';

  constructor(private http: HttpClient) {
  }

  getAllAnnonces(): Observable<Annonce[]> {
    return this.http.get<Annonce[]>(`${this.apiUrl}/annonces`);
  }

  envoyerDemande(expediteurId: number, annonceId: number, demande: DemandeTransport): Observable<DemandeTransport> {
    const token = localStorage.getItem('token');
    const headers = new HttpHeaders({ 'Authorization': `Bearer ${token}` });
    return this.http.post<DemandeTransport>(
      `${this.apiUrl}/Add?expediteurId=${expediteurId}&annonceId=${annonceId}`,
      demande,
      { headers }
    );
  }
}
