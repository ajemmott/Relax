package com.example.jmbwb.relax;

public class DatosTecnicas {
    private String titulo, url_video, descripcion;
    private int imagen;
    private Float rating;
    private static boolean bandera=false;

    public DatosTecnicas(String titulo, String url_video, String descripcion, int imagen, float rating){
        this.titulo=titulo;
        this.imagen=imagen;
        this.descripcion=descripcion;
        this.url_video=url_video;
        this.rating=rating;
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

    public Float getRating() {
        return rating;
    }

    public void setRating(Float rating) {
        this.rating = rating;
    }

    public String getTitulo() {
        return titulo;
    }

    public int getImagen() {
        return imagen;
    }

    public static boolean isFlag() {
        return bandera;
    }

    public static void setFlag(boolean flag) {
        bandera = flag;
    }

    public static Integer  buscarObj (String nombre){
        int i;

        for (i=0; i < Fragmento_inicio.datosTecnicas.size(); i++){
            if (Fragmento_inicio.datosTecnicas.get(i).titulo.equals(nombre)){
                return i;
            }
        }
        return null;
    }

}
