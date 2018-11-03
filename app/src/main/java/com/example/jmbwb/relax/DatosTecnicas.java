package com.example.jmbwb.relax;

public class DatosTecnicas {
    private String titulo, url_video, descripcion;
    private int imagen;

    public DatosTecnicas(String titulo, int imagen){
        this.titulo=titulo;
        this.imagen=imagen;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setImagen(int imagen) {
        this.imagen = imagen;
    }

    public String getTitulo() {
        return titulo;
    }

    public int getImagen() {
        return imagen;
    }
}
