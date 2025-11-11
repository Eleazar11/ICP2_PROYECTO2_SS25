import { Component } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-anunciante',
  imports: [],
  templateUrl: './anunciante.component.html',
  styleUrl: './anunciante.component.css'
})
export class AnuncianteComponent {

    constructor(private router: Router) {}

  cerrarSesion() {
    // Elimnar token del localStorage:
    localStorage.removeItem('token');

    // Redirigimos al home
    this.router.navigate(['/home']);
  }
}
