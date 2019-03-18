/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reloj;

/**
 *
 * @author jmulki
 */
public class Datos {
    private String dia;
    private String fecha;
    private String turno1;
    private String turno2;
    private String turno3;
    private String fichadas;
    private String novedad;

    public Datos(String dia,String fecha, String turno1, String turno2, String turno3,String fichadas,String novedad) {
        this.dia      = dia;
        this.fecha    = fecha;
        this.turno1   = turno1;
        this.turno2   = turno2;
        this.turno3   = turno3;
        this.fichadas = fichadas;
        this.novedad  = novedad;
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
}
