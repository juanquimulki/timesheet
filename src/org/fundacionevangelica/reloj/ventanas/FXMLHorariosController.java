/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fundacionevangelica.reloj.ventanas;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.Initializable;
import org.fundacionevangelica.reloj.clases.Empleados;
import org.fundacionevangelica.reloj.clases.Fecha;
import org.fundacionevangelica.reloj.datos.BD;

/**
 * FXML Controller class
 *
 * @author SYSTEM
 */
public class FXMLHorariosController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            // TODO
            int i,id;
            String nombre;
            String[] dias = Fecha.diasSemana1();
            Connection conn = BD.Conexion();
            ResultSet rs = Empleados.getEmpleados();
            
            String sql = "DELETE * FROM InformeHorarios";
            BD.Actualizar(conn, sql);
            
            while (rs.next()) {
                id = rs.getInt("IdLegajo");
                nombre = rs.getString("Nombre");
                System.out.print(nombre+" ");
                ResultSet rs1 = BD.Ejecutar(conn,"SELECT * FROM DetalleHorariosLegajos WHERE CodLegajo="+id+" ORDER BY Dia");

                sql = "INSERT INTO InformeHorarios (Nombre,Lunes,Martes,Miercoles,Jueves,Viernes,Sabado,Domingo) VALUES (?,?,?,?,?,?,?,?)";
                PreparedStatement st = conn.prepareStatement(sql);
                st.setString(1, nombre);
                i=2;
                while (rs1.next()) {
                    System.out.print(dias[rs1.getInt("Dia")-1]+":");
                    System.out.print(rs1.getInt("defe1")+"-"+rs1.getInt("defs1")+" "+rs1.getInt("defe2")+"-"+rs1.getInt("defs2")+" "+rs1.getInt("defe3")+"-"+rs1.getInt("defs3")+" ");
                    System.out.print(" ");
                    
                    st.setString(i, rs1.getInt("defe1")+"-"+rs1.getInt("defs1")+" "+rs1.getInt("defe2")+"-"+rs1.getInt("defs2")+" "+rs1.getInt("defe3")+"-"+rs1.getInt("defs3")+" ");
                    
                    i++;
                }
                //st.executeUpdate(); 
                System.out.println();
            }
        } catch (SQLException ex) {
            Logger.getLogger(FXMLHorariosController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    
    
}
