package org.fundacionevangelica.reloj.clases;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Ventana {
    
    public static final String EMPLEADO = "/org/fundacionevangelica/reloj/ventanas/FXMLEmpleado.fxml";
    public static boolean EMPLEADO_abierta = false;
    public static final String NOVEDAD = "/org/fundacionevangelica/reloj/ventanas/FXMLNovedad.fxml";
    public static boolean NOVEDAD_abierta = false;
    public static final String CONFIGURACION = "/org/fundacionevangelica/reloj/ventanas/FXMLConfiguracion.fxml";
    public static boolean CONFIGURACION_abierta = false;
    public static final String CONTEO = "/org/fundacionevangelica/reloj/ventanas/FXMLConteo.fxml";
    public static boolean CONTEO_abierta = false;

    public static final String VENTANA_ABIERTA = "Atención! La ventana ya está abierta.";
    public static final String ACERCA_DE = "Ing. Juan M. Mulki A.\n"
                + "Fundación Evangélica\n"
                + "Santiago del Estero, Argentina\n"
                + "2019(r)(c) - Todos los derechos reservados";
    
    public Stage mostrar(String ruta,String titulo) {
        try {
            FXMLLoader fxmlLoader= new FXMLLoader(getClass().getResource(ruta));
            Parent root;
            root = (Parent)fxmlLoader.load();

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.resizableProperty().setValue(Boolean.FALSE);
            stage.setTitle(titulo);
            
            stage.show();
            return stage;
        } catch (IOException ex) {
            ///Error.errorSistema(ex.getMessage(), ex.getClass());
            return null;
        }
    }
    
    public Stage mostrarModal(String ruta,String titulo) {
        try {
            FXMLLoader fxmlLoader= new FXMLLoader(getClass().getResource(ruta));
            Parent root;
            root = (Parent)fxmlLoader.load();

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.resizableProperty().setValue(Boolean.FALSE);
            stage.setTitle(titulo);
            stage.initModality(Modality.APPLICATION_MODAL);
            
            stage.showAndWait();
            return stage;
        } catch (IOException ex) {
            ///Error.errorSistema(ex.getMessage(), ex.getClass());
            return null;
        }
    }

}