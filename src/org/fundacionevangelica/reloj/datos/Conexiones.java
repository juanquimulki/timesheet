package org.fundacionevangelica.reloj.datos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexiones {

    public static Connection Conexion1() {
        String directorio = "Program Files (x86)";
        String password = "ngtayeXmr?h";
        
        try {    
            return DriverManager.getConnection("jdbc:ucanaccess://C:/"+directorio+"/C.In.Ti.A. Version Base/Cintia.mdb","",password);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return null;
    }
    
    public static Connection Conexion2() {
        String directorio = "Program Files";
        String password = "ngtayeXmr?h";
        
        try {    
            return DriverManager.getConnection("jdbc:ucanaccess://C:/"+directorio+"/C.In.Ti.A. Version Base/Cintia.mdb","",password);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return null;
    }    
}