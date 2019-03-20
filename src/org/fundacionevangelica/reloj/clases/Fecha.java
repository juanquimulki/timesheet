package org.fundacionevangelica.reloj.clases;

import java.util.Calendar;

public class Fecha {
    private static String[] dias = {"Domingo","Lunes","Martes", "Miércoles","Jueves","Viernes","Sábado"};
    
    public static String[] diasSemana () {
        return dias;
    }    

    public static int ultimoDia(int anio, int mes) {
        Calendar calendario=Calendar.getInstance();
        calendario.set(anio, mes-1, 1);
        return calendario.getActualMaximum(Calendar.DAY_OF_MONTH);
    } 
}
