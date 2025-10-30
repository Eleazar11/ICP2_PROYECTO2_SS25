/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.baticinesapi.servicios;

import com.mycompany.baticinesapi.models.Usuario;
import com.mycompany.baticinesapi.database.UsuarioDB;
import java.sql.SQLException;

/**
 *
 * @author eleaz
 */
public class UsuarioService {

    private UsuarioDB usuarioDB = new UsuarioDB();
    //private CarteraDB carteraDB = new CarteraDB();

    public void registrarUsuario(Usuario usuario) throws SQLException {

        boolean agregarCartera = false;

        switch (usuario.getRol().toString()) {
            
            case "ADMINISTRADOR":
                // Para los administradores, no se necesita agregar cartera
//                usuario = new Administrador(
//                        usuario.getDpi(),
//                        usuario.getCorreo(), 
//                        usuario.getContrasena(),
//                        usuario.getRol()
//                );
                break;
            case "":
//                usuario = new Cliente(
//                        usuario.getDpi(),
//                        usuario.getCorreo(), 
//                        usuario.getContrasena(),
//                        usuario.getRol()
//                );
                break;

            default:
                // Si no coincide con ningún rol, puedes lanzar un error o manejarlo de otra forma
                throw new IllegalArgumentException("Rol desconocido: " + usuario.getRol().toString());
        }

        // Registro del usuario en la base de datos
        if (usuarioDB.registrarUsuario(usuario)) {
            if (agregarCartera) {
                //carteraDB.crearCartera(usuario);  // Si el rol es Editor o Anunciante, agregar cartera
            }
        }
    }

//    public Usuario autenticarUsuario(String nombreUsuario, String contrasena) {
//        return usuarioDB.iniciarSesion(nombreUsuario, contrasena);
//    }
}
