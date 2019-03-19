/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fundacionevangelica.reloj;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 *
 * @author jmulki
 */
public class Reloj extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/org/fundacionevangelica/reloj/ventanas/FXMLPrincipal.fxml"));
        
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.setTitle("Análisis de Reloj Biométrico");
        stage.getIcons().add(new Image("/org/fundacionevangelica/reloj/recursos/calendar-clock.png"));
        //stage.setResizable(false);
        //stage.setMaximized(true);
        stage.setOnCloseRequest(new javafx.event.EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event1) {
                Platform.exit();
            }
        });

        stage.show();
        /*
        Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
        
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.setTitle("Novedades de los Empleados");
        stage.getIcons().add(new Image("/org/fundacionevangelica/reloj/recursos/calendar-clock.png"));
        stage.setResizable(false);
        stage.show();
        */
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
