package com.app.pruebatres;

public class Usuario {
    private int idUsuario;
    private String nombre;
    private int edad;
    private String contraseña;
    private String email;
    private String fechaRegistro;
    private String pregunta;

    public Usuario() {

    }

    public Usuario(String nombre, int edad, String contraseña, String email, String fechaRegistro, String pregunta) {
        this.nombre = nombre;
        this.edad = edad;
        this.contraseña = contraseña;
        this.email = email;
        this.fechaRegistro = fechaRegistro;
        this.pregunta = pregunta;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(String fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public String getPregunta() {
        return pregunta;
    }

    public void setPregunta(String pregunta) {
        this.pregunta = pregunta;
    }
}
