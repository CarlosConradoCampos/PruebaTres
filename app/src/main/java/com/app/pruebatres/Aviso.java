package com.app.pruebatres;

public class Aviso {

    private String  titulo, fecha, importancia, observacion, lugar, tiempoAviso;
    private int idUsuario;

    public Aviso() {
    }

    public Aviso(String titulo, String fecha, String importancia, String observacion, String lugar, String tiempoAviso) {
        this.titulo = titulo;
        this.fecha = fecha;
        this.importancia = importancia;
        this.observacion = observacion;
        this.lugar = lugar;
        this.tiempoAviso = tiempoAviso;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getImportancia() {
        return importancia;
    }

    public void setImportancia(String importancia) {
        this.importancia = importancia;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public String getLugar() {
        return lugar;
    }

    public void setLugar(String lugar) {
        this.lugar = lugar;
    }

    public String getTiempoAviso() {
        return tiempoAviso;
    }

    public void setTiempoAviso(String tiempoAviso) {
        this.tiempoAviso = tiempoAviso;
    }
}
