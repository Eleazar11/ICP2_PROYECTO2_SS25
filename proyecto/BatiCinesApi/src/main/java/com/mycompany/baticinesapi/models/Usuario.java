/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.baticinesapi.models;

import java.util.Date;

/**
 *
 * @author eleaz
 */
public class Usuario {
    private int idUsuario;
    private String nombreUsuario;
    private String correo;
    private String numeroTelefono;
    private String contrasena;
    private Rol rol;
    private Date fechaRegistro;
    private double carteraDigital;

    public Usuario(String nombreUsuario, String correo, String numeroTelefono, String contrasena) {
        this.nombreUsuario = nombreUsuario;
        this.correo = correo;
        this.numeroTelefono = numeroTelefono;
        this.contrasena = contrasena;
    }
    
    public Usuario(){} //constructor vacio pa cosas que usamos luego
    
    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getNumeroTelefono() {
        return numeroTelefono;
    }

    public void setNumeroTelefono(String numeroTelefono) {
        this.numeroTelefono = numeroTelefono;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    public Date getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public double getCarteraDigital() {
        return carteraDigital;
    }

    public void setCarteraDigital(double carteraDigital) {
        this.carteraDigital = carteraDigital;
    }
    
    
}
