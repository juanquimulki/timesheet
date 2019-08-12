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
import java.text.DecimalFormat;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
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
        Alert alerta = new Alert(Alert.AlertType.NONE,"Espere por favor...",ButtonType.CLOSE);
        alerta.show();
        
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
                ResultSet rs1 = BD.Ejecutar(conn,"SELECT * FROM DetalleHorariosLegajos WHERE CodLegajo="+id+" AND CodLegajo<>166 ORDER BY Dia");

                sql = "INSERT INTO InformeHorarios (Nombre,Lunes,Martes,Miercoles,Jueves,Viernes,Sabado,Domingo) VALUES (?,?,?,?,?,?,?,?)";
                PreparedStatement st = conn.prepareStatement(sql);
                st.setString(1, nombre);
                i=2;
                while (rs1.next()) {
                    System.out.print(dias[rs1.getInt("Dia")-1]+":");
                    System.out.print(aHoras(rs1.getInt("defe1"))+"-"+aHoras(rs1.getInt("defs1"))+" "+aHoras(rs1.getInt("defe2"))+"-"+aHoras(rs1.getInt("defs2"))+" "+aHoras(rs1.getInt("defe3"))+"-"+aHoras(rs1.getInt("defs3"))+" ");
                    System.out.print(" ");
                    
                    st.setString(i, aHoras(rs1.getInt("defe1"))+"-"+aHoras(rs1.getInt("defs1"))+" "+aHoras(rs1.getInt("defe2"))+"-"+aHoras(rs1.getInt("defs2"))+" "+aHoras(rs1.getInt("defe3"))+"-"+aHoras(rs1.getInt("defs3"))+" ");
                    
                    i++;
                }
                if (id!=166) st.executeUpdate(); 
                System.out.println();
            }
        } catch (SQLException ex) {
            Logger.getLogger(FXMLHorariosController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        alerta.close();
    }

    private String aHoras(int min) {
        double minutos = min;
        double horas;
        double resto;
        int mins;
        String cadena="";
        
        DecimalFormat df = new DecimalFormat("#.00");
        
        if (minutos>=1) {
            horas = minutos / 60;

            horas = Double.parseDouble(df.format(horas).replace(",", "."));
            
            resto = horas - (int) horas;
            resto = Double.parseDouble(df.format(resto).replace(",", "."));
            mins  = (int) (resto * 60);
         
            if (horas<10) cadena = cadena + "0";
            cadena = cadena + String.valueOf((int)horas);
            cadena = cadena + ":";
            if (mins<10) cadena = cadena + "0";
            cadena = cadena + String.valueOf(mins);
            
            return cadena;
        }
        else
            return "";
    }    
    
}
