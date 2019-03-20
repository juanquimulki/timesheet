package org.fundacionevangelica.reloj.ventanas;

import org.fundacionevangelica.reloj.datos.BD;
import org.fundacionevangelica.reloj.clases.Datos;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.SelectionModel;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.fundacionevangelica.reloj.clases.Fecha;

public class FXMLEmpleadoController implements Initializable {
    int turnos    = 3;
    int tardanza  = 10;
    int tempranza = 5;
   
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
    @FXML
    private TableColumn colSistema;
    
    private ObservableList datosTabla = FXCollections.observableArrayList();    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        int mes  = LocalDate.now().getMonthValue();
        int anio = LocalDate.now().getYear();
        
        datDesde.setValue(LocalDate.of(anio,mes,1));
        int ultimo = Fecha.ultimoDia(anio, mes);
        datHasta.setValue(LocalDate.of(anio,mes,ultimo));
        
        modelo = cmbEmpleados.getSelectionModel();
        
        try {
            Connection conn = BD.Conexion();
            ResultSet rs = BD.Ejecutar(conn,"SELECT IdLegajo,Nombre FROM Legajos");
            while (rs.next()) {
                cmbEmpleados.getItems().add(rs.getString(2));
                lista.add(rs.getInt(1));
            }
            cmbEmpleados.getSelectionModel().selectFirst();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }        
    }    
 
    public void mostrar() {
        Alert alerta = new Alert(Alert.AlertType.NONE,"Espere por favor...",ButtonType.CLOSE);
        alerta.show();
        
        try {
            Connection conn = BD.Conexion();
            String sql = "SELECT Fecha,DefE1,DefS1,DefE2,DefS2,DefE3,DefS3,Descripcion"
                    + " FROM Fichadas"
                    + " LEFT OUTER JOIN Novedades ON Fichadas.CodNovedad=Novedades.IdNovedad"
                    + " WHERE IdLegajo="+lista.get(modelo.getSelectedIndex())+" AND Fecha BETWEEN #"+datDesde.getValue()+"# and #"+datHasta.getValue()+"#"
                    + " ORDER BY Fecha";
            ResultSet rs = BD.Ejecutar(conn,sql);

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
            colSistema.setCellValueFactory(
                    new PropertyValueFactory<Datos, String>("sistema"));  
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
                        novedades((int)lista.get(modelo.getSelectedIndex()),yyyymmddFormat.format(rs.getTimestamp(1))),
                        rs.getString(8)
                );
                datosTabla.add(objDatos);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } 
        tblDatos.setItems(datosTabla);
        alerta.close();
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
        
        DecimalFormat df = new DecimalFormat("#.00");
        
        if (minutos>=0) {
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
    
    private String aDia(String fec) {
        SimpleDateFormat formatoDelTexto = new SimpleDateFormat("yyyy-MM-dd");
        String strFecha = fec;
        Date fecha = null;
        try {
            fecha = formatoDelTexto.parse(strFecha);
        } catch (ParseException ex) {
            System.out.println(ex.getMessage());
        }
        
      String[] dias = Fecha.diasSemana();
      int numeroDia=0;
      Calendar cal= Calendar.getInstance();

      cal.setTime(fecha);
      numeroDia=cal.get(Calendar.DAY_OF_WEEK);
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
        }
        
        return cadena;
    }
    
    private String novedades(int idlegajo, String fecha) {
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
                            if (minutos>tempranza) {
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
        }
        return cadena;
    }
}