/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.baticinesapi.servicios;

import com.mycompany.baticinesapi.models.Usuario;
import com.mycompany.baticinesapi.database.UsuarioDB;
import com.mycompany.baticinesapi.models.Rol;
import com.mycompany.baticinesapi.models.Seguridad;
import java.sql.SQLException;

/**
 *
 * @author eleaz
 */
public class UsuarioService {

    private final UsuarioDB usuarioDB = new UsuarioDB();
    private final Seguridad seguridad = new Seguridad();

    /**
     * Registra un nuevo usuario en la base de datos.
     *
     * @param usuario Objeto con los datos del usuario (nombre, correo,
     * teléfono, contraseña)
     * @throws SQLException si ocurre un error en la base de datos
     */
    public void registrarUsuario(Usuario usuario) throws SQLException {
        // Si el tipo no se especifica, será COMUN por defecto
        if (usuario.getRol() == null) {
            usuario.setRol(Rol.COMUN);
        }

        // Encriptar la contraseña antes de guardar
        String contrasenaEncriptada = seguridad.encriptarContrasena(usuario.getContrasena());
        usuario.setContrasena(contrasenaEncriptada);

        boolean registrado = usuarioDB.registrarUsuario(usuario);

        if (!registrado) {
            throw new SQLException("No se pudo registrar el usuario. Verifique los datos.");
        }
    }

    public Usuario autenticarUsuario(String correo, String contrasenaIngresada) throws SQLException {
        Usuario usuario = usuarioDB.obtenerUsuarioPorCorreo(correo);

        if (usuario == null) {
            return null; // No existe usuario con ese correo
        }

        // Verificar contraseña usando BCrypt
        boolean contrasenaValida = seguridad.verificarContrasena(contrasenaIngresada, usuario.getContrasena());

        if (!contrasenaValida) {
            return null; // Contraseña incorrecta
        }

        return usuario; // Login exitoso
    }

}
