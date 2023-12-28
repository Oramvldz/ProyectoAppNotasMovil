package com.example.proyectoappnotasmovil;

public class Nota {
    private String Titulo;
    private String Contenido;
    private int Id;

    public Nota(String titulo, String contenido, int id) {
        Titulo = titulo;
        Contenido = contenido;
        Id=id;
    }

    public String getTitulo() {
        return Titulo;
    }

    public String getContenido() {
        return Contenido;
    }

    public int getId()
    {
        return Id;
    }
}
