import { Component } from '@angular/core';
import { RouterModule } from '@angular/router';
import { CommonModule, NgIf } from '@angular/common';
import { UserService } from '../../services/user.service';

@Component({
  selector: 'app-navbar',
  standalone: true,
  imports: [CommonModule, RouterModule, NgIf],
  templateUrl: './navbar.component.html',
})
export class NavbarComponent {
  isLoggedIn = false;

  constructor(private userService: UserService) {
    this.isLoggedIn = this.userService.isLoggedIn();
  }

  logout(): void {
    this.userService.logout();
    window.location.href = '/login';
  }

  isLogged(): boolean {
    return this.userService.isLoggedIn();
  }
}
