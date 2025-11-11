import { Component } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-admin-sistema',
  imports: [],
  templateUrl: './admin-sistema.component.html',
  styleUrl: './admin-sistema.component.css'
})
export class AdminSistemaComponent {
 constructor(private router: Router) {}

  cerrarSesion() {
    // Elimnar token del localStorage:
    localStorage.removeItem('token');

    // Redirigimos al home
    this.router.navigate(['/home']);
  }
}
