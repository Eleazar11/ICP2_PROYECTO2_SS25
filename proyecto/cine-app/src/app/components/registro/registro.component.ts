import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { UsuariosService } from '../../services/usuarios/usuarios.service';
import { Router } from '@angular/router';
import { Usuario } from '../../models/Usuario';

@Component({
  selector: 'app-registro',
  standalone: true,
  imports: [FormsModule, CommonModule],
  templateUrl: './registro.component.html',
  styleUrls: ['./registro.component.css']
})
export class RegistroComponent {

  mensaje: string | null = null;
  errorMensaje: string | null = null;

  constructor(
    private usuariosService: UsuariosService,
    private router: Router
  ) {}

  onRegister(form: any) {
    if (form.valid) {
      const formValue = form.value;

      // Armamos el objeto Usuario según el modelo y backend
      const usuario: Usuario = {
        nombreUsuario: formValue.nombreUsuario,
        correo: formValue.correo,
        numeroTelefono: formValue.numeroTelefono,
        contrasena: formValue.contrasena
      };

      console.log('📤 Enviando usuario al backend:', usuario);

      this.usuariosService.registrarUsuario(usuario).subscribe({
        next: (response) => {
          console.log('✅ Usuario registrado:', response);
          this.mensaje = response.message || 'Registro exitoso';
          this.errorMensaje = null;

          // Redirigir a la página principal después de 2 segundos
          setTimeout(() => {
            this.router.navigate(['/home']);
          }, 2000);
        },
        error: (err) => {
          console.error('❌ Error al registrar usuario:', err);
          this.errorMensaje = err.error?.error || 'Ocurrió un error en el registro';
          this.mensaje = null;
        }
      });
    } else {
      this.errorMensaje = 'Por favor, complete todos los campos.';
    }
  }

  navigateTo(ruta: string) {
    this.router.navigate([ruta]);
  }
}
