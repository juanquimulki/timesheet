package org.fundacionevangelica.reloj;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class Reloj extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/org/fundacionevangelica/reloj/ventanas/FXMLPrincipal.fxml"));
        
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.setTitle("Análisis de Reloj Biométrico");
        stage.getIcons().add(new Image("/org/fundacionevangelica/reloj/recursos/calendar-clock.png"));
        //stage.setResizable(false);
        stage.setMaximized(true);
        stage.setOnCloseRequest(new javafx.event.EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event1) {
                Platform.exit();
            }
        });

        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}