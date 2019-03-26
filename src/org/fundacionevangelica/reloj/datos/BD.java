package org.fundacionevangelica.reloj.datos;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class BD {
    
    public static Connection Conexion() {
        Connection conn = Conexiones.Conexion1();
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

    public static int Actualizar(Connection conn,String sql) {
        int resultado = 0;
        try {
            Statement s = conn.createStatement();
            resultado = s.executeUpdate(sql);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return resultado;
    }
}