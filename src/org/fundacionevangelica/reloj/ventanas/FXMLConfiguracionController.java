package org.fundacionevangelica.reloj.ventanas;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.fundacionevangelica.reloj.clases.Ventana;
import org.fundacionevangelica.reloj.datos.Propiedades;

public class FXMLConfiguracionController implements Initializable {
    
    @FXML
    private TextField txtBase;
    @FXML
    private TextField txtClave;
    @FXML
    private TextField txtTardanza;
    @FXML
    private TextField txtExcesiva;
    @FXML
    private TextField txtTempranza;
    @FXML
    private ScrollBar scrTardanza;
    @FXML
    private ScrollBar scrExcesiva;
    @FXML
    private ScrollBar scrTempranza;
    @FXML
    private Button btnCancelar;
    @FXML
    private Button btnGuardar;
    
    int tardanza,excesiva,tempranza;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        scrTardanza.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> ov,
                Number old_val, Number new_val) {
                    txtTardanza.setText(String.valueOf((int)scrTardanza.getValue()));
            }
        });        
        scrExcesiva.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> ov,
                Number old_val, Number new_val) {
                    txtExcesiva.setText(String.valueOf((int)scrExcesiva.getValue()));
            }
        });        
        scrTempranza.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> ov,
                Number old_val, Number new_val) {
                    txtTempranza.setText(String.valueOf((int)scrTempranza.getValue()));
            }
        });

        propiedades();
    }

    public void archivo() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Buscar Base de Datos");

        // Agregar filtros para facilitar la busqueda
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("BD Access", "*.mdb")
        );

        // Obtener la imagen seleccionada
        try {
            File imgFile = fileChooser.showOpenDialog(null);
            txtBase.setText(imgFile.getAbsolutePath());
        } catch (Exception e) {
             System.out.println(e.getMessage());
        }
    }
    
    public void propiedades() {
        Propiedades p = new Propiedades();

        txtBase.setText(p.getBd());
        txtClave.setText(p.getPass());

        txtTardanza.setText(String.valueOf(p.getTardanza()));
        txtExcesiva.setText(String.valueOf(p.getExcesiva()));
        txtTempranza.setText(String.valueOf(p.getTempranza()));

        scrTardanza.setValue(p.getTardanza());
        scrExcesiva.setValue(p.getExcesiva());
        scrTempranza.setValue(p.getTempranza());
    }
    
    /*public void crear_archivo() {
        try {
            String ruta = "config.properties";
            String contenido = "#Propiedades de la aplicacion\n"
                    + "bd=C:/Program Files (x86)/C.In.Ti.A. Version Base/Cintia.mdb\n"
                    + "pass=ngtayeXmr?h\n"
                    + "tardanza=10\n"
                    + "excesiva=15\n"
                    + "tempranza=10";
            File file = new File(ruta);

            if (!file.exists()) {
                file.createNewFile();
            }
            FileWriter fw = new FileWriter(file);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(contenido);
            bw.close();

            propiedades();
        } catch (Exception e) {
            System.out.println("archivo no creado");
        }        
    }*/
    
    public void cancelar() {
        Stage stage = (Stage) btnCancelar.getScene().getWindow(); 
        stage.close(); 
        Ventana.CONFIGURACION_abierta = false;
    }

    public void guardar() {
        String ruta = txtBase.getText();
        ruta = ruta.replace("\\", "/");
        
        Properties p = new Properties();
        p.setProperty("bd", ruta);
        p.setProperty("pass", txtClave.getText());
        p.setProperty("tardanza", txtTardanza.getText());
        p.setProperty("excesiva", txtExcesiva.getText());
        p.setProperty("tempranza", txtTempranza.getText());
        try {
            p.store(new FileWriter("config.properties"),"Propiedades de la aplicacion");
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        
        Stage stage = (Stage) btnGuardar.getScene().getWindow(); 
        stage.close(); 
        Ventana.CONFIGURACION_abierta = false;
    }
}
