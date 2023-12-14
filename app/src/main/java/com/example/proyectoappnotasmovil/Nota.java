package com.example.proyectoappnotasmovil;

public class Nota {
    private String Titulo;
    private String Contenido;

    public Nota(String titulo, String contenido) {
        Titulo = titulo;
        Contenido = contenido;
    }

    public String getTitulo() {
        return Titulo;
    }

    public String getContenido() {
        return Contenido;
    }
}
