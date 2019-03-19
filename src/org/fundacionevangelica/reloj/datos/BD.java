/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fundacionevangelica.reloj.datos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author jmulki
 */
public class BD {
    
    public static Connection Conexion() {
        try {
            Connection conn=DriverManager.getConnection("jdbc:ucanaccess://C:/Program Files (x86)/C.In.Ti.A. Version Base/Cintia.mdb","","ngtayeXmr?h");
            return conn;
        } catch (SQLException ex) {
            //Logger.getLogger(BD.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex.getMessage());
        }
        return null;
    }
    
    public static ResultSet Ejecutar(Connection conn,String sql) {
        try {
            Statement s = conn.createStatement();
            ResultSet rs = s.executeQuery(sql);
            return rs;
        } catch (SQLException ex) {
            //Logger.getLogger(BD.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex.getMessage());
        }
        return null;
    }
    
}
