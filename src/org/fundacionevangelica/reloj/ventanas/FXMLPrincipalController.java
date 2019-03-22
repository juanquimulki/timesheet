package org.fundacionevangelica.reloj.ventanas;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import org.fundacionevangelica.reloj.clases.Ventana;

public class FXMLPrincipalController implements Initializable {

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    public void menu_salir() {
        Platform.exit();
    }
    
    public void menu_acercade() {
        String mensaje;
        mensaje = Ventana.ACERCA_DE;
        
        Alert alerta = new Alert(Alert.AlertType.INFORMATION,mensaje,ButtonType.OK);
        alerta.setTitle("Acerca de...");
        alerta.showAndWait();
    }    

    public void menu_empleado() {
        Ventana window = new Ventana();
        if (!Ventana.EMPLEADO_abierta) {
            Ventana.EMPLEADO_abierta = true;
            Stage stage = window.mostrar(Ventana.EMPLEADO,"Reporte Integral");
            
            stage.setOnCloseRequest(new javafx.event.EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent event1) {
                    Ventana.EMPLEADO_abierta = false;
                }
            });
        }
        else {
            String mensaje;
            mensaje = Ventana.VENTANA_ABIERTA;

            Alert alerta = new Alert(Alert.AlertType.NONE,mensaje,ButtonType.OK);
            alerta.showAndWait();
        }
    }
}
