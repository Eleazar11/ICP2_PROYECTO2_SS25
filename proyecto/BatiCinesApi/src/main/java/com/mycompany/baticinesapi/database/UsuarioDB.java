/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.baticinesapi.database;

import com.mycompany.baticinesapi.models.Seguridad;
import com.mycompany.baticinesapi.models.Usuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author eleaz
 */
public class UsuarioDB {

    private Connection connection;

    public UsuarioDB() {
        try {
            this.connection = DataSourceDBSingleton.getInstance().getConnection();
        } catch (SQLException e) {
            throw new RuntimeException("Error al obtener la conexión a la base de datos", e);
        }
    }

    // Registro de usuario
    public boolean registrarUsuario(Usuario usuario) throws SQLException {
        String sql = "INSERT INTO usuarios (nombre_usuario, correo, numero_telefono, contrasena, tipo_usuario, fecha_registro) "
                + "VALUES (?, ?, ?, ?, ?, CURRENT_TIMESTAMP)";

        try (PreparedStatement ps = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, usuario.getNombreUsuario());
            ps.setString(2, usuario.getCorreo());
            ps.setString(3, usuario.getNumeroTelefono());
            ps.setString(4, usuario.getContrasena());
            ps.setString(5, usuario.getRol().toString());

            int filas = ps.executeUpdate();

            if (filas > 0) {
                // Obtener el ID del nuevo usuario
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        int idUsuario = rs.getInt(1);
                        usuario.setIdUsuario(idUsuario);

                        // Si es anunciante → crear cartera digital
                        if ("ANUNCIANTE".equalsIgnoreCase(usuario.getRol().toString())) {
                            crearCarteraDigital(usuario);
                        }
                    }
                }
            }

            return filas > 0;
        } catch (SQLException e) {
            System.out.println("Error al registrar usuario: " + e.getMessage());
            throw e;
        }
    }

//    // Método para registrar cartera digital si el usuario es de tipo 'especial'
    /**
     * Crea una cartera digital asociada a un usuario tipo ANUNCIANTE.
     */
    private void crearCarteraDigital(Usuario usuario) {
        String sql = "INSERT INTO Carteras_Digitales (id_usuario, correo, saldo_actual) VALUES (?, ?, 0.00)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, usuario.getIdUsuario());
            ps.setString(2, usuario.getCorreo());
            ps.executeUpdate();
            System.out.println("Cartera digital creada para anunciante: " + usuario.getCorreo());
        } catch (SQLException e) {
            System.out.println("Error al crear cartera digital: " + e.getMessage());
        }
    }

    //Método para verificar si el usuario ya existe en la base de datos
    private boolean existeUsuario(String dpi) {
        String consulta = "SELECT COUNT(*) FROM usuarios WHERE dpi = ?";
        try (PreparedStatement statement = connection.prepareStatement(consulta)) {
            statement.setString(1, dpi);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    int count = resultSet.getInt(1);
                    return count > 0;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al verificar existencia de usuario", e);
        }
        return false;
    }

    public Usuario obtenerUsuarioPorCorreo(String correo) throws SQLException {
        String sql = "SELECT * FROM usuarios WHERE correo = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, correo);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Usuario usuario = new Usuario();
                    usuario.setIdUsuario(rs.getInt("id_usuario"));
                    usuario.setNombreUsuario(rs.getString("nombre_usuario"));
                    usuario.setCorreo(rs.getString("correo"));
                    usuario.setNumeroTelefono(rs.getString("numero_telefono"));
                    usuario.setContrasena(rs.getString("contrasena"));
                    usuario.setRol(com.mycompany.baticinesapi.models.Rol.valueOf(rs.getString("tipo_usuario")));
                    usuario.setFechaRegistro(rs.getTimestamp("fecha_registro"));
                    return usuario;
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener usuario por correo: " + e.getMessage());
            throw e;
        }
        return null; // si no se encuentra
    }

    // Método para obtener un usuario por nombre de usuario
    public Usuario obtenerUsuario(String nombreUsuario) {
        String consulta = "SELECT * FROM usuarios WHERE dpi = ?";
        try (PreparedStatement statement = connection.prepareStatement(consulta)) {
            statement.setString(1, nombreUsuario);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    String dpi = resultSet.getString("dpi");
                    String correo = resultSet.getString("correo");
                    String contrasena = resultSet.getString("contrasena");
                    String rol = resultSet.getString("tipo_usuario");

                    // Construcción del usuario
//                    Usuario usuario = new Usuario(
//                            
//                            dpi, correo, contrasena, Rol.valueOf(rol.toUpperCase())
//                    );
                    System.out.println("Rol recibido de la BD: '" + rol + dpi + contrasena + "'");
                    //return usuario;
                    return null;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al obtener el usuario", e);
        }
        return null;
    }
//    // Método para actualizar el perfil de un usuario
//    public void actualizarUsuario(String nombreUsuario, String descripcion, String fotoPerfilPath) {
//        String consulta = "UPDATE usuarios SET descripcion = ?, foto_perfil = ? WHERE nombre = ?";
//
//        try (PreparedStatement stmt = connection.prepareStatement(consulta)) {
//            stmt.setString(1, descripcion);
//            stmt.setString(2, fotoPerfilPath);
//            stmt.setString(3, nombreUsuario);
//            stmt.executeUpdate();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
}
