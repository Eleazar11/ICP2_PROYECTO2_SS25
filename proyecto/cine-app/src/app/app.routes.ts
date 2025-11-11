import { Routes } from '@angular/router';
import { HomeComponent } from './components/home/home.component';
import { RegistroComponent } from './components/registro/registro.component';
import { LoginComponent } from './components/login/login.component';
import { AdminSistemaComponent } from './components/admin-sistema/admin-sistema.component';
import { AdminCineComponent } from './components/admin-cine/admin-cine.component';
import { ComunComponent } from './components/comun/comun.component';
import { AnuncianteComponent } from './components/anunciante/anunciante.component';

export const routes: Routes = [
  { path: 'home', component: HomeComponent },
  { path: 'registro', component: RegistroComponent },
  { path: 'login', component: LoginComponent },
  { path: 'admin-sistema', component: AdminSistemaComponent },
  { path: 'admin-cine', component: AdminCineComponent },
  { path: 'comun', component: ComunComponent },
  { path: 'anunciante', component: AnuncianteComponent },
  { path: '', redirectTo: '/home', pathMatch: 'full' }
];
