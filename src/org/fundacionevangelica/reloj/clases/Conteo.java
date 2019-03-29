package org.fundacionevangelica.reloj.clases;

public class Conteo {
    private String novedad;
    private String cantidad;

    public Conteo() {}
    
    public Conteo(String novedad, String cantidad) {
        this.novedad  = novedad;
        this.cantidad = cantidad;
    }

    public String getNovedad() {
        return novedad;
    }

    public void setNovedad(String novedad) {
        this.novedad = novedad;
    }

    public String getCantidad() {
        return cantidad;
    }

    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
    }

}