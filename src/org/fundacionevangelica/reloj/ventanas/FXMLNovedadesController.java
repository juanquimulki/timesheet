/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.SelectionModel;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.input.KeyCode;
import org.fundacionevangelica.reloj.clases.ComboBoxAutoComplete;
import org.fundacionevangelica.reloj.clases.Empleados;
import org.fundacionevangelica.reloj.clases.Fecha;
import org.fundacionevangelica.reloj.clases.FxUtilTest;
import org.fundacionevangelica.reloj.datos.BD;

/**
 * FXML Controller class
 *
 * @author SYSTEM
 */
public class FXMLNovedadesController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
    @FXML
    private ComboBox cmbEmpleados;
    ObservableList listaEmp = FXCollections.observableArrayList();
    SelectionModel modeloEmp;
    
    @FXML
    private ComboBox cmbNovedades;
    ObservableList listaNov = FXCollections.observableArrayList();
    SelectionModel modeloNov;

    @FXML
    private TextField txtDiaD;
    @FXML
    private TextField txtMesD;
    @FXML
    private TextField txtAnioD;
    
    @FXML
    private TextField txtDiaH;
    @FXML
    private TextField txtMesH;
    @FXML
    private TextField txtAnioH;

    @FXML
    private Button btnGuardar;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        modeloEmp = cmbEmpleados.getSelectionModel();
        cmbEmpleados.getItems().add("Seleccione...");
        listaEmp.add(0);
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

        modeloNov = cmbNovedades.getSelectionModel();
        cmbNovedades.getItems().add("Seleccione...");
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
        
        txtDiaD.setText(Fecha.getDia());
        txtMesD.setText(Fecha.getMes());
        txtAnioD.setText(Fecha.getAnio());
        
        txtDiaH.setText(Fecha.getDia());
        txtMesH.setText(Fecha.getMes());
        txtAnioH.setText(Fecha.getAnio());
        
        txtDiaD.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ENTER) { 
                //System.out.println("TEST");
                txtDiaH.requestFocus();
            }
        });
        
        txtDiaH.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ENTER) { 
                //System.out.println("TEST");
                cmbNovedades.requestFocus();
            }
        });

        btnGuardar.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ENTER) { 
                //System.out.println("TEST");
                guardar();
            }
        });

        cmbEmpleados.setTooltip(new Tooltip());
        new ComboBoxAutoComplete<String>(cmbEmpleados,txtDiaD);
        cmbNovedades.setTooltip(new Tooltip());
        new ComboBoxAutoComplete<String>(cmbNovedades,btnGuardar);
        //FxUtilTest.autoCompleteComboBoxPlus(cmbEmpleados, (typedText, itemToCompare) -> itemToCompare.getName().toLowerCase().contains(typedText.toLowerCase()) || itemToCompare.getAge().toString().equals(typedText));
    }    
    
    public void guardar() {
            Alert alerta = new Alert(Alert.AlertType.NONE,"La novedad ha sido guardada!",ButtonType.OK);
            alerta.showAndWait();        
    }
}
