package com.example.jmbwb.relax;

public class DatosTecnicas {
    private String titulo, url_video, descripcion;
    private int imagen;

    public DatosTecnicas(String titulo, String url_video, String descripcion, int imagen){
        this.titulo=titulo;
        this.imagen=imagen;
        this.descripcion=descripcion;
        this.url_video=url_video;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setImagen(int imagen) {
        this.imagen = imagen;
    }

    public String getUrl_video() {
        return url_video;
    }

    public void setUrl_video(String url_video) {
        this.url_video = url_video;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getTitulo() {
        return titulo;
    }

    public int getImagen() {
        return imagen;
    }
}
