import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import {Router, RouterLink, RouterModule} from '@angular/router';
import { UserService } from '../../../services/user.service';
import { ConducteurService, Annonce } from '../../../services/conducteur.service';

@Component({
  selector: 'app-create',
  templateUrl: './create.component.html',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule,RouterModule]
})
export class CreateComponent implements OnInit {
  annonceForm!: FormGroup;
  formSubmitted = false;
  conducteurId!: number;

  constructor(
    private fb: FormBuilder,
    private conducteurService: ConducteurService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.conducteurId = Number(localStorage.getItem('userId')); // Assure-toi que l'ID est stocké après login
    this.annonceForm = this.fb.group({
      lieuDepart: ['', [Validators.required, Validators.minLength(2)]],
      destination: ['', [Validators.required, Validators.minLength(2)]],
      etapes: [''],
      typeMarchandise: ['', Validators.required],
      capaciteDisponible: [0, [Validators.required, Validators.min(1)]],
      dimensionsMax: [0, [Validators.required, Validators.min(1)]]
    });
  }

  onSubmit(): void {
    this.formSubmitted = true;

    if (this.annonceForm.invalid) return;

    const annonce: Annonce = this.annonceForm.value;
    this.conducteurService.publierAnnonce(this.conducteurId, annonce).subscribe({
      next: (res) => {
        console.log('Annonce publiée avec succès', res);
        this.router.navigate(['/conducteur']);
      },
      error: (err) => console.error('Erreur lors de la publication', err)
    });
  }

  get f() {
    return this.annonceForm.controls;
  }
}
