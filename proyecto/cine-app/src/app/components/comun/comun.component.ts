import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-comun',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './comun.component.html',
  styleUrls: ['./comun.component.css']
})
export class ComunComponent {

  constructor(private router: Router) {}

  cerrarSesion() {
    // Elimnar token del localStorage:
    localStorage.removeItem('token');

    // Redirigimos al home
    this.router.navigate(['/home']);
  }

}
