import { TipoUsuario } from "./TipoUsuario";

export interface Usuario {
  idUsuario?: number;           // opcional porque cuado creamos no existe
  nombreUsuario: string;
  correo: string;
  numeroTelefono: string;
  contrasena: string;
  rol?: TipoUsuario;            // puede venir del backend -> opcional
  fechaRegistro?: Date;
  carteraDigital?: number;
}
