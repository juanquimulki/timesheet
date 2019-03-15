/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reloj;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.SelectionModel;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 *
 * @author jmulki
 */
public class FXMLDocumentController implements Initializable {
   
    @FXML
    private ComboBox cmbEmpleados;
    @FXML
    private DatePicker datDesde;
    @FXML
    private DatePicker datHasta;
    
    ObservableList lista = FXCollections.observableArrayList();
    SelectionModel modelo;

    @FXML
    private TableView tblDatos;
    @FXML
    private TableColumn colDia;
    @FXML
    private TableColumn colFecha;
    @FXML
    private TableColumn colTurno1;
    @FXML
    private TableColumn colTurno2;
    @FXML
    private TableColumn colTurno3;
    
    private ObservableList datosTabla = FXCollections.observableArrayList();    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        datDesde.setValue(LocalDate.now());
        datHasta.setValue(LocalDate.now());

        modelo = cmbEmpleados.getSelectionModel();
        
        try {
            Connection conn = BD.Conexion();
            ResultSet rs = BD.Ejecutar(conn,"SELECT IdLegajo,Nombre FROM Legajos");
            while (rs.next()) {
                System.out.println(rs.getString(2));
                cmbEmpleados.getItems().add(rs.getString(2));
                lista.add(rs.getInt(1));
            }
        } catch (SQLException ex) {
            //Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex.getMessage());
        }        
    }    
 
    public void mostrar() {
        System.out.println(datDesde.getValue());
        System.out.println(datHasta.getValue());
        
        System.out.println(lista.get(modelo.getSelectedIndex()));
        System.out.println(modelo.getSelectedItem().toString());
        
        try {
            Connection conn = BD.Conexion();
            String sql = "SELECT Fecha,DefE1,DefS1,DefE2,DefS2,DefE2,DefS2"
                    + " FROM Fichadas"
                    + " WHERE IdLegajo="+lista.get(modelo.getSelectedIndex())+" AND Fecha BETWEEN #"+datDesde.getValue()+"# and #"+datHasta.getValue()+"#"
                    + " ORDER BY Fecha";
            ResultSet rs = BD.Ejecutar(conn,sql);
            System.out.println(sql);


            colDia.setCellValueFactory(
                    new PropertyValueFactory<Datos, String>("dia"));  
            colFecha.setCellValueFactory(
                    new PropertyValueFactory<Datos, String>("fecha"));  
            colTurno1.setCellValueFactory(
                    new PropertyValueFactory<Datos, String>("turno1"));  
            colTurno2.setCellValueFactory(
                    new PropertyValueFactory<Datos, String>("turno2"));  
            colTurno3.setCellValueFactory(
                    new PropertyValueFactory<Datos, String>("turno3"));  
            datosTabla.clear();
        
            SimpleDateFormat mmddyyyyFormat = new SimpleDateFormat("dd/MM/yyyy");
            SimpleDateFormat yyyymmddFormat = new SimpleDateFormat("yyyy-MM-dd");
            while (rs.next()) {
                Datos objDatos = new Datos(
                        aDia(yyyymmddFormat.format(rs.getTimestamp(1))),
                        mmddyyyyFormat.format(rs.getTimestamp(1)),
                        aTurno(rs.getInt(2),rs.getInt(3)),
                        aTurno(rs.getInt(4),rs.getInt(6)),
                        aTurno(rs.getInt(6),rs.getInt(7))
                );
                datosTabla.add(objDatos);
                
                System.out.println(mmddyyyyFormat.format(rs.getTimestamp(1)));
            }
        } catch (SQLException ex) {
            //Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex.getMessage());
        } 
        tblDatos.setItems(datosTabla);
    }
    
    private String aTurno(int entrada, int salida) {
        String ent = aHoras(entrada);
        String sal = aHoras(salida);
        return ent + " - " + sal;
    }
    
    private String aHoras(int min) {
        double minutos = min;
        double horas;
        double resto;
        int mins;
        String cadena="";
        
        if (minutos>=0) {
            horas = minutos / 60;
            resto = horas - (int) horas;
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
    
    private String aDia(String fec) {
        SimpleDateFormat formatoDelTexto = new SimpleDateFormat("yyyy-MM-dd");
        String strFecha = fec;
        Date fecha = null;
        try {
            fecha = formatoDelTexto.parse(strFecha);
        } catch (ParseException ex) {
            //Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex.getMessage());
        }
        
        
      String[] dias={"Domingo","Lunes","Martes", "Miércoles","Jueves","Viernes","Sábado"};
      int numeroDia=0;
      Calendar cal= Calendar.getInstance();

      cal.setTime(fecha);
      numeroDia=cal.get(Calendar.DAY_OF_WEEK);
      System.out.println("hoy es "+ dias[numeroDia - 1]);
      return dias[numeroDia - 1];
    }
}
