package com.chat.client.view.client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {

    private double xOffset;
    private double yOffset;

    @Override
    public void start(Stage primaryStage) throws Exception{

        Parent root = FXMLLoader.load(getClass().getResource("/templates/login-view.fxml"));
        primaryStage.initStyle(StageStyle.TRANSPARENT);
        Scene scene = new Scene(root);

        scene.setFill(Color.TRANSPARENT);
        primaryStage.setScene(scene);

        root.setOnMousePressed((e) -> {

            xOffset = primaryStage.getX() - e.getScreenX();
            yOffset = primaryStage.getX() - e.getScreenY();
        });

        root.setOnMouseDragged((e) -> {

            primaryStage.setX(xOffset + e.getScreenX());
            primaryStage.setY(yOffset + e.getScreenY());
        });

        primaryStage.setMinHeight(900);
        primaryStage.setMinWidth(450);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
