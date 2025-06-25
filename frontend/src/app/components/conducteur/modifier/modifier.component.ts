import { Component } from '@angular/core';
import {FormBuilder, FormGroup, ReactiveFormsModule, Validators} from '@angular/forms';
import {CommonModule} from '@angular/common';
import {Annonce, ConducteurService} from '../../../services/conducteur.service';
import {ActivatedRoute, Router, RouterLink} from '@angular/router';
import { UserService } from '../../../services/user.service';

@Component({
  selector: 'app-modifier',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, RouterLink],
  templateUrl: './modifier.component.html',
  styleUrl: './modifier.component.css'
})
export class ModifierComponent {
  form!: FormGroup;
  annonceId!: number;
  loading = false;
  error: string | null = null;
  success: string | null = null;

  constructor(
    private route: ActivatedRoute,
    private fb: FormBuilder,
    private router: Router,
    private conducteurService: ConducteurService,
    private userService: UserService
  ) {}

  ngOnInit(): void {
    this.annonceId = +this.route.snapshot.queryParamMap.get('annonceId')!;
    this.initForm();
    this.loadAnnonce();
  }

  initForm() {
    this.form = this.fb.group({
      lieuDepart: ['', Validators.required],
      destination: ['', Validators.required],
      etapes: [''],
      typeMarchandise: ['', Validators.required],
      capaciteDisponible: [0, [Validators.required, Validators.min(1)]],
      dimensionsMax: [0, [Validators.required, Validators.min(1)]],
    });
  }

  loadAnnonce() {
    this.loading = true;
    const conducteurId = this.userService.getUserId();
    if (!conducteurId) {
      this.error = 'Utilisateur non connecté';
      this.loading = false;
      return;
    }
    this.conducteurService.getAnnoncesParConducteur(conducteurId).subscribe({
      next: annonces => {
        const annonce = annonces.find(a => a.id === this.annonceId);
        if (annonce) {
          this.form.patchValue(annonce);
        } else {
          this.error = 'Annonce introuvable';
        }
        this.loading = false;
      },
      error: err => {
        this.error = 'Erreur lors du chargement de l’annonce';
        this.loading = false;
      }
    });
  }

  onSubmit() {
    if (this.form.invalid) return;
    this.loading = true;
    const updatedAnnonce: Annonce = {id: this.annonceId, ...this.form.value};

    this.conducteurService.updateAnnonce(this.annonceId, updatedAnnonce).subscribe({
      next: () => {
        this.success = 'Annonce mise à jour avec succès';
        this.loading = false;
        setTimeout(() => this.router.navigate(['/conducteur']), 1500);
      },
      error: err => {
        this.error = 'Erreur lors de la modification';
        this.loading = false;
      }
    });
  }
}
