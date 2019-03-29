package org.fundacionevangelica.reloj.clases;

import java.sql.Connection;
import java.sql.ResultSet;
import org.fundacionevangelica.reloj.datos.BD;

public class Empleados {
    
    public static ResultSet getEmpleados() {
        String sql = "SELECT IdLegajo,Nombre FROM Legajos";
        Connection conn = BD.Conexion();
        ResultSet rs = BD.Ejecutar(conn,sql);
        return rs;
    }
}
