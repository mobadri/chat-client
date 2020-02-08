package com.chat.client.view.client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/templates/user/startPage.fxml"));
        Parent root = loader.load();
        primaryStage.initStyle(StageStyle.TRANSPARENT);
        Scene scene = new Scene(root);
        startpageController startpageController = loader.getController();
        System.out.println(primaryStage);
        startpageController.setStage(primaryStage);

        scene.setFill(Color.TRANSPARENT);
        primaryStage.setScene(scene);

        primaryStage.setMinHeight(900);
        primaryStage.setMinWidth(450);
        primaryStage.show();

    }


    public static void main(String[] args) {

        launch(args);
    }
}
