package org.fundacionevangelica.reloj.ventanas;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionModel;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.fundacionevangelica.reloj.clases.Ventana;
import org.fundacionevangelica.reloj.datos.BD;

public class FXMLNovedadController implements Initializable {

    @FXML
    private TextField txtId;
    @FXML
    private TextField txtDate;
    @FXML
    private Label lblFecha;
    @FXML
    private Label lblEmpleado;
    @FXML
    private Label lblActual;
    @FXML
    private Button btnCancelar;
    @FXML
    private Button btnAceptar;
    
    @FXML
    private ComboBox cmbNovedades;
    ObservableList listaNov = FXCollections.observableArrayList();
    SelectionModel modeloNov;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        txtId.setText(FXMLEmpleadoController.datos.getId());
        txtDate.setText(FXMLEmpleadoController.datos.getDate());
        
        lblFecha.setText(FXMLEmpleadoController.datos.getDia()+" "+FXMLEmpleadoController.datos.getFecha());
        lblEmpleado.setText(FXMLEmpleadoController.datos.getEmpleado());
        lblActual.setText(FXMLEmpleadoController.datos.getSistema());
        
        modeloNov = cmbNovedades.getSelectionModel();
        cmbNovedades.getItems().add("(SIN NOVEDADES)");
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
    }

    public void actualizar_novedad() {
        int idlegajo  = Integer.parseInt(txtId.getText());
        String fecha  = txtDate.getText();
        int idnovedad = (int)listaNov.get(modeloNov.getSelectedIndex());
        
        Connection conn = BD.Conexion();
        BD.Actualizar(conn,"UPDATE Fichadas SET CodNovedad="+idnovedad+" WHERE IdLegajo="+idlegajo+" AND Fecha=#"+fecha+"#");
        
        cerrar_ventana();
    }
    
    public void cerrar_ventana() {
        Stage stage = (Stage) btnCancelar.getScene().getWindow();
        Ventana.NOVEDAD_abierta = false;
        stage.close();        
    }
    
}
