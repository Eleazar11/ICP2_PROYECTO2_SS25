/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.baticinesapi.controladores;

import com.mycompany.baticinesapi.models.Rol;
import com.mycompany.baticinesapi.models.Usuario;
import com.mycompany.baticinesapi.servicios.UsuarioService;
import com.mycompany.baticinesapi.util.GeneradorToken;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author eleaz
 */
@Path("/usuarios")
public class UsuarioController {

    private UsuarioService usuarioService;
    private static final String SECRET_KEY = "clave_usr_bati_cine"; 
    public UsuarioController() {
        this.usuarioService = new UsuarioService();
    }

    @POST
    @Path("/registro")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response registrarUsuario(Usuario usuario) {
        System.out.println("📥 Endpoint /usuarios/registro llamado");

        // Validación básica
        if (usuario == null || usuario.getCorreo() == null || usuario.getCorreo().isEmpty()
                || usuario.getContrasena() == null || usuario.getContrasena().isEmpty()
                || usuario.getNombreUsuario() == null || usuario.getNombreUsuario().isEmpty()
                || usuario.getNumeroTelefono() == null || usuario.getNumeroTelefono().isEmpty()) {

            Map<String, String> response = new HashMap<>();
            response.put("error", "Todos los campos son obligatorios");
            return Response.status(Response.Status.BAD_REQUEST).entity(response).build();
        }

        // Si no se envía tipo_usuario, se asume COMUN
        if (usuario.getRol() == null) {
            usuario.setRol(Rol.COMUN);
        }

        try {
            usuarioService.registrarUsuario(usuario);

            Map<String, Object> response = new HashMap<>();
            response.put("message", "Usuario registrado exitosamente");
            response.put("tipo_usuario", usuario.getRol().toString());

            if (usuario.getRol() == Rol.ANUNCIANTE) {
                response.put("info", "Cartera digital creada automáticamente");
            }

            return Response.ok(response).build();

        } catch (SQLException e) {
            Map<String, String> response = new HashMap<>();
            response.put("error", "Error al registrar el usuario: " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(response).build();
        }
    }

    /**
     * Método para autenticar a un usuario mediante sus credenciales.
     *
     * @param usuario El objeto Usuario con el nombre de usuario y la contraseña
     * a autenticar.
     * @return Response con el resultado de la operación.
     */
    @POST
    @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response loginUsuario(Usuario usuario) {
        System.out.println("Endpoint /usuarios/login llamado");

        if (usuario == null || usuario.getCorreo() == null || usuario.getContrasena() == null) {
            Map<String, String> response = new HashMap<>();
            response.put("error", "Correo y contraseña son obligatorios");
            return Response.status(Response.Status.BAD_REQUEST).entity(response).build();
        }

        try {
            Usuario usuarioAutenticado = usuarioService.autenticarUsuario(usuario.getCorreo(), usuario.getContrasena());

            if (usuarioAutenticado == null) {
                Map<String, String> response = new HashMap<>();
                response.put("error", "Credenciales incorrectas");
                return Response.status(Response.Status.UNAUTHORIZED).entity(response).build();
            }

            GeneradorToken generadorToken = new GeneradorToken();
            String token = generadorToken.crearTokenJWT(usuarioAutenticado);

            Map<String, Object> response = new HashMap<>();
            response.put("message", "Inicio de sesión exitoso");
            response.put("rol", usuarioAutenticado.getRol().toString());
            response.put("token", token);

            return Response.ok(response).build();

        } catch (SQLException e) {
            Map<String, String> response = new HashMap<>();
            response.put("error", "Error en el servidor: " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(response).build();
        }
    }

}
