package org.fundacionevangelica.reloj.ventanas;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
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
import org.fundacionevangelica.reloj.clases.Conteo;
import org.fundacionevangelica.reloj.clases.Empleados;
import org.fundacionevangelica.reloj.clases.Fecha;
import org.fundacionevangelica.reloj.datos.BD;

public class FXMLConteoController implements Initializable {

    @FXML
    private DatePicker datDesde;
    @FXML
    private DatePicker datHasta;

    @FXML
    private ComboBox cmbEmpleados;
    ObservableList listaEmp = FXCollections.observableArrayList();
    SelectionModel modeloEmp;
    
    @FXML
    private TableView tblConteo;
    @FXML
    private TableColumn colNovedad;
    @FXML
    private TableColumn colCantidad;
    ObservableList conteoTabla = FXCollections.observableArrayList();    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        datDesde.setValue(Fecha.getDesde());
        datHasta.setValue(Fecha.getHasta());

        modeloEmp = cmbEmpleados.getSelectionModel();
        //cmbEmpleados.getItems().add("(Todos...)");
        //listaEmp.add(0);
        try {
            ResultSet rs = Empleados.getEmpleados();
            while (rs.next()) {
                cmbEmpleados.getItems().add(rs.getString(2));
                listaEmp.add(rs.getInt(1));
            }
            cmbEmpleados.getSelectionModel().selectFirst();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        modeloEmp.select(0);
    }    
    
    public void mostrar() {
        Alert alerta = new Alert(Alert.AlertType.NONE,"Espere por favor...",ButtonType.CLOSE);
        alerta.show();
        
        LocalDate desde = datDesde.getValue();
        LocalDate hasta = datHasta.getValue();
        int idlegajo = (int)listaEmp.get(modeloEmp.getSelectedIndex());
        
        try {
            Connection conn = BD.Conexion();
            String sql = "SELECT Descripcion,COUNT(Fecha) AS Cantidad" +
                         " FROM Fichadas" +
                         " LEFT OUTER JOIN Novedades on Fichadas.CodNovedad=Novedades.IdNovedad" +
                         " WHERE IdLegajo="+idlegajo+" AND Fecha BETWEEN #"+desde+"# AND #"+hasta+"# AND Descripcion<>''" +
                         " GROUP BY Descripcion ORDER BY Descripcion;";
            ResultSet rs = BD.Ejecutar(conn,sql);

            colNovedad.setCellValueFactory(
                    new PropertyValueFactory<Conteo, String>("novedad"));  
            colCantidad.setCellValueFactory(
                    new PropertyValueFactory<Conteo, String>("cantidad"));  
            conteoTabla.clear();
        
            while (rs.next()) {
                Conteo objConteo = new Conteo(
                        rs.getString(1),
                        rs.getString(2)
                );
                conteoTabla.add(objConteo);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } 
        tblConteo.setItems(conteoTabla);
        alerta.close();
    }
}
