package org.fundacionevangelica.reloj.clases;

import java.time.LocalDate;
import java.util.Calendar;

public class Fecha {
    private static String[] dias = {"Domingo","Lunes","Martes", "Miércoles","Jueves","Viernes","Sábado"};
    private static String[] dias1 = {"Lunes","Martes", "Miércoles","Jueves","Viernes","Sábado","Domingo"};
    
    public static String[] diasSemana () {
        return dias;
    }    
    
    public static String[] diasSemana1 () {
        return dias1;
    }     
    
    public static int ultimoDia(int anio, int mes) {
        Calendar calendario=Calendar.getInstance();
        calendario.set(anio, mes-1, 1);
        return calendario.getActualMaximum(Calendar.DAY_OF_MONTH);
    } 
    
    public static LocalDate getDesde() {
        int mes  = LocalDate.now().getMonthValue();
        int anio = LocalDate.now().getYear();
        
        return LocalDate.of(anio,mes,1);
    }
    
    public static LocalDate getHasta() {
        int mes  = LocalDate.now().getMonthValue();
        int anio = LocalDate.now().getYear();
        int ultimo = Fecha.ultimoDia(anio, mes);
        
        return LocalDate.of(anio,mes,ultimo);
    }
    
    public static String getDia() {
        int dia = LocalDate.now().getDayOfMonth();
        return String.valueOf(dia);
    }

    public static String getMes() {
        int mes = LocalDate.now().getMonthValue();
        return String.valueOf(mes);
    }

    public static String getAnio() {
        int anio = LocalDate.now().getYear();
        return String.valueOf(anio);
    }
}
