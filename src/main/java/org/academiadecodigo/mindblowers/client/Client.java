package org.academiadecodigo.mindblowers.client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * Developed @ <Academia de Código_>
 * Created by
 * <Code Cadet> Filipe Santos Sá
 */

public class Client extends Application {

    public static void main(String[] args) {

        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        final String VIEW = "/view/game.fxml";

        FXMLLoader loader = new FXMLLoader(getClass().getResource(VIEW));

        Pane root = loader.load();
        ((Controller) loader.getController()).setStage(primaryStage);

        Scene scene = new Scene(root, primaryStage.getWidth(), primaryStage.getHeight());
        scene.getStylesheets().add("/stylesheet/game.css");

        primaryStage.setScene(scene);

        primaryStage.show();
    }

}
