package fr.insa.gautier.projet_info_2;


import java.util.ArrayList;
import java.util.Arrays;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;


/**
 * JavaFX App
 */
public class App extends Application {

    @Override
    public void start(Stage stage) {
        var javaVersion = SystemInfo.javaVersion();
        var javafxVersion = SystemInfo.javafxVersion();
        
        // On recupere du fichier tous les revetements

        
        var scene = new Scene(new MainPaine(),800,800);
        stage.setTitle("projet info 2.0 dolby digital stereo laser 2024");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
    


}