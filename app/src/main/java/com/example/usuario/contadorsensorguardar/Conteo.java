package com.example.usuario.contadorsensorguardar;

public class Conteo {
    String conteoId;
    String conteo;

    public Conteo (){

    }

    public Conteo(String conteoId, String conteo) {
        this.conteoId = conteoId;
        this.conteo = conteo;
    }

    public String getConteoId() {
        return conteoId;
    }

    public String getConteo() {
        return conteo;
    }
}
