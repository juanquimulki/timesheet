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
    int tardanza = 10;
   
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
    @FXML
    private TableColumn colFichadas;
    @FXML
    private TableColumn colNovedad;
    
    private ObservableList datosTabla = FXCollections.observableArrayList();    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        /*datDesde.setValue(LocalDate.now());
        datHasta.setValue(LocalDate.now());*/
        
        int dia  = LocalDate.now().getDayOfMonth();
        int mes  = LocalDate.now().getMonthValue();
        int anio = LocalDate.now().getYear();
        
        datDesde.setValue(LocalDate.of(anio,mes,1));
        int ultimo = ultimoDia(anio, mes);
        datHasta.setValue(LocalDate.of(anio,mes,ultimo));
        
        modelo = cmbEmpleados.getSelectionModel();
        
        try {
            Connection conn = BD.Conexion();
            ResultSet rs = BD.Ejecutar(conn,"SELECT IdLegajo,Nombre FROM Legajos");
            while (rs.next()) {
                System.out.println(rs.getString(2));
                cmbEmpleados.getItems().add(rs.getString(2));
                lista.add(rs.getInt(1));
            }
            cmbEmpleados.getSelectionModel().selectFirst();
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
            String sql = "SELECT Fecha,DefE1,DefS1,DefE2,DefS2,DefE3,DefS3"
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
            colFichadas.setCellValueFactory(
                    new PropertyValueFactory<Datos, String>("fichadas"));  
            colNovedad.setCellValueFactory(
                    new PropertyValueFactory<Datos, String>("novedad"));  
            datosTabla.clear();
        
            SimpleDateFormat mmddyyyyFormat = new SimpleDateFormat("dd/MM/yyyy");
            SimpleDateFormat yyyymmddFormat = new SimpleDateFormat("yyyy-MM-dd");
            while (rs.next()) {
                Datos objDatos = new Datos(
                        aDia(yyyymmddFormat.format(rs.getTimestamp(1))),
                        mmddyyyyFormat.format(rs.getTimestamp(1)),
                        aTurno(rs.getInt(2),rs.getInt(3)),
                        aTurno(rs.getInt(4),rs.getInt(5)),
                        aTurno(rs.getInt(6),rs.getInt(7)),
                        getFichadas((int) lista.get(modelo.getSelectedIndex()),yyyymmddFormat.format(rs.getTimestamp(1))),
                        novedades((int)lista.get(modelo.getSelectedIndex()),yyyymmddFormat.format(rs.getTimestamp(1)))
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
        if (ent!="")
            return ent + " - " + sal;
        else
            return "";
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
    
    private String getFichadas(int idlegajo, String fecha) {
        String cadena = "";
        Connection conn = BD.Conexion();
        String sql = "SELECT Hora"
                + " FROM ArchivoRegistracion"
                + " WHERE IdLegajo="+idlegajo+" AND Fecha = #"+fecha+"#"
                + " ORDER BY IdFichada";
        ResultSet rs = BD.Ejecutar(conn,sql);

        try {
            while (rs.next()) {
                cadena = cadena + aHoras(rs.getInt(1)) + " ";
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            //Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        //return String.valueOf(idlegajo)+" - "+fecha;
        return cadena;
    }
    
    public int ultimoDia(int anio, int mes) {
        Calendar calendario=Calendar.getInstance();
        calendario.set(anio, mes-1, 1);
        return calendario.getActualMaximum(Calendar.DAY_OF_MONTH);
    }    

    private String novedades(int idlegajo, String fecha) {
        //return idlegajo + " - " + fecha;
        String cadena = "";
        int i;
        
        Connection conn = BD.Conexion();
        String sql1 = "SELECT DefE1,DefS1,DefE2,DefS2,DefE3,DefS3"
                + " FROM Fichadas"
                + " WHERE IdLegajo="+idlegajo+" AND Fecha = #"+fecha+"#"
                + " ORDER BY Fecha";
        
        String sql2 = "SELECT Hora"
                + " FROM ArchivoRegistracion"
                + " WHERE IdLegajo="+idlegajo+" AND Fecha = #"+fecha+"#"
                + " ORDER BY IdFichada";

        boolean bh=false;
        boolean br=false;
        
        int entrada_teorica;
        int salida_teorica;
        int entrada_real;
        int salida_real;
        int minutos;
        
        try {
            ResultSet horario = BD.Ejecutar(conn,sql1);
            ResultSet registros = BD.Ejecutar(conn,sql2);
            if (horario.next()) {
                i = 1;
                while (horario.getInt("DefE"+i)>=0) {
                    bh=true;
                    entrada_teorica = horario.getInt("DefE"+i);
                    salida_teorica  = horario.getInt("DefS"+i);
                    
                    if (registros.next()) {
                        br=true;
                        entrada_real = registros.getInt("Hora");
                        minutos = entrada_real - entrada_teorica;
                        if (minutos>tardanza) {
                            cadena += "Entró tarde ("+minutos+")\n";
                        }
                        
                        if (registros.next()) {
                            salida_real = registros.getInt("Hora");
                            minutos = salida_teorica - salida_real;
                            if (minutos>5) {
                                cadena += "Salió temprano ("+minutos+")\n";
                            }
                        }
                        else {
                            cadena = "Faltan registros\n";
                        }
                    }
                    else {
                        if (br) {
                            cadena = "Faltan registros\n";
                        }
                        else {
                            cadena = "Ausente\n";
                        }
                    }
                    i++;
                }
                if (bh) {
                    
                }
                else {
                    //cadena = "Franco";
                }
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            //Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return cadena;
    }
}
