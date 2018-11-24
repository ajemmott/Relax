package com.example.jmbwb.relax;

public class Usuarios {
    private String nombre, genero, correo, contraseña;
    private int id_usuario, edad, tipo;

    //Constructores
    public Usuarios(String nombre, String genero, String correo, int id_usuario, int edad, int tipo, String contraseña) {
        this.nombre = nombre;
        this.genero = genero;
        this.correo = correo;
        this.id_usuario = id_usuario;
        this.edad = edad;
        this.tipo = tipo;
        this.contraseña = contraseña;
    }

    public Usuarios(){

    }

    //Getters and setters
    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public int getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(int id_usuario) {
        this.id_usuario = id_usuario;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }
}
