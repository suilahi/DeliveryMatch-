import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import {Router, RouterLink} from '@angular/router';
import { UserService } from '../../services/user.service';
import { ConducteurService, Annonce } from '../../services/conducteur.service';

@Component({
  selector: 'app-conducteur',
  templateUrl: './conducteur.component.html',
  standalone: true,
  imports: [CommonModule,RouterLink]
})
export class ConducteurComponent implements OnInit {
  userInfo: { id: number | null; nom: string | null; role: string | null };
  annonces: Annonce[] = [];
  loading = false;
  error: string | null = null;

  constructor(
    private router: Router,
    private userService: UserService,
    private conducteurService: ConducteurService
  ) {
    this.userInfo = this.userService.getUserInfo();
    console.log('Informations utilisateur conducteur:', this.userInfo);
  }

  ngOnInit() {
    // Charger automatiquement les annonces au démarrage
    if (this.userInfo.id) {
      this.chargerAnnonces();
    }
  }

  chargerAnnonces() {
    if (!this.userInfo.id) {
      this.error = 'ID utilisateur non disponible';
      return;
    }

    this.loading = true;
    this.error = null;

    this.conducteurService.getAnnoncesParConducteur(this.userInfo.id).subscribe({
      next: (annonces) => {
        this.annonces = annonces;
        this.loading = false;
        console.log('Annonces récupérées:', annonces);
      },
      error: (err) => {
        this.loading = false;
        this.error = 'Erreur lors du chargement des annonces: ' + (err.error?.message || err.message || 'Erreur inconnue');
        console.error('Erreur lors du chargement des annonces:', err);
      }
    });
  }

  voirDemandes(annonceId: number) {
    // Navigation vers la page des demandes pour cette annonce
    this.router.navigate(['/conducteur/demandes'], { queryParams: { annonceId: annonceId } });
  }

  modifierAnnonce(annonceId: number) {
    if (!annonceId) return;
    this.router.navigate(['/modifier'], {
      queryParams: { annonceId }
    });
  }


  logout() {
    this.userService.logout();
    this.router.navigate(['/login']);
  }
}
