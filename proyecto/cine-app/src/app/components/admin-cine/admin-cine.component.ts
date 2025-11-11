import { Component } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-admin-cine',
  imports: [],
  templateUrl: './admin-cine.component.html',
  styleUrl: './admin-cine.component.css'
})
export class AdminCineComponent {
 constructor(private router: Router) {}

  cerrarSesion() {
    // Elimnar token del localStorage:
    localStorage.removeItem('token');

    // Redirigimos al home
    this.router.navigate(['/home']);
  }
}
