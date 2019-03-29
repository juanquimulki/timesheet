package org.fundacionevangelica.reloj.datos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
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
    
}