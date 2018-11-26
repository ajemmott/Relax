package com.example.jmbwb.relax;

public class DatosTecnicas {
    private String titulo, url_video, descripcion;
    private int imagen, id;
    private Float rating;
    private static boolean bandera  =false;

    public DatosTecnicas( int id, String titulo, String url_video, String descripcion, int imagen){
        this.titulo=titulo;
        this.id = id;
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

    public void setId_tecnica(int id) {

        this.id = id;
    }

    public int getid_tecnica() {
        return id;
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

    public static boolean isFlag() {
        return bandera;
    }

    public static void setFlag(boolean flag) {
        bandera = flag;
    }

    public static Integer  buscarObj(String nombre){
        int i;

        for (i=0; i < Fragmento_inicio.datosTecnicas.size(); i++){
            if (Fragmento_inicio.datosTecnicas.get(i).titulo.equals(nombre)){
                return i;
            }
        }
        return null;
    }

}
