import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { UsuariosService } from '../../services/usuarios/usuarios.service';
import { jwtDecode } from 'jwt-decode';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {

  loginData = { correo: '', contrasena: '' };
  errorMensaje: string | null = null;

  constructor(private usuariosService: UsuariosService, private router: Router) {}

  onLogin() {
    this.usuariosService.loginUsuario(this.loginData).subscribe({
      next: (token) => {
        const decoded: any = jwtDecode(token);
        const rol = decoded.rol; // asegúrate de que tu backend lo incluya así

        console.log(' Usuario logueado con rol:', rol);

        switch (rol) {
          case 'ADMIN_SISTEMA':
            this.router.navigate(['/admin-sistema']);
            break;
          case 'ADMIN_CINE':
            this.router.navigate(['/admin-cine']);
            break;
          case 'CLIENTE':
            this.router.navigate(['/comun']);
            break;
          case 'ANUNCIANTE':
            this.router.navigate(['/anunciante']);
            break;
          default:
            this.router.navigate(['/home']);
            break;
        }
      },
      error: (err) => {
        console.error(' Error al iniciar sesión:', err);
        this.errorMensaje = 'Credenciales inválidas o error en el servidor.';
      }
    });
  }
  volverHome() {
    this.router.navigate(['/home']);
  }
}
