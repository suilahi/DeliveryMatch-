import { Component } from '@angular/core';
import { ReactiveFormsModule, FormBuilder, Validators, FormGroup } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { Authservice } from '../../services/authservice.service';
import { UserService } from '../../services/user.service';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {
  loginForm: FormGroup;
  error: string = '';
  loading: boolean = false;

  constructor(
      private fb: FormBuilder,
      private authService: Authservice,
      private router: Router,
      private userService: UserService
  ) {
    this.loginForm = this.fb.group({
      email: ['', [Validators.required, Validators.email]],
      password: ['', Validators.required],
    });
  }

  onSubmit(): void {
    if (this.loginForm.valid) {
      this.loading = true;
      this.error = '';

      const credentials = {
        email: this.loginForm.value.email,
        password: this.loginForm.value.password
      };

      this.authService.login(credentials).subscribe({
        next: (response: any) => {
          this.loading = false;

          // Stocker le token
          localStorage.setItem('token', response.token);

          this.userService.refreshUserInfo();

          const userInfo = this.userService.getUserInfo();

          console.log('Utilisateur connecté depuis le token:', userInfo);

          // Stocker le rôle utilisateur
          if (userInfo.role) {
            localStorage.setItem('userRole', userInfo.role);
          }

          // Redirection selon le rôle
          this.redirectBasedOnRole(userInfo.role);
        },
        error: (error: any) => {
          this.loading = false;
          this.error = 'Email ou mot de passe incorrect';
          console.error('Erreur de connexion:', error);
        }
      });
    }
  }

  private redirectBasedOnRole(role: string | null): void {
    if (!role) {
      console.warn('Aucun rôle trouvé');
      this.router.navigate(['/']);
      return;
    }

    switch (role.toUpperCase()) {
      case 'ROLE_CONDUCTEUR':
      case 'CONDUCTEUR':
        this.router.navigate(['/conducteur/dashboard']);
        break;
      case 'ROLE_EXPEDITEUR':
      case 'EXPEDITEUR':
        this.router.navigate(['/expediteur']);
        break;
      case 'ROLE_ADMIN':
      case 'ADMIN':
        this.router.navigate(['/admin/dashboard']);
        break;
      default:
        console.warn('Rôle non reconnu:', role);
        this.router.navigate(['/']);
        break;
    }
  }
}
