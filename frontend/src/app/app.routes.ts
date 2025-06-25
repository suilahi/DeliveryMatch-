import { Routes } from '@angular/router';
import { LoginComponent } from './components/login/login.component';
import { RegisterComponent } from './components/register/register.component';
import { ConducteurComponent } from './components/conducteur/conducteur.component';
import { AuthGuard } from './guards/auth.guard';
import {CreateComponent} from './components/conducteur/create/create.component';
import {ModifierComponent} from './components/conducteur/modifier/modifier.component';
import {ExpediteurComponent} from './components/expediteur/expediteur.component';

export const routes: Routes = [
  { path: '', redirectTo: '/login', pathMatch: 'full' },
  { path: 'login', component: LoginComponent },
  { path: 'register', component: RegisterComponent },
  { path: 'modifier',
    component: ModifierComponent,
    canActivate: [AuthGuard],
    data: { role: 'CONDUCTEUR' }
  },

  {
    path: 'conducteur',
    component: ConducteurComponent,
    canActivate: [AuthGuard],
    data: { role: 'CONDUCTEUR' }
  },
  {
    path: 'conducteur/dashboard',
    component: ConducteurComponent,
    canActivate: [AuthGuard],
    data: { role: 'CONDUCTEUR' }
  },
  {
    path: 'create',
    component: CreateComponent,
    canActivate: [AuthGuard],
    data: { role: 'CONDUCTEUR' }
  },
  {
    path: 'expediteur',
    component: ExpediteurComponent,
    canActivate: [AuthGuard],
    data: { role: 'EXPEDITEUR' }
  },
  { path: '**', redirectTo: '/login' }
];
