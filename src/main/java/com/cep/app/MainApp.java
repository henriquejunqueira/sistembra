package com.cep.app;

import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class MainApp extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/loginView.fxml"));
        
        Scene scene = new Scene(root);
        scene.getStylesheets().add("/styles/Styles.css");
        
        Image applicationIcon = new Image(getClass().getResourceAsStream("/icons/Shopping cart.png"));
        stage.getIcons().add(applicationIcon);
        
        stage.setTitle("Sistema Vendas");
        //stage.setMaximized(true);
        stage.setScene(scene);
        stage.show();
        
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                event.consume();
                System.out.println("Saindo do SistemBra...");
                stage.close();
                Platform.exit();
                System.exit(0);
            }
        });
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}
