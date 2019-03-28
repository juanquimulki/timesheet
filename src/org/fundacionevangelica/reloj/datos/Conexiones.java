package org.fundacionevangelica.reloj.datos;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

public class Conexiones {

    public static Connection Conexion1() {
        Propiedades p = new Propiedades();
        
        String directorio = p.getBd();
        String password = p.getPass();
        
        try {    
            return DriverManager.getConnection("jdbc:ucanaccess://"+directorio,"",password);
        } catch (SQLException ex) {
            Alert alerta = new Alert(Alert.AlertType.ERROR,"Error de acceso de Base de Datos",ButtonType.CLOSE);
            alerta.show();
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