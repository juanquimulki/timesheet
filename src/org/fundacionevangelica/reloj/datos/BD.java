package org.fundacionevangelica.reloj.datos;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class BD {
    
    public static Connection Conexion() {
        Connection conn = Conexiones.Conexion2();
        return conn;
    }
    
    public static ResultSet Ejecutar(Connection conn,String sql) {
        try {
            Statement s = conn.createStatement();
            ResultSet rs = s.executeQuery(sql);
            return rs;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return null;
    }
}