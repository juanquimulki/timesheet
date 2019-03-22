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
import javafx.event.EventHandler;
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
import javafx.scene.input.MouseEvent;
import org.fundacionevangelica.reloj.clases.Fecha;

public class FXMLEmpleadoController implements Initializable {
    int turnos    = 3;
    int tardanza  = 10;
    int tempranza = 5;
   
    @FXML
    private ComboBox cmbEmpleados;
    ObservableList listaEmp = FXCollections.observableArrayList();
    SelectionModel modeloEmp;

    @FXML
    private ComboBox cmbNiveles;
    ObservableList listaNiv = FXCollections.observableArrayList();
    SelectionModel modeloNiv;

    @FXML
    private ComboBox cmbNovedades;
    ObservableList listaNov = FXCollections.observableArrayList();
    SelectionModel modeloNov;

    @FXML
    private DatePicker datDesde;
    @FXML
    private DatePicker datHasta;
    
    @FXML
    private TableView tblDatos;
    @FXML
    private TableColumn colId;
    @FXML
    private TableColumn colDate;
    @FXML
    private TableColumn colEmpleado;
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
        
        modeloEmp = cmbEmpleados.getSelectionModel();
        cmbEmpleados.getItems().add("(Todos...)");
        listaEmp.add(0);
        try {
            Connection conn = BD.Conexion();
            ResultSet rs = BD.Ejecutar(conn,"SELECT IdLegajo,Nombre FROM Legajos");
            while (rs.next()) {
                cmbEmpleados.getItems().add(rs.getString(2));
                listaEmp.add(rs.getInt(1));
            }
            cmbEmpleados.getSelectionModel().selectFirst();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }        

        modeloNiv = cmbNiveles.getSelectionModel();
        cmbNiveles.getItems().add("(Todos...)");
        listaNiv.add(0);
        try {
            Connection conn = BD.Conexion();
            ResultSet rs = BD.Ejecutar(conn,"SELECT IdEmpresa,Nombre FROM Empresas WHERE IdEmpresa>=4");
            while (rs.next()) {
                cmbNiveles.getItems().add(rs.getString(2));
                listaNiv.add(rs.getInt(1));
            }
            cmbNiveles.getSelectionModel().selectFirst();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }        

        modeloNov = cmbNovedades.getSelectionModel();
        cmbNovedades.getItems().add("(Todas...)");
        listaNov.add(-1);
        cmbNovedades.getItems().add("CON NOVEDADES");
        listaNov.add(-2);
        cmbNovedades.getItems().add("SIN NOVEDADES");
        listaNov.add(0);
        try {
            Connection conn = BD.Conexion();
            ResultSet rs = BD.Ejecutar(conn,"SELECT IdNovedad,Descripcion FROM Novedades ORDER BY Descripcion");
            while (rs.next()) {
                cmbNovedades.getItems().add(rs.getString(2));
                listaNov.add(rs.getInt(1));
            }
            cmbNovedades.getSelectionModel().selectFirst();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }   

        tblDatos.setOnMousePressed(new javafx.event.EventHandler<MouseEvent>() {
            @Override 
            public void handle(MouseEvent event) {
                if (event.isPrimaryButtonDown() && event.getClickCount() == 2) {
                    //System.out.println(tblDatos.getSelectionModel().getSelectedItem());
                    Datos objDatos = new Datos();
                    objDatos = (Datos)tblDatos.getSelectionModel().getSelectedItem();
                    System.out.println(objDatos.getEmpleado());
                }
            }
        });
    }    
 
    public void mostrar() {
        Alert alerta = new Alert(Alert.AlertType.NONE,"Espere por favor...",ButtonType.CLOSE);
        alerta.show();
        
        try {
            Connection conn = BD.Conexion();
            String sql = generarSQL(
                    (int)listaNiv.get(modeloNiv.getSelectedIndex()),
                    (int)listaEmp.get(modeloEmp.getSelectedIndex()),
                    (int)listaNov.get(modeloNov.getSelectedIndex()),
                    datDesde.getValue(),
                    datHasta.getValue()
            );
            ResultSet rs = BD.Ejecutar(conn,sql);

            colId.setCellValueFactory(
                    new PropertyValueFactory<Datos, String>("id"));  
            colDate.setCellValueFactory(
                    new PropertyValueFactory<Datos, String>("date"));  
            colEmpleado.setCellValueFactory(
                    new PropertyValueFactory<Datos, String>("empleado"));  
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
                        String.valueOf(rs.getInt(9)),
                        yyyymmddFormat.format(rs.getTimestamp(1)),
                        rs.getString(10),
                        aDia(yyyymmddFormat.format(rs.getTimestamp(1))),
                        mmddyyyyFormat.format(rs.getTimestamp(1)),
                        aTurno(rs.getInt(2),rs.getInt(3)),
                        aTurno(rs.getInt(4),rs.getInt(5)),
                        aTurno(rs.getInt(6),rs.getInt(7)),
                        getFichadas(rs.getInt(9),yyyymmddFormat.format(rs.getTimestamp(1))),
                        novedades(rs.getInt(9),yyyymmddFormat.format(rs.getTimestamp(1))),
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

    private String generarSQL(int idnivel, int idempleado, int idnovedad, LocalDate desde, LocalDate hasta) {
        String where = " WHERE Fecha BETWEEN #"+desde+"# and #"+hasta+"#";
        
        if (idnivel>0)    where += " AND IdEmpresa="+idnivel;
        if (idempleado>0) where += " AND IdLegajo="+idempleado;
        if (idnovedad>=0) 
            where += " AND Fichadas.CodNovedad="+idnovedad;
        else
            if (idnovedad==(-2))
                where += " AND Fichadas.CodNovedad>0";
        
        System.out.println(where);
        return "SELECT Fecha,DefE1,DefS1,DefE2,DefS2,DefE3,DefS3,Descripcion,Fichadas.IdLegajo,Nombre,CodNovedad"
                    + " FROM Fichadas"
                    + " LEFT OUTER JOIN Novedades ON Fichadas.CodNovedad=Novedades.IdNovedad"
                    + " INNER JOIN Legajos ON Fichadas.IdLegajo=Legajos.IdLegajo"
                    + where
                    + " ORDER BY Nombre,Fecha";
    }
}