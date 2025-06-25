import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ExpediteurService, Annonce, DemandeTransport, Colis } from '../../services/expediteur.service';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { UserService } from '../../services/user.service';

@Component({
  selector: 'app-expediteur',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './expediteur.component.html',
  styleUrls: ['./expediteur.component.css']
})
export class ExpediteurComponent implements OnInit {
  annonces: Annonce[] = [];
  loading = false;
  error: string | null = null;
  showFormAnnonceId: number | null = null;
  demandeForm: FormGroup;
  demandeSuccess: string | null = null;
  demandeError: string | null = null;

  constructor(
    private expediteurService: ExpediteurService,
    private fb: FormBuilder,
    private userService: UserService
  ) {
    this.demandeForm = this.fb.group({
      description: ['', Validators.required],
      poids: [0, [Validators.required, Validators.min(0.1)]],
      longueur: [0, [Validators.required, Validators.min(1)]],
      largeur: [0, [Validators.required, Validators.min(1)]],
      hauteur: [0, [Validators.required, Validators.min(1)]]
    });
  }

  ngOnInit(): void {
    this.loading = true;
    this.expediteurService.getAllAnnonces().subscribe({
      next: (annonces) => {
        this.annonces = annonces;
        this.loading = false;
      },
      error: (err) => {
        this.error = 'Erreur lors du chargement des annonces';
        this.loading = false;
      }
    });
  }

  ouvrirFormulaire(annonceId: number) {
    this.showFormAnnonceId = annonceId;
    this.demandeForm.reset();
    this.demandeSuccess = null;
    this.demandeError = null;
  }

  annulerFormulaire() {
    this.showFormAnnonceId = null;
    this.demandeSuccess = null;
    this.demandeError = null;
  }

  envoyerDemande(annonce: Annonce) {
    if (this.demandeForm.invalid) return;
    const expediteurId = this.userService.getUserId();
    if (!expediteurId) {
      this.demandeError = 'Utilisateur non connecté';
      return;
    }
    const colis: Colis = this.demandeForm.value;
    const demande: DemandeTransport = { colis };
    this.expediteurService.envoyerDemande(expediteurId, annonce.id, demande).subscribe({
      next: () => {
        this.demandeSuccess = 'Demande envoyée avec succès !';
        this.showFormAnnonceId = null;
      },
      error: () => {
        this.demandeError = 'Erreur lors de l’envoi de la demande.';
      }
    });
  }
} 