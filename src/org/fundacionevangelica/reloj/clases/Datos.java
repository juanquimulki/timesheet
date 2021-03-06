package org.fundacionevangelica.reloj.clases;

public class Datos {
    private String id;
    private String date;
    private String empleado;
    private String dia;
    private String fecha;
    private String turno1;
    private String turno2;
    private String turno3;
    private String fichadas;
    private String novedad;
    private String sistema;
    private String minutos;

    public Datos() {}
    
    public Datos(String id, String date, String empleado, String dia, String fecha, String turno1, String turno2, String turno3,String fichadas,String novedad, String sistema, String minutos) {
        this.id       = id;
        this.date     = date;
        this.empleado = empleado;
        this.dia      = dia;
        this.fecha    = fecha;
        this.turno1   = turno1;
        this.turno2   = turno2;
        this.turno3   = turno3;
        this.fichadas = fichadas;
        this.novedad  = novedad;
        this.sistema  = sistema;
        this.minutos  = minutos;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getEmpleado() {
        return empleado;
    }

    public void setEmpleado(String empleado) {
        this.empleado = empleado;
    }

    public String getDia() {
        return dia;
    }

    public void setDia(String dia) {
        this.dia = dia;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getTurno1() {
        return turno1;
    }

    public void setTurno1(String turno1) {
        this.turno1 = turno1;
    }

    public String getTurno2() {
        return turno2;
    }

    public void setTurno2(String turno2) {
        this.turno2 = turno2;
    }

    public String getTurno3() {
        return turno3;
    }

    public void setTurno3(String turno3) {
        this.turno3 = turno3;
    }

    public String getFichadas() {
        return fichadas;
    }

    public void setFichadas(String fichadas) {
        this.fichadas = fichadas;
    }

    public String getNovedad() {
        return novedad;
    }

    public void setNovedad(String novedad) {
        this.novedad = novedad;
    }

    public String getSistema() {
        return sistema;
    }

    public void setSistema(String sistema) {
        this.sistema = sistema;
    }

    public String getMinutos() {
        return minutos;
    }

    public void setMinutos(String minutos) {
        this.minutos = minutos;
    }
}